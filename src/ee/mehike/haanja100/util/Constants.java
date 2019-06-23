package ee.mehike.haanja100.util;

import android.app.AlarmManager;
import android.content.SharedPreferences;
import android.os.SystemClock;


public class Constants {

	public static final String ALARM_TIME = "alarm_time";
	//public static final String ALARM_TIME_TIME = "alarm_time_time";
	public static final String ALARM_TIME_BG = "alarm_time_bg";
	public static final String ALARM_TIME_FG = "alarm_time_fg";
	public static final String REMINDER_TIME = "reminder_time";
	public static final String VIBRATE_ON_ALARM = "vibrate_on_alarm";
	public static final String DEFAULT_HOUR = "default_hour";
	public static final String DEFAULT_REMINDER_TIME = "6";
	public static final String DEFAULT_ALARM_TIME_FG = "1";
	public static final String DEFAULT_ALARM_TIME_BG = "10";
	public static final String DEFAULT_ALARM_TIME = "15";
	
	   public static final String LOG_TAG = "Haanja100";
	   
	   public static final String FORCE_RELOAD = "FORCE_RELOAD";
	   
	   public static final String RECORD = "07:34:21";
	   public static final String RECORD_100km = "07:34:21";
	   public static final String NAME = "Roosa Mehike";
	   public static final String COMPNAME = "Haanja Jala100";

	   // In real life, use AlarmManager.INTERVALs with longer periods of time 
	   // for dev you can shorten this to 10000 or such, but deals don't change often anyway
	   // (better yet, allow user to set and use PreferenceActivity)
	   ///public static final long ALARM_INTERVAL = 10000;
	   public static final long ALARM_INTERVAL = 1000*30;   
	   public static final long ALARM_TRIGGER_AT_TIME = SystemClock.elapsedRealtime() + 30000;

	   public static final double HAANJA100_KM = 100.00;
	   public static final long HAANJA100_SPLITS = 15;
	   
	   public static final String PREFERENCE_FILENAME = "HaanjaSettings";
	   
	   public static final long JOOKS_HOURS = 24; //ajaline ultra default 24tunnine
	   public static final long JOOKS_LAPS = 5; //iga 5km peal tahan vajutada. vs iga mitme ringi järel tahan vajutada?
	   public static final double JOOKS_LAPKM = 1.00; //iga ringi pikkus on, default 1km
	   public static final double BEST_KM_RECORD = 303.506;
	   public static final double BEST_KM24INDOORM_RECORD = 275.576;
	   public static final double BEST_KM24INDOORW_RECORD = 240.631;
	   public static final double BEST_KM24ROADM_RECORD = 290.221;
	   public static final double BEST_KM24TRACKM_RECORD = 303.506;
	   public static final double BEST_KM12INDOORM_RECORD = 140.844;
	   public static final double BEST_KM12ROADM_RECORD = 162.543;
	   public static final double BEST_KM12TRACKM_RECORD = 162.400;
	   
	   public static final double EST_KM_RECORD = 223.918;
	   public static final double EST_KM24_RECORD = 223.918;
	   public static final double EST_KM12_RECORD = 133.699;
	   public static final long BEST_KM_1 = 300;
	   public static final long BEST_KM_2 = 290;
	   public static final long BEST_KM_3 = 276;
	   public static final long BEST_KM_4 = 241;
	   
	   
	   ///////public static final double SPLIT_KM = Math.round((HAANJA100_KM/HAANJA100_SPLITS)*100)/100d;
	   public static final double SPLIT_KM = Util.round(HAANJA100_KM/HAANJA100_SPLITS, 4);
	   
	   public static final long BEST_TIME_1 = (8*3600+3*60+35)*1000;
	   public static final long BEST_TIME_2 = (8*3600+30*60)*1000;
	   public static final long BEST_TIME_3 = (8*3600+50*60)*1000;
	   public static final long BEST_TIME_4 = (9*3600)*1000;
	   

}
