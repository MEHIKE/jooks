package ee.mehike.haanja100.provider;

import java.util.HashMap;


import ee.mehike.haanja100.data.RaceTable;

import android.net.Uri;

public final class RaceContent {   

	public static final String AUTHORITY = "ee.mehike.haanja100.races.provider";
	public static final String BASE_PATH = "races";
    //public static final String AUTHORITY = "com.manning.aip.mymoviesdatabase";
	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
	   
	public static final class Race {      

		  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
	      //public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "movies");
	      
	      public static abstract class RaceColumns extends RaceTable.RaceColumns {         

	         //public static final String SHOPS = "shop_names";

	         static final HashMap<String, String> projectionMap = new HashMap<String, String>() {
	            {
	               put(_ID, _ID);
	               put(NAME, NAME);
	               put(DESCRIPTION, DESCRIPTION);
	               put(DELETED, DELETED);
	               put(SPLITS, SPLITS);
	               put(RACE_KM, RACE_KM);
	               //put(SHOPS, "shop.name");
	            }
	         };

	         private RaceColumns() {
	         }         
	      }
	      
	      private Race() {
	      }
	   }
	   
	   private RaceContent() {
	   }
	} 