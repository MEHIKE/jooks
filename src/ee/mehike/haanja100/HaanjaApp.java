package ee.mehike.haanja100;



import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.manning.aip.dealdroid.model.Item;

//import com.manning.aip.dealdroid.model.Section;

//import com.manning.aip.dealdroid.xml.DailyDealsFeedParser;

//import com.manning.aip.dealdroid.xml.DailyDealsXmlPullFeedParser;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.client.HttpClient;

import ee.mehike.haanja100.data.DataManager;
import ee.mehike.haanja100.data.DataManagerImpl;
import ee.mehike.haanja100.data.HaanjaTable;
import ee.mehike.haanja100.provider.HaanjaContent;
//import ee.mehike.haanja100.services.CustomHttpClient;
import ee.mehike.haanja100.util.Constants;
import ee.mehike.haanja100.util.Util;


public class HaanjaApp extends Application {

	public static Context _context;
	public static Activity _activity;
	
	private static ConnectivityManager cMgr;
	private SQLiteDatabase db;
	public static DataManager dataManager;
	public static boolean isNetwork=false;
	public static long nextRunningTime;
	public static String username;
	public static String userfullname;
	
	public void longToast(String str) {
		Toast msg = Toast.makeText(this, str, 5000);
		msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
				msg.getYOffset() / 2);

