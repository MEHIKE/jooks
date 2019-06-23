package ee.mehike.haanja100.provider;

import java.util.Arrays;
import java.util.HashSet;

import ee.mehike.haanja100.HaanjaApp;
import ee.mehike.haanja100.data.HaanjaDatabaseHelper;
import ee.mehike.haanja100.data.YearTable;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;


public class YearContentProvider extends ContentProvider {

	// database
	//private ShopDatabaseHelper database;
	private SQLiteDatabase db;

	// Used for the UriMacher
	private static final int YEARS = 10;
	private static final int YEAR_ID = 20;


	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/years";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/year";

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(YearContent.AUTHORITY, YearContent.BASE_PATH, YEARS);
		sURIMatcher.addURI(YearContent.AUTHORITY, YearContent.BASE_PATH + "/#", YEAR_ID);
	}

	//private ShoppingApp app; 
	
	@Override
	public boolean onCreate() {
		//app = (ShoppingApp) getContext().getApplication();
		//if (app.getDatabase()!=null) {
		HaanjaDatabaseHelper database = new HaanjaDatabaseHelper(getContext());
			db = database.getWritableDatabase();
		//} else 
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		// Check if the caller has requested a column which does not exists
		
		checkColumns(projection);

		// Uisng SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		// Set the table
		queryBuilder.setTables(YearTable.TABLE_YEAR);

		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case YEARS:
			break;
		case YEAR_ID:
			// Adding the ID to the original query
			queryBuilder.appendWhere(YearTable.YearColumns._ID + "="
					+ uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		//SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);
		// Make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		cursor.moveToFirst();
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		//SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		//values.put(HaanjaContent.Haanja.HaanjaColumns.TIME, System.currentTimeMillis());
		long id = 0;
		switch (uriType) {
		case YEARS:
			id = db.insert(YearTable.TABLE_YEAR, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(HaanjaContent.BASE_PATH + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		//SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case YEARS:
			rowsDeleted = db.delete(YearTable.TABLE_YEAR, selection,
					selectionArgs);
			break;
		case YEAR_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = db.delete(YearTable.TABLE_YEAR,
						YearTable.YearColumns._ID + "=" + id, 
						null);
			} else {
				rowsDeleted = db.delete(YearTable.TABLE_YEAR,
						YearTable.YearColumns._ID + "=" + id 
						+ " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int uriType = sURIMatcher.match(uri);
		//values.put(HaanjaContent.Haanja.HaanjaColumns.TIME, System.currentTimeMillis());
		//SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
		case YEARS:
			rowsUpdated = db.update(YearTable.TABLE_YEAR, 
					values, 
					selection,
					selectionArgs);
			break;
		case YEAR_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = db.update(YearTable.TABLE_YEAR, 
						values,
						YearTable.YearColumns._ID + "=" + id, 
						null);
			} else {
				rowsUpdated = db.update(YearTable.TABLE_YEAR, 
						values,
						YearTable.YearColumns._ID + "=" + id 
						+ " and " 
						+ selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection) {
		/*String[] available = { TitleTable.TitleColumns.DESCRIPTION,
				TitleTable.TitleColumns.NAME, TitleTable.TitleColumns.REMINDER,
				TitleTable.TitleColumns.TIME, TitleTable.TitleColumns.ID,
				TitleTable.TitleColumns.DELETED, TitleTable.TitleColumns.CHECKED};*/
		//HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
		HashSet<String> projectionCols = new HashSet<String>(Arrays.asList(projection));
		if (projection != null) {
				//availableColumns = new HashSet<String>(Arrays.asList(projection));
				if (!YearContent.Year.YearColumns.projectionMap.keySet().containsAll(projectionCols)) {
				throw new IllegalArgumentException("Unrecognized column(s) in projection");
				
				
			//HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			//HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

			// Check if all columns which are requested are available
			/*if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Unknown columns in projection");
			}*/
				}
		}
	}


} 