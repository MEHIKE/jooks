package ee.mehike.haanja100.data;

//import ee.mehike.haanja100.data.BudgetTable.BudgetsColumns;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public final class YearTable {

	// Database table
	public static final String TABLE_YEAR = "year";
	
	public static class YearColumns implements BaseColumns {
		//public static final String ID = "_id";
		
		//aasta - id, võistlus_id, aasta,  best_ajad(eesmärgid) võivad iga aasta olla erinevad
		public static final String RACE_ID = "race_id";
		public static final String YEAR = "year";
		public static final String DELETED = "deleted";

		public static final String BEST_TIME1 = "best_time1";
		public static final String BEST_TIME2 = "best_time2";
		public static final String BEST_TIME3 = "best_time3";
	}

	// Database creation SQL statement
	private static final String YEAR_CREATE = "create table " 
			+ TABLE_YEAR
			+ "(" 
			+ BaseColumns._ID + " integer primary key autoincrement, " 
			+ YearColumns.RACE_ID + " integer," 
			+ YearColumns.YEAR + " integer,"
			+ YearColumns.DELETED + " integer,"
			+ YearColumns.BEST_TIME1 + " integer,"
			+ YearColumns.BEST_TIME2 + " integer,"
			+ YearColumns.BEST_TIME3 + " integer"
			
			/*+ HaanjaColumns.SHOP_ID + " integer NOT NULL DEFAULT 0,"
			+ HaanjaColumns.MAP_POINTS + " text,"
			+ HaanjaColumns.ADDRESS + " text,"
			+ HaanjaColumns.PHONES + " text,"
			+ HaanjaColumns.MAILS + " text,"
			
			+ HaanjaColumns.REMOTE_ID + " integer NOT NULL DEFAULT 0,"
			+ HaanjaColumns.SYNCED + " integer,"
			+ HaanjaColumns.LAST_SYNC_TIME + " integer"*/

			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(YearTable.YEAR_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(YearTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + YearTable.TABLE_YEAR);
		YearTable.onCreate(database);
	}
} 
