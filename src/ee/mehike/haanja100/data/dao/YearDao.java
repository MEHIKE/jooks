package ee.mehike.haanja100.data.dao;

import java.util.ArrayList;
import java.util.List;

import ee.mehike.haanja100.data.YearTable;
import ee.mehike.haanja100.data.YearTable.YearColumns;
import ee.mehike.haanja100.model.Year;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;


public class YearDao implements Dao<Year> {
	
	   /*private long race_id;
	   private long year;
	   private boolean delete;
	   private long best_tim1;
	   private long best_tim2;
	   private long best_tim3;*/
	   
	   private static final String INSERT =
	            "insert into " + YearTable.TABLE_YEAR + "(" + YearColumns._ID + ", "
	            		+ YearColumns.RACE_ID + ", " 
	            		+ YearColumns.YEAR + ", "
	                     + YearColumns.DELETED + ", "
	                     + YearColumns.BEST_TIME1 + ", "
	                     + YearColumns.BEST_TIME2 + ", "
	                     + YearColumns.BEST_TIME3
	                     
	                 	 /*+ HaanjaColumns.MAP_POINTS + ", "
	                	 + HaanjaColumns.ADDRESS + ", "
	                	 + HaanjaColumns.PHONES + ", "
	                	 + HaanjaColumns.MAILS + ", "
	                	 
		         			+ HaanjaColumns.REMOTE_ID + ", "
		        			+ HaanjaColumns.SYNCED + ", "
		        			+ HaanjaColumns.LAST_SYNC_TIME*/

	                     + ") values (?, ?, ?, ?, ?, ?, ?)";

	   private SQLiteDatabase db;
	   private SQLiteStatement insertStatement;

	   public YearDao(SQLiteDatabase db) {
	      this.db = db;
	      insertStatement = db.compileStatement(YearDao.INSERT);
	   } 
	   
	   @Override
	   public long save(Year entity) {
	      insertStatement.clearBindings();
	      insertStatement.bindLong(1, entity.getId());
	      insertStatement.bindLong(2, entity.getRace_id());
	      insertStatement.bindLong(3, entity.getYear());
	      insertStatement.bindLong(4, entity.isDelete()?1:0);
	      insertStatement.bindLong(5, entity.getBest_tim1());
	      insertStatement.bindLong(6, entity.getBest_tim2());
	      insertStatement.bindLong(7, entity.getBest_tim3());
	      return insertStatement.executeInsert();
	   }

	   @Override
	   public void update(Year entity) {
	      final ContentValues values = new ContentValues();
	      values.put(YearColumns._ID, entity.getId());
	      values.put(YearColumns.RACE_ID, entity.getRace_id());
	      values.put(YearColumns.YEAR, entity.getYear());
	      values.put(YearColumns.DELETED, entity.isDelete());
	      values.put(YearColumns.BEST_TIME1, entity.getBest_tim1());
	      values.put(YearColumns.BEST_TIME2, entity.getBest_tim2());
	      values.put(YearColumns.BEST_TIME3, entity.getBest_tim3());

	      db.update(YearTable.TABLE_YEAR, values, BaseColumns._ID + " = ?", new String[] { String
	               .valueOf(entity.getId()) });
	   }

	   @Override
	   public void delete(Year entity) {
	      if (entity.getId() > 0) {
	         db.delete(YearTable.TABLE_YEAR, BaseColumns._ID + " = ?", new String[] { String.valueOf(entity.getId()) });
	      }
	   }

	   
	   public void deleteSplit(Year entity) {
	      if (entity.getYear() > 0 && entity.getRace_id() > 0) {
	         db.delete(YearTable.TABLE_YEAR, YearColumns.YEAR + " = ? and "+YearColumns.RACE_ID + " = ?", new String[] { String.valueOf(entity.getYear()), String.valueOf(entity.getRace_id()) });
	      }
	   }

	   
	   // get field slot error, show how it works by removing a column in the columns array here)
	   @Override
	   public Year get(long id) {
	      Year haanja = null;
	      Cursor c =
	               db.query(YearTable.TABLE_YEAR, new String[] { BaseColumns._ID,
	                        YearColumns.RACE_ID, YearColumns.YEAR, YearColumns.DELETED , 
	                        YearColumns.BEST_TIME1 , YearColumns.BEST_TIME2,
	                        YearColumns.BEST_TIME3 , },
	                        BaseColumns._ID + " = ?", new String[] { String.valueOf(id) }, null, null, null, "1");
	      if (c.moveToFirst()) {
	         haanja = this.buildYearFromCursor(c);
	      }
	      if (!c.isClosed()) {
	         c.close();
	      }
	      return haanja;
	   }

	   @Override
	   public List<Year> getAll() {
	      List<Year> list = new ArrayList<Year>();
	      Cursor c =
	               db.query(YearTable.TABLE_YEAR, new String[] { BaseColumns._ID, 
	                        YearColumns.RACE_ID, YearColumns.YEAR, YearColumns.DELETED, 
	                        YearColumns.BEST_TIME1, YearColumns.BEST_TIME2,
	                        YearColumns.BEST_TIME3 },
	                null, null, null, null, YearColumns.YEAR, null);
	      if (c.moveToFirst()) {
	         do {
	            Year haanja = this.buildYearFromCursor(c);
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
	   public List<Year> getAll(long year) {
	      List<Year> list = new ArrayList<Year>();
	      Cursor c =
	               db.query(YearTable.TABLE_YEAR, new String[] { BaseColumns._ID, 
	                        YearColumns.RACE_ID, YearColumns.YEAR, YearColumns.DELETED, 
	                        YearColumns.BEST_TIME1, YearColumns.BEST_TIME2,
	                        YearColumns.BEST_TIME3 },
	                        YearColumns.YEAR + " = ?", new String[] { String.valueOf(year) }, null, null, YearColumns.RACE_ID, null);
	      if (c.moveToFirst()) {
	         do {
	            Year haanja = this.buildYearFromCursor(c);
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

	   private Year buildYearFromCursor(Cursor c) {
		      Year haanja = null;
		      if (c != null) {
		         haanja = new Year();
		         haanja.setId(c.getLong(0));
		         haanja.setRace_id(c.getLong(1));
		         haanja.setYear(c.getLong(2));
		         haanja.setDelete(c.getLong(3)==1);
		         haanja.setBest_tim1(c.getLong(4));
		         haanja.setBest_tim2(c.getLong(5));
		         haanja.setBest_tim3(c.getLong(6));

		      }
		      return haanja;
		   } 
	   
	   // as an oversimplification our db requires movie names to be unique
	   // in real-life, we'd need to return multiple results here (if found)
	   // and allow the user to select, or make query use other attributes in combination with name
	   // (also note here we expand on the DAO interface definition for just this class)
	   public Year find(String name) {
	      long haanjaId = 0L;
	      String sql = "select _id from " + YearTable.TABLE_YEAR + " where " + 
	      YearColumns.RACE_ID + " = ? limit 1";
	      Cursor c = db.rawQuery(sql, new String[] { String.valueOf(name)});
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
	   public Year find(String name, long year) {
	      long haanjaId = 0L;
	      String sql = "select _id from " + YearTable.TABLE_YEAR + " where " + 
	      YearColumns.RACE_ID + " = ? and "+YearColumns.YEAR+" = ? limit 1";
	      Cursor c = db.rawQuery(sql, new String[] { String.valueOf(name), String.valueOf(year) });
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