		msg.show();
	}

	public void verylongToast(String str) {
		Toast msg = Toast.makeText(this, str, 10000);
		msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
				msg.getYOffset() / 2);

		msg.show();
	}

	public void shortToast(String str) {
		Toast msg = Toast.makeText(this, str, 2000);
		msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
				msg.getYOffset() / 2);

		msg.show();
	}
	
	public void customToastLong(Activity ctx, String str) {
		customToastLong(ctx, str, true);	
	}
	
	public void customToastShort(Activity ctx, String str) {
		customToastShort(ctx, str, true);	
	}

	public void customToastLong1(Activity ctx, String str, String str2) {
		customToastLong1(ctx, str, str2, true);	
	}
	
	public void customToastShort1(Activity ctx, String str, String str2) {
		customToastShort1(ctx, str, str2, true);	
	}

	public void customToastLong(Activity ctx, String str, boolean pic) {
		LayoutInflater inflater = ctx.getLayoutInflater(); 
		View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) ctx.findViewById(R.id.toast_layout));  
		TextView text = (TextView) layout.findViewById(R.id.toasttext); 
		text.setText(str);  
		if (!pic) {
			ImageView image = (ImageView) layout.findViewById(R.id.toastimage);
			image.setVisibility(View.INVISIBLE);
			image.setVisibility(View.GONE);
		}
		Toast toast = new Toast(getApplicationContext()); 
		//toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
		toast.setGravity(Gravity.CENTER_VERTICAL, toast.getXOffset() / 2,
				toast.getYOffset() / 2);
		toast.setDuration(Toast.LENGTH_LONG); 
		toast.setView(layout); 
		toast.show(); 
	}

	public void customToastShort(Activity ctx, String str, boolean pic) {
		LayoutInflater inflater = ctx.getLayoutInflater(); 
		View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) ctx.findViewById(R.id.toast_layout));  
		TextView text = (TextView) layout.findViewById(R.id.toasttext); 
		text.setText(str);
		
		if (!pic) {
			ImageView image = (ImageView) layout.findViewById(R.id.toastimage);
			image.setVisibility(View.INVISIBLE);
			image.setVisibility(View.GONE);
		}
		Toast toast = new Toast(getApplicationContext()); 
		//toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
		toast.setGravity(Gravity.CENTER_VERTICAL, toast.getXOffset() / 2,
				toast.getYOffset() / 2);
		toast.setDuration(Toast.LENGTH_SHORT); 
		toast.setView(layout); 
		toast.show(); 
	}

	public void customToastLong1(Activity ctx, String str, String str2, boolean pic) {
		LayoutInflater inflater = ctx.getLayoutInflater(); 
		View layout = inflater.inflate(R.layout.custom_toast2, (ViewGroup) ctx.findViewById(R.id.toast_layout));  
		TextView text = (TextView) layout.findViewById(R.id.toasttext);
		TextView text2 = (TextView) layout.findViewById(R.id.toasttext2); 
		text.setText(str);  
		text2.setText(str2);
		if (!pic) {
			ImageView image = (ImageView) layout.findViewById(R.id.toastimage);
			image.setVisibility(View.INVISIBLE);
			image.setVisibility(View.GONE);
		}
		Toast toast = new Toast(getApplicationContext()); 
		//toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		//toast.setGravity(Gravity.CENTER_VERTICAL, toast.getXOffset() / 2,
		//		toast.getYOffset() / 2);
		toast.setDuration(Toast.LENGTH_LONG); 
		toast.setView(layout); 
		toast.show(); 
	}

	public void customToastShort1(Activity ctx, String str, String str2, boolean pic) {
		LayoutInflater inflater = ctx.getLayoutInflater(); 
		View layout = inflater.inflate(R.layout.custom_toast2, (ViewGroup) ctx.findViewById(R.id.toast_layout));  
		TextView text = (TextView) layout.findViewById(R.id.toasttext);
		TextView text1 = (TextView) layout.findViewById(R.id.toasttext2);
		text.setText(str);
		text1.setText(str2);
		if (!pic) {
			ImageView image = (ImageView) layout.findViewById(R.id.toastimage);
			image.setVisibility(View.INVISIBLE);
			image.setVisibility(View.GONE);
		}
		Toast toast = new Toast(getApplicationContext()); 
		//toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		//toast.setGravity(Gravity.CENTER_VERTICAL, toast.getXOffset() / 2,
		//		toast.getYOffset() / 2);
		toast.setDuration(Toast.LENGTH_SHORT); 
		toast.setView(layout); 
		toast.show(); 
	}

	public Integer getCellId() {
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		GsmCellLocation cellLocation = (GsmCellLocation)telephonyManager.getCellLocation();
		return cellLocation.getCid();
	}

	public Integer getLocationAreaCode() {
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		GsmCellLocation cellLocation = (GsmCellLocation)telephonyManager.getCellLocation();
		return cellLocation.getLac();
	}

	public String getCellLocation() {
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		GsmCellLocation cellLocation = (GsmCellLocation)telephonyManager.getCellLocation();
		return cellLocation.toString();
	}
	
	public String getUniqueMobileId() {
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		return Util.getDeviceID(telephonyManager);
	}

	public String getMobileType() {
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		return Util.getPhoneType(telephonyManager);
	}

	public String getNetworkType() {
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	    return Util.getNetworkType(telephonyManager);
	}

	public String getNetworkOperator() {
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	    return telephonyManager.getNetworkOperator();
	}

	public String getNetworkOperatorName() {
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	    return telephonyManager.getNetworkOperatorName();
	}

	public String getNetworkCountryIso() {
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	    return telephonyManager.getNetworkCountryIso();
	}

	public void setWifiOn() {
		WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(true);	
	}

	public void setWifiOff() {
		WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(false);	
	}

	
	
	public int wifiState() {
		/*
		 * wifistate=
		 * WIFI_STATE_DISABLED 
		 * WIFI_STATE_DISABLING 
		 * WIFI_STATE_ENABLED 
		 * WIFI_STATE_ENABLING 
		 * WIFI_STATE_UNKNOWN 
		 */
		Intent intent = new Intent(); //kas nii saab selle kätte? examples oli aind see järgmine lause
		return intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,    WifiManager.WIFI_STATE_UNKNOWN);	
	}
	
	public boolean GpsProvider() {
		String GpsProvider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	        
	        if(GpsProvider.equals("")){
	         //GPS Disabled
	         //gpsState.setText("GPS Disable");
	        	
	        	//
	        	Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
	            startActivity(intent);

	        	return false;
	        }else{
	         //GPS Enabled
	         //gpsState.setText("GPS Enable");
	        	return true;
	        }
	}

	
   	//private DailyDealsFeedParser parser;
    //private List<Section> sectionList;
	//private Map<Long, Bitmap> imageCache;
	//private Item currentItem;
   
//
// getters/setters
//

	/*public DailyDealsFeedParser getParser() {
		return this.parser;
	}

	public List<Section> getSectionList() {
		return this.sectionList;
	}

	public Map<Long, Bitmap> getImageCache() {
		return this.imageCache;
	}

	public Item getCurrentItem() {
		return this.currentItem;
	}

	public void setCurrentItem(Item currentItem) {
		this.currentItem = currentItem;
	}
*/

//
// lifecycle
//
	private HttpClient httpClient;
	
	/*public HttpClient getHttpClient() {
		return this.
	}*/
	
	public long user_remote_id;
	public long user_group_remote_id;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		this._context=this.getApplicationContext();
		this.cMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		//ShopDatabaseHelper database = new ShopDatabaseHelper(this.getApplicationContext());
		//this.db = database.getWritableDatabase();
		//this.parser = new DailyDealsXmlPullFeedParser();
		//this.sectionList = new ArrayList<Section>(6);
		//this.imageCache = new HashMap<Long, Bitmap>();
	      dataManager = new DataManagerImpl(this); 
	      try{
	      Log.d("ShoppingApp","getCellLocation="+this.getCellLocation());
	      Log.d("ShoppingApp","getMobileType="+this.getMobileType());
	      Log.d("ShoppingApp","getNetworkCountryIso="+this.getNetworkCountryIso());
	      Log.d("ShoppingApp","getNetworkOperator="+this.getNetworkOperator());
	      Log.d("ShoppingApp","getNetworkOperatorName="+this.getNetworkOperatorName());
	      Log.d("ShoppingApp","getNetworkType="+this.getNetworkType());
	      Log.d("ShoppingApp","getUniqueMobileID="+this.getUniqueMobileId());
	      Log.d("ShoppingApp","getCellID="+this.getCellId());
	      Log.d("ShoppingApp","getLocationAreaCode="+this.getLocationAreaCode());
	      Log.d("ShoppingApp","getConnectionPreset="+this.connectionPresent());
	      } catch (Exception e) {
	    	  
	      }
	      this.isNetwork=this.connectionPresent();
	      //httpClient = CustomHttpClient.getHttpClient();  
	      //user=Util.getDeviceID((TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE));
	      username=Util.getDeviceIMEI((TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE));
	      userfullname=Util.getDeviceID((TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE));
	      
/*	      ContentValues values = new ContentValues();
      	values.put(ItemTable.ItemColumns.CHECKED, 0);
      	values.put(ItemTable.ItemColumns.SYNCED, 0);
      	long time = System.currentTimeMillis();
      	values.put(ItemTable.ItemColumns.TIME, time);*/
      	
	    /*String[] projection = new String[] {
	        UsersContent.Users.UserColumns._ID,
	        UsersContent.Users.UserColumns.NAME,
	        UsersContent.Users.UserColumns.USERNAME,
	        UsersContent.Users.UserColumns.PASSWORD,
	        UsersContent.Users.UserColumns.LAST_LOGIN_TIME,
	        UsersContent.Users.UserColumns.LAST_IP,
	        UsersContent.Users.UserColumns.LAST_LOGIN_TIME,
      	};
      	*/
      	
      	
      	/*Cursor cur = getContentResolver().query(UsersContent.Users.CONTENT_URI, projection, "name='"+username+"'", null, null);
      	if (cur==null || cur.getCount()<=0) {
      		//kas kõigepealt peaks kasutajanime looma serveris ja sealt tagasi id või piisab remote id-st?
      		//tegelikkuses anname asja edasi ju Httptaskile ja see tegutseb edasi omapead
      		//ning salvestab ise maha remote_id
      		ContentValues values = new ContentValues();
      		values.put("username", username);
      		values.put("name", userfullname);
      		Uri user_obj = getContentResolver().insert(UsersContent.Users.CONTENT_URI, values );//UsersContent.Users.CONTENT_URI, projection, "name='"+user+"'", null, null);
      		Log.d("user","user id="+user_obj.getLastPathSegment()+" uri="+user_obj.toString());
      		//------------siin siis laseme teele ka httptaski poole
      		//usergroup ka?
      		
      		
      	}
      	cur.close();
      	cur=null;*/
	      
	      
      	//.update(Uri.parse(ItemsContent.Items.CONTENT_URI+"/"+obj.id), values, null, null);

	      Locale locale = new Locale("et","EE");
	      locale.setDefault(locale);

	      
	    /*  SQLiteDatabase mDB = getDatabase();
	  	Log.i("db", "Database path: " + mDB.getPath());
		Log.i("db", "Database Version: " + mDB.getVersion());
		Log.i("db", "Database Page Size: " + mDB.getPageSize());
		Log.i("db", "Database Max Size: " + mDB.getMaximumSize());

		Log.i("db", "Database Open?  " + mDB.isOpen());
		Log.i("db", "Database readonly?  " + mDB.isReadOnly());
		Log.i("db", "Database Locked by current thread?  "+mDB.isDbLockedByCurrentThread());*/

	}

	public SQLiteDatabase getDatabase() {
		return this.db;
	}
	
	   public DataManager getDataManager() {
		   return this.dataManager;
	   } 
	   
	@Override
   public void onTerminate() {
		// not guaranteed to be called
		super.onTerminate();
	}

	//
	// helper methods (used by more than one other activity, so placed here, could be util class too)
	//		
	public Bitmap retrieveBitmap(String urlString) {
		Log.d(Constants.LOG_TAG, "making HTTP trip for image:" + urlString);
		Bitmap bitmap = null;
		try {
			URL url = new URL(urlString);
			// NOTE, be careful about just doing "url.openStream()"  
			// it's a shortcut for openConnection().getInputStream() and doesn't set timeouts
			// (the defaults are "infinite" so it will wait forever if endpoint server is down)
			// do it properly with a few more lines of code . . .         
			URLConnection conn = url.openConnection();     
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(5000);         
			bitmap = BitmapFactory.decodeStream(conn.getInputStream());
		} catch (MalformedURLException e) {
			Log.e(Constants.LOG_TAG, "Exception loading image, malformed URL", e);
		} catch (IOException e) {
			Log.e(Constants.LOG_TAG, "Exception loading image, IO error", e);
		} 
		return bitmap;
	}

	public boolean connectionPresent() {
		NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
		if ((netInfo != null) && (netInfo.getState() != null)) {
			return netInfo.getState().equals(State.CONNECTED);
		} 
		return false;
	}

	/** ----------------------- MESSAGE HANDLING ----------------------- */
	/*private String message = "";
 
	public void setApplicationMessage(String messageText) {
		message = messageText;
	}
 
	public void showAndDeleteApplicationMessage(Activity activity) {
		if (message.length() > 0) {
			LayoutInflater inflater = activity.getLayoutInflater();
			View toastLayout = inflater.inflate(
					R.layout.toast_layout,
					(ViewGroup) activity.findViewById(R.id.toast_layout_root)
					);
			TextView text = (TextView) toastLayout.findViewById(R.id.toastlayout_text);
			text.setText(message);
			message = "";
			Toast toast = new Toast(this);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(toastLayout);
			toast.show();
		}
	}
 
	public void showToastMessage(Activity activity, String messageText) {
		message = messageText;
		showAndDeleteApplicationMessage(activity);
	}*/
	
	// This function iterates a Cursor and prints its contents
	// Note: Sqlite databases are not strongly typed, so you can pull everything out as a string, or you can use the
	// appropriate get call to enforce type safety. 
	// In this case, we are just logging so we treat all columns as strings using getString() method
	public static void LogCursorInfo(Cursor c) {
		Log.i("ShoppingApp", "*** Cursor Begin *** " + " Results:" + c.getCount() + " Columns: " + c.getColumnCount());

		// Print column names
		String rowHeaders = "|| ";
		for (int i = 0; i < c.getColumnCount(); i++) {

			rowHeaders = rowHeaders.concat(c.getColumnName(i) + " || ");
		}
		Log.i("ShoppingApp", "COLUMNS " + rowHeaders);

		// Print records
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			String rowResults = "|| ";
			for (int i = 0; i < c.getColumnCount(); i++) {
				rowResults = rowResults.concat(c.getString(i) + " || ");
			}
			Log.i("ShoppingApp", "Row " + c.getPosition() + ": " + rowResults);

			c.moveToNext();
		}
		Log.i("ShoppingApp", "*** Cursor End ***");
	}
}


