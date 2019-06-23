package ee.mehike.haanja100.services;



import java.util.Calendar;

import ee.mehike.haanja100.HaanjaApp;
import ee.mehike.haanja100.MainActivity;
import ee.mehike.haanja100.R;
import ee.mehike.haanja100.data.HaanjaTable;
import ee.mehike.haanja100.model.Haanja;
import ee.mehike.haanja100.provider.HaanjaContent;
import ee.mehike.haanja100.util.Constants;
import ee.mehike.haanja100.util.Util;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

public class BackgroundService extends Service {

	private static final String TAG = "BackgroundService";
	private NotificationManager notificationMgr;
	private ThreadGroup myThreads = new ThreadGroup("ServiceWorker");

	@Override
	public void onCreate() {
		super.onCreate();
		Log.v(TAG, "in onCreate()");
		//Context ctx=HaanjaApp.dataManager
		notificationMgr =(NotificationManager)getSystemService(
				NOTIFICATION_SERVICE);
		/*SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, 0);
		String str=settings.getString("compname_pref", Constants.COMPNAME);
		String str2=settings.getString("name_pref", Constants.NAME);
		String str3=Util.displayCalcTime(getApplicationContext(), System.currentTimeMillis()+time);
		displayNotificationMessage(str, str2, str3);*/
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		int counter = 0;
		if (intent!=null && intent.getExtras()!=null)
			counter=intent.getExtras().getInt("counter");
		Log.v(TAG, "in onStartCommand(), counter = " + counter +", startId = " + startId);
		new Thread(myThreads, new ServiceWorker(counter), "Jooks").start();
		return START_STICKY;
	}

	class ServiceWorker implements Runnable {
		private int counter = -1;
		public ServiceWorker(int counter) {
			this.counter = counter;
		}

		public void run() {
			final String TAG2 = "ServiceWorker:" +Thread.currentThread().getId();
			// do background processing here... we’ll just sleep...
			try {
				Util.recalculateAll(getApplicationContext());
				//Util.summaryFields(getApplicationContext());
				SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, 0);
				long time=settings.getLong("last_time", 1000*60*60)-1000*60;

				String str=settings.getString("compname_pref", Constants.COMPNAME);
				String str2=settings.getString("name_pref", Constants.NAME);
				//String str3=Util.displayCalcTime(getApplicationContext(), System.currentTimeMillis()+time);
				//String str3=Util.displayCalcDate(getApplicationContext(), System.currentTimeMillis()+time, true);
				String str3="";
				
				displayNotificationMessage(str, str2, str3);
				
				Log.e(TAG2, "sleeping for 10 seconds. counter = " +counter+"   sleeptime="+Util.displayCalcTime(getApplicationContext(), time));
				Thread.sleep(time);
				//getContentResolver().
				//recalculateAll();
				//Util.recalculateAll(getApplicationContext());
            	//summaryFields();
				//getApplicationContext().get
				counter++;
				Log.v(TAG2, "... waking up");
			} catch (InterruptedException e) {
				Log.v(TAG2, "... sleep interrupted");
			}
		}
	}

    public String displayPace(double pace) {
    	long now = System.currentTimeMillis();

    	int h = (int) ((pace) / 3600); 
    	int m = (int) (((pace) / 60) % 60); 
    	int s = (int) ((pace) % 60); 
    	long ms = Util.getFraction(pace, 1);
	
    		Log.d("AEG", " now="+now+" date="+pace+" arvutatudaeg="+Util.getNr(h)+":"+Util.getNr(m)+":"+Util.getNr(s));
    		if (pace<=0) {
    			Calendar cal = Calendar.getInstance();
    			return " - ";
    		} else {
    			return " "+Util.getNr(m)+":"+Util.getNr(s)+","+ms+getApplicationContext().getString(R.string.kiirus);
    		}
    }
    
    public final static String PREFERENCE_FILENAME = "HaanjaSettings";
    
	//arvutame kõik haanja read üle ja kirjutame keskmised kiirused tagasi baasi ning eeldatava aja ka
	public boolean recalculateAll() {
		boolean ok=true;
		boolean service=false;
		Context ctx=getApplicationContext();
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
	       	    	SharedPreferences settings = ctx.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
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
//	       	        if (jaanud_aega>0) {
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
//		       	        if (jaanud_aega>0) {
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
   	    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, 0);

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
   	     	setEditor.putString("future_lastlap_finish_datetime", Util.displayCalcTime(ctx.getApplicationContext(), (Util.normalize_time(System.currentTimeMillis()))));
   	     	setEditor.commit();
		}
		c.close();
		c=null;
		return ok;
	}
	
	public boolean recalculateAll1() {
		boolean ok=true;
		Cursor c = HaanjaApp.dataManager.getHaanjaCursor();
		if (c.getCount()>0) {
			c.moveToFirst();
			Haanja haanja_prev = new Haanja();
			long lap_start_time=c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.START_TIME));
			long start_time=lap_start_time;
			long lap_end_time=0;//c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.END_TIME));
			double distance = 0;//c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.SPLIT));
			long id = 0;//c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns._ID));
			long time = 0;//c.getLong(c.getColumnIndex(HaanjaTable.HaanjaColumns.TIME));
			long f_time=time;
			double lap_pace=0;
			double avg_pace=0;
			long split_nr=0;
			
	        while (c.isAfterLast() == false) {
	            //leiame esimese rea
	        	//ja arvutame ringi keskmise ning liidame koguajale ja km-le
	        	haanja_prev = Util.buildHaanjaFromCursor(c);
	        	//arvutused sooritan ainult siis kui lõppaeg on olemas - ring täis
	        	if (haanja_prev.getEnd_time()>0 && haanja_prev.getTime()>0) {
	        		split_nr = haanja_prev.getSplit();
	        		//ringiaeg
	        		haanja_prev.setTime(haanja_prev.getEnd_time()-haanja_prev.getStart_time());
	        		//kogu kulunud aeg
	        		time = time + haanja_prev.getTime();
	        		//hetkel läbitud maa
	        		distance = distance + Constants.SPLIT_KM;
	        		//nüüd leiame keskmised kiirused
	        		
	        		lap_pace = haanja_prev.getTime()/1000/Constants.SPLIT_KM;
	        		avg_pace = time/1000/(Constants.SPLIT_KM*haanja_prev.getSplit());
	        		haanja_prev.setAvg_pace(displayPace(avg_pace));
	        		haanja_prev.setLap_pace(displayPace(lap_pace));
	        		Log.d("arvutamine","distance="+distance+" time="+time+" split_nr="+split_nr+" lap_time="+haanja_prev.getTime()+" const_km="+Constants.SPLIT_KM+
	        				" läbitud="+Constants.SPLIT_KM*haanja_prev.getSplit()+" lap_pace="+lap_pace+" avg_pace="+avg_pace+" a_pace="+haanja_prev.getAvg_pace()+" l_pace="+haanja_prev.getLap_pace());
	        		//nüüd salvestame keskmised kiirused baasi ja muud muudatused
	        		getContentResolver().update(Uri.parse(HaanjaContent.Haanja.CONTENT_URI+"/"+haanja_prev.getId()), Util.haanjaValues(haanja_prev), null, null);
	        		
	        	}
	        	
	        	//kui on olemas järgmine rida, siis
	       	    if (c.moveToNext()) {
	       	    	
	       	    } else { //kui oli viimnae rida sisi teeme seda...
	       	    	//leiame eeldatavad kiirused ja lõppajad
	       	    	//ning paneme properties faili need
	       	    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, 0);
	       	    	//if (!settings.contains("user_id")) {
	       	     	Log.i("HaanjaPrefs", "tegevused prefferences failiga, svaed settings");
	       	     	SharedPreferences.Editor setEditor = settings.edit();
	       	     	//if (!settings.contains("future_time"))
	       	     	//	setEditor.remove("");
	       	     	//if (!settings.contains("future_finish_time"))
	       	     	
	       	     	setEditor.putLong("future_time", 0);
	       	     	setEditor.putLong("splits", split_nr);
	       	     	setEditor.putLong("start_time_long", start_time);
	       	     	setEditor.putString("start_time", Util.displayCalcTime(getApplicationContext(), start_time));
	       	     	setEditor.putLong("normalized_start_time_long", Util.normalize_time(start_time));
	       	     	setEditor.putString("normalized_start_time", Util.displayCalcTime(getApplicationContext(), Util.normalize_time(start_time)));
	       	     
	       	     	//keskmise ringiaja järgi finishi aja ennustamine (pole eriti reaalne)
	       	     	//praegune aeg + jäänud+km*km tempo)
	       	     	long avglap_finish_time = time + (long)(avg_pace*1000*(Constants.HAANJA100_KM-distance));
	       	     	//kui välja arvutatud finsihiaeg on suurem kui praegune aeg, siis arvutame praeguse aja põhjal (1ringiaeg rohkem
	       	     	//kuna reaalselt kindlasti läheb kauem aega
	       	     	//enne arvutame praeguseks kulunud ringiaja
	       	     	Calendar now=Calendar.getInstance();
	       	     	long time_now=Util.getLongTimeFromstring("test: "+Util.getHourStr(now)+":"+Util.getMinuteStr(now)+":"+Util.getSecondStr(now));
	       	     	Log.i("TESTIKE1", " avg fin time="+Util.displayCalcTime(getApplicationContext(), (start_time/1000+avglap_finish_time/1000))+"  uusaeg="+Util.displayCalcTime(getApplicationContext(), start_time+time+time_now)+"  ehk siis esimene="+
	       	     		start_time+avglap_finish_time+"  ja teine="+start_time+time+time_now+"   time_now="+time_now+"   time="+time+"  start_time="+start_time+
	       	     		"   system="+System.currentTimeMillis()+"  kulunud="+(System.currentTimeMillis()-start_time)+" avglapfintime="+avglap_finish_time+
	       	     		" start_time inimesekuul="+Util.displayCalcTime(getApplicationContext(), Util.normalize_time(start_time))+
	       	     		"  avgtime inimse kujul="+Util.displayCalcTime(getApplicationContext(), avglap_finish_time)+
	       	     		"  haanja_prevendtime="+haanja_prev.getStart_time());
	       	     	if ((start_time+avglap_finish_time) < (start_time+time+(time_now)-haanja_prev.getStart_time())) {
	       	     		Log.i("SEES1"," läks sisi sisse");
	       	     		long a_time=time+(System.currentTimeMillis()-start_time);
	       	     		avg_pace = a_time/1000/(Constants.SPLIT_KM*(haanja_prev.getSplit()));
	       	     		avglap_finish_time = a_time + (long)(avg_pace*1000*(Constants.HAANJA100_KM-distance));
	       	     	}
	       	     	setEditor.putString("future_avglap_finish_time", Util.displayCalcTime(getApplicationContext(), avglap_finish_time) );
	       	     	
	       	     	long lastlap_finish_time = time + (long)(lap_pace*1000*(Constants.HAANJA100_KM-distance));
	       	     	Log.i("TESTIKE2", " avg fin time="+Util.displayCalcTime(getApplicationContext(), start_time+avglap_finish_time)+"  uusaeg="+Util.displayCalcTime(getApplicationContext(), start_time+time+time_now)+"  ehk siis esimene="+
		       	     		start_time+lastlap_finish_time+"  ja teine="+start_time+time+time_now+"   time_now="+time_now+"   time="+time+"  start_time="+start_time+
		       	     		"   system="+System.currentTimeMillis()+"  kulunud="+(System.currentTimeMillis()-start_time));
		       	     	if ((start_time+lastlap_finish_time)< (start_time+time+time_now-haanja_prev.getStart_time())) {
		       	     	Log.i("SEES2"," läks sisi sisse");
		       	     		long l_time=time+(System.currentTimeMillis()-start_time);
		       	     		lap_pace = l_time/1000/(Constants.SPLIT_KM*(haanja_prev.getSplit()));
		       	     		lastlap_finish_time = l_time + (long)(lap_pace*1000*(Constants.HAANJA100_KM-distance));
		       	     	}

		       	     	
	       	     	setEditor.putString("future_lastlap_finish_time", Util.displayCalcTime(getApplicationContext(), lastlap_finish_time));
	       	     	setEditor.putLong("future_lastlap_finish_time_long", lastlap_finish_time);
	       	     	setEditor.putString("future_lastlap_finish_datetime", Util.displayCalcTime(getApplicationContext(), (Util.normalize_time(start_time)+lastlap_finish_time)));
	       	     	
	       	        Log.i("HaanjaPrefs", "prefferences. time="+time+" + "+(long)(avg_pace*1000*(Constants.HAANJA100_KM-distance))+" või + "+(long)(lap_pace*1000*(Constants.HAANJA100_KM-distance)));
	       	     	Log.i("HaanjaPrefs", "tegevused prefferences failiga. avg_lap_finish="+Util.displayCalcTime(getApplicationContext(), avglap_finish_time)+
	       	     			"  last_lap_finish="+Util.displayCalcTime(getApplicationContext(), lastlap_finish_time)+" läbida veel km="+(Constants.HAANJA100_KM-distance)+
	       	     			" lap_pace="+lap_pace+" avg_pace="+avg_pace+" avglap_finish_time="+avglap_finish_time+" lastlap_finish_time="+lastlap_finish_time+
	       	     			" start_time_long="+start_time+" start_time="+Util.displayCalcTime(getApplicationContext(), start_time)+" nomralized_time_long="+Util.normalize_time(start_time)+" normalized_time="+Util.displayCalcTime(getApplicationContext(), Util.normalize_time(start_time)));
	       	     	
	       	     	setEditor.commit();
	       	     	
	       	     //}
	       	    }
	        }
		} else {
			SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, 0);
   	    	//if (!settings.contains("user_id")) {
   	     	Log.i("HaanjaPrefs", "tegevused prefferences failiga, svaed settings");
   	     	SharedPreferences.Editor setEditor = settings.edit();
   	     	setEditor.putLong("future_time", 0);
   	     	setEditor.putLong("splits", 0);
   	     	setEditor.putLong("start_time_long", System.currentTimeMillis());
   	     	setEditor.putString("start_time", Util.displayCalcTime(getApplicationContext(), System.currentTimeMillis()));
   	     	setEditor.putLong("normalized_start_time_long", Util.normalize_time(System.currentTimeMillis()));
   	     	setEditor.putString("normalized_start_time", Util.displayCalcTime(getApplicationContext(), Util.normalize_time(System.currentTimeMillis())));
   	     	setEditor.putString("future_avglap_finish_time", Util.displayCalcTime(getApplicationContext(), -1) );
   	     	setEditor.putString("future_lastlap_finish_time", Util.displayCalcTime(getApplicationContext(), -1));
   	     	setEditor.putLong("future_lastlap_finish_time_long", -1);
   	     	setEditor.putString("future_lastlap_finish_datetime", Util.displayCalcTime(getApplicationContext(), (Util.normalize_time(System.currentTimeMillis()))));
   	     	setEditor.commit();
		}
		c.close();
		c=null;
		return ok;
	}
	


	
	
	@Override
	public void onDestroy() {
		Log.v(TAG, "in onDestroy(). Interrupting threads and cancelling notifications");
		myThreads.interrupt();
		notificationMgr.cancelAll();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.v(TAG, "in onBind()");
		return null;
	}

	//public Activity ctx;
	
	private void displayNotificationMessage(String message, String user, String time) {
		message=message+" "+time;
		Notification notification = new Notification(R.drawable.ic_launcher, message, System.currentTimeMillis());
		//notification.flags = Notification.FLAG_NO_CLEAR;
		notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
		notification.setLatestEventInfo(this, "Ultrajooks->"+user, message,
				contentIntent);
		//this.ctx=(Activity)contentIntent;
		notificationMgr.notify(0, notification);
	}
	
}
