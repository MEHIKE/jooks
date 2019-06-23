package ee.mehike.haanja100.data;

import java.util.List;

import ee.mehike.haanja100.data.HaanjaTable.HaanjaColumns;
import ee.mehike.haanja100.data.dao.HaanjaDao;
import ee.mehike.haanja100.model.Haanja;
import ee.mehike.haanja100.util.Constants;
import ee.mehike.haanja100.util.DataConstants;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.util.Log;

/**
 * Android DataManagerImpl to encapsulate SQL and DB details.
 * Includes SQLiteOpenHelper, and uses Dao objects
 * to create/update/delete and otherwise manipulate data.
 *
 * @author rr
 *
 */
public class DataManagerImpl implements DataManager {

   private Context context;

   private SQLiteDatabase db;

   //private CategoryDao categoryDao;
   private HaanjaDao haanjaDao;

   public DataManagerImpl(Context context) {

      this.context = context;

      SQLiteOpenHelper openHelper = new HaanjaDatabaseHelper(this.context);
      db = openHelper.getWritableDatabase();
      Log.i(Constants.LOG_TAG, "DataManagerImpl created, db open status: " + db.isOpen());

      //categoryDao = new CategoryDao(db);
      haanjaDao = new HaanjaDao(db);
   }

   public SQLiteDatabase getDb() {
      return db;
   }

