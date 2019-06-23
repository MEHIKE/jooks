package ee.mehike.haanja100.provider;

import java.util.HashMap;

import ee.mehike.haanja100.data.HaanjaTable;
import ee.mehike.haanja100.data.HaanjaTable.HaanjaColumns;

import android.net.Uri;

public final class HaanjaContent {   

	public static final String AUTHORITY = "ee.mehike.haanja100.haanjas.provider";
	public static final String BASE_PATH = "haanjas";
    //public static final String AUTHORITY = "com.manning.aip.mymoviesdatabase";
	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
	   
	public static final class Haanja {      

		  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
	      //public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "movies");
	      
	      public static abstract class HaanjaColumns extends HaanjaTable.HaanjaColumns {         

	         //public static final String SHOPS = "shop_names";

	         static final HashMap<String, String> projectionMap = new HashMap<String, String>() {
	            {
	               put(_ID, _ID);
	               put(SPLIT, SPLIT);
	               put(TIME, TIME);
	               put(DELETED, DELETED);
	               put(START_TIME, START_TIME);
	               put(END_TIME, END_TIME);
	               put(AVG_PACE, AVG_PACE);
	               put(LAP_PACE, LAP_PACE);
	               put(YEAR, YEAR);

	               //put(SHOPS, "shop.name");
	            }
	         };

	         private HaanjaColumns() {
	         }         
	      }
	      
	      private Haanja() {
	      }
	   }
	   
	   private HaanjaContent() {
	   }
	} 