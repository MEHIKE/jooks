package ee.mehike.haanja100.services;

//http://www.e-nature.ch/tech/android-sdk-add-a-repeating-alarmmanager-to-the-autostart-displaying-messages-in-the-notification-bar/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

//import com.shop.checklist.receiver.NotificationBarAlarm;
//import com.shop.checklist.receiver.OnAlarmReceiver;

import ee.mehike.haanja100.HaanjaApp;
import ee.mehike.haanja100.util.Constants;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class StarterService extends WakefulIntentService { //rynno, lisatud Service asemele laiendus
private static final String TAG = "MyService";

	public StarterService() {
		super("StarterService");
	}
	/**
	 * The started service starts the AlarmManager.
	 */
/*	@Override
	public void onStart(Intent intent, int startid) {
		Intent i = new Intent(this, NotificationBarAlarm.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

		// Repeat the notification every 15 seconds (15000)
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		if (Util.isForeground2(this))
			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60*1000, pi);
		else
			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10*60*1000, pi);

		Toast.makeText(this, "My Service started - StarterService", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service stopped - StarterService", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}
*/	
    @Override
    protected void onHandleIntent(Intent intent) {
        //TasksDataSource db = TasksDataSource.getInstance(this); //get access to the instance of TasksDataSource
        //TaskAlarm alarm = new TaskAlarm();
        
        Toast.makeText(this.getApplicationContext(), "StartService started", 5000).show();
 
        /*List<Task> tasks = DbUtils.getAllTasks(this.getApplicationContext());//db.getAllTasks(); 
        //Get a list of all the tasks there
        for (Task task : tasks) {
            // Cancel existing alarm
            alarm.cancelAlarm(this.getApplicationContext(), task.getID());
             
            //Procrastinator and Reminder alarm
            if(task.isPastDue()){
                alarm.setReminder(this, task.getID());
            }
             
            //handle repeat alarms
            if(task.isRepeating() && task.isCompleted()){
                task = alarm.setRepeatingAlarm(this.getApplicationContext(), task.getID());
            }
             
            //regular alarms
            if(!task.isCompleted() && (task.getDateDue() >= System.currentTimeMillis())){
                alarm.setAlarm(this.getApplicationContext(), task);
            }
        }*/
     	SharedPreferences settings = HaanjaApp._context.getSharedPreferences(ee.mehike.haanja100.util.Constants.PREFERENCE_FILENAME, 0);
     	String strAlarmFG = settings.getString(Constants.ALARM_TIME_FG, Constants.DEFAULT_ALARM_TIME_FG);
     	String strAlarmBG = settings.getString(Constants.ALARM_TIME_BG, Constants.DEFAULT_ALARM_TIME_BG);
     	long runTime = settings.getLong(Constants.ALARM_TIME, System.currentTimeMillis()-1);
		Intent i = new Intent(this, NotificationBarAlarm.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// Repeat the notification every 15 seconds (15000)
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Calendar cal = Calendar.getInstance();
		//Calendar calRunTime = Calendar.getInstance();
		int iAlarm=0;
		if (ee.mehike.haanja100.util.Util.isForeground2(this)) {
			iAlarm = Integer.parseInt(strAlarmFG);
		}
		else {
			iAlarm = Integer.parseInt(strAlarmBG);
		}
		//int iRunTime=Integer.parseInt(runTime);
		cal.add(Calendar.MINUTE, iAlarm);
		//calRunTime.add(Calendar.MINUTE, iAlarm);
		long lAlarm = cal.getTimeInMillis();

		
		if (runTime<=lAlarm) {
			SharedPreferences.Editor setEditor = settings.edit();
			setEditor.putLong(Constants.ALARM_TIME, lAlarm);
			setEditor.commit();
			i = doSync(i);
			//am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60*1000, pi);
			
			PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
			am.set(AlarmManager.RTC_WAKEUP, lAlarm, pi);
		}
		//String strAlarm = prefs.getString(BaseActivity.ALARM_TIME, BaseActivity.DEFAULT_ALARM_TIME);
		//Calendar cal = Calendar.getInstance();    	
		//int iAlarm = Integer.parseInt(strAlarm);
		//cal.add(Calendar.MINUTE, iAlarm);
		//long lAlarm = cal.getTimeInMillis();
		//Intent intent =  new Intent(context, OnAlarmReceiver.class)
		//	.putExtra(Task.EXTRA_TASK_ID, id)
		//	.putExtra(TaskAlarm.ALARM_EXTRA, BaseActivity.ALARM_TIME);
		
		//AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		//am.set(AlarmManager.RTC_WAKEUP, lAlarm, PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT));

		
		
        super.onHandleIntent(intent);
    }
    
    private Intent doSync(Intent intent) {
    	
    	Context context=this;
		SharedPreferences settings = context.getSharedPreferences(ee.mehike.haanja100.util.Util.PREFERENCE_FILENAME, 0);
		boolean logged=false;
		//ShoppingApp.customToastLong(this, "logged_in="+settings.getBoolean("logged_in", false)+" accepted="+settings.getBoolean("logged_in", false));
     	if (settings.contains("logged_in"))
     		logged=settings.getBoolean("logged_in", false);
     	else 
     		logged=false;
     	String username=null;
     	if (logged && settings.contains("logged_username") && !"".equals(settings.getString("logged_username", ""))) {
     		long user_id =  settings.getLong("logged_in_userid", -1);
     		long user_group_user_id =  settings.getLong("logged_in_user_group_id", -1);
     		//saame arvu, palju tuli ja uue meetodiga käime üle ja jagame laiali
     		//või teeme seda kohe??
     	}
     	return intent;
    }
    

}