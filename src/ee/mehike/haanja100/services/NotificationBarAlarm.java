package ee.mehike.haanja100.services;

//http://www.e-nature.ch/tech/android-sdk-add-a-repeating-alarmmanager-to-the-autostart-displaying-messages-in-the-notification-bar/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ee.mehike.haanja100.MainActivity;
import ee.mehike.haanja100.R;
import ee.mehike.haanja100.util.Util;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class NotificationBarAlarm extends BroadcastReceiver {

	NotificationManager notifyManager;
	public static int counter=0;

	@Override
	public void onReceive(Context context, Intent intent) {
		WakefulIntentService.acquireStaticLock(context);
		Log.d("NotificationAlarm", "onReceive");

		//int arv = intent.getIntExtra(BaseActivity.ARV, 0);
		//int kustutatud = intent.getIntExtra(BaseActivity.KUSTUTATUD, 0);
		
		SharedPreferences settings = context.getSharedPreferences(ee.mehike.haanja100.util.Util.PREFERENCE_FILENAME, 0);
		boolean logged=false;
		//ShoppingApp.customToastLong(this, "logged_in="+settings.getBoolean("logged_in", false)+" accepted="+settings.getBoolean("logged_in", false));
     	if (settings.contains("logged_in"))
     		logged=settings.getBoolean("logged_in", false);
     	else 
     		logged=false;
     	String username="";
     	//if (logged && settings.contains("logged_username") && !"".equals(settings.getString("logged_username", ""))) {
     		long user_id =  settings.getLong("logged_in_userid", -1);
     		long user_group_user_id =  settings.getLong("logged_in_user_group_id", -1);
     		username=settings.getString("logged_username", "");

     		notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
     		boolean vibrate=true;
     		long arv=0;
     		long kustutatud=0;
     		NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context)
     		.setAutoCancel(true)
     		.setContentInfo("Urgent")
     		.setNumber(++counter)
     		.setContentTitle("Uus: Andmed on sünkroonis serveriga")
     		.setContentText(username+", Saadeti ("+arv+"), kustutati ("+kustutatud+") sõnumit")
     		.setTicker("Uus teade")
     		.setWhen(System.currentTimeMillis())
     		//.setDefaults(vibrate ? Notification.DEFAULT_ALL : Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS)
     		.setSmallIcon(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? 
					R.drawable.ic_notification : R.drawable.ic_notification_deprecated);
     		//.setSmallIcon(R.drawable.ic_launcher);
		
     		mNotifyBuilder.setContentText(username+", teade: "+Util.getDatetime(System.currentTimeMillis()));
     		//.setNumber(++counter);

		
     		// This Activity will be started when the user clicks the notification
     		// in the notification bar
     		Intent notificationIntent = new Intent(context, MainActivity.class);

     		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
     		//Notification notif = new Notification(R.drawable.ic_launcher, "Tere, "+username, System.currentTimeMillis());

     		// Play sound?
     		// If you want you can play a sound when the notification shows up.
     		// Place the MP3 file into the /raw folder.
     		//notif.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.jingle);

     		//notif.setLatestEventInfo(context, "Ostukorvi teade "+username, "Teade: "+Util.getDatetime(System.currentTimeMillis()), contentIntent);
		
Toast.makeText(context, "NotificationBarAlarm", 5000).show();
			//notifyManager.notify(1, notif);
			//notifyManager.notify(1, mNotifyBuilder.build());
			Notification notification = mNotifyBuilder.getNotification();
			notification.setLatestEventInfo(context, "Korv: "+Util.getDatetime(System.currentTimeMillis()), username+", Saadeti ("+arv+"), kustutati ("+kustutatud+") sõnumit", contentIntent);
		
			//PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
			//notification.contentIntent = contentIntent;

/*			RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
			contentView.setImageViewResource(R.id.image, R.drawable.icon); // for displaying icon on notification
			contentView.setTextViewText(R.id.not_text, "Your text here"); // for displaying text on notification
		
			notification.contentView = contentView;
*/
		
		
			/*PendingIntent pi = getPendingIntent(context);
			android.app.Notification.Builder builder = new Notification.Builder(context) 
			.setContentTitle("IS Notification") 
			.setContentText("Inbox Style notification!!") 
			.setSmallIcon(R.drawable.ic_launcher);
			//.addAction(R.drawable.ic_alarm, "show activity", pi); 

			Notification notification2 = new android.app.Notification.InboxStyle(mNotifyBuilder) 
			.addLine("First message").addLine("Second message") 
			.addLine("Thrid message").addLine("Fourth Message") 
			.setSummaryText("+2 more").build(); 

			// Put the auto cancel notification flag 
			notification2.flags |= Notification.FLAG_AUTO_CANCEL; 
			NotificationManager notificationManager2 = context.getNotificationManager(); 
			notificationManager2.notify(0, notification2);*/ 

		
			//notification.add
			/*notification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear the notification
        	notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
        	notification.defaults |= Notification.DEFAULT_VIBRATE; //<span class="IL_AD" id="IL_AD7">Vibration</span>
        	notification.defaults |= Notification.DEFAULT_SOUND; // Sound
			 */
			notifyManager.notify(1, notification);

     	//}
     	context.startService(new Intent(context, StarterService.class));
	}
	

	
	public PendingIntent getPendingIntent(Context context) { 
		return PendingIntent.getActivity(context, 0, new Intent(context, 
				MainActivity.class), 0); 
  } 

	
	public void makeToast(Context context, String text) {
		Toast.makeText(context, "test notif="+text, 5000).show();
	}
	
	private PendingIntent makeMoodIntent(int moodId, Context context) {
        // The PendingIntent to launch our activity if the user selects this
        // notification.  Note the use of FLAG_UPDATE_CURRENT so that if there
        // is already an active matching pending intent, we will update its
        // extras (and other Intents in the array) to be the ones passed in here.
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class).putExtra("moodimg", moodId),
                PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }
    


