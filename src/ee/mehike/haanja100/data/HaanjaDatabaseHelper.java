package ee.mehike.haanja100.data;


import ee.mehike.haanja100.data.dao.HaanjaDao;
import ee.mehike.haanja100.util.Constants;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class HaanjaDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "haanja.db";
	private static final int DATABASE_VERSION = 5;
	private Context context;
	   
	public HaanjaDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	
	   @Override
	   public void onOpen(final SQLiteDatabase db) {
	      super.onOpen(db);
	      if (!db.isReadOnly()) {
	         // versions of SQLite older than 3.6.19 don't support foreign keys
	         // and neither do any version compiled with SQLITE_OMIT_FOREIGN_KEY
	         // http://www.sqlite.org/foreignkeys.html#fk_enable
	         // 
	         // make sure foreign key support is turned on if it's there (should be already, just a double-checker)          
	         db.execSQL("PRAGMA foreign_keys=ON;");

	         // then we check to make sure they're on 
	         // (if this returns no data they aren't even available, so we shouldn't even TRY to use them)
	         Cursor c = db.rawQuery("PRAGMA foreign_keys", null);
	         if (c.moveToFirst()) {
	            int result = c.getInt(0);
	            Log.i(Constants.LOG_TAG, "SQLite foreign key support (1 is on, 0 is off): " + result);
	         } else {
	            // could use this approach in onCreate, and not rely on foreign keys it not available, etc.
	            Log.i(Constants.LOG_TAG, "SQLite foreign key support NOT AVAILABLE");
	            // if you had to here you could fall back to triggers
	         }
	         if (!c.isClosed()) {
	            c.close();
	         }
	      }
	   } 

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		HaanjaTable.onCreate(database);
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
	      Log.i(Constants.LOG_TAG, "SQLiteOpenHelper onUpgrade - oldVersion:" + oldVersion + " newVersion:"
                   + newVersion); 
		HaanjaTable.onUpgrade(database, oldVersion, newVersion);
	}
} 