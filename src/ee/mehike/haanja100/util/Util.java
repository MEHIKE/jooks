package ee.mehike.haanja100.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ee.mehike.haanja100.HaanjaApp;
import ee.mehike.haanja100.MainActivity;
import ee.mehike.haanja100.R;
//import com.shop.checklist.data.ItemTable;
//import com.shop.checklist.data.ItemTable.ItemColumns;
//import com.shop.checklist.data.TagTable;
import ee.mehike.haanja100.data.HaanjaTable;
import ee.mehike.haanja100.fragments.SplitsFragment;
import ee.mehike.haanja100.fragments.dialog.FacebookDialogFragment;
import ee.mehike.haanja100.fragments.dialog.InfoDialogFragment;
import ee.mehike.haanja100.model.Haanja;
import ee.mehike.haanja100.provider.HaanjaContent;
//import com.shop.checklist.model.Item;
//import com.shop.checklist.provider.ItemsContent;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.NetworkInfo.State;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public abstract class Util {

	public static boolean isForeground1(Context context) {

	    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningTaskInfo> services = activityManager
	            .getRunningTasks(Integer.MAX_VALUE);
	    boolean isActivityFound = false;

	    if (services.get(0).topActivity.getPackageName().toString()
	            .equalsIgnoreCase(context.getPackageName().toString())) {
	        isActivityFound = true;
	        return true;
	    }

	    if (isActivityFound) {
	        return true;
	    } else {
	        // write your code to build a notification.
	        // return the notification you built here
	    }
	    return false;
	}
	
	public static boolean isForeground2(Context context) {
		if (context.getPackageName().
				equalsIgnoreCase(((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)).
						getRunningTasks(1).get(0).topActivity.getPackageName()))
		{
			return true;
		}
		return false;
	}

	public static void printAppInfo(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService( Context.ACTIVITY_SERVICE );
		List<RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
		for(int i = 0; i < procInfos.size(); i++){
			if(procInfos.get(i).processName.equals("com.shop.checklist")) {
				Toast.makeText(context, "appinfo="+procInfos.get(i).processName, Toast.LENGTH_LONG).show();
			}
		}
	}
	
	/**
	   * Checks if the application is being sent in the background (i.e behind
	   * another application's Activity).
	   * 
	   * @param context the context
	   * @return <code>true</code> if another application will be above this one.
	   */
	  public static void printTasks(final Context context) {
	    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    //am.getRunningAppProcesses();
	    //am.getRunningServices(maxNum);
	    //am.getRunningTasks(maxNum);
	    List<RunningTaskInfo> tasks = am.getRunningTasks(10);
	    if (!tasks.isEmpty()) {
	    	for(int i = 0; i < tasks.size(); i++){
	    		Toast.makeText(context, "tasks="+tasks.get(i).description, Toast.LENGTH_LONG).show();
	    		/*if (!topActivity.getPackageName().equals(context.getPackageName())) {
	    			return true;
	    		}*/
	    	}
	    }
	  }
	
		/**
	   * Checks if the application is being sent in the background (i.e behind
	   * another application's Activity).
	   * 
	   * @param context the context
	   * @return <code>true</code> if another application will be above this one.
	   */
	  public static void printProcesses(final Context context) {
	    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    //am.getRunningAppProcesses();
	    //am.getRunningServices(maxNum);
	    //am.getRunningTasks(maxNum);
	    List<RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
	    if (!tasks.isEmpty()) {
	    	for(int i = 0; i < tasks.size(); i++){
	    		Toast.makeText(context, "process="+tasks.get(i).processName, Toast.LENGTH_LONG).show();
	    		/*if (!topActivity.getPackageName().equals(context.getPackageName())) {
	    			return true;
	    		}*/
	    	}
	    }
	  }
	
	/**
	   * Checks if the application is being sent in the background (i.e behind
	   * another application's Activity).
	   * 
	   * @param context the context
	   * @return <code>true</code> if another application will be above this one.
	   */
	  public static boolean isApplicationSentToBackground(final Context context) {
	    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningTaskInfo> tasks = am.getRunningTasks(1);
	    if (!tasks.isEmpty()) {
	      ComponentName topActivity = tasks.get(0).topActivity;
	      if (!topActivity.getPackageName().equals(context.getPackageName())) {
	        return true;
	      }
	    }

	    return false;
	  }

		public static Bitmap scaleBitMap(Bitmap bitmap) {
	        final int MAX_DIM = 320;

	        int decodeWidth = bitmap.getWidth();
	        int decodeHeight = bitmap.getHeight();

	        while (decodeWidth > MAX_DIM || decodeHeight > MAX_DIM) {
	            decodeWidth >>= 1;
	            decodeHeight >>= 1;
	        }

	        bitmap = Bitmap.createScaledBitmap(bitmap, decodeWidth, decodeHeight, false);

	        return bitmap;
	    }
		
		/**
	     * Method for hiding the Keyboard
	     * @param context The context of the activity
	     * @param editText The edit text for which we want to hide the keyboard
	     */
	    public static void hideTheKeyboard(Context context, View editText){
	        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
	        //imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
	        //imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_HIDDEN);
	        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_HIDDEN);
	    }
	    
	    /**
	     * Method for hiding the Keyboard
	     * @param context The context of the activity
	     * @param editText The edit text for which we want to hide the keyboard
	     */
	    public static void hidTheKeyboard(Context context, View editText){
	        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
	    }
	    
	    public static void hideTheKeyboardSecond(EditText editText){
	    	editText.setInputType(InputType.TYPE_NULL);
	    }
	    
	    /**
	     * Method for showing the Keyboard
	     * @param context The context of the activity
	     * @param editText The edit text for which we want to show the keyboard
	     */
	    public static void showTheKeyboard(Context context, EditText editText){
	        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	    }
	    
		
		   /*public static void login() {
		    *  setContentView(R.layout.main);
	           TextView textView = (TextView) findViewById(R.id.textViewTest);
			   UserSession user = new UserSession("dcmaglasang@gmail.com", "userpass");
	           if (user.loginSuccess())
	                   textView.setText(user.getToken() + " " + user.getName());
	           else
	                   textView.setText(user.getLoginFailMessage());
		   }*/
		   
		   public static int dp2px(float dp, Context ctx){
			   DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
			   float fpixels = metrics.density * dp;
			   int pixels = (int) (metrics.density * dp + 0.5f);
			   return pixels;
		   }
		   
		   public static float dp2px(int dp, Context ctx) {
			   //int dp = 48;
			   float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,  ctx.getResources().getDisplayMetrics());
			   return px;
		   }

	public static TextView getNiceTV(View ctx, int id, int type) {
		TextView text = (TextView) ctx.findViewById(id);
		return Util.getNiceTV(text, type);
	}
	
	public static TextView getNiceTV(TextView normal, int type) {
		if (type==1) {
			SpannableString content = new SpannableString(normal.getText()); //android.text.style.
			content.setSpan(new UnderlineSpan(), 0, normal.length()-1, 0);//udata.length() 
			normal.setText(content);
		} else if (type==2){
			normal.setPaintFlags(normal.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		} else if (type==3) {
			String htmlString="<u>"+normal.getText()+"</u>"; 
			normal.setText(Html.fromHtml(htmlString)); 
		}
		return normal;
	}
	
	   public static long getSplits(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   long splits=0;
		   if (!settings.getBoolean("istimebased", true)) {
			   splits=settings.getLong("split_pref", Constants.HAANJA100_SPLITS);
		   } else {
			   splits=(long)(Util.getTimedKM1(ctx)/Util.getTimedLapKM(ctx));
		   }
		   return splits;
	   }

	   public static long getLaps(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   long laps=1;
		   if (settings.getBoolean("istimebased", true)) {
		   //laps=settings.getLong("split_pref", Constants.HAANJA100_SPLITS);
			   laps=Util.getTimedLaps(ctx);
		   }
		   return laps;
	   }

	   public static double getTimedWR(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   return settings.getFloat("recordtime_pref", (float)Constants.BEST_KM_RECORD);
	   }

	   public static long getTimedHours(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   return settings.getLong("hour_pref", Constants.JOOKS_HOURS);
	   }

	   public static long getJaanudAegLong(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   long aeg=0;
		   if (!settings.getBoolean("istimebased", true)) {
			   aeg=-1;
		   } else {
			   aeg=settings.getLong("hours_pref", Constants.JOOKS_HOURS);
			   aeg=(aeg*3600)*1000;
		   }
		   return aeg;
	   }

	   
	   public static long getTimedLaps(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   return settings.getLong("laps_pref", Constants.JOOKS_LAPS);
	   }

	   public static double getTimedLapKM(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   return settings.getFloat("splitkm_pref", (float)Constants.JOOKS_LAPKM);
	   }
	   
	   public static long getTimedKM1(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   return settings.getLong("km1_pref", Constants.BEST_KM_1);
	   }

	   public static long getTimedKM2(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   return settings.getLong("km2_pref", Constants.BEST_KM_2);
	   }

	   public static long getTimedKM3(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   return settings.getLong("km3_pref", Constants.BEST_KM_3);
	   }

	   public static double getTimedSplitKm(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   double km=(double)Util.getTimedLapKM(ctx);
		   long split = Util.getTimedLaps(ctx);
		   double splitkm=Util.round(km*split, 4);
				   
		   return splitkm;
	   }

	   public static double getKm(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   double km=0;
		   if (!settings.getBoolean("istimebased", true)) {
			   km=(double)settings.getFloat("km_pref", (float)Constants.HAANJA100_KM);
		   } else {
			   km=(double)settings.getLong("km1_pref", Constants.BEST_KM_1);
		   }
		   return km;
	   }

	   public static long getJaanudAeg(Context ctx, long kulunud_aeg) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   long aeg=0;
		   if (!settings.getBoolean("istimebased", true)) {
			   aeg=-1;
		   } else {
			   aeg=settings.getLong("hours_pref", Constants.JOOKS_HOURS);
			   Log.e("VIGA1", "aeg1="+aeg);
			   aeg=(aeg*3600)*1000;
			   Log.e("VIGA2", "aeg2="+aeg+"   aeg="+Util.displayCalcTime(ctx, aeg)+"   kulunud_aeg="+Util.displayCalcTime(ctx, kulunud_aeg));
			   aeg=aeg-kulunud_aeg;
			   Log.e("VIGA3", "aeg4="+aeg);
			   if (aeg<0)
				   aeg=0;
			   Log.e("VIGA4", "aeg4="+aeg);
		   }
		   return aeg;
	   }

	   public static double getSplitKm(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   double splitkm=0;
		   if (!settings.getBoolean("istimebased", true)) {
			   double km=(double)settings.getFloat("km_pref", (float)Constants.HAANJA100_KM);
			   long split = settings.getLong("split_pref", Constants.HAANJA100_SPLITS);
			   splitkm=Util.round(km/split, 4);
		   } else {
			   double km=(double)Util.getTimedLapKM(ctx);
			   long split = Util.getTimedLaps(ctx);
			   splitkm=Util.round(km*split, 4);
			   
	   		}
		   return splitkm;
	   }
	   
	   public static long getTime1(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   return settings.getLong("time1_pref", Constants.BEST_TIME_1);
	   }

	   public static long getTime2(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   return settings.getLong("time2_pref", Constants.BEST_TIME_2);
	   }

	   public static long getTime3(Context ctx) {
		   SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		   return settings.getLong("time3_pref", Constants.BEST_TIME_3);
	   }

	   
	   public static boolean isApp(String packageName, Activity activity) {
		   Intent mIntent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
		   if (mIntent != null) {
			   try {
				   return true;
				   //startActivity(mIntent);
            } catch (ActivityNotFoundException err) {
                //Toast t = Toast.makeText(getActivity().getApplicationContext(), "app not found", Toast.LENGTH_SHORT);
                //t.show();
                return false;
            }
		   } return false;
	   }

	
	public static String getDeviceID(TelephonyManager phonyManager){

		 String id = phonyManager.getDeviceId();
		 if (id == null){
		  id = "not available";
		 }

		 int phoneType = phonyManager.getPhoneType();
		 switch(phoneType){
		 case TelephonyManager.PHONE_TYPE_NONE:
		  return "NONE: " + id;

		 case TelephonyManager.PHONE_TYPE_GSM:
		  return "GSM: IMEI=" + id;

		 case TelephonyManager.PHONE_TYPE_CDMA:
		  return "CDMA: MEID/ESN=" + id;

		 /*
		  *  for API Level 11 or above
		  *  case TelephonyManager.PHONE_TYPE_SIP:
		  *   return "SIP";
		  */

		 default:
		  return "UNKNOWN: ID=" + id;
		 }

	}
	
	public static String getDeviceIMEI(TelephonyManager phonyManager){

		 String id = phonyManager.getDeviceId();
		 if (id == null){
		  id = "not available";
		 }

		 //int phoneType = phonyManager.getPhoneType();
		 return id.trim();
	}
	
	public static String getPhoneType(TelephonyManager phonyManager){
		  int phoneType = phonyManager.getPhoneType();
		  switch(phoneType){
		  case TelephonyManager.PHONE_TYPE_NONE:
		   return "NONE";

		  case TelephonyManager.PHONE_TYPE_GSM:
		   return "GSM";

		  case TelephonyManager.PHONE_TYPE_CDMA:
		   return "CDMA";
		  
		  /*
		   *  for API Level 11 or above
		   *  case TelephonyManager.PHONE_TYPE_SIP:
		   *   return "SIP";
		   */ 

		  default:
		   return "UNKNOWN";
		  }
		 
	 }
	
	public static String getNetworkType(TelephonyManager phonyManager){
		  int type = phonyManager.getNetworkType();

		  switch(type){
		  case TelephonyManager.NETWORK_TYPE_UNKNOWN:
		   return "NETWORK_TYPE_UNKNOWN";
		  case TelephonyManager.NETWORK_TYPE_GPRS:
		   return "NETWORK_TYPE_GPRS";
		  case TelephonyManager.NETWORK_TYPE_EDGE:
		   return "NETWORK_TYPE_EDGE";
		  case TelephonyManager.NETWORK_TYPE_UMTS:
		   return "NETWORK_TYPE_UMTS";
		  case TelephonyManager.NETWORK_TYPE_HSDPA:
		   return "NETWORK_TYPE_HSDPA";
		  case TelephonyManager.NETWORK_TYPE_HSUPA:
		   return "NETWORK_TYPE_HSUPA";
		  case TelephonyManager.NETWORK_TYPE_HSPA:
		   return "NETWORK_TYPE_HSPA";
		  case TelephonyManager.NETWORK_TYPE_CDMA:
		   return "NETWORK_TYPE_CDMA";
		  case TelephonyManager.NETWORK_TYPE_EVDO_0:
		   return "NETWORK_TYPE_EVDO_0";
		  case TelephonyManager.NETWORK_TYPE_EVDO_A:
		   return "NETWORK_TYPE_EVDO_0";
		  /* Since: API Level 9
		   *  case TelephonyManager.NETWORK_TYPE_EVDO_B:
		   *  return "NETWORK_TYPE_EVDO_B";
		   */
		  case TelephonyManager.NETWORK_TYPE_1xRTT:
		   return "NETWORK_TYPE_1xRTT";
		  case TelephonyManager.NETWORK_TYPE_IDEN:
		   return "NETWORK_TYPE_IDEN";
		  /* Since: API Level 11
		   *  case TelephonyManager.NETWORK_TYPE_LTE:
		   * return "NETWORK_TYPE_LTE";
		   */
		  /* Since: API Level 11
		   *  case TelephonyManager.NETWORK_TYPE_EHRPD:
		   *  return "NETWORK_TYPE_EHRPD";
		   */
		  default:
		   return "unknown";
		 
		  }
	}


	public static boolean connectionPresent(Activity activity) {
		ConnectivityManager cMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
		if ((netInfo != null) && (netInfo.getState() != null)) {
			return netInfo.getState().equals(State.CONNECTED);
		} 
		return false;
	}
	
	public static boolean connectionPresent(Context context) {
		ConnectivityManager cMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
		if ((netInfo != null) && (netInfo.getState() != null)) {
			return netInfo.getState().equals(State.CONNECTED);
		} 
		return false;
	}
	
	public void testViewNetwork(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager)
				activity.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni =
				cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				boolean isWifiAvail = ni.isAvailable();
				boolean isWifiConn = ni.isConnected();
				ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				boolean isMobileAvail = ni.isAvailable();
				boolean isMobileConn = ni.isConnected();
	/*			String text="WiFi\nAvail = "+ isWifiAvail +
				"\nConn = " + isWifiConn +
				"\nMobile\nAvail = "+ isMobileAvail +
				"\nConn = " + isMobileConn;
				if (network_status!=null)
					network_status.setText(text);*/
				String text="WiFi: A="+ isWifiAvail +
				"|C=" + isWifiConn +
				"<->Mob: A="+ isMobileAvail +
				"|C=" + isMobileConn;
				//if (network_status!=null)
					//network_status.setText(text);

				Log.i("NETWORK", text);
	}


		public static boolean isOnline(Activity activity) {
			ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				return true;
			}
			return false;
		}
		
		/*http://androidmaterial.blogspot.com/2012/08/get-latitude-and-longitude-from.html
		public static double getLongitude() {
			double lat = ((Address)parent.getItemAtPosition(position)).getLatitude();
			double lon = ((Address)parent.getItemAtPosition(position)).getLongitude();
		}

		public static double getLatitude() {
			double lat = ((Address)parent.getItemAtPosition(position)).getLatitude();
			double lon = ((Address)parent.getItemAtPosition(position)).getLongitude();
		}
		*/

		public static String getNr(long arv){
			if (arv>9) {
				return ""+arv;
			} else
				return "0"+arv;
		}
		
		public static double round(double valueToRound, int numberOfDecimalPlaces) 
		{ 
		    double multipicationFactor = Math.pow(10, numberOfDecimalPlaces); 
		    double interestedInZeroDPs = valueToRound * multipicationFactor; 
		    return Math.round(interestedInZeroDPs) / multipicationFactor; 
		} 
		
		public static long getFraction(double num, int digits) {
			//Array POWERS_OF_TEN = 
			digits=digits-1;
			int[] arr={10, 100, 1000, 10000, 100000}; 
		    int multiplier = arr[digits]; 
		    long result = ((long) (num * multiplier)) - (((long) num) * multiplier); 
		    return result; 
		} 

	    public static String displayPace(Context ctx,  double pace) {
	        //final long timestamp = Long.parseLong(strDate);
	    	long now = System.currentTimeMillis();
	    	//java.text.DateFormat formatter = android.text.format.DateFormat.getTimeFormat(this.ctx.getApplicationContext());
	    	//java.text.DateFormat formatter = android.text.format.DateFormat.getDateFormat(getApplicationContext());
	    	//Date dateObj = new Date(date * 1000);
		
	    	//Calendar cal = Calendar.getInstance(); 
	    	//cal.clear(); 
	    	//cal.set(Calendar.MILLISECOND, x); 
	    	//SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss.SSS"); 
		
	    	//http://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java?lq=1
	    	//int h = (int) ((pace / 1000) / 3600); 
	    	//int m = (int) (((pace / 1000) / 60) % 60); 
	    	//int s = (int) ((pace / 1000) % 60); 

	    	int h = (int) ((pace) / 3600); 
	    	int m = (int) (((pace) / 60) % 60); 
	    	int s = (int) ((pace) % 60); 
	    	long ms = Util.getFraction(pace, 1);
		
	    	//Date dateObj = new Date(pace);
	    		Log.d("AEG", " now="+now+" date="+pace+" arvutatudaeg="+Util.getNr(h)+":"+Util.getNr(m)+":"+Util.getNr(s));
	    		if (pace<=0) {
	    			Calendar cal = Calendar.getInstance();
	    			//SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
	    			//return formatter.format(cal.getTime());
	    			//return TIMEFORMAT.format(cal.getTime());
	    			return " - ";
	    			//return DATEFORMAT.format(cal.getTime());
	    		} else {
	    			//final String formattedDate = DATEFORMAT.format(new Date(date));
	    			//return formatter.format(dateObj);
	    			//Log.d("AEG", "timeformat="+TIMEFORMAT.format(dateObj));
	    			//Log.d("AEG", "dateformat="+DATEFORMAT.format(dateObj));
	    			//return TIMEFORMAT.format(dateObj);
	    			return " "+Util.getNr(m)+":"+Util.getNr(s)+","+ms+ctx.getApplicationContext().getString(R.string.kiirus);
	    			//return formattedDate;
	    		}
	}
	    
	    
	    public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");
	    public static final SimpleDateFormat DATEFORMAT_SHORT = new SimpleDateFormat("dd.MM HH:mm:ss");
		public static final SimpleDateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");
		public static final SimpleDateFormat PACEFORMAT = new SimpleDateFormat("mm:ss,S");
		
		public static long normalize_time(long time) {
			Date dateObj = new Date(time);
			long normalized_time = (dateObj.getHours()*3600+dateObj.getMinutes()*60+dateObj.getSeconds())*1000;
			return normalized_time;
		}

		public static long normalize_endtime(long start_time, long time, long end_time) {
			//Date dateObj = new Date(time);
			//long normalized_time = (dateObj.getHours()*3600+dateObj.getMinutes()*60+dateObj.getSeconds())*1000;
			long normalized_time=Util.normalize_time(start_time);
			if (normalized_time+time!=end_time) {
				time=end_time-normalized_time;
				//normalized_time=
			}
			//if ()
			end_time=start_time+time;
			return end_time;
		}
		
		public static String displayCalcDate(Context ctx, long date) {
			return Util.displayCalcDate(ctx, date, false);
		}
		
	    public static String displayCalcDate(Context ctx, long date, boolean shor) {
            //final long timestamp = Long.parseLong(strDate);
    	long now = System.currentTimeMillis();
    	//java.text.DateFormat formatter = android.text.format.DateFormat.getTimeFormat(this.ctx.getApplicationContext());
    	java.text.DateFormat formatter = android.text.format.DateFormat.getDateFormat(ctx.getApplicationContext());
    	//Date dateObj = new Date(date * 1000);
    	
    	int h = (int) ((date / 1000) / 3600); 
    	int m = (int) (((date / 1000) / 60) % 60); 
    	int s = (int) ((date / 1000) % 60); 

//    	int h = (int) ((date) / 3600); 
//    	int m = (int) (((date) / 60) % 60); 
//    	int s = (int) ((date) % 60); 

    	
    	Date dateObj = new Date(date);
    	Log.d("AEG", "date="+dateObj+" now="+now+" date="+date+" arvutatudaeg="+Util.getNr(h)+":"+Util.getNr(m)+":"+Util.getNr(s));
    	if (date<=0) {
    			Calendar cal = Calendar.getInstance();
    		    //SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
    			//return formatter.format(cal.getTime());
    			return DATEFORMAT.format(cal.getTime());
    		    //return DATEFORMAT.format(cal.get());
    		} else {
    			//final String formattedDate = DATEFORMAT.format(new Date(date));
    			//return formatter.format(dateObj);
    			Log.d("AEG1", "timeformat="+TIMEFORMAT.format(dateObj));
    			Log.d("AEG2", "dateformat="+DATEFORMAT.format(dateObj));
    			//return TIMEFORMAT.format(dateObj);
    			if (!shor)
    				return DATEFORMAT.format(dateObj);
    			else
    				return DATEFORMAT_SHORT.format(dateObj);
    			//return Util.getNr(h)+":"+Util.getNr(m)+":"+Util.getNr(s);
            	//return formattedDate;
    		}
        //DateFormat formatter = android.text.format.DateFormat.getDateFormat(getActivity().getApplicationContext());
      //long date = cursor.getLong(index);
      //Date dateObj = new Date(date * 1000);
      //((TextView) view).setText(formatter.format(dateObj));

    }

	    /**
	     * Formats and displays the time
	     * 
	     * @param view
	     * @param circumference
	     */
	    public static String displayCalcTime(Context ctx, long date) {
	            //final long timestamp = Long.parseLong(strDate);
	    	long now = System.currentTimeMillis();
	    	//java.text.DateFormat formatter = android.text.format.DateFormat.getTimeFormat(this.ctx.getApplicationContext());
	    	java.text.DateFormat formatter = android.text.format.DateFormat.getDateFormat(ctx.getApplicationContext());
	    	//Date dateObj = new Date(date * 1000);
	    	
	    	int h = (int) ((date / 1000) / 3600); 
	    	int m = (int) (((date / 1000) / 60) % 60); 
	    	int s = (int) ((date / 1000) % 60); 

//	    	int h = (int) ((date) / 3600); 
//	    	int m = (int) (((date) / 60) % 60); 
//	    	int s = (int) ((date) % 60); 

	    	
	    	Date dateObj = new Date(date);
	    	Log.d("AEG", "date="+dateObj+" now="+now+" date="+date+" arvutatudaeg="+Util.getNr(h)+":"+Util.getNr(m)+":"+Util.getNr(s));
	    	if (date<=0) {
	    			Calendar cal = Calendar.getInstance();
	    		    //SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
	    			//return formatter.format(cal.getTime());
	    			//return TIMEFORMAT.format(cal.getTime());
	    			return "00:00:00";
	    		    //return DATEFORMAT.format(cal.get());
	    		} else {
	    			//final String formattedDate = DATEFORMAT.format(new Date(date));
	    			//return formatter.format(dateObj);
	    			Log.d("AEG1", "timeformat="+TIMEFORMAT.format(dateObj));
	    			Log.d("AEG2", "dateformat="+DATEFORMAT.format(dateObj));
	    			//return TIMEFORMAT.format(dateObj);
	    			return Util.getNr(h)+":"+Util.getNr(m)+":"+Util.getNr(s);
	            	//return formattedDate;
	    		}
	        //DateFormat formatter = android.text.format.DateFormat.getDateFormat(getActivity().getApplicationContext());
	      //long date = cursor.getLong(index);
	      //Date dateObj = new Date(date * 1000);
	      //((TextView) view).setText(formatter.format(dateObj));

	    }

	    public static double getJaanudKM(Context ctx, long jaanud, double pace) {
	    	double km=0;
	    	if (jaanud>0 && pace>0)
	    		km=jaanud/1000/pace;
	    	//else km=0;
	    	return Util.roundDecimal(km);
	    }
	    
	    public static double roundDecimal(double arv) {
	    	//Log.e("ERROR","arv="+arv);
	    	DecimalFormat df = new DecimalFormat("#.000");
	    	//Log.e("ERROR","toString="+df.toString());
	    	String str=df.format(arv).replace(",", ".");
	    	//Log.e("ERROR","str="+str);
	    	double tulemus=Double.parseDouble(str);
	    	//Log.e("ERROR","tulemus="+tulemus);
	    	return tulemus;
	    }
	    
	    public static final String PREFERENCE_FILENAME = "HaanjaSettings";

	    /*public static double getSplitKm(Context ctx) {
	    	return Util.round(HAANJA100_KM/HAANJA100_SPLITS, 4);
	    }*/
	    
		//arvutame kõik haanja read üle ja kirjutame keskmised kiirused tagasi baasi ning eeldatava aja ka
		public static boolean recalculateAll(Context ctx) {
			boolean ok=true;
			boolean service=false;
			if (ctx==null)
				service=true;
			Cursor c = HaanjaApp.dataManager.getHaanjaCursor();
			if (c.getCount()>0) {
				c.moveToFirst();
				Haanja haanja_prev = new Haanja();
				long lap_start_time=c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.START_TIME));
				long start_time=lap_start_time;
				long start_time_first=start_time;
				long lap_end_time=0;//c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.END_TIME));
				double distance = 0;//c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.SPLIT));
				long id = 0;//c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns._ID));
				long time = 0;//c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.TIME));
				long f_time=time;
				double lap_pace=0;
				double avg_pace=0;
				long split_nr=0;
				long end_time=-1;
				long new_start_time=0;
				long start_time_row=0;
				//long start_time=0;
				
				//ajalise arvestuse jaoks mõõtmed
				double km0=0;
				double km_ended=0;
				long laps_for_wr=0;  //et lõpetada maailmarekordiga, mitu ringi selleks vaja teha
				//double timed_distance=0;
				long timed_lap_pace=0;
				long nr=0;
				String str="";
				long aeg=0;
		        while (c.isAfterLast() == false) {
		            //leiame esimese rea
		        	//ja arvutame ringi keskmise ning liidame koguajale ja km-le
		        	haanja_prev = Util.buildHaanjaFromCursor(c);
		        	nr=nr+1;
		        	//arvutused sooritan ainult siis kui lõppaeg on olemas - ring täis
		        	if (haanja_prev.getEnd_time()>0 && haanja_prev.getTime()>0) {
		        		split_nr = haanja_prev.getSplit();
		        		//ringiaeg
		        		
		        		//anname starttimele väärtuseks eelmise ringi endtime kui see on olemas
		        		if (end_time>0)
		        			start_time=end_time+1;
		        		end_time=haanja_prev.getEnd_time();
		        		
		        		//omistame uue start_time väljale. end_time ei saa ümber arvutada, see on sisestaud
		        		//start_time ei saa muuta, kuna see on eelmise ringi lõpp ja see oli salvestatud juba nagunii
		        		if (end_time>start_time) {
		        			haanja_prev.setStart_time(start_time);
		        		}
		        		if (end_time>start_time)
		        			haanja_prev.setTime(haanja_prev.getEnd_time()-haanja_prev.getStart_time());
		        		else
		        			haanja_prev.setTime(System.currentTimeMillis()-haanja_prev.getStart_time());
		        		//kogu kulunud aeg
		        		time = time + haanja_prev.getTime();
		        		aeg=haanja_prev.getTime();
		        		//hetkel läbitud maa
		        		distance = distance + Util.getSplitKm(ctx);
		        				//Constants.SPLIT_KM;
		        		//nüüd leiame keskmised kiirused
		        		
		        		lap_pace = haanja_prev.getTime()/1000/Util.getSplitKm(ctx);
		        		avg_pace = time/1000/(Util.getSplitKm(ctx)*haanja_prev.getSplit());
		        		haanja_prev.setAvg_pace(Util.displayPace(ctx, avg_pace));
		        		haanja_prev.setLap_pace(Util.displayPace(ctx, lap_pace));
		        		Log.e("arvutamine","distance="+distance+" time="+time+" split_nr="+split_nr+" lap_time="+
		        		haanja_prev.getTime()+" const_km="+Util.getSplitKm(ctx)+"  dispTime="+Util.displayCalcTime(ctx, haanja_prev.getTime())+
		        		" algus="+Util.displayCalcTime(ctx, haanja_prev.getStart_time())+" lõpp="+Util.displayCalcTime(ctx,haanja_prev.getEnd_time())+
		        				" läbitud="+Util.getSplitKm(ctx)*haanja_prev.getSplit()+" lap_pace="+lap_pace+" avg_pace="+avg_pace+" a_pace="+haanja_prev.getAvg_pace()+" l_pace="+haanja_prev.getLap_pace());
		        		
		        		Log.e("arvutamine2","distance="+distance+" time="+time+" split_nr="+split_nr+"  ID="+haanja_prev.getId()+" lap_time="+
		        		haanja_prev.getTime()+" algus="+start_time+" lõpp="+end_time+" const_km="+Util.getSplitKm(ctx)+"  dispTime="+Util.displayCalcTime(ctx, haanja_prev.getTime())+
		        		" dispAlgus="+Util.displayCalcTime(ctx, haanja_prev.getStart_time())+" dispLõpp="+Util.displayCalcTime(ctx,haanja_prev.getEnd_time())+
		        				" läbitud="+Util.getSplitKm(ctx)*haanja_prev.getSplit()+" lap_pace="+lap_pace+" avg_pace="+avg_pace+" a_pace="+haanja_prev.getAvg_pace()+" l_pace="+haanja_prev.getLap_pace());

		        		/*str=str+"distance="+distance+" time="+time+" split_nr="+split_nr+"  ID="+haanja_prev.getId()+" lap_time="+
				        		haanja_prev.getTime()+" algus="+start_time+" lõpp="+end_time+" const_km="+Util.getSplitKm(ctx)+"  dispTime="+Util.displayCalcTime(ctx, haanja_prev.getTime())+
				        		" dispAlgus="+Util.displayCalcTime(ctx, haanja_prev.getStart_time())+" dispLõpp="+Util.displayCalcTime(ctx,haanja_prev.getEnd_time())+
				        				" läbitud="+Util.getSplitKm(ctx)*haanja_prev.getSplit()+" lap_pace="+lap_pace+" avg_pace="+avg_pace+" a_pace="+haanja_prev.getAvg_pace()+" l_pace="+haanja_prev.getLap_pace()+" </" +
				        						"br> </br> ";*/
		        		//nüüd salvestame keskmised kiirused baasi ja muud muudatused
		        		ctx.getContentResolver().update(Uri.parse(HaanjaContent.Haanja.CONTENT_URI+"/"+haanja_prev.getId()), Util.haanjaValues(haanja_prev), null, null);
		        		new_start_time=end_time;
		        	} else {
		        		if (end_time>=0) {
		        			start_time=end_time+1;
		        			haanja_prev.setStart_time(start_time);
		        			ctx.getContentResolver().update(Uri.parse(HaanjaContent.Haanja.CONTENT_URI+"/"+haanja_prev.getId()), Util.haanjaValues(haanja_prev), null, null);
		        		}
		        	}
		        	start_time_row=haanja_prev.getStart_time();
		        	
		        	//kui on olemas järgmine rida, siis
		       	    if (c.moveToNext()) {
		       	    	
		       	    } else { //kui oli viimnae rida sisi teeme seda...
		       	    	//leiame eeldatavad kiirused ja lõppajad
		       	    	//ning paneme properties faili need
		       	    	SharedPreferences settings = ctx.getSharedPreferences(PREFERENCE_FILENAME, 0);
		       	    	//if (!settings.contains("user_id")) {
		       	     	Log.i("HaanjaPrefs", "tegevused prefferences failiga, svaed settings");
		       	     	SharedPreferences.Editor setEditor = settings.edit();
		       	     	//if (!settings.contains("future_time"))
		       	     	//	setEditor.remove("");
		       	     	//if (!settings.contains("future_finish_time"))
	       	     		setEditor.putLong("nr", nr);
		       	     	setEditor.putFloat("runned_km", (float)distance);
		       	     	setEditor.putLong("future_time", 0);
		       	     	setEditor.putLong("splits", split_nr);
		       	     	setEditor.putLong("last_time", aeg);
		       	     	setEditor.putLong("start_time_long", start_time_first);
		       	     	setEditor.putString("start_time", Util.displayCalcTime(ctx.getApplicationContext(), start_time));
		       	     	setEditor.putLong("normalized_start_time_long", Util.normalize_time(start_time));
		       	     	setEditor.putString("normalized_start_time", Util.displayCalcTime(ctx.getApplicationContext(), Util.normalize_time(start_time)));
		       	     
		       	     	//keskmise ringiaja järgi finishi aja ennustamine (pole eriti reaalne)
		       	     	//praegune aeg + jäänud+km*km tempo)
		       	     	long avglap_finish_time = time + (long)(avg_pace*1000*(Util.getKm(ctx)-distance));
		       	     	//kui välja arvutatud finsihiaeg on suurem kui praegune aeg, siis arvutame praeguse aja põhjal (1ringiaeg rohkem
		       	     	//kuna reaalselt kindlasti läheb kauem aega
		       	     	//enne arvutame praeguseks kulunud ringiaja
		       	     	Calendar now=Calendar.getInstance();
		       	     	long time_now=Util.getLongTimeFromstring("test: "+Util.getHourStr(now)+":"+Util.getMinuteStr(now)+":"+Util.getSecondStr(now));
		       	     	Log.i("TESTIKE1", " avg fin time="+Util.displayCalcTime(ctx, (start_time/1000+avglap_finish_time/1000))+"  uusaeg="+Util.displayCalcTime(ctx, start_time+time+time_now)+"  ehk siis esimene="+
		       	     		start_time+avglap_finish_time+"  ja teine="+start_time+time+time_now+"   time_now="+time_now+"   time="+time+"  start_time="+start_time+
		       	     		"   system="+System.currentTimeMillis()+"  kulunud="+(System.currentTimeMillis()-start_time)+" avglapfintime="+avglap_finish_time+
		       	     		" start_time inimesekuul="+Util.displayCalcTime(ctx.getApplicationContext(), Util.normalize_time(start_time))+
		       	     		"  avgtime inimse kujul="+Util.displayCalcTime(ctx.getApplicationContext(), avglap_finish_time)+
		       	     		"  haanja_prevendtime="+haanja_prev.getStart_time());
		       	     	if ((start_time+avglap_finish_time) < (start_time+time+(time_now)-haanja_prev.getStart_time())) {
		       	     		Log.i("SEES1"," läks sisi sisse");
		       	     		long a_time=time+(System.currentTimeMillis()-start_time);
		       	     		avg_pace = a_time/1000/(Util.getSplitKm(ctx)*(haanja_prev.getSplit()));
		       	     		avglap_finish_time = a_time + (long)(avg_pace*1000*(Util.getKm(ctx)-distance));
		       	     	}
       	     	
		       	     	//TODO
		       	     	//siin peaks arvutatama tegelikkuses - keskmise kiiruse põhjal palju ajaliselt on veel võimalik läbida kilomeetreid 
		       	     	//ja selle põhjal, palju kokku km saab ajaliselt tulla
		       	     	//jäänud veel võrreldes maksimaalse ajaga joosta. max aeg-kulunud aeg
		       	     	//long jaanud_aega=Util.getJaanudAeg(ctx, start_time_row-start_time);
		       	     	//long jaanud_aega=Util.getJaanudAeg(ctx, start_time-start_time_first);
		       	     	//jäänud_aega=maxaeg-time
		       	     	//long jaanud_aega=Util.getJaanudAeg(ctx, System.currentTimeMillis()-start_time);
		       	     	long jaanud_aega=Util.getJaanudAeg(ctx, time);
		       	     	//jäänud ajaga on praeguse kiiruse põhjal võimalik läbida km-t-> aeg/kiirus=maa
		       	     	double jaanud_km=Util.getJaanudKM(ctx, jaanud_aega, avg_pace)+distance;
		       	        setEditor.putString("future_avglap_finish_km", (""+jaanud_km).trim()+"km");
//		       	        if (jaanud_aega>0) {
		          	     if (settings.getBoolean("istimebased", true)) { 

		       	        	setEditor.putString("future_avglap_finish_km", (""+jaanud_km).trim()+"km" );
		       	        	if (jaanud_km>0) {
		       	        		//setEditor.putString("future_avglap_finish_time", Util.displayCalcTime(ctx.getApplicationContext(), jaanud_aega) );
		       	        		//setEditor.putString("future_avglap_finish_time", (""+jaanud_km).trim()+"km" );
		       	        		setEditor.putString("future_avglap_finish_time", Util.displayCalcTime(ctx.getApplicationContext(), jaanud_aega) );
		       	        		setEditor.putLong("future_avglap_finish_time_long", jaanud_aega );
		       	        	}
		       	        	else {
		       	        		setEditor.putString("future_avglap_finish_km", (""+jaanud_km).trim()+"km" );
		       	        		//setEditor.putString("future_avglap_finish_time", Util.displayCalcTime(ctx.getApplicationContext(), jaanud_aega) );
		       	        		setEditor.putString("future_avglap_finish_time", Util.displayCalcTime(ctx.getApplicationContext(), 0) );
		       	        		
		       	        	}
		       	        	Log.e("ERROR", "future_avglap_finish_time_long="+settings.getLong("future_avglap_finish_time_long",0)+" jaanud_km="+jaanud_km+
		       	        			"  future_avglap_finish_time="+settings.getString("future_avglap_finish_time","0")+
		       	        			"  jaanud_aega="+jaanud_aega+"   start_time="+start_time+
		       	        			"  start_time="+Util.displayCalcTime(ctx.getApplicationContext(), start_time)+
		       	        			"  jaanud_aega="+Util.displayCalcTime(ctx.getApplicationContext(), jaanud_aega)+
		       	        			"  kulunud_aeg="+Util.displayCalcTime(ctx.getApplicationContext(), jaanud_aega-time)+
		       	        			"  time="+Util.displayCalcTime(ctx.getApplicationContext(), time)+
		       	        			"  uus_time="+Util.displayCalcTime(ctx.getApplicationContext(), start_time-start_time_first));
		       	        } else
		       	        	setEditor.putString("future_avglap_finish_time", Util.displayCalcTime(ctx.getApplicationContext(), avglap_finish_time) );

		       	        //Log.e("TESTIKE3","start_time="+Util.displayCalcTime(ctx.getApplicationContext(), Util.normalize_time(start_time))+" start_time_Row="+
		          	   Log.e("TESTIKE3","start_time="+Util.displayCalcDate(ctx.getApplicationContext(), start_time, true)+" start_time_first="+
		       	    		//Util.displayCalcTime(ctx.getApplicationContext(), Util.normalize_time(start_time_row))+"  start_time="+
		       	    		Util.displayCalcDate(ctx, start_time_first, true)+"  start_time="+
		       	    		//Util.displayCalcTime(ctx.getApplicationContext(), Util.normalize_time(start_time))+"  jaanud="+
		       	    		Util.displayCalcDate(ctx.getApplicationContext(), start_time, true)+"  jaanud="+
		       	    		Util.displayCalcTime(ctx.getApplicationContext(), System.currentTimeMillis()-start_time)+" jaanud_aega="+
		       	    		Util.displayCalcTime(ctx.getApplicationContext(), jaanud_aega)+" jaanud_km="+jaanud_km+
		       	    		" ");
		       	        
		       	     	long lastlap_finish_time = time + (long)(lap_pace*1000*(Util.getKm(ctx)-distance));
		       	     	Log.i("TESTIKE2", " avg fin time="+Util.displayCalcTime(ctx, start_time+avglap_finish_time)+"  uusaeg="+Util.displayCalcTime(ctx, start_time+time+time_now)+"  ehk siis esimene="+
			       	     		start_time+lastlap_finish_time+"  ja teine="+start_time+time+time_now+"   time_now="+time_now+"   time="+time+"  start_time="+start_time+
			       	     		"   system="+System.currentTimeMillis()+"  kulunud="+(System.currentTimeMillis()-start_time));
			       	     	if ((start_time+lastlap_finish_time)< (start_time+time+time_now-haanja_prev.getStart_time())) {
			       	     	Log.i("SEES2"," läks sisi sisse");
			       	     		long l_time=time+(System.currentTimeMillis()-start_time);
			       	     		lap_pace = l_time/1000/(Util.getSplitKm(ctx)*(haanja_prev.getSplit()));
			       	     		lastlap_finish_time = l_time + (long)(lap_pace*1000*(Util.getKm(ctx)-distance));
			       	     	}

			       	     jaanud_km=Util.getJaanudKM(ctx, jaanud_aega, lap_pace)+distance;
			       	     setEditor.putString("future_lastlap_finish_km", (""+jaanud_km).trim()+"km");
			       	  setEditor.putFloat("future_lastlap_finish_km_float", (float)jaanud_km);
//			       	        if (jaanud_aega>0) {
			       	     if (settings.getBoolean("istimebased", true)) { 

				       	     	setEditor.putString("future_lastlap_finish_time", (""+jaanud_km).trim()+"km");
				       	     	setEditor.putString("future_lastlap_finish_km", (""+jaanud_km).trim()+"km");
				       	        //setEditor.putString("future_lastlap_finish_time", Util.displayCalcTime(ctx.getApplicationContext(), jaanud_aega));
				       	     	//setEditor.putLong("future_lastlap_finish_time_long", jaanud_aega);
				       	     	setEditor.putLong("future_lastlap_finish_time_long", (long)jaanud_aega);
				       	     	//setEditor.putString("future_lastlap_finish_datetime", Util.displayCalcTime(ctx.getApplicationContext(), (Util.normalize_time(start_time)+lastlap_finish_time)));
				       	     	setEditor.putString("future_lastlap_finish_datetime", (""+jaanud_km).trim()+"km");
			       	        } else {

		       	     	setEditor.putString("future_lastlap_finish_time", Util.displayCalcTime(ctx.getApplicationContext(), lastlap_finish_time));
		       	     	setEditor.putLong("future_lastlap_finish_time_long", lastlap_finish_time);
		       	     	setEditor.putString("future_lastlap_finish_datetime", Util.displayCalcTime(ctx.getApplicationContext(), (Util.normalize_time(start_time)+jaanud_aega)));
			       	        }
			       	        
		       	        Log.i("HaanjaPrefs", "prefferences. time="+time+" + "+(long)(avg_pace*1000*(Util.getKm(ctx)-distance))+" või + "+(long)(lap_pace*1000*(Util.getKm(ctx)-distance)));
		       	     	Log.i("HaanjaPrefs", "tegevused prefferences failiga. avg_lap_finish="+Util.displayCalcTime(ctx.getApplicationContext(), avglap_finish_time)+
		       	     			"  last_lap_finish="+Util.displayCalcTime(ctx.getApplicationContext(), lastlap_finish_time)+" läbida veel km="+(Util.getKm(ctx)-distance)+
		       	     			" lap_pace="+lap_pace+" avg_pace="+avg_pace+" avglap_finish_time="+avglap_finish_time+" lastlap_finish_time="+lastlap_finish_time+
		       	     			" start_time_long="+start_time+" start_time="+Util.displayCalcTime(ctx.getApplicationContext(), start_time)+" nomralized_time_long="+Util.normalize_time(start_time)+" normalized_time="+Util.displayCalcTime(ctx.getApplicationContext(), Util.normalize_time(start_time)));
		       	     	
		       	     	setEditor.commit();
		       	     	
		       	     //}
		       	    }
		        }
		        /*FragmentTransaction ft = ((FragmentActivity)(MainActivity.act)).getSupportFragmentManager().beginTransaction();
		        ft = ((FragmentActivity)MainActivity.act).getSupportFragmentManager().beginTransaction();
		        InfoDialogFragment pdf = InfoDialogFragment.newInstance( str );
    			//Bundle bundle=new Bundle();
    			//bundle.putString("name", search_item.getText().toString());
    			//pdf.setArguments(bundle);
    			pdf.show(ft, "test show");*/
			} else {
				SharedPreferences settings = ctx.getSharedPreferences(PREFERENCE_FILENAME, 0);
       	    	//if (!settings.contains("user_id")) {
       	     	Log.i("HaanjaPrefs", "tegevused prefferences failiga, svaed settings");
       	     	SharedPreferences.Editor setEditor = settings.edit();
       	     	setEditor.putLong("future_time", 0);
       	     	setEditor.putLong("splits", 0);
       	     	setEditor.putFloat("runned_km", 0);
       	     	setEditor.putLong("start_time_long", System.currentTimeMillis());
       	     	setEditor.putString("start_time", Util.displayCalcTime(ctx.getApplicationContext(), System.currentTimeMillis()));
       	     	setEditor.putLong("normalized_start_time_long", Util.normalize_time(System.currentTimeMillis()));
       	     	setEditor.putString("normalized_start_time", Util.displayCalcTime(ctx.getApplicationContext(), Util.normalize_time(System.currentTimeMillis())));
       	     	setEditor.putString("future_avglap_finish_time", Util.displayCalcTime(ctx.getApplicationContext(), -1) );
       	     	setEditor.putString("future_lastlap_finish_time", Util.displayCalcTime(ctx.getApplicationContext(), -1));
       	     	setEditor.putString("future_lastlap_finish_km", "0km");
       	     	setEditor.putString("future_avglap_finish_km", "0km");
       	     	setEditor.putLong("future_lastlap_finish_time_long", -1);
       	     	setEditor.putLong("last_time", 1000*60*60);
       	     	setEditor.putString("future_lastlap_finish_datetime", Util.displayCalcTime(ctx.getApplicationContext(), (Util.normalize_time(System.currentTimeMillis()))));
       	     	setEditor.commit();
			}
			c.close();
			c=null;
			return ok;
		}
		
		
		public static void summaryFields(Activity ctx) {
			SharedPreferences settings = ctx.getSharedPreferences(PREFERENCE_FILENAME, 0);
   	    	//if (!settings.contains("user_id")) {
   	     	Log.i("HaanjaPrefs", "tegevused prefferences failiga, summary_fields");
   	     	Cursor c = HaanjaApp.dataManager.getHaanjaCursor();
 	     	TextView spl = (TextView)ctx.findViewById(R.id.splits);
   	     	TextView kms = (TextView)ctx.findViewById(R.id.kms);
   	     	TextView future_avglap_finish_time = (TextView)ctx.findViewById(R.id.fin_time);
   	     	TextView future_lastlap_finish_time = (TextView)ctx.findViewById(R.id.fin_time1);
   	     	
   	     	TextView t_elapsed_time = (TextView)ctx.findViewById(R.id.elapsed_time);
   	     	TextView t_fin_datetime = (TextView)ctx.findViewById(R.id.fin_time2);
   	     	TextView t_yet_time = (TextView)ctx.findViewById(R.id.yet_time); //jäänud aega soovitud tulemuseni
   	     	TextView t_kaotus_time = (TextView)ctx.findViewById(R.id.fin_time3); //kaotus soovitavale tulemusele
   	     	if (c.getCount()==0) {
				c.close();
				c=null;
				spl.setText(ctx.getString(R.string.ringid)+": 0/"+Util.getSplits(ctx));
				kms.setText(ctx.getString(R.string.labitud)+" 0->"+Util.round(Util.getKm(ctx)-settings.getLong("splits", 0)*Util.getSplitKm(ctx),2)+"km");
				future_avglap_finish_time.setText(ctx.getString(R.string.loodetav)+": ");
				future_lastlap_finish_time.setText(ctx.getString(R.string.reaalne)+": ");
				t_elapsed_time.setText(ctx.getString(R.string.kulunud)+": ");
				t_fin_datetime.setText(ctx.getString(R.string.lopukell)+": ");
				t_yet_time.setText(ctx.getString(R.string.yet_time)+": ");
				t_kaotus_time.setText(ctx.getString(R.string.voit)+": ");
				return;
			}
			c.close();
			c=null;
			
   	     	SharedPreferences.Editor setEditor = settings.edit();
  
   	     long nr=settings.getLong("nr", 0);
   	     double pikkus=(double)settings.getFloat("future_lastlap_finish_km_float", 0);
   	     Log.e("EROR","nr="+nr);
   	     //kui km võistlus - 100km
   	     if (!settings.getBoolean("istimebased", true)) { 
   	     	if (settings.contains("future_avglap_finish_time")) {
   	     		future_avglap_finish_time.setText(ctx.getString(R.string.loodetav)+": "+settings.getString("future_avglap_finish_time", ctx.getString(R.string.veeleitea)));
   	     	}
   	     	if (settings.contains("future_lastlap_finish_time")) {
   	     		future_lastlap_finish_time.setText(ctx.getString(R.string.reaalne)+": "+settings.getString("future_lastlap_finish_time", ctx.getString(R.string.veeleitea)));
   	     	}
   	     } else { //kui ajaline võistlus - 24tundi
   	    	 Log.e("TTTT", "km1="+settings.getString("future_avglap_finish_km", ctx.getString(R.string.veeleitea))+"  km2="+settings.getString("future_lastlap_finish_km", ctx.getString(R.string.veeleitea)));
    	     	if (settings.contains("future_avglap_finish_km")) {
       	     		future_avglap_finish_time.setText(ctx.getString(R.string.loodetavkm)+": "+settings.getString("future_avglap_finish_km", ctx.getString(R.string.veeleitea)));
       	     	}
       	     	if (settings.contains("future_lastlap_finish_km")) {
       	     		future_lastlap_finish_time.setText(ctx.getString(R.string.reaalnekm)+": "+settings.getString("future_lastlap_finish_km", ctx.getString(R.string.veeleitea)));
       	     	}
   	    	 
   	     }
   	     
   	  //if (!settings.getBoolean("istimebased", true)) {
   	     	if (settings.contains("splits")) {
   	     		spl.setText(ctx.getString(R.string.ringid)+": "+settings.getLong("splits", 0)*Util.getLaps(ctx)+"/"+Util.getSplits(ctx));
   	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);
   	  /*} else {
 	     	if (settings.contains("splits")) {
   	     		spl.setText(ctx.getString(R.string.ringid)+": "+settings.getLong("splits", 0)+"/"+Util.getSplits(ctx));
   	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);
   	  }*/
   	     	if (settings.contains("splits")) {
   	     		kms.setText(ctx.getString(R.string.labitud)+" "+Util.round(settings.getLong("splits", 0)*Util.getSplitKm(ctx),2)+"->"+Util.round(Util.getKm(ctx)-settings.getLong("splits", 0)*Util.getSplitKm(ctx),2)+"km");
   	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);

   	     	long time = System.currentTimeMillis();
     		long elapsed_time = time - settings.getLong("start_time_long", time);
     		long start_time = settings.getLong("start_time_long", time);
     		//Log.e("ERROR", "start_time_long="+start_time);
   	     	if (settings.contains("start_time_long")) {
   	     		t_elapsed_time.setText(ctx.getString(R.string.kulunud)+" "+(elapsed_time<=5000?ctx.getString(R.string.veeleitea):Util.displayCalcTime(ctx, elapsed_time)));
   	     		
   	     	if (!settings.getBoolean("istimebased", true)) { 
   	     		t_fin_datetime.setText(ctx.getString(R.string.lopukell)+" "+settings.getString("future_lastlap_finish_datetime", ctx.getString(R.string.veeleitea)));
   	     	} else {
   	     		long start=(settings.getLong("start_time_long", System.currentTimeMillis()));
   	     		long hours=Util.getJaanudAegLong(ctx);
   	     		t_fin_datetime.setText(ctx.getString(R.string.lopukell)+" "+Util.displayCalcDate(ctx, start+hours, true));
   	     	}
   	     		
   	     		long last_lap_finish_time = settings.getLong("future_lastlap_finish_time_long", 0);
   	     		double finish_km = (double)settings.getFloat("future_lastlap_finish_km_float", 0);
   	     		double runned_km = (double)settings.getFloat("runned_km", 0);
   	     		if (last_lap_finish_time>0 || settings.getBoolean("istimebased", true)) {
   	     			Calendar cal = Calendar.getInstance();
   	     			long caltime = cal.getTimeInMillis();

   	     			Log.d("titlebar","last_lap_finish_time="+last_lap_finish_time+
   	     					" Constants.BEST_TIME_1="+Util.getTime1(ctx)+
   	     					" Constants.BEST_TIME_2="+Util.getTime2(ctx)+
   	     					" Constants.BEST_TIME_3="+Util.getTime3(ctx)+
   	     					" Systemtime="+System.currentTimeMillis()+
   	     					" lastlapfintime="+Util.displayCalcTime(ctx, last_lap_finish_time)+" normalize curtime="+Util.normalize_time(System.currentTimeMillis())+
   	     					" best_time1="+Util.displayCalcTime(ctx, Util.getTime1(ctx))+ //7:00:00
   	     					" best_time2="+Util.displayCalcTime(ctx, Util.getTime2(ctx))+ //7:30:00
   	     					" best_time3="+Util.displayCalcTime(ctx, Util.getTime3(ctx))); //8:00:00
   	     			//Date dateObj = new Date(System.currentTimeMillis());
   	     		if (!settings.getBoolean("istimebased", true)) {
     				t_yet_time.setText(ctx.getString(R.string.yet_time)+" "+Util.displayCalcTime(ctx, Util.normalize_time(start_time)+last_lap_finish_time-Util.normalize_time(System.currentTimeMillis())));
   	     		} else {
   	     			if (nr==1) {
   	     				//Log.e("ERROR","getJaanudAegLong="+Util.getJaanudAegLong(ctx)+"  nr="+nr);
   	     				t_yet_time.setText(ctx.getString(R.string.yet_time)+" "+Util.displayCalcTime(ctx, Util.getJaanudAegLong(ctx)-elapsed_time));
	     					t_yet_time.setTextColor(Color.GREEN);
   	     			} else {
   	     				t_yet_time.setText(ctx.getString(R.string.yet_time)+" "+settings.getString("future_avglap_finish_time",""+Util.displayCalcTime(ctx, Util.getJaanudAegLong(ctx))));
   	     				Log.e("ERROR", "future_avglap_finish_time_long="+settings.getLong("future_avglap_finish_time_long",0)+
   	     					"   future_avglap_finish_time="+settings.getString("future_avglap_finish_time","aeg"));
   	     				if (settings.getLong("future_avglap_finish_time_long",0)==0) {
   	     					t_yet_time.setTextColor(Color.RED);
   	     				} else t_yet_time.setTextColor(Color.GREEN);
   	     			}
   	     		}
   	      	     //kui km võistlus - 100km
   	       	     if (!settings.getBoolean("istimebased", true)) { 
   	       	    	 
   	     			if ((Util.getTime3(ctx)-last_lap_finish_time)<0) {
   	     				//ideaalne-roheline suurem kui 7:00:00 kollane
   	     				t_kaotus_time.setText(ctx.getString(R.string.kaotus)+" "+Util.displayCalcTime(ctx, last_lap_finish_time-Util.getTime3(ctx)));
   	     				t_kaotus_time.setTextColor(Color.RED); 
   	     			} else if ((Util.getTime2(ctx)-last_lap_finish_time)<0) {
   	     				//ideaalne-roheline suurem kui 7:30:00 - lilla
   	     				t_kaotus_time.setText(ctx.getString(R.string.kaotus)+" "+Util.displayCalcTime(ctx, last_lap_finish_time-Util.getTime2(ctx)));
   	     				t_kaotus_time.setTextColor(Color.MAGENTA); 
   	     			} else if ((Util.getTime1(ctx)-last_lap_finish_time)<0) {
   	     				//ideaalne-roheline suurem kui 8:00:00  - punane
   	     				t_kaotus_time.setText(ctx.getString(R.string.kaotus)+" "+Util.displayCalcTime(ctx, last_lap_finish_time-Util.getTime1(ctx)));
   	     				t_kaotus_time.setTextColor(Color.YELLOW);
   	     					
   	     			} else {
   	     				t_kaotus_time.setText(ctx.getString(R.string.voit)+" "+Util.displayCalcTime(ctx, Util.getTime1(ctx)-last_lap_finish_time));
   	     				t_kaotus_time.setTextColor(Color.GREEN);
   	     			}
   	       	    	 
   	     		} else { // ajaline võistlus, siis teisiti - kaotus enda pakutud km-tele 1,2,3 ja kui MR siis bold
   	     			if (nr==1) {
   	   	     			t_kaotus_time.setText(ctx.getString(R.string.esimene_ring)+" "+Util.roundDecimal((Util.getTimedKM3(ctx)-pikkus))+"km");
   	     				//t_kaotus_time.setTextColor(Color.GREEN);
   	     				t_kaotus_time.setTextColor(Color.GREEN);

   	     			} else 
   	     			if ((Util.getTimedKM3(ctx)-pikkus)>0) {
   	     				//ideaalne-roheline suurem kui 7:00:00 kollane
   	     				Log.e("ERROR", "util="+Util.getTimedKM3(ctx)+"  runned_km="+runned_km+"  finish_km="+finish_km+"   summa="+(Util.getTimedKM3(ctx)-finish_km-runned_km)+"  pikkus="+pikkus);
   	     				//t_kaotus_time.setText(ctx.getString(R.string.kaotus3)+" "+(""+Util.roundDecimal((Util.getTimedKM3(ctx)-finish_km-runned_km))).trim());
   	     			t_kaotus_time.setText(ctx.getString(R.string.kaotus)+" "+(""+Util.roundDecimal((Util.getTimedKM3(ctx)-pikkus))).trim());
   	     				t_kaotus_time.setTextColor(Color.RED); 
   	     			} else if ((Util.getTimedKM2(ctx)-pikkus)>0) {
   	     				//ideaalne-roheline suurem kui 7:30:00 - lilla
   	     			Log.e("ERROR", "util="+Util.getTimedKM2(ctx)+"  runned_km="+runned_km+"  finish_km="+finish_km+"  pikkus="+pikkus);
   	     				//t_kaotus_time.setText(ctx.getString(R.string.kaotus2)+" "+(""+Util.roundDecimal((Util.getTimedKM2(ctx)-finish_km-runned_km))).trim());
   	     		t_kaotus_time.setText(ctx.getString(R.string.kaotus)+" "+(""+Util.roundDecimal((Util.getTimedKM2(ctx)-pikkus))).trim());
   	     				t_kaotus_time.setTextColor(Color.MAGENTA); 
   	     			} else if ((Util.getTimedKM1(ctx)-pikkus)>0) {
   	     				//ideaalne-roheline suurem kui 8:00:00  - punane
   	     			Log.e("ERROR", "util="+Util.getTimedKM1(ctx)+"  runned_km="+runned_km+"  finish_km="+finish_km+"  pikkus="+pikkus);
   	     				//t_kaotus_time.setText(ctx.getString(R.string.kaotus1)+" "+(""+Util.roundDecimal((Util.getTimedKM1(ctx)-finish_km-runned_km))).trim());
   	     				t_kaotus_time.setText(ctx.getString(R.string.kaotus)+" "+(""+Util.roundDecimal((Util.getTimedKM1(ctx)-pikkus))).trim());
   	     				t_kaotus_time.setTextColor(Color.YELLOW);
   	     					
   	     			} else if (finish_km<=0 && nr!=1) {
   	     				//t_kaotus_time.setText(ctx.getString(R.string.voit)+" "+(Util.getTimedKM1(ctx)-last_lap_finish_km));
   	     				//t_kaotus_time.setText(ctx.getString(R.string.kaotus)+" "+Util.roundDecimal((Util.getTimedKM3(ctx)-finish_km-runned_km)));
   	     				t_kaotus_time.setText(ctx.getString(R.string.kaotus)+(" "+Util.roundDecimal((Util.getTimedKM3(ctx)-pikkus))).trim());
   	     				//t_kaotus_time.setTextColor(Color.GREEN);
   	     				t_kaotus_time.setTextColor(Color.MAGENTA);
   	     			} else if (nr==1) {
   	     				//t_kaotus_time.setText(ctx.getString(R.string.esimene_ring)+" "+Util.roundDecimal((Util.getTimedKM3(ctx)-finish_km-runned_km))+"km");
   	     				t_kaotus_time.setText(ctx.getString(R.string.esimene_ring)+" "+Util.roundDecimal((Util.getTimedKM3(ctx)-pikkus))+"km");
   	     				//t_kaotus_time.setTextColor(Color.GREEN);
   	     				t_kaotus_time.setTextColor(Color.GREEN);
   	     			} else {
   	     				Log.e("ERROR", "POLE väärtust util="+Util.getTimedKM3(ctx)+"  runned_km="+runned_km+"  finish_km="+finish_km+"  pikkus="+pikkus);
   	     				t_kaotus_time.setText(ctx.getString(R.string.voit)+" "+(""+Util.roundDecimal((pikkus-Util.getTimedKM3(ctx)))).trim()+"km");
   	     				t_kaotus_time.setTextColor(Color.GREEN);
   	     			}
   	     			
   	     		}
   	     	}}
   	     	if (settings.contains("start_time")) {
   	     		
   	     	}
   	     	TextView myRecordView = (TextView)ctx.findViewById(R.id.rekord);
   	        if (!settings.getBoolean("istimebased", true)) { 
   		     	//if (settings.contains("record_pref")) {
   		     		myRecordView.setText("Eesti rekord: "+settings.getString("record_pref", Constants.RECORD));
   		     	//} else myRecordView.setText("Eesti rekord: "+settings.getString("record_pref", Constants.RECORD));
   	        } else {
   		     	if (settings.contains("recordkm_pref")) {
   		     		if (settings.getLong("hours_pref", 24)==24)
   		     			myRecordView.setText(("MR:"+settings.getFloat("recordkm_pref", (float)Constants.BEST_KM_RECORD)).trim()+("/ER:"+settings.getFloat("recordkmest_pref", (float)Constants.EST_KM_RECORD)).trim()+"km");
   		     		else 
   		     			myRecordView.setText(("MR:"+settings.getFloat("recordkm_pref", (float)Constants.BEST_KM12INDOORM_RECORD)).trim()+("/ER:"+settings.getFloat("recordkmest_pref", (float)Constants.EST_KM12_RECORD)).trim()+"km");

   		     	} else myRecordView.setText("Eesti: "+settings.getFloat("recordkm_pref", (float)Constants.BEST_KM_RECORD));
   	        	
   	        }
   	     	
		}

		
		//changing text style using HTML formatting
		//Spanned is text to which you could add formatting features
		public static Spanned beautify (String originalText, String selectedStyle){
			Spanned answer = null;
			if(selectedStyle.equals("BOLD"))
				answer = Html.fromHtml("<b>"+ originalText+"</b");
			else if(selectedStyle.equals("ITALIC"))
				answer = Html.fromHtml("<i>"+ originalText+"</i>");
			else if(selectedStyle.equals("NORMAL"))
				answer = Html.fromHtml("<normal>"+ originalText+"</normal");
			return answer;
		} //beautify
		
		
		// This function iterates a Cursor and prints its contents
		// Note: Sqlite databases are not strongly typed, so you can pull everything out as a string, or you can use the
		// appropriate get call to enforce type safety. 
		// In this case, we are just logging so we treat all columns as strings using getString() method
		public static void LogCursorInfo(Cursor c) {
			Log.i("CURSOR", "*** Cursor Begin *** " + " Results:" + c.getCount() + " Columns: " + c.getColumnCount());

			// Print column names
			String rowHeaders = "|| ";
			for (int i = 0; i < c.getColumnCount(); i++) {

				rowHeaders = rowHeaders.concat(c.getColumnName(i) + " || ");
			}
			Log.i("CURSOR", "COLUMNS " + rowHeaders);

			// Print records
			c.moveToFirst();
			while (c.isAfterLast() == false) {
				String rowResults = "|| ";
				for (int i = 0; i < c.getColumnCount(); i++) {
					rowResults = rowResults.concat(c.getString(i) + " || ");
				}
				Log.i("CURSOR", "Row " + c.getPosition() + ": " + rowResults);

				c.moveToNext();
			}
			Log.i("CURSOR", "*** Cursor End ***");
		}
		
		public static String getCurrentStringDate()
		{
		    String dt;
		    Date cal=Calendar.getInstance().getTime();
		    dt=cal.toLocaleString();      
		    return dt;

		}

		public static long getCurrentDateTime()
		{
		    //String dt;
		    Date cal=Calendar.getInstance().getTime();
		    //dt=cal.toLocaleString();      
		    return cal.getTime();

		}

		   public static Haanja buildHaanjaFromCursor(Cursor c) {
			      Haanja haanja = null;
			      if (c != null) {
			         haanja = new Haanja();
			         haanja.setId(c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns._ID)));
			         haanja.setSplit(c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.SPLIT)));
			         haanja.setTime(c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.TIME)));
			         haanja.setDeleted(c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.DELETED))==1);
			         haanja.setStart_time(c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.START_TIME)));
			         haanja.setEnd_time(c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.END_TIME)));
			         haanja.setAvg_pace(c.getString(c.getColumnIndex(HaanjaTable.HaanjaColumns.AVG_PACE)));
			         haanja.setLap_pace(c.getString(c.getColumnIndex(HaanjaTable.HaanjaColumns.LAP_PACE)));

			      }
			      return haanja;
			   } 

		   
	public static Calendar getCurrentCalendar() {
		Calendar mCalendar=Calendar.getInstance();
		mCalendar.setTime(new Date());
		mCalendar.getTimeInMillis();
		return mCalendar;
	}

	public static String getCurrentCalendarText() {
		Calendar mCalendar=Calendar.getInstance();
		mCalendar.setTime(new Date());
		//mCalendar.getTimeInMillis();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		return (formatter.format(mCalendar.getTime()));
		
		//return mCalendar;
	}

	public static String getDatetime(long time) {
		//Calendar mCalendar=Calendar.getInstance();
		//mCalendar.setTime(new Date());
		//mCalendar.getTimeInMillis();
		String TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
		return (formatter.format(time));
		
		//return mCalendar;
	}

	public static final String DATE_FORMAT = "dd.MM.yyyy";
	
	public static long getLongDate(String date) {
		Calendar outDate=Calendar.getInstance();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			outDate.setTime(formatter.parse(date));
		} catch (ParseException e) {
		    Log.w("viga", "viga päeva parsimisel stringist. Date: " + date + " not in format: " + DATE_FORMAT);
		    return -1;
		}
		return outDate.getTimeInMillis();
	}

	public static final String TIME_FORMAT = "HH:mm:ss";
	
	public static long getLongTime(String time) {
		Calendar outDate=Calendar.getInstance();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
			outDate.setTime(formatter.parse(time));
		} catch (ParseException e) {
		    Log.w("viga", "viga päeva parsimisel stringist. Date: " + time + " not in format: " + TIME_FORMAT);
		    return -1;
		}
		return outDate.getTimeInMillis();
	}

	public static long getLongTimeFromstring(EditText time) {
		//Calendar outDate=Calendar.getInstance();
		String time_str=time.getText().toString();
		time_str=time_str.trim();
		return Util.getLongTimeFromstring(time_str);
	}

	
	public static long getLongTimeFromstring(String time) {
		//Calendar outDate=Calendar.getInstance();
		String[] str=time.trim().split(":");
		Log.d("uus"," time="+time+" str="+str.toString());
		long aeg=0;
		try {
			//SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
			//outDate.setTime(formatter.parse(time));
			int hour=Integer.parseInt(str[1].trim());
			int min=Integer.parseInt(str[2].trim());
			int sek=Integer.parseInt(str[3].trim());
			aeg=hour*3600+min*60+sek;

		} catch (Exception e) {
		    Log.w("viga", "viga päeva parsimisel stringist. Date: " + time + " not in format: " + str);
		    return -1;
		}
		return aeg*1000;
	}

	
	//public static long getLongTime(long time) {
	//	return time;
		/*Calendar outDate=Calendar.getInstance();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
			outDate.setTime(formatter.parse(time));
		} catch (ParseException e) {
		    Log.w("viga", "viga päeva parsimisel stringist. Date: " + time + " not in format: " + DATE_FORMAT);
		    return -1;
		}
		return outDate.getTimeInMillis();*/
	//}
	
	public static Calendar getCalendar(String date) {
		if (date==null) return getCurrentCalendar();
		Calendar outDate=Calendar.getInstance();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			outDate.setTime(formatter.parse(date));
			return outDate;
		} catch (ParseException e) {
		    Log.w("viga", "viga päeva parsimisel stringist. Date: " + date + " not in format: " + DATE_FORMAT);
		    return getCurrentCalendar();
		}
		//return null;
	}
	
	public static Calendar getCurrentTimeCalendar() {
		Calendar mCalendar=Calendar.getInstance();
		mCalendar.setTime(new Date());
		mCalendar.getTimeInMillis();
		return mCalendar;
	}
	
	public static Calendar getCalendarFromTime(String time) {
		if (time==null) return getCurrentTimeCalendar();
		Calendar outDate=Calendar.getInstance();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(TIMEFORMAT.toString());
			outDate.setTime(formatter.parse(time));
			//outDate.set(Calendar.HOUR_OF_DAY, value)
			Log.w("kell", " Time: " + time + " calendar format: " + outDate+"  format="+TIMEFORMAT.toString());
			return outDate;
		} catch (ParseException e) {
		    Log.w("viga", "viga päeva parsimisel stringist. Time: " + time + " not in format: " + TIMEFORMAT);
		    return getCurrentTimeCalendar();
		}
		//return null;
	}

	public static String getCalendarText(Calendar date) {
		if (date==null) return getCurrentCalendarText();
		//Calendar outDate=Calendar.getInstance();
		//try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			return (formatter.format(date.getTime()));
			//return outDate;
		/*} catch (ParseException e) {
		    Log.w("viga", "viga päeva parsimisel stringist. Date: " + date + " not in format: " + DATE_FORMAT);
		    return getCurrentCalendarText();
		}*/
		//return null;
	}

	public static int getDay(Calendar cal) {
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static String getDayStr(Calendar cal) {
		int day=cal.get(Calendar.DAY_OF_MONTH);
		if (day<10)
			return "0"+Util.getDay(cal);
		return ""+Util.getDay(cal);
	}

	public static String getDayStrInt(int day) {
		if (day<10)
			return "0"+day;
		return ""+day;
	}

	public static int getMonth(Calendar cal) {
		return cal.get(Calendar.MONTH);
	}

	public static String getMonthStr(Calendar cal) {
		int month = cal.get(Calendar.MONTH);
		if (month<10)
			return "0"+Util.getMonth(cal);
		return ""+Util.getMonth(cal);
	}

	public static String getMonthStrInt(int month) {
		if (month<10)
			return "0"+month;
		return ""+month;
	}

	public static int getYear(Calendar cal) {
		return cal.get(Calendar.YEAR);
	}

	public static String getYearStr(Calendar cal) {
		return ""+cal.get(Calendar.YEAR);
	}

	public static String getYearStrInt(int year) {
		return ""+year;
	}
	
	public static int getHour(Calendar cal) {
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static String getHourStr(Calendar cal) {
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		if (hour<10)
			return "0"+Util.getHour(cal);
		return ""+Util.getHour(cal);
	}

	public static int getMinute(Calendar cal) {
		return cal.get(Calendar.MINUTE);
	}

	public static String getMinuteStr(Calendar cal) {
		int minute=cal.get(Calendar.MINUTE);
		if (minute<10)
			return "0"+Util.getMinute(cal);
		return ""+Util.getMinute(cal);
	}

	public static int getSecond(Calendar cal) {
		return cal.get(Calendar.SECOND);
	}

	public static String getSecondStr(Calendar cal) {
		int sec=cal.get(Calendar.SECOND);
		if (sec<10)
			return "0"+Util.getSecond(cal);
		return ""+Util.getSecond(cal);
	}


	public static long getLongDate(String day, String month, String year) {
		long time=0;
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.set(Calendar.YEAR, Integer.parseInt(year));
	    mCalendar.set(Calendar.MONTH, Integer.parseInt(month));
	    mCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
	    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
	    //formatter.format(mCalendar.getTime()));
		time = mCalendar.getTimeInMillis();
		return time;
	}
	
	
	public static long getLongDate(int day, int month, int year) {
		long time=0;
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.set(Calendar.YEAR, year);
	    mCalendar.set(Calendar.MONTH, month);
	    mCalendar.set(Calendar.DAY_OF_MONTH, day);
	    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
	    //formatter.format(mCalendar.getTime()));
		time = mCalendar.getTimeInMillis();
		return time;
	}

	public static boolean testDate(int day, int month, int year) {
		//long time=0;
		try {
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.set(Calendar.YEAR, year);
	    mCalendar.set(Calendar.MONTH, month);
	    mCalendar.set(Calendar.DAY_OF_MONTH, day);
	    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
	    formatter.format(mCalendar.getTime());
	    formatter.parse(Util.getDayStrInt(day)+"."+Util.getMonthStrInt(month)+"."+Util.getYearStrInt(year));
	    //formatter.format(mCalendar.getTime()));
		//time = mCalendar.getTimeInMillis();
		} catch (Exception e ) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Bitmap retrieveBitmap(final String urlString) {
		Log.d(Constants.LOG_TAG, "making HTTP trip for image:" + urlString);
		Bitmap bitmap = null;
		InputStream stream = null;
		try {
			URL url = new URL(urlString);
			stream = url.openConnection().getInputStream();
			bitmap = BitmapFactory.decodeStream(stream);
		} catch (MalformedURLException e) {
			Log.e(Constants.LOG_TAG, "Exception loading image, malformed URL",
					e);
		} catch (IOException e) {
			Log.e(Constants.LOG_TAG, "Exception loading image, IO error", e);
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				Log.w(Constants.LOG_TAG, "Error closing stream", e);
			}
		}
		return bitmap;
	}
	
	/*public static String optimizeList(String list) {
		Log.d("optimizeList","vana sissetulnud list on: "+list);
		String newList="";
		if (list==null) return null;
		if (list.endsWith(",")) {
			list = list.substring(0, list.length() - 1);
		}
		list = list.trim();
		if (list.length()>0) {
			String taglist[] = list.split(",");
			for (String tag : taglist) {
				if (!tag.equals("")) {
					//tagset.add(tag.trim());
					tag=tag.trim();
					if (newList==null)
						newList=tag+", ";
					else {
						if (!newList.contains(tag+","))
							newList=newList+tag+", ";
						else
							Log.d("optimizeList","selline tag("+tag+",) on juba olemas listis ("+newList+")!!!!");
					}
				}
			}
		}
		newList=newList.trim();
		if (newList.endsWith(",")) {
			newList = newList.substring(0, newList.length() - 1);
		}
		Log.d("optimizeList","uus koostatud list on: "+newList);
		return newList.trim();
	}
	*/
	
	public static ContentValues haanjaValues(Haanja haanja) {
	      ContentValues values = new ContentValues();
	      values.put(HaanjaTable.HaanjaColumns.SPLIT, haanja.getSplit());
	      values.put(HaanjaTable.HaanjaColumns.TIME, haanja.getTime());
	      values.put(HaanjaTable.HaanjaColumns.START_TIME, haanja.getStart_time());
	      values.put(HaanjaTable.HaanjaColumns.END_TIME, haanja.getEnd_time());
	      values.put(HaanjaTable.HaanjaColumns.AVG_PACE, haanja.getAvg_pace());
	      values.put(HaanjaTable.HaanjaColumns.LAP_PACE, haanja.getLap_pace());
	      
	      return values;
	}
	
/*	   public static Item buildItemFromCursor(Cursor c) {
		      Item item = null;
		      if (c != null && c.getCount()>0) {
		    	  
		         item = new Item();
		         item.setId(c.getLong(0));
		         item.setUserId(c.getLong(1));
		         item.setName(c.getString(2));
		         item.setDesc(c.getString(3));
		         item.setTime(c.getLong(4));
		         item.setTitleId(c.getLong(5));
		         item.setChecked(c.getLong(6)==1);
		         item.setDeleted(c.getLong(7)==1);
		         item.setModified(c.getInt(8)==1);
		         item.setModifiedBy(c.getLong(9));
		         item.setPrivate(c.getInt(10)==1);
		         item.setSyncable(c.getInt(11)==1);
		         item.setCreatedTime(c.getLong(12));
		         item.setImportant_level(c.getLong(13));
		         item.setAmount(c.getDouble(14));
		         item.setBudget_id(c.getLong(15));
		         item.setIs_weighed(c.getLong(16)==1);
		         item.setQuantity(c.getDouble(17));
		         item.setUnit(c.getString(18));
		         item.setPrice(c.getDouble(19));
		         item.setItem_summary_id(c.getLong(20));
		         item.setBarcode(c.getString(21));
		         item.setWhom_id(c.getLong(22));
		         item.setPicture_url(c.getString(23));
		         item.setTags(c.getString(c.getColumnIndex(ItemTable.ItemColumns.TAGS)));
		         item.setGroup_id(c.getLong(25));
		         item.setJrk(c.getLong(26));
		         
		         item.setReal_time(c.getLong(27));
		         item.setSave_sum(c.getLong(28)==1);
		         item.setRemote_id(c.getLong(29));
		         item.setSynced(c.getLong(30)==1);
		      }
		      return item;
		   } 

	   public static ContentValues itemValues(View v, long time) {
		   return itemValues(v, 0, time);
	   }
	   
	   public static ContentValues itemValues(View v, long jrk, long time) {
		      ContentValues values = new ContentValues();
		      values.put(ItemTable.ItemColumns.TITLE_ID,ItemsFragment.title.getId());
		      
		      values.put(ItemTable.ItemColumns.TIME, time);
		      values.put(ItemTable.ItemColumns.SYNCED, 0);
		      
		      EditText head1 = (EditText) v.findViewById(R.id.edit_item_details);
		      if (head1!=null)
				values.put(ItemTable.ItemColumns.NAME, head1.getText().toString());
		      Log.d("Util","name="+head1.getText().toString());
		      
		      CheckBox cb_kaal = (CheckBox) v.findViewById(R.id.is_kaal_item_details);
		      if (cb_kaal!=null)
				values.put(ItemTable.ItemColumns.IS_WEIGHED, cb_kaal.isChecked()?1:0);
		      
		      head1 = (EditText) v.findViewById(R.id.edit_kogus_item_details);
		      if (head1!=null)
				values.put(ItemTable.ItemColumns.QUANTITY, Double.parseDouble(head1.getText().toString()));

		      head1 = (EditText) v.findViewById(R.id.edit_yhik_item_details);
		      if (head1!=null)
				values.put(ItemTable.ItemColumns.UNIT, head1.getText().toString());
		      
		      head1 = (EditText) v.findViewById(R.id.edit_hind_item_details);
		      if (head1!=null)
				values.put(ItemTable.ItemColumns.PRICE, Double.parseDouble(head1.getText().toString()));
		      
		      head1 = (EditText) v.findViewById(R.id.edit_hind_item_details);
		      if (head1!=null)
				values.put(ItemTable.ItemColumns.PRICE, Double.parseDouble(head1.getText().toString()));

		      head1 = (EditText) v.findViewById(R.id.edit_sum_item_details);
		      if (head1!=null)
				values.put(ItemTable.ItemColumns.AMOUNT, Double.parseDouble(head1.getText().toString()));

		      Spinner spin = (Spinner) v.findViewById(R.id.edit_group_item_details);
		      if (spin!=null)
				values.put(ItemTable.ItemColumns.GROUP_ID, spin.getSelectedItemPosition());
		      Log.d("Util","Spinner group="+spin.getSelectedItemId()+"  position="+spin.getSelectedItemPosition());

		      spin = (Spinner) v.findViewById(R.id.edit_whom_item_details);
		      if (spin!=null)
				values.put(ItemTable.ItemColumns.WHOM_ID, spin.getSelectedItemPosition());
		      Log.d("Util","Spinner whom="+spin.getSelectedItemId()+"  position="+spin.getSelectedItemPosition());
		      
		      head1 = (EditText) v.findViewById(R.id.edit_tags_item_details);
		    	// Remove trailing ","
		    	String tags = head1.getText().toString().trim();
		    	tags=Util.optimizeList(tags);

		      if (head1!=null)
				values.put(ItemTable.ItemColumns.TAGS, tags);

		      spin = (Spinner) v.findViewById(R.id.importnt_item_details);
		      if (spin!=null)
				values.put(ItemTable.ItemColumns.IMPORTANT_LEVEL, spin.getSelectedItemPosition());
		      Log.d("Util","Spinner important="+spin.getSelectedItemId()+"  position="+spin.getSelectedItemPosition());

		      cb_kaal = (CheckBox) v.findViewById(R.id.is_private_item_details);
		      if (cb_kaal!=null)
				values.put(ItemTable.ItemColumns.IS_PRIVATE, cb_kaal.isChecked()?1:0);

		      cb_kaal = (CheckBox) v.findViewById(R.id.is_sync_item_details);
		      if (cb_kaal!=null)
				values.put(ItemTable.ItemColumns.IS_SYNCABLE, cb_kaal.isChecked()?1:0);

		      cb_kaal = (CheckBox) v.findViewById(R.id.is_savedsum_item_details);
		      if (cb_kaal!=null)
				values.put(ItemTable.ItemColumns.SAVE_SUM, cb_kaal.isChecked()?1:0);

		      head1 = (EditText) v.findViewById(R.id.edit_barcode_item_details);
		      if (head1!=null)
				values.put(ItemTable.ItemColumns.BARCODE, head1.getText().toString());

		      
		      //values.put(ItemTable.ItemColumns.TIME, System.currentTimeMillis());
		      
		      if (jrk!=0) 
		    	  values.put(ItemTable.ItemColumns.JRK, jrk);

		      //Toast.makeText(getActivity(), "", 2000)
		      return values;
		   } 
*/
	   /*public static String[] ITEMS_SUMMARY_PROJECTION = new String[] {
		        ItemsContent.Items.ItemColumns._ID,
		        ItemsContent.Items.ItemColumns.USER_ID,
		        ItemsContent.Items.ItemColumns.NAME,
		        ItemsContent.Items.ItemColumns.DESCRIPTION,
		        ItemsContent.Items.ItemColumns.TIME,
		        ItemsContent.Items.ItemColumns.TITLE_ID,
		        ItemsContent.Items.ItemColumns.CHECKED,
		        ItemsContent.Items.ItemColumns.DELETED,
		        ItemsContent.Items.ItemColumns.IS_MODIFIED,
		        ItemsContent.Items.ItemColumns.MODIFIED_BY,
		        ItemsContent.Items.ItemColumns.IS_PRIVATE,
		        ItemsContent.Items.ItemColumns.IS_SYNCABLE,
		        ItemsContent.Items.ItemColumns.CREATED_TIME,
		        ItemsContent.Items.ItemColumns.IMPORTANT_LEVEL,
		        ItemsContent.Items.ItemColumns.AMOUNT,
		        ItemsContent.Items.ItemColumns.BUDGET_ID,
		        ItemsContent.Items.ItemColumns.IS_WEIGHED,
		        ItemsContent.Items.ItemColumns.QUANTITY,
		        ItemsContent.Items.ItemColumns.UNIT,
		        ItemsContent.Items.ItemColumns.PRICE,
		        ItemsContent.Items.ItemColumns.ITEM_SUMMARY_ID,
		        ItemsContent.Items.ItemColumns.BARCODE,
		        ItemsContent.Items.ItemColumns.WHOM_ID,
		        ItemsContent.Items.ItemColumns.PICTURE_URL,
		        ItemsContent.Items.ItemColumns.TAGS,
		        ItemsContent.Items.ItemColumns.GROUP_ID,
		        ItemsContent.Items.ItemColumns.JRK,
		        ItemsContent.Items.ItemColumns.REAL_TIME,
		        ItemsContent.Items.ItemColumns.SAVE_SUM,
		        ItemsContent.Items.ItemColumns.REMOTE_ID,
		        ItemsContent.Items.ItemColumns.SYNCED,
		    }; 
	   */


}