/*    private PendingIntent makeDefaultIntent(Context context) {
        // A typical convention for notifications is to launch the user deeply
        // into an application representing the data in the notification; to
        // accomplish this, we can build an array of intents to insert the back
        // stack stack history above the item being displayed.
        Intent[] intents = new Intent[4];


        // First: root activity of ApiDemos.
        // This is a convenient way to make the proper Intent to launch and
        // reset an application's task.
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(context,
                com.shop.checklist.activity.CheckTitlesActivity.class));


        // "App"
        intents[1] = new Intent(context, com.shop.checklist.activity.MainActivity.class);
        intents[1].putExtra("com.example.android.apis.Path", "App");
        // "App/Notification"
        intents[2] = new Intent(context, com.shop.checklist.activity.CheckTitlesActivity.class);
        intents[2].putExtra("com.example.android.apis.Path", "App/Notification");


        // Now the activity to display to the user.
        intents[3] = new Intent(context, NotificationBarAlarm.class);


        // The PendingIntent to launch our activity if the user selects this
        // notification.  Note the use of FLAG_UPDATE_CURRENT so that if there
        // is already an active matching pending intent, we will update its
        // extras (and other Intents in the array) to be the ones passed in here.
        PendingIntent contentIntent = PendingIntent.getActivities(context, 0,
                intents, PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }
*/
    
	/*private Notification getDlNotif(Context c,PendingIntent contentIntent) {
	    Notification notification = new Notification(
	        R.drawable.button_blue, 
	        "Downloading ...", 
	        System.currentTimeMillis()
	    );
	 
	    RemoteViews dlNotifContentView = new RemoteViews(c.getPackageName(), R.layout.download_notification);
	    dlNotifContentView.setImageViewResource(R.id.dln_img,R.drawable.butt_dl_on);
	    dlNotifContentView.setTextViewText(R.id.dln_dlfile,"Downloading ...");
	    dlNotifContentView.setTextViewText(R.id.dln_dlnumber, status);
	    dlNotifContentView.setProgressBar(R.id.dln_progress, 100,        currentDownload.progress:0, false);
	    //set text colour and size for the title and text parts of the custom notification (if necessary ...)
	    if (getSDK()<10) {
	        NotificationUpdater.setTextTitleColourAndSize(this,dlNotifContentView,R.id.dln_dlfile);     
	        NotificationUpdater.setTextColourAndSize(this,dlNotifContentView,R.id.dln_dlnumber);
	    }
	    //set background to black for >= HoneyComb(11)
	    if (getSDK()>=11) {
	        dlNotifContentView.setInt(R.id.dln_ctnr, "setBackgroundColor", Color.BLACK);
	    }
	    notification.contentView=dlNotifContentView;
	    notification.contentIntent = contentIntent;
	    return notification;
	}*/
	 
	public static int getSDK() {
	    try {
	        return Integer.parseInt(Build.VERSION.SDK);
	    } catch (Throwable e) {
	        return 1;
	    }
	}

	
}
