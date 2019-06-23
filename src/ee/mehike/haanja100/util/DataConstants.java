package ee.mehike.haanja100.util;

import android.os.Environment;

public class DataConstants {

   private static final String APP_PACKAGE_NAME = "ee.mehike.haanja100";

   private static final String EXTERNAL_DATA_DIR_NAME = "haanja100data";
   public static final String EXTERNAL_DATA_PATH =
            Environment.getExternalStorageDirectory() + "/" + DataConstants.EXTERNAL_DATA_DIR_NAME;

   public static final String DATABASE_NAME = "haanja100.db";
   public static final String DATABASE_PATH =
            Environment.getDataDirectory() + "/data/" + DataConstants.APP_PACKAGE_NAME + "/databases/"
                     + DataConstants.DATABASE_NAME;

   private DataConstants() {
   }
} 