package ee.mehike.haanja100.data;

import java.util.List;

import android.database.Cursor;

import ee.mehike.haanja100.model.Haanja;

/**
 * Android DataManager interface used to define data operations.
 * 
 * @author ccollins
 *
 */
public interface DataManager {  
   
   // Title
   public Haanja getHaanja(long haanjaId);
   public List<Haanja> getHaanjaHeaders();
   public List<Haanja> getHaanjaHeaders(long year);
   public Haanja findHaanja(String name);
   public Haanja findHaanja(String name, long year);
   public long saveHaanja(Haanja haanja);
   public boolean deleteHaanja(long haanjaId);
   // optional -- used for CursorAdapter
   public Cursor getHaanjaCursor();
   public Cursor getHaanjaCursor(long year);

   
/*   // category
   public Category getCategory(long categoryId);

   public List<Category> getAllCategories();

   public Category findCategory(String name);

   public long saveCategory(Category category);

   public void deleteCategory(Category category);   
*/
}