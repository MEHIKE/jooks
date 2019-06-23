package ee.mehike.haanja100.data;

//import ee.mehike.haanja100.data.BudgetTable.BudgetsColumns;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public final class HaanjaTable {

	// Database table
	public static final String TABLE_HAANJA = "haanja100";
	
	public static class HaanjaColumns implements BaseColumns {
		//public static final String ID = "_id";
		public static final String SPLIT = "split";
		public static final String TIME = "time";
		public static final String DELETED = "deleted";

		public static final String START_TIME = "start_time";
		public static final String END_TIME = "end_time";
		
		public static final String AVG_PACE = "avg_pace";
		public static final String LAP_PACE = "lap_pace";
		
		public static final String YEAR = "year";
		
		/*public static final String SHOP_ID="shop_id";
		public static final String MAP_POINTS="map_points";
		public static final String ADDRESS="address";  //esimese hooga pole vaja
		public static final String PHONES="phones"; //esimese hooga pole vaja
		public static final String MAILS="mails"; //esimese hooga pole vaja
		
		public static final String REMOTE_ID="remote_id"; 
		public static final String SYNCED="synced";
		public static final String LAST_SYNC_TIME="last_sync_time";
		 */

	}

	// Database creation SQL statement
	private static final String HAANJA100_CREATE = "create table " 
			+ TABLE_HAANJA
			+ "(" 
			+ BaseColumns._ID + " integer primary key autoincrement, " 
			+ HaanjaColumns.SPLIT + " integer," 
			+ HaanjaColumns.TIME + " integer,"
			+ HaanjaColumns.DELETED + " integer,"
			+ HaanjaColumns.START_TIME + " integer,"
			+ HaanjaColumns.END_TIME + " integer,"
			+ HaanjaColumns.AVG_PACE + " integer,"
			+ HaanjaColumns.LAP_PACE + " integer,"
			+ HaanjaColumns.YEAR + " integer"
			
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
		database.execSQL(HaanjaTable.HAANJA100_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(HaanjaTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + HaanjaTable.TABLE_HAANJA);
		HaanjaTable.onCreate(database);
	}
} 
