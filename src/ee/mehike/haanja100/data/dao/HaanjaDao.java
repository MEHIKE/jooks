package ee.mehike.haanja100.data.dao;

import java.util.ArrayList;
import java.util.List;

import ee.mehike.haanja100.data.HaanjaTable;
import ee.mehike.haanja100.data.HaanjaTable.HaanjaColumns;
import ee.mehike.haanja100.model.Haanja;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;


public class HaanjaDao implements Dao<Haanja> {
	
	   private static final String INSERT =
	            "insert into " + HaanjaTable.TABLE_HAANJA + "(" + HaanjaColumns._ID + ", "
	                    + HaanjaColumns.SPLIT + ", " 
	            		+ HaanjaColumns.TIME + ", "
	                     + HaanjaColumns.DELETED + ", "
	                     + HaanjaColumns.START_TIME + ", "
	                     + HaanjaColumns.END_TIME + ", "
	                     + HaanjaColumns.AVG_PACE + ", "
	                     + HaanjaColumns.LAP_PACE +  ", " 
	                     + HaanjaColumns.YEAR
	                     
	                 	 /*+ HaanjaColumns.MAP_POINTS + ", "
	                	 + HaanjaColumns.ADDRESS + ", "
	                	 + HaanjaColumns.PHONES + ", "
	                	 + HaanjaColumns.MAILS + ", "
	                	 
		         			+ HaanjaColumns.REMOTE_ID + ", "
		        			+ HaanjaColumns.SYNCED + ", "
		        			+ HaanjaColumns.LAST_SYNC_TIME*/

	                     + ") values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	   private SQLiteDatabase db;
	   private SQLiteStatement insertStatement;

	   public HaanjaDao(SQLiteDatabase db) {
	      this.db = db;
	      insertStatement = db.compileStatement(HaanjaDao.INSERT);
	   } 
	   
	   @Override
	   public long save(Haanja entity) {
	      insertStatement.clearBindings();
	      insertStatement.bindLong(1, entity.getId());
	      insertStatement.bindLong(2, entity.getSplit());
	      insertStatement.bindLong(3, entity.getTime());
	      insertStatement.bindLong(4, entity.isDeleted()?1:0);
	      insertStatement.bindLong(5, entity.getStart_time());
	      insertStatement.bindLong(6, entity.getEnd_time());
	      insertStatement.bindString(7, entity.getAvg_pace());
	      insertStatement.bindString(8, entity.getLap_pace());
	      insertStatement.bindLong(9, entity.getYear());
	      return insertStatement.executeInsert();
	   }

	   @Override
	   public void update(Haanja entity) {
	      final ContentValues values = new ContentValues();
	      values.put(HaanjaColumns._ID, entity.getId());
	      values.put(HaanjaColumns.SPLIT, entity.getSplit());
	      values.put(HaanjaColumns.TIME, entity.getTime());
	      values.put(HaanjaColumns.DELETED, entity.isDeleted());
	      values.put(HaanjaColumns.START_TIME, entity.getStart_time());
	      values.put(HaanjaColumns.END_TIME, entity.getEnd_time());
	      values.put(HaanjaColumns.AVG_PACE, entity.getAvg_pace());
	      values.put(HaanjaColumns.LAP_PACE, entity.getLap_pace());
	      values.put(HaanjaColumns.YEAR, entity.getYear());

	      db.update(HaanjaTable.TABLE_HAANJA, values, BaseColumns._ID + " = ?", new String[] { String
	               .valueOf(entity.getId()) });
	   }

	   @Override
	   public void delete(Haanja entity) {
	      if (entity.getId() > 0) {
	         db.delete(HaanjaTable.TABLE_HAANJA, BaseColumns._ID + " = ?", new String[] { String.valueOf(entity.getId()) });
	      }
	   }

	   
	   public void deleteSplit(Haanja entity) {
	      if (entity.getSplit() > 0) {
	         db.delete(HaanjaTable.TABLE_HAANJA, HaanjaColumns.SPLIT + " = ? and "+HaanjaColumns.YEAR + " = ?", new String[] { String.valueOf(entity.getSplit()), String.valueOf(entity.getYear()) });
	      }
	   }

	   
	   // get field slot error, show how it works by removing a column in the columns array here)
	   @Override
	   public Haanja get(long id) {
	      Haanja haanja = null;
	      Cursor c =
	               db.query(HaanjaTable.TABLE_HAANJA, new String[] { BaseColumns._ID,
	                        HaanjaColumns.SPLIT, HaanjaColumns.TIME, HaanjaColumns.DELETED , 
	                        HaanjaColumns.START_TIME , HaanjaColumns.END_TIME,
	                        HaanjaColumns.AVG_PACE , HaanjaColumns.LAP_PACE, HaanjaColumns.YEAR, },
	                        BaseColumns._ID + " = ?", new String[] { String.valueOf(id) }, null, null, null, "1");
	      if (c.moveToFirst()) {
	         haanja = this.buildHaanjaFromCursor(c);
	      }
	      if (!c.isClosed()) {
	         c.close();
	      }
	      return haanja;
	   }