   private void openDb() {
      if (!db.isOpen()) {
         db = SQLiteDatabase.openDatabase(DataConstants.DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
         // since we pass db into DAO, have to recreate DAO if db is re-opened
         //categoryDao = new CategoryDao(db);
         haanjaDao = new HaanjaDao(db);
      }
   }

   /*@Override
   public Haanja buildHaanjaFromCursor(Cursor cursor) {
	   Haanja haanja = haanjaDao.buildHaanjaFromCursor(cursor);
	   return haanja;
   }*/
   
   private void closeDb() {
      if (db.isOpen()) {
         db.close();
      }
   }

   private void resetDb() {
      Log.i(Constants.LOG_TAG, "Resetting database connection (close and re-open).");
      closeDb();
      SystemClock.sleep(500);
      openDb();
   }

   //
   // "Manager" data methods that wrap DAOs
   //
   // this lets us encapsulate usage of DAOs 
   // we only expose methods app is actually using, and we can combine DAOs, with logic in one place
   //  
   // title
   @Override
   public Haanja getHaanja(long haanjaId) {
      Haanja haanja = haanjaDao.get(haanjaId);
      if (haanja != null) {
         //title.getCategories().addAll(movieCategoryDao.getCategories(title.getId()));
      }
      return haanja;
   }

   
   @Override
   public List<Haanja> getHaanjaHeaders() {
      // these movies don't have categories, but they're really used as "headers" anyway, it's ok
      return haanjaDao.getAll();
   }

   @Override
   public List<Haanja> getHaanjaHeaders(long year) {
      // these movies don't have categories, but they're really used as "headers" anyway, it's ok
      return haanjaDao.getAll(year);
   }
   
   @Override
   public Haanja findHaanja(String name) {
      Haanja haanja = haanjaDao.find(name);
      if (haanja != null) {
         //title.getCategories().addAll(movieCategoryDao.getCategories(movie.getId()));
      }
      return haanja;
   }


   @Override
   public Haanja findHaanja(String name, long year) {
      Haanja haanja = haanjaDao.find(name, year);
      if (haanja != null) {
         //title.getCategories().addAll(movieCategoryDao.getCategories(movie.getId()));
      }
      return haanja;
   }

   
   @Override
   public long saveHaanja(Haanja haanja) {
      // NOTE could wrap entity manip functions in DataManagerImpl, make "manager" for each entity
      // here though, to keep it simpler, we use the DAOs directly (even when multiple are involved)
      long haanjaId = 0L;

      // put it in a transaction, since we're touching multiple tables
      try {
         db.beginTransaction();

         // first save movie                                 
         haanjaId = haanjaDao.save(haanja);

         // second, make sure categories exist, and save movie/category association
         // (this makes multiple queries, but usually not many cats, could just save and catch exception too, but that's ugly)
         /*if (haanja.getHaanja().size() > 0) {
            for (Category c : title.getItems()) {
               long catId = 0L;
               Category dbCat = categoryDao.find(c.getName());
               if (dbCat == null) {
                  catId = categoryDao.save(c);
               } else {
                  catId = dbCat.getId();
               }
               MovieCategoryKey mcKey = new MovieCategoryKey(movieId, catId);
               if (!movieCategoryDao.exists(mcKey)) {
                  movieCategoryDao.save(mcKey);
               }
		}*/

         db.setTransactionSuccessful();
      } catch (SQLException e) {

         Log.e(Constants.LOG_TAG, "Error saving haanja (transaction rolled back)", e);
         haanjaId = 0L;
      } finally {
         // an "alias" for commit
         db.endTransaction();
      }

      return haanjaId;
   }


   
   @Override
   public boolean deleteHaanja(long haanjaId) {
      boolean result = false;
      // NOTE switch this order around to see constraint error (foreign keys work)
      try {
         db.beginTransaction();
         // make sure to use getMovie and not movieDao directly, categories need to be included
         Haanja haanja = getHaanja(haanjaId);
         if (haanja != null) {
/*            for (Category c : title.getItems()) {
               movieCategoryDao.delete(new MovieCategoryKey(title.getId(), c.getId()));
            }
*/            haanjaDao.delete(haanja);
         }
         db.setTransactionSuccessful();
         result = true;
      } catch (SQLException e) {
         Log.e(Constants.LOG_TAG, "Error deleting haanja (transaction rolled back)", e);
      } finally {
         db.endTransaction();
      }
      return result;
   }


   @Override
   public Cursor getHaanjaCursor() {
      // note that query MUST have a column named _id
      return db.rawQuery("select " + HaanjaColumns._ID + ", " + HaanjaColumns.SPLIT+ ", " + HaanjaColumns.TIME + ", " + 
      HaanjaColumns.DELETED + ", " + HaanjaColumns.START_TIME + ", " + HaanjaColumns.END_TIME +
      ", " + HaanjaColumns.AVG_PACE+ ", " + HaanjaColumns.LAP_PACE+ ", " + HaanjaColumns.YEAR
               + " from " + HaanjaTable.TABLE_HAANJA+" ORDER BY "+HaanjaColumns.SPLIT, null);
   }
   
   
   @Override
   public Cursor getHaanjaCursor(long year) {
      // note that query MUST have a column named _id
      return db.rawQuery("select " + HaanjaColumns._ID + ", " + HaanjaColumns.SPLIT+ ", " + HaanjaColumns.TIME + ", " + 
      HaanjaColumns.DELETED + ", " + HaanjaColumns.START_TIME + ", " + HaanjaColumns.END_TIME +
      ", " + HaanjaColumns.AVG_PACE+ ", " + HaanjaColumns.LAP_PACE+ ", " + HaanjaColumns.YEAR
               + " from " + HaanjaTable.TABLE_HAANJA+" where "+ee.mehike.haanja100.provider.HaanjaContent.Haanja.HaanjaColumns.YEAR +
               " = "+String.valueOf(year)+" ORDER BY "+HaanjaColumns.SPLIT, null);
   }

   /*@Override
   public Item getItemFromCursor(Cursor cursor) {
      return itemDao.buildItemFromCursor(cursor);
   }*/

   
/*   // category
   @Override
   public Category getCategory(long categoryId) {
      return categoryDao.get(categoryId);
   }

   @Override
   public List<Category> getAllCategories() {
      return categoryDao.getAll();
   }

   @Override
   public Category findCategory(String name) {
      return categoryDao.find(name);
   }

   @Override
   public long saveCategory(Category category) {
      return categoryDao.save(category);
   }

   @Override
   public void deleteCategory(Category category) {
      categoryDao.delete(category);
   }
*/
}
