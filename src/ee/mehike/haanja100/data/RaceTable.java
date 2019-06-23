package ee.mehike.haanja100.data;

//import ee.mehike.haanja100.data.BudgetTable.BudgetsColumns;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public final class RaceTable {

	// Database table
	public static final String TABLE_RACE = "race";
	
	public static class RaceColumns implements BaseColumns {
		//public static final String ID = "_id";
		
	 	//võistlus - id, nimi, ringid, pikkus, delete, description
		
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
		public static final String DELETED = "deleted";

		public static final String SPLITS = "splits";
		public static final String RACE_KM = "race_km";

	}

	// Database creation SQL statement
	private static final String RACE_CREATE = "create table " 
			+ TABLE_RACE
			+ "(" 
			+ BaseColumns._ID + " integer primary key autoincrement, " 
			+ RaceColumns.NAME + " text," 
			+ RaceColumns.DESCRIPTION + " text,"
			+ RaceColumns.DELETED + " integer,"
			+ RaceColumns.SPLITS + " integer,"
			+ RaceColumns.RACE_KM + " double"
			
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
		database.execSQL(RaceTable.RACE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(RaceTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + RaceTable.TABLE_RACE);
		RaceTable.onCreate(database);
	}
} 
