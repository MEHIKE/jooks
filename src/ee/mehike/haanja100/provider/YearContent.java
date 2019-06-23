package ee.mehike.haanja100.provider;

import java.util.HashMap;

import ee.mehike.haanja100.data.YearTable;

import android.net.Uri;

public final class YearContent {   

	public static final String AUTHORITY = "ee.mehike.haanja100.years.provider";
	public static final String BASE_PATH = "years";
    //public static final String AUTHORITY = "com.manning.aip.mymoviesdatabase";
	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
	   
	public static final class Year {      

		  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
	      //public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "movies");
	      
	      public static abstract class YearColumns extends YearTable.YearColumns {         

	         //public static final String SHOPS = "shop_names";

	         static final HashMap<String, String> projectionMap = new HashMap<String, String>() {
	            {
	               put(_ID, _ID);
	               put(YEAR, YEAR);
	               put(RACE_ID, RACE_ID);
	               put(DELETED, DELETED);
	               put(BEST_TIME1, BEST_TIME1);
	               put(BEST_TIME2, BEST_TIME2);
	               put(BEST_TIME3, BEST_TIME3);

	               //put(SHOPS, "shop.name");
	            }
	         };

	         private YearColumns() {
	         }         
	      }
	      
	      private Year() {
	      }
	   }
	   
	   private YearContent() {
	   }
	} 