package ee.mehike.haanja100.data.dao;

import java.util.ArrayList;
import java.util.List;

import ee.mehike.haanja100.data.RaceTable;
import ee.mehike.haanja100.data.RaceTable.RaceColumns;
import ee.mehike.haanja100.model.Race;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;


public class RaceDao implements Dao<Race> {
	
	   private static final String INSERT =
	            "insert into " + RaceTable.TABLE_RACE + "(" + RaceColumns._ID + ", "
	                    + RaceColumns.NAME + ", " 
	            		+ RaceColumns.DESCRIPTION + ", "
	                     + RaceColumns.DELETED + ", "
	                     + RaceColumns.SPLITS + ", "
	                     + RaceColumns.RACE_KM
	                     
	                 	 /*+ HaanjaColumns.MAP_POINTS + ", "
	                	 + HaanjaColumns.ADDRESS + ", "
	                	 + HaanjaColumns.PHONES + ", "
	                	 + HaanjaColumns.MAILS + ", "
	                	 
		         			+ HaanjaColumns.REMOTE_ID + ", "
		        			+ HaanjaColumns.SYNCED + ", "
		        			+ HaanjaColumns.LAST_SYNC_TIME*/

	                     + ") values (?, ?, ?, ?, ?, ?)";

	   private SQLiteDatabase db;
	   private SQLiteStatement insertStatement;

	   public RaceDao(SQLiteDatabase db) {
	      this.db = db;
	      insertStatement = db.compileStatement(RaceDao.INSERT);
	   } 
	   
	   @Override
	   public long save(Race entity) {
	      insertStatement.clearBindings();
	      insertStatement.bindLong(1, entity.getId());
	      insertStatement.bindString(2, entity.getName());
	      insertStatement.bindString(3, entity.getDescription());
	      insertStatement.bindLong(4, entity.isDeleted()?1:0);
	      insertStatement.bindLong(5, entity.getSplits());
	      insertStatement.bindDouble(6, entity.getRace_km());
	      return insertStatement.executeInsert();
	   }

	   @Override
	   public void update(Race entity) {
	      final ContentValues values = new ContentValues();
	      values.put(RaceColumns._ID, entity.getId());
	      values.put(RaceColumns.NAME, entity.getName());
	      values.put(RaceColumns.DESCRIPTION, entity.getDescription());
	      values.put(RaceColumns.DELETED, entity.isDeleted());
	      values.put(RaceColumns.SPLITS, entity.getSplits());
	      values.put(RaceColumns.RACE_KM, entity.getRace_km());

	      db.update(RaceTable.TABLE_RACE, values, BaseColumns._ID + " = ?", new String[] { String
	               .valueOf(entity.getId()) });
	   }

	   @Override
	   public void delete(Race entity) {
	      if (entity.getId() > 0) {
	         db.delete(RaceTable.TABLE_RACE, BaseColumns._ID + " = ?", new String[] { String.valueOf(entity.getId()) });
	      }
	   }

	   
/*	   public void deleteSplit(Race entity) {
	      if (entity.getSplit() > 0) {
	         db.delete(HaanjaTable.TABLE_HAANJA, HaanjaColumns.SPLIT + " = ? and "+HaanjaColumns.YEAR + " = ?", new String[] { String.valueOf(entity.getSplit()), String.valueOf(entity.getYear()) });
	      }
	   }
*/
	   
	   // get field slot error, show how it works by removing a column in the columns array here)
	   @Override
	   public Race get(long id) {
	      Race haanja = null;
	      Cursor c =
	               db.query(RaceTable.TABLE_RACE, new String[] { BaseColumns._ID,
	                        RaceColumns.NAME, RaceColumns.DESCRIPTION, RaceColumns.DELETED , 
	                        RaceColumns.SPLITS , RaceColumns.RACE_KM, },
	                        BaseColumns._ID + " = ?", new String[] { String.valueOf(id) }, null, null, null, "1");
	      if (c.moveToFirst()) {
	         haanja = this.buildRaceFromCursor(c);
	      }
	      if (!c.isClosed()) {
	         c.close();
	      }
	      return haanja;
	   }

	   @Override
	   public List<Race> getAll() {
	      List<Race> list = new ArrayList<Race>();
	      Cursor c =
	               db.query(RaceTable.TABLE_RACE, new String[] { BaseColumns._ID, 
	                        RaceColumns.NAME, RaceColumns.DESCRIPTION, RaceColumns.DELETED , 
	                        RaceColumns.SPLITS , RaceColumns.RACE_KM, },
	                null, null, null, null, RaceColumns.NAME, null);
	      if (c.moveToFirst()) {
	         do {
	            Race haanja = this.buildRaceFromCursor(c);
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
	   public List<Race> getAll(long year) {
	      List<Race> list = new ArrayList<Race>();
	      Cursor c =
	               db.query(RaceTable.TABLE_RACE, new String[] { BaseColumns._ID, 
	                        RaceColumns.NAME, RaceColumns.DESCRIPTION, RaceColumns.DELETED , 
	                        RaceColumns.SPLITS , RaceColumns.RACE_KM, },
	                        null, null, null, null, RaceColumns.NAME, null);
	      if (c.moveToFirst()) {
	         do {
	            Race haanja = this.buildRaceFromCursor(c);
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

	   private Race buildRaceFromCursor(Cursor c) {
		      Race haanja = null;
		      if (c != null) {
		         haanja = new Race();
		         haanja.setId(c.getLong(0));
		         haanja.setName(c.getString(1));
		         haanja.setDescription(c.getString(2));
		         haanja.setDeleted(c.getLong(3)==1);
		         haanja.setSplits(c.getLong(4));
		         haanja.setRace_km(c.getDouble(5));
		      }
		      return haanja;
		   } 
	   
	   // as an oversimplification our db requires movie names to be unique
	   // in real-life, we'd need to return multiple results here (if found)
	   // and allow the user to select, or make query use other attributes in combination with name
	   // (also note here we expand on the DAO interface definition for just this class)
	   public Race find(String name) {
	      long haanjaId = 0L;
	      String sql = "select _id from " + RaceTable.TABLE_RACE + " where " + 
	      RaceColumns.NAME + " = ? limit 1";
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
	   public Race find(String name, long year) {
	      long haanjaId = 0L;
	      String sql = "select _id from " + RaceTable.TABLE_RACE + " where " + 
	      RaceColumns.NAME + " = ? and "+RaceColumns._ID+" = ? limit 1";
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