	   @Override
	   public List<Haanja> getAll() {
	      List<Haanja> list = new ArrayList<Haanja>();
	      Cursor c =
	               db.query(HaanjaTable.TABLE_HAANJA, new String[] { BaseColumns._ID, 
	                        HaanjaColumns.SPLIT, HaanjaColumns.TIME, HaanjaColumns.DELETED, 
	                        HaanjaColumns.START_TIME, HaanjaColumns.END_TIME,
	                        HaanjaColumns.AVG_PACE , HaanjaColumns.LAP_PACE, HaanjaColumns.YEAR },
	                null, null, null, null, HaanjaColumns.SPLIT, null);
	      if (c.moveToFirst()) {
	         do {
	            Haanja haanja = this.buildHaanjaFromCursor(c);
	            if (haanja != null) {
	               list.add(haanja);
	            }
	         } while (c.moveToNext());
	      }
	      if (!c.isClosed()) {
	         c.close();
	      }
	      return list;
	   } 

	   @Override
	   public List<Haanja> getAll(long year) {
	      List<Haanja> list = new ArrayList<Haanja>();
	      Cursor c =
	               db.query(HaanjaTable.TABLE_HAANJA, new String[] { BaseColumns._ID, 
	                        HaanjaColumns.SPLIT, HaanjaColumns.TIME, HaanjaColumns.DELETED, 
	                        HaanjaColumns.START_TIME, HaanjaColumns.END_TIME,
	                        HaanjaColumns.AVG_PACE , HaanjaColumns.LAP_PACE, HaanjaColumns.YEAR },
	                        HaanjaColumns.YEAR + " = ?", new String[] { String.valueOf(year) }, null, null, HaanjaColumns.SPLIT, null);
	      if (c.moveToFirst()) {
	         do {
	            Haanja haanja = this.buildHaanjaFromCursor(c);
	            if (haanja != null) {
	               list.add(haanja);
	            }
	         } while (c.moveToNext());
	      }
	      if (!c.isClosed()) {
	         c.close();
	      }
	      return list;
	   } 

	   private Haanja buildHaanjaFromCursor(Cursor c) {
		      Haanja haanja = null;
		      if (c != null) {
		         haanja = new Haanja();
		         haanja.setId(c.getLong(0));
		         haanja.setSplit(c.getLong(1));
		         haanja.setTime(c.getLong(2));
		         haanja.setDeleted(c.getLong(3)==1);
		         haanja.setStart_time(c.getLong(4));
		         haanja.setEnd_time(c.getLong(5));
		         haanja.setAvg_pace(c.getString(6));
		         haanja.setLap_pace(c.getString(7));
		         haanja.setYear(c.getLong(8));

		      }
		      return haanja;
		   } 
	   
	   // as an oversimplification our db requires movie names to be unique
	   // in real-life, we'd need to return multiple results here (if found)
	   // and allow the user to select, or make query use other attributes in combination with name
	   // (also note here we expand on the DAO interface definition for just this class)
	   public Haanja find(String name) {
	      long haanjaId = 0L;
	      String sql = "select _id from " + HaanjaTable.TABLE_HAANJA + " where " + 
	      HaanjaColumns.SPLIT + " = ? limit 1";
	      Cursor c = db.rawQuery(sql, new String[] { name.toUpperCase() });
	      if (c.moveToFirst()) {
	         haanjaId = c.getLong(0);
	      }
	      if (!c.isClosed()) {
	         c.close();
	      }
	      // we make another query here, which is another trip, 
	      // this is a trade off we accept with such a small amount of data
	      return this.get(haanjaId);
	   }
	   
	   // as an oversimplification our db requires movie names to be unique
	   // in real-life, we'd need to return multiple results here (if found)
	   // and allow the user to select, or make query use other attributes in combination with name
	   // (also note here we expand on the DAO interface definition for just this class)
	   public Haanja find(String name, long year) {
	      long haanjaId = 0L;
	      String sql = "select _id from " + HaanjaTable.TABLE_HAANJA + " where " + 
	      HaanjaColumns.SPLIT + " = ? and "+HaanjaColumns.YEAR+" = ? limit 1";
	      Cursor c = db.rawQuery(sql, new String[] { name.toUpperCase(), String.valueOf(year) });
	      if (c.moveToFirst()) {
	         haanjaId = c.getLong(0);
	      }
	      if (!c.isClosed()) {
	         c.close();
	      }
	      // we make another query here, which is another trip, 
	      // this is a trade off we accept with such a small amount of data
	      return this.get(haanjaId);
	   }
	 
}
