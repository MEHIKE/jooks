package ee.mehike.haanja100;

//import com.shop.checklist.fragments.TitlesFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import ee.mehike.haanja100.adapter.HaanjaCursorAdapter;
import ee.mehike.haanja100.data.DataManagerImpl;
import ee.mehike.haanja100.data.HaanjaTable;
import ee.mehike.haanja100.fragments.SplitsFragment;
import ee.mehike.haanja100.fragments.dialog.ChangeTimeDialogFragment;
import ee.mehike.haanja100.fragments.dialog.FacebookDialogFragment;
import ee.mehike.haanja100.fragments.dialog.FacebookEditDialogFragment;
import ee.mehike.haanja100.fragments.dialog.HelpDialogFragment;
import ee.mehike.haanja100.listener.OnChangeTimeDialogDoneListener;
import ee.mehike.haanja100.listener.OnFbDialogDoneListener;
import ee.mehike.haanja100.model.Haanja;
import ee.mehike.haanja100.provider.HaanjaContent;
import ee.mehike.haanja100.util.ActivityUtils;
import ee.mehike.haanja100.util.Constants;
import ee.mehike.haanja100.util.Util;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.widget.Toast;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.WebDialog;


public class MainActivity extends FragmentActivity implements OnChangeTimeDialogDoneListener, OnFbDialogDoneListener { //extends Activity {

	public static final String TAG = "Haanja|MainActivity";
	public static SplitsFragment tf;
	public static boolean mSort=true;
	private Button shareButton;

	@Override
	public void onFbDialogDone(String tag, boolean cancelled, CharSequence message, long rec_id) {
		// TODO Auto-generated method stub
		//publisfacebook siia
		Log.d("FbDialogDone", "tagasi, rec_id="+rec_id);
		if (!cancelled)
			this.publishStory(rec_id, message);
	}
	
	public void onChangeTimeDialogDone(String tag, boolean cancelled, CharSequence end_time,
			CharSequence time, long split, long rec_id){
	}
	
	public void onChangeTimeDialogDone(String tag, boolean cancelled, long end_time, 
			long time, long split, long rec_id){
		//Cursor first_cur = getContentResolver().query(HaanjaContent.Haanja.CONTENT_URI, Haanja.PROJECTION, "split="+split, null, "split desc");
		Cursor cur = getContentResolver().query(Uri.parse(HaanjaContent.Haanja.CONTENT_URI+"/"+rec_id), Haanja.PROJECTION, null, null, null);
		Haanja haanja = Util.buildHaanjaFromCursor(cur);
		cur.close();
		cur=null;
		//haanja.setEnd_time(Util.getLongTimeFromstring(end_time.toString().trim()));
		Log.e("TAGASI AJAMUUTMISEST", "end_time -> "+(end_time)+"="+Util.displayCalcTime(app.getApplicationContext(), end_time).trim());
		//Log.e("TAGASI AJAMUUTMISEST", (end_time.toString().trim())+"="+Util.getLongTime(end_time.toString().trim()));
		Log.e("TAGASI AJAMUUTMISEST", "time"+" -> "+(time)+"="+Util.displayCalcTime(app.getApplicationContext(),time).trim());
		end_time=Util.normalize_endtime(haanja.getStart_time(), time, end_time);
		
		haanja.setEnd_time(end_time);
		haanja.setTime(time);
		//Log.e("TAGASI AJAMUUTMISEST", (time.toString().trim())+"="+Util.getLongTime(time.toString().trim()));
		app.getApplicationContext().getContentResolver().update(Uri.parse(HaanjaContent.Haanja.CONTENT_URI+"/"+haanja.getId()), Util.haanjaValues(haanja), null, null);
		Util.recalculateAll(this);
    	Util.summaryFields(this);
    	//this.oncr
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    /*if (state.isOpened()) {
	        Log.i(TAG, "Logged in...");
	    } else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	    }*/
		if (state.isOpened()) {
	        shareButton.setVisibility(View.VISIBLE);
	        if (pendingPublishReauthorization && 
	                state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
	            pendingPublishReauthorization = false;
//publishStory();
	        }

	            		Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

	            					  // callback after Graph API response with user object
	            					@Override
	            					  public void onCompleted(GraphUser user, Response response) {
	            						if (user != null) {
	            							  TextView welcome = (TextView) findViewById(R.id.welcome);
	            							  welcome.setText("Tere " + user.getName());
	            						}
	            						
	            					  }
	            					});
	            	

	    } else if (state.isClosed()) {
	        shareButton.setVisibility(View.INVISIBLE);
	        //shareButton.setVisibility(View.GONE);
	        
	            							  TextView welcome = (TextView) findViewById(R.id.welcome);
	            							  welcome.setText(this.getString(R.string.welcome));

	    }
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	public UiLifecycleHelper uiHelper;
	
	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.main, container, false);

	    return view;
	}*/
	
	/*@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.main, container, false);

	    return view;
	}*/
	
	private HaanjaApp app;
	
	public HaanjaApp getApp() {
		return this.app;
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {			  
				Bundle bundle = msg.getData();
				String string = bundle.getString("myAeg");
				TextView myTextView = (TextView)findViewById(R.id.aeg);
				myTextView.setText(string);
		}
	}; 

	// Intent filter for incoming broadcasts from the IntentService
	IntentFilter mBroadcastFilter;
	 
	// Instance of a local broadcast manager
	private LocalBroadcastManager mBroadcastManager;
	
	/**
	 * 
	 * http://www.javacodegeeks.com/2013/10/android-activity-recognition.html
	 * 
	 * http://developer.android.com/training/location/activity-recognition.html
	 * 
	 * http://tsicilian.wordpress.com/2013/09/23/android-activity-recognition/
	 * 
	 * http://java.dzone.com/articles/android-geofencing-google-maps
	 * 
	* Broadcast receiver that receives activity update intents
	* This receiver is local only. It can't read broadcast Intents from other apps.
	*/
	BroadcastReceiver updateListReceiver = new BroadcastReceiver() {
	   @Override
	   public void onReceive(Context context, Intent intent) {
	    // When an Intent is received from the update listener IntentService,
	    // update the display.
	     //updateActivityHistory();
	   }
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        app = (HaanjaApp) getApplication();
        
        mBroadcastManager = LocalBroadcastManager.getInstance(this);
        
        // Create a new Intent filter for the broadcast receiver
        mBroadcastFilter = new IntentFilter(ActivityUtils.ACTION_REFRESH_STATUS_LIST);
        mBroadcastFilter.addCategory(ActivityUtils.CATEGORY_LOCATION_SERVICES);
        
        //setContentView(R.layout.activity_main);
        uiHelper = new UiLifecycleHelper((Activity)this, callback);
        uiHelper.onCreate(savedInstanceState);

        act=this;
        
        Intent intent = new Intent(MainActivity.this, ee.mehike.haanja100.services.BackgroundService.class);
	    int counter=0;
	    intent.putExtra("counter", counter++);
     	startService(intent);
     	
     	this.startService();
     	
     	SharedPreferences settings = getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
     	if (!settings.contains("sort_list"))
     		MainActivity.mSort=settings.getBoolean("sort_list", true);
     	else {
     		SharedPreferences.Editor setEditor = settings.edit();
     		setEditor.putBoolean("sort_list", true);
     		setEditor.commit();
     	}
     	
     // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "ee.mehike.haanja100", 
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e) {
        	Log.e("KeyHash:", "Name not found Exception");
        } catch (NoSuchAlgorithmException e) {
        	Log.d("KeyHash:", "No such algorithm Exception");
        }
     	
     	//	setEditor.remove("");
     	
     	//tegelikkuses  aasta tähendab tegelikult aasta tabeli_id-d
     	//sealt peavad tulema setingud ja aasta
     	//mis tähendab - vaja juurde teha 2tabelit lisaks
     	//võistlus - id, nimi, ringid, pikkus
     	//aasta - id, võistlus_id, aasta,  best_ajad(eesmärgid) võivad iga aasta olla erinevad
     	//küsitakse proge töölepanekul üle ja salvestatakse kasutamiseks aasta tabeli id, mis pannakse ka külge
     	//kui võistlust pole loodud, saab selle luua ning samamoodi aasta, kui 
     	//antud võistluse kohta pole aktiivset aastat
     	//muidu küsitakse dialog Võistluse nimi (valik spinner) ja all aasta (muutub vastavalt võistlusele, aktiivne valik praegune aasta)
     	//kui aasta andmeid pole siis hüppab ette uus dialog kus saab andmed sisestada.
     	//tundub, et shared preference mõte kaob üldse ära....
     	//kõige selle puhul saab appi kasutada suvalisel võistlusel eriaastatel ja erinevate aastate andmeid võrrelda hiljem.
     	
     	
     	//siin peaks kontrollima, kas preferencides on kirjas aktiivne aasta
     	//kui seda pole siis viskab ette dialogi aasta küsimusega (def=praegune aasta)
     	//kui on aga see ei klapi hetkel käiva aastaga, siis viskab ette sama dialoogi.
     	//dailoogiaknas on ainult valik (ise sisestada ei saa aastat)
     	//kui andmebaasis pole ridu, siis aktiivne aasta ja ok
     	//kui on read, siis grupeeritakse aasta järgi kokku (group by aasta, samuti order by suurenevas järjestuses)
     	//ja valikusse pannakse need aastad (kui praegust aastat pole, siis see lisatakse ja on aktiivne)
     	//käesolev aasta on alati valikus aktiivne, et piisaks ok valikust.
     	//aktiivset aastat saab muuta, kas preference ekraanilt või
     	//appi title reale pannakse aktiivne aasta kirja, selle peal klõpsides. (nupukujul)
     	//aktiivse aasta kontrolli rohkem ilmselt vaja pole? või küsiks enne
     	//iga teatud nupuvajutust kui aktiivne aasta pole käesolev aasta?
     	
     	
        FragmentManager.enableDebugLogging(true);
		DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Toast.makeText(this, "Resolutsioon: "+dm.widthPixels+"/"+dm.heightPixels, 2000);
        app.customToastLong1(this, "Resolutsioon: "+dm.widthPixels,"ja... "+dm.heightPixels);
        
        setContentView(R.layout.main);
        //this.runOnUiThread(new Runnable() {
        //Runnable runnable = new Runnable() {
        new Thread(new Runnable() {
	        public void run() {
            	Message msg = handler.obtainMessage();
    			Bundle bundle = new Bundle();
    			//SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss");
    			long time=0;
    			long start_time=0;
    			SharedPreferences settings = act.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
       	     	if (settings.contains("start_time_long")) {
       	     		start_time=settings.getLong("start_time_long", 0);
       	     	}
       	     	long aeg=time-start_time;
       	     Cursor c = HaanjaApp.dataManager.getHaanjaCursor();
 			if (c.getCount()==0) {
 				//c.close();
 				//c=null;
 				start_time=0;
 			}
 			c.close();
 			c=null;
       	     	if (start_time>0)
       	     	while (true) {
	        		synchronized (this) {
	        		  try {
	        			  wait(1000);
	        			  time=System.currentTimeMillis();
	        			  //String dateString = dateformat.format(new Date());
	        			  aeg=time-start_time;
	        			  if (settings.contains("end_time_long")) {
	             	     		aeg=settings.getLong("end_time_long", 0);
	             	      }  
	        			  final TextView myTextView = (TextView)findViewById(R.id.aeg);
	        			  final String str="Aeg: "+Util.displayCalcTime(act, aeg);
	        			  myTextView.post(new Runnable() {
	        				 public void run() {
	        					 myTextView.setText(str);
	        				 }
	        			  });
	        			  /*bundle.putString("myAeg", Util.displayCalcTime(act, aeg)+" - "+aeg);
	        			  //bundle.putString("myAeg", ""+aeg);
	        			  msg.setData(bundle);
	        			  handler.sendMessage(msg);*/
	        		  } catch (Exception e) {e.printStackTrace();}	
	        	}
	        	}
	        }
      }
	   ).start();     
        TextView myRecordView = (TextView)findViewById(R.id.rekord);
		//SharedPreferences settings = act.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
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
      //Thread mythread = new Thread(runnable);
      //mythread.start();

        
        if (savedInstanceState != null) {
            pendingPublishReauthorization = 
                savedInstanceState.getBoolean(PENDING_PUBLISH_KEY, false);
        }

     // start Facebook Login
       /* Session.openActiveSession(this, true, new Session.StatusCallback() {
        	// callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception) {
            	if (session.isOpened()) {
            		//tegevused
            		// make request to the /me API
            		Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

            					  // callback after Graph API response with user object
            					@Override
            					  public void onCompleted(GraphUser user, Response response) {
            						if (user != null) {
            							  //TextView welcome = (TextView) findViewById(R.id.welcome);
            							  //welcome.setText("Hello " + user.getName() + "!");
            						}
            						
            					  }
            					});
            	}
            	
            	
            }
          });*/
	    
          
        LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
        authButton.setFragment(tf);
        //authButton.setFragment(this.getSupportFragmentManager().findFragmentById(R.id.authButton));
        authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
        
        shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shareStory();
            	
            	Animation hyperspaceJump = AnimationUtils.loadAnimation(app.getApplicationContext(), R.anim.hyperspace_jump);
    			v.startAnimation(hyperspaceJump);
    			
            	//v.startAnimation(hyperspaceJump);
            	
            	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    	    	ft = ((FragmentActivity)act).getSupportFragmentManager().beginTransaction();
    			Util.recalculateAll(act);
    			Util.summaryFields(act);

    	    	String fbtext = getShareText();
    	    	Log.e("SplitText", "FB text="+fbtext);
    	    	
    	    	//FacebookDialogFragment pdf = FacebookDialogFragment.newInstance( R.string.facebook, rec_id );
    	    	FacebookEditDialogFragment pdf = FacebookEditDialogFragment.newInstance( R.string.facebook, -1, fbtext );
    			//Bundle bundle=new Bundle();
    			//bundle.putString("name", search_item.getText().toString());
    			//pdf.setArguments(bundle);
    			pdf.show(ft, "test show");

            }
        });
        
        
        Session.openActiveSession(this, true, new Session.StatusCallback() {
        	// callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception) {
            	if (session.isOpened()) {
            		//tegevused
            		// make request to the /me API
            		Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

            					  // callback after Graph API response with user object
            					@Override
            					  public void onCompleted(GraphUser user, Response response) {
            						if (user != null) {
            							  TextView welcome = (TextView) findViewById(R.id.welcome);
            							  welcome.setText("Tere " + user.getName() + "!");
            						}
            						
            					  }
            					});
            	}
            	
            	
            }
          });
        
        
        /*Session session = Session.getActiveSession();
	    if (session != null && (session.isOpened() )) { //|| session.isClosed()) ) {
	    	Bundle fb = session.getAuthorizationBundle();
	    	Log.e("BUNDLE", fb.toString());
	        //onSessionStateChange(session, session.getState(), null);
	    } else Log.e("BUNDLE", "state="+session.getState()+" isClosed="+session.isClosed()+" isOpened="+session.isOpened());*/
	    
        
        // siia kas esimene nupp on start või ringi vaheaeg ja vastav tekst nupule
        Button mBtnSplit = (Button) findViewById(R.id.bt_split);
		if (mBtnSplit==null)
			Log.e("TitlesFragment","pole nuppu layouti peal sellise id-ga R.id.bt_split");
		
		//HaanjaApp.dataManager = new DataManagerImpl(this);
		Cursor c = HaanjaApp.dataManager.getHaanjaCursor();
		if (c.getCount()==0) {
			mBtnSplit.setText(getResources().getText(R.string.add_start));
			//Toast.makeText(this, "Pole veel ridu", 5000).show();
			//siin oleks vaja teha teised nupud mitteaktiivseks
			
		}
		else {
			mBtnSplit.setText(getResources().getText(R.string.add_split));
			//Toast.makeText(this, "Read olemas", 5000).show();
		}
		c.close();
		c=null;
		
		LinearLayout titlebar = (LinearLayout) findViewById(R.id.titlebar);
		titlebar.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				//shareStory();
				Animation hyperspaceJump = AnimationUtils.loadAnimation(app.getApplicationContext(), R.anim.rotate_360);
    			v.startAnimation(hyperspaceJump);
    			
            	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    	    	ft = ((FragmentActivity)act).getSupportFragmentManager().beginTransaction();
    			Util.recalculateAll(act);
    			Util.summaryFields(act);

    	    	String fbtext = getShareText();
    	    	Log.e("SplitText", "FB text="+fbtext);
    	    	
    	    	//FacebookDialogFragment pdf = FacebookDialogFragment.newInstance( R.string.facebook, rec_id );
    	    	FacebookEditDialogFragment pdf = FacebookEditDialogFragment.newInstance( R.string.facebook, -1, fbtext );
    			//Bundle bundle=new Bundle();
    			//bundle.putString("name", search_item.getText().toString());
    			//pdf.setArguments(bundle);
    			pdf.show(ft, "test show");
				return true;
			};
		});
		
		titlebar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Animation hyperspaceJump = AnimationUtils.loadAnimation(app.getApplicationContext(), R.anim.hyperspace_jump);
    			v.startAnimation(hyperspaceJump);
    			
                // Perform action on click
            	/*Intent intent= getActivity().getIntent();
            	  String msg = intent.getStringExtra("sampleData");
            	  msg += ", Added at Third";
            	  intent.putExtra("returnedData", msg);
            	  getActivity().setResult(ItemsActivity.RESULT_CANCELED, intent);*/
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				//ft.remove(((DetailsActivity)getActivity()).details_fragment);
				// in this case, you want to show the help text, but
				// come back to the previous dialog when you're done
				ft.addToBackStack(null);
				//	null represents no name for the back stack transaction
				HelpDialogFragment hdf = HelpDialogFragment.newInstance(R.string.help1);
				hdf.show(ft, MainActivity.TAG);
				return;	
 
            	//Toast.makeText(getActivity(), "Itemit ei savlestatud", 2000).show();
            	//getActivity().finish();
            }
        });
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

		Util.recalculateAll(this);
		Util.summaryFields(this);
		//.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(android.R.drawable.ic_input_add), null, null, null);
		
    }

    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId()==R.id.menu_sort) {
    		
    		MainActivity.tf.setListShown(false);
    		
    		Log.d("MENU", "menüüpunkt="+R.string.menu_sort);
         	SharedPreferences settings = getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
         	if (settings.contains("sort_list")) {
         		MainActivity.mSort=settings.getBoolean("sort_list", true);
         		MainActivity.mSort=!MainActivity.mSort;
         		SharedPreferences.Editor setEditor = settings.edit();
         		setEditor.putBoolean("sort_list", MainActivity.mSort);
         		Log.d("MENU", "sorteerin1="+MainActivity.mSort);
         		setEditor.commit();
         	}
         	else {
         		SharedPreferences.Editor setEditor = settings.edit();
         		setEditor.putBoolean("sort_list", true);
         		setEditor.commit();
         		MainActivity.mSort=true;
         		Log.d("MENU", "sorteerin2="+MainActivity.mSort);
         	}
         	Util.recalculateAll(this);
    		Util.summaryFields(this);
    		//MainActivity.tf
    		HaanjaCursorAdapter mAdapter_new = new HaanjaCursorAdapter(this,
    	            R.layout.advanced_list_haanja, 
    	            null,
    	            new String[] { HaanjaTable.HaanjaColumns.SPLIT, 
    	    		HaanjaTable.HaanjaColumns.START_TIME,
    	    		HaanjaTable.HaanjaColumns.END_TIME,
    	    		HaanjaTable.HaanjaColumns.TIME,
    	    		HaanjaTable.HaanjaColumns.AVG_PACE,
    	    		HaanjaTable.HaanjaColumns.LAP_PACE},
    	            new int[] { R.id.split_nr, R.id.start_time, R.id.end_time, R.id.time, 
    	    		R.id.av_pace, R.id.av_split_pace }, 0); 
    		
    		mAdapter_new.notifyDataSetChanged();
    	    MainActivity.tf.mAdapter=mAdapter_new;
    	    MainActivity.tf.setListAdapter(MainActivity.tf.mAdapter);
    	    
    		//MainActivity.tf.mAdapter.getCursor().requery();
    		//MainActivity.tf.mAdapter.notifyDataSetChanged();
    	    
    		//MainActivity.tf.getLoaderManager().initLoader(1, null, MainActivity.tf);
    		MainActivity.tf.buildUI(1);
    		//MainActivity.tf.mAdapter.getCursor().requery();
    		//MainActivity.tf.mAdapter.notifyDataSetChanged();
    		//MainActivity.tf.buildUI(1);
    		//getContentResolver().notifyChange(HaanjaContent.Haanja.CONTENT_URI, null);
			//MainActivity.tf.mAdapter.notifyDataSetChanged();
    	}
    	if (item.getItemId()==R.id.menu_settings) {
    		// Launch to our preferences screen.
    		Intent intent = new Intent().setClass(this,	ee.mehike.haanja100.activity.HaanjaPreferenceActivity.class);
    		//SharedPreferences settings = getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
            //mCurViewMode = mPrefs.getInt("view_mode", DAY_VIEW_MODE);

    		/*Bundle b= new Bundle();
    		b.putDouble("km_pref", (double) settings.getFloat("km_pref", (float)Constants.HAANJA100_KM));
    		b.putLong("split_pref", settings.getLong("split_pref", Constants.HAANJA100_SPLITS));
    		b.putLong("time1_pref", settings.getLong("time1_pref", Constants.BEST_TIME_1));
    		b.putLong("time2_pref", settings.getLong("time2_pref", Constants.BEST_TIME_2));
    		b.putLong("time3_pref", settings.getLong("time3_pref", Constants.BEST_TIME_3));
    		intent.putExtras(b);
    		intent.putExtra("test", 123);*/
    		this.startActivityForResult(intent, 0);
    	}
    	if (item.getItemId()==R.id.menu_close) {
    		this.stopService();
    	}
    	return true;
    }
    
    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
    	super.onActivityResult(reqCode, resCode, data);
    	//Session.getActiveSession().onActivityResult(this, reqCode, resCode, data);
    	//uiHelper.onActivityResult(reqCode, resCode, data);
    	uiHelper.onActivityResult(reqCode, resCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    	/*Bundle bundle = data.getExtras();
    	Log.d("TAGASI", "km="+bundle.getDouble("km_pref")); 
    	Log.d("TAGASI", "split="+bundle.getLong("split_pref"));
    	Log.d("TAGASI", "time1="+bundle.getLong("time1_pref"));
    	Log.d("TAGASI", "time2="+bundle.getLong("time2_pref"));
    	Log.d("TAGASI", "time3="+bundle.getLong("time3_pref"));*/
    	//setOptionText();
    }
    
    private void stopService() {
		Log.v("stopservice", "Stopping service...");
		if(stopService(new Intent(MainActivity.tf.getActivity(), ee.mehike.haanja100.services.BackgroundService.class)))
			Log.v("stopservice", "stopService was successful");
		else
			Log.v("stopService", "stopService was unsuccessful");
		this.finish();
	
	}
    
    private void startService() {
		Log.v("startservice", "Starting service...");
		Intent intent = new Intent(MainActivity.this, ee.mehike.haanja100.services.BackgroundService.class);
	    int counter=0;
	    intent.putExtra("counter", counter++);
     	//startService(intent);
		Log.v("startservice", "startService was successful");
	}
    
	@Override
	public void onAttachFragment(Fragment fragment) {
		Log.v(TAG, "in MainActivity onAttachFragment. fragment id = "
				+ fragment.getId());
		
		super.onAttachFragment(fragment);
	}
	
	@Override
	public void onStart() {
		Log.v(TAG, "in MainActivity onStart");
		super.onStart();
		
	}
	
	@Override
	public void onResume() {
		Log.v(TAG, "in MainActivity onResume");
		super.onResume();
		// For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	        /*Bundle ret = session.getAuthorizationBundle();
			if(ret != null) {
				for(String key : ret.keySet()) {
					Log.e(MainActivity.TAG, "myBundle ret =  " + key);
				}
			}
			else {
				Log.v(MainActivity.TAG, " myBundle ret is null");
			}*/
	    }
		uiHelper.onResume();
	}
	
	@Override
	public void onPause() {
		Log.v(TAG, "in MainActivity onPause");
		super.onPause();
		uiHelper.onPause();
	}
	
	@Override
	public void onStop() {
		Log.v(TAG, "in MainActivity onStop");
		super.onStop();
		uiHelper.onStop();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.v(MainActivity.TAG, "in MainActivity onSaveInstanceState");
		super.onSaveInstanceState(outState);
		outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
		uiHelper.onSaveInstanceState(outState);
	}
	
	@Override
	public void onDestroy() {
		Log.v(TAG, "in MainActivity onDestroy");
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	public boolean isMultiPane() {

		return getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE;
	}
	
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;

	public static Activity act ;
	
	
	public String getSplitText(long rec_id) {
		String str = "";
		Cursor first_cur = getContentResolver().query(HaanjaContent.Haanja.CONTENT_URI, Haanja.PROJECTION, "split=1", null, "split desc");
		Haanja first_haanja = null;
		if (first_cur!=null && first_cur.moveToFirst()) {
			//Haanja first_rec=group_cur.getLong(group_cur.getColumnIndex(UserGroupUserTable.UserGroupUserColumns._ID));
			first_haanja = Util.buildHaanjaFromCursor(first_cur);
			Util.LogCursorInfo(first_cur);
		}
		if (first_cur!=null)
				first_cur.close();
		first_cur=null;
		Cursor current_cur = getContentResolver().query(Uri.parse(HaanjaContent.Haanja.CONTENT_URI+"/"+rec_id), Haanja.PROJECTION, null, null, null);
		Haanja current_haanja = null;
		if (current_cur!=null && current_cur.moveToFirst()) {
			//Haanja first_rec=group_cur.getLong(group_cur.getColumnIndex(UserGroupUserTable.UserGroupUserColumns._ID));
			current_haanja = Util.buildHaanjaFromCursor(current_cur);
			Util.LogCursorInfo(current_cur);
			SharedPreferences settings = act.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
			str=str+"Start kell: "+Util.displayCalcDate(act, first_haanja.getStart_time())+"  \n ";
			long start = first_haanja.getStart_time();
			//String kulunud= Util.displayCalcTime(act, Util.normalize_time(start)+current_haanja.getEnd_time()-Util.normalize_time(System.currentTimeMillis()));
			String kulunud= Util.displayCalcTime(act, Util.normalize_time(current_haanja.getEnd_time())-Util.normalize_time(start));
			String raeg= Util.displayCalcTime(act, Util.normalize_time(current_haanja.getEnd_time())-Util.normalize_time(current_haanja.getStart_time()));
			str=str+"Kokku kulunud:  "+kulunud+" \n ";
			str=str+"Keskmine kiirus: "+current_haanja.getAvg_pace()+" \n\n ";
			if (current_haanja.getSplit()==first_haanja.getSplit()) {
				//kulunud = Util.displayCalcTime(act, Util.normalize_time(start)+current_haanja.getEnd_time()-Util.normalize_time(System.currentTimeMillis()));
				str=str+"Esimene ring, \n ringiaeg:  "+raeg;
			}
			else {
				str=str+current_haanja.getSplit()+".ring  \n Ringiaeg: "+raeg;
			}
			str=str+"\n Ringi kiirus: "+current_haanja.getLap_pace()+" \n \n ";
			
			
   	     	if (settings.contains("splits")) {
   	     		str=str+act.getString(R.string.ringid)+": "+settings.getLong("splits", 0)+"/"+Util.getSplits(act)+" \n ";
   	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);
   	     	if (settings.contains("splits")) {
   	     		str=str+act.getString(R.string.labitud)+" "+Util.round(settings.getLong("splits", 0)*Util.getSplitKm(act),2)+"->"+Util.round(Util.getKm(act)-settings.getLong("splits", 0)*Util.getSplitKm(act),2)+"km \n \n ";
   	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);

			str=str+"Loodetavad lõputulemused-> \n ";

			if (settings.contains("future_avglap_finish_time")) {
				str=str+"Keskmise kiiruse põhjal: \n ";
				str=str+settings.getString("future_avglap_finish_time", act.getString(R.string.veeleitea))+" \n ";
			}
			if (settings.contains("future_lastlap_finish_time")) {
				str=str+"Viimase ringi põhjal: \n ";
				str=str+settings.getString("future_lastlap_finish_time", act.getString(R.string.veeleitea))+" \n ";
			}
			str=str+"";
			//str=str+current_haanja.toString()+"  \n ";
		}
		if (current_cur!=null)
			current_cur.close();
		current_cur=null;
		current_haanja=null;
		first_haanja=null;
		return str;
	}

	public String getSplitTextHtml(long rec_id) {
		String str = "";
		SharedPreferences settings = act.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);

		//str=settings.getString("pref_name", Constants.NAME)+": \n ";
		Cursor first_cur = getContentResolver().query(HaanjaContent.Haanja.CONTENT_URI, Haanja.PROJECTION, "split=1", null, "split desc");
		Haanja first_haanja = null;
		if (first_cur!=null && first_cur.moveToFirst()) {
			//Haanja first_rec=group_cur.getLong(group_cur.getColumnIndex(UserGroupUserTable.UserGroupUserColumns._ID));
			first_haanja = Util.buildHaanjaFromCursor(first_cur);
			Util.LogCursorInfo(first_cur);
		}
		if (first_cur!=null)
				first_cur.close();
		first_cur=null;
		Cursor current_cur = getContentResolver().query(Uri.parse(HaanjaContent.Haanja.CONTENT_URI+"/"+rec_id), Haanja.PROJECTION, null, null, null);
		Haanja current_haanja = null;
		if (current_cur!=null && current_cur.moveToFirst()) {
			//Haanja first_rec=group_cur.getLong(group_cur.getColumnIndex(UserGroupUserTable.UserGroupUserColumns._ID));
			current_haanja = Util.buildHaanjaFromCursor(current_cur);
			Util.LogCursorInfo(current_cur);
			str=str+"Start kell: "+Util.displayCalcDate(act, first_haanja.getStart_time())+"  </br> ";
			long start = first_haanja.getStart_time();
			//String kulunud= Util.displayCalcTime(act, Util.normalize_time(start)+current_haanja.getEnd_time()-Util.normalize_time(System.currentTimeMillis()));
			String kulunud= Util.displayCalcTime(act, Util.normalize_time(current_haanja.getEnd_time())-Util.normalize_time(start));
			String raeg= Util.displayCalcTime(act, Util.normalize_time(current_haanja.getEnd_time())-Util.normalize_time(current_haanja.getStart_time()));
			str=str+"Kokku kulunud:  "+kulunud+" </br> ";
			str=str+"Keskmine kiirus: "+current_haanja.getAvg_pace()+" </br></br> ";
			if (current_haanja.getSplit()==first_haanja.getSplit()) {
				//kulunud = Util.displayCalcTime(act, Util.normalize_time(start)+current_haanja.getEnd_time()-Util.normalize_time(System.currentTimeMillis()));
				str=str+"Esimene ring, </br> ringiaeg:  "+raeg;
			}
			else {
				str=str+current_haanja.getSplit()+".ring  </br> Ringiaeg: "+raeg;
			}
			str=str+"</br> Ringi kiirus: "+current_haanja.getLap_pace()+" </br></br> ";
			
   	     	if (settings.contains("splits")) {
   	     		str=str+act.getString(R.string.ringid)+": "+settings.getLong("splits", 0)+"/"+Util.getSplits(act)+" </br> ";
   	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);
   	     	if (settings.contains("splits")) {
   	     		str=str+act.getString(R.string.labitud)+" "+Util.round(settings.getLong("splits", 0)*Util.getSplitKm(act),2)+"->"+Util.round(Util.getKm(act)-settings.getLong("splits", 0)*Util.getSplitKm(act),2)+"km </br></br> ";
   	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);

			str=str+"Loodetavad lõputulemused-> </br> ";

			if (settings.contains("future_avglap_finish_time")) {
				str=str+"Keskmise kiiruse põhjal: </br> ";
				str=str+settings.getString("future_avglap_finish_time", act.getString(R.string.veeleitea))+" </br> ";
			}
			if (settings.contains("future_lastlap_finish_time")) {
				str=str+"Viimase ringi põhjal: </br> ";
				str=str+settings.getString("future_lastlap_finish_time", act.getString(R.string.veeleitea))+" </br> ";
			}
			str=str+"";
			//str=str+current_haanja.toString()+"  \n ";
		}
		if (current_cur!=null)
			current_cur.close();
		current_cur=null;
		current_haanja=null;
		first_haanja=null;
		str="<html>"+str+"</html>";
		return str;
	}

	public String getShareText() {
		SharedPreferences settings = act.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		long split=1;
	     	if (settings.contains("splits")) {
	     		split=settings.getLong("splits", 0);
	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);

		String str = "";
  		//str=settings.getString("pref_name", Constants.NAME)+": \n ";
     	
		Cursor first_cur = getContentResolver().query(HaanjaContent.Haanja.CONTENT_URI, Haanja.PROJECTION, "split=1", null, "split desc");
		Haanja first_haanja = null;
		if (first_cur!=null && first_cur.moveToFirst()) {
			//Haanja first_rec=group_cur.getLong(group_cur.getColumnIndex(UserGroupUserTable.UserGroupUserColumns._ID));
			first_haanja = Util.buildHaanjaFromCursor(first_cur);
			Util.LogCursorInfo(first_cur);
		}
		if (first_cur!=null)
				first_cur.close();
		first_cur=null;
		Cursor current_cur = getContentResolver().query(HaanjaContent.Haanja.CONTENT_URI, Haanja.PROJECTION, "split="+split, null, "split desc");
		Haanja current_haanja = null;
		if (current_cur!=null && current_cur.moveToFirst()) {
			//Haanja first_rec=group_cur.getLong(group_cur.getColumnIndex(UserGroupUserTable.UserGroupUserColumns._ID));
			current_haanja = Util.buildHaanjaFromCursor(current_cur);
			Util.LogCursorInfo(current_cur);
			str=str+"Start kell: "+Util.displayCalcDate(act, first_haanja.getStart_time())+"  \n ";
			long start = first_haanja.getStart_time();
			//String kulunud= Util.displayCalcTime(act, Util.normalize_time(start)+current_haanja.getEnd_time()-Util.normalize_time(System.currentTimeMillis()));
			String kulunud= Util.displayCalcTime(act, Util.normalize_time(current_haanja.getEnd_time())-Util.normalize_time(start));
			String raeg= Util.displayCalcTime(act, Util.normalize_time(current_haanja.getEnd_time())-Util.normalize_time(current_haanja.getStart_time()));
			str=str+"Kokku kulunud:  "+kulunud+" \n ";
			str=str+"Keskmine kiirus: "+current_haanja.getAvg_pace()+" \n\n ";
			if (current_haanja.getSplit()==first_haanja.getSplit()) {
				//kulunud = Util.displayCalcTime(act, Util.normalize_time(start)+current_haanja.getEnd_time()-Util.normalize_time(System.currentTimeMillis()));
				str=str+"Esimene ring, \n ringiaeg:  "+raeg;
			}
			else {
				str=str+current_haanja.getSplit()+".ring  \n Ringiaeg: "+raeg;
			}
			str=str+"\n Ringi kiirus: "+current_haanja.getLap_pace()+" \n \n ";
			
   	     	if (settings.contains("splits")) {
   	     		str=str+"Läbitud/kokku ringe: "+act.getString(R.string.ringid)+": "+settings.getLong("splits", 0)+"/"+Util.getSplits(act)+" \n ";
   	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);
   	     	if (settings.contains("splits")) {
   	     		str=str+"Läbitud/läbida km-d: "+act.getString(R.string.labitud)+" "+Util.round(settings.getLong("splits", 0)*Util.getSplitKm(act),2)+"->"+Util.round(Util.getKm(act)-settings.getLong("splits", 0)*Util.getSplitKm(act),2)+"km \n \n ";
   	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);

			str=str+"Loodetavad lõputulemused-> \n ";

			if (settings.contains("future_avglap_finish_time")) {
				str=str+"Keskmise kiiruse põhjal: \n ";
				str=str+settings.getString("future_avglap_finish_time", act.getString(R.string.veeleitea))+" \n ";
			}
			if (settings.contains("future_lastlap_finish_time")) {
				str=str+"Viimase ringi põhjal: \n ";
				str=str+settings.getString("future_lastlap_finish_time", act.getString(R.string.veeleitea))+" \n ";
			}
			str=str+"";
			//str=str+current_haanja.toString()+"  \n ";
		}
		if (current_cur!=null)
			current_cur.close();
		current_cur=null;
		current_haanja=null;
		first_haanja=null;
		return str;
	}

	public String getShareTextHtml() {
		SharedPreferences settings = act.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		long split=1;
	     	if (settings.contains("splits")) {
	     		split=settings.getLong("splits", 0);
	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);

		String str = "";
		
		Cursor first_cur = getContentResolver().query(HaanjaContent.Haanja.CONTENT_URI, Haanja.PROJECTION, "split=1", null, "split desc");
		Haanja first_haanja = null;
		if (first_cur!=null && first_cur.moveToFirst()) {
			//Haanja first_rec=group_cur.getLong(group_cur.getColumnIndex(UserGroupUserTable.UserGroupUserColumns._ID));
			first_haanja = Util.buildHaanjaFromCursor(first_cur);
			Util.LogCursorInfo(first_cur);
		}
		if (first_cur!=null)
				first_cur.close();
		first_cur=null;
		Cursor current_cur = getContentResolver().query(HaanjaContent.Haanja.CONTENT_URI, Haanja.PROJECTION, "split="+split, null, "split desc");
		Haanja current_haanja = null;
		if (current_cur!=null && current_cur.moveToFirst()) {
			//Haanja first_rec=group_cur.getLong(group_cur.getColumnIndex(UserGroupUserTable.UserGroupUserColumns._ID));
			current_haanja = Util.buildHaanjaFromCursor(current_cur);
			Util.LogCursorInfo(current_cur);
			str=str+"Start kell: "+Util.displayCalcDate(act, first_haanja.getStart_time())+"  </br> ";
			long start = first_haanja.getStart_time();
			//String kulunud= Util.displayCalcTime(act, Util.normalize_time(start)+current_haanja.getEnd_time()-Util.normalize_time(System.currentTimeMillis()));
			String kulunud= Util.displayCalcTime(act, Util.normalize_time(current_haanja.getEnd_time())-Util.normalize_time(start));
			String raeg= Util.displayCalcTime(act, Util.normalize_time(current_haanja.getEnd_time())-Util.normalize_time(current_haanja.getStart_time()));
			str=str+"Kokku kulunud:  "+kulunud+" </br> ";
			str=str+"Keskmine kiirus: "+current_haanja.getAvg_pace()+" </br></br> ";
			if (current_haanja.getSplit()==first_haanja.getSplit()) {
				//kulunud = Util.displayCalcTime(act, Util.normalize_time(start)+current_haanja.getEnd_time()-Util.normalize_time(System.currentTimeMillis()));
				str=str+"Esimene ring, </br> ringiaeg:  "+raeg;
			}
			else {
				str=str+current_haanja.getSplit()+".ring  </br> Ringiaeg: "+raeg;
			}
			str=str+"</br> Ringi kiirus: "+current_haanja.getLap_pace()+" </br></br> ";
			
   	     	if (settings.contains("splits")) {
   	     		str=str+"Läbitud/kokku ringe: "+act.getString(R.string.ringid)+": "+settings.getLong("splits", 0)+"/"+Util.getSplits(act)+" </br> ";
   	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);
   	     	if (settings.contains("splits")) {
   	     		str=str+"Läbitud/läbida km-d: "+act.getString(R.string.labitud)+" "+Util.round(settings.getLong("splits", 0)*Util.getSplitKm(act),2)+"->"+Util.round(Util.getKm(act)-settings.getLong("splits", 0)*Util.getSplitKm(act),2)+"km </br></br> ";
   	     	} //else spl.setText(ctx.getString(R.string.ringid)+" 0/"+Constants.HAANJA100_SPLITS);

			str=str+"Loodetavad lõputulemused-> </br> ";

			if (settings.contains("future_avglap_finish_time")) {
				str=str+"Keskmise kiiruse põhjal: </br> ";
				str=str+settings.getString("future_avglap_finish_time", act.getString(R.string.veeleitea))+" </br> ";
			}
			if (settings.contains("future_lastlap_finish_time")) {
				str=str+"Viimase ringi põhjal: </br> ";
				str=str+settings.getString("future_lastlap_finish_time", act.getString(R.string.veeleitea))+" </br> ";
			}
			str=str+"";
			//str=str+current_haanja.toString()+"  \n ";
		}
		if (current_cur!=null)
			current_cur.close();
		current_cur=null;
		current_haanja=null;
		first_haanja=null;
		str="<html>"+str+"</html>";
		return str;
	}

	public void publishStory() {
		this.publishStory(-1);
	}
	
	public void publishStory(long rec_id) {
		this.publishStory(rec_id, null);
	}
	
	public void publishStory(long rec_id, CharSequence str) {
	    Session session = Session.getActiveSession();

	    if (session != null){

	        // Check for publish permissions    
	        List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(PERMISSIONS, permissions)) {
	            pendingPublishReauthorization = true;
	            Session.NewPermissionsRequest newPermissionsRequest = new Session
	                    .NewPermissionsRequest(this, PERMISSIONS);
	            session.requestNewPublishPermissions(newPermissionsRequest);
	            return;
	        }
	        
	        SharedPreferences settings = act.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
	        Bundle postParams = new Bundle();
	        String name=settings.getString("name_pref", "Neon Runners Club"); 
	        postParams.putString("name", name);
	        String compname=settings.getString("compname_pref", Constants.COMPNAME);
	        postParams.putString("caption", compname);
	        if (str!=null) {
	        	postParams.putString("description", str.toString());
	        	//SharedPreferences settings = act.getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
	        	//name=settings.getString("pref_name", Constants.NAME)+": \n ";
	        	name=settings.getString("name_pref", Constants.NAME)+": \n ";
	        	postParams.putString("message", name+str.toString());
	        }
	        else {
	        	postParams.putString("description", name+"se hetkeseis "+compname+"-l");
	        }
	        //TODO preferencesse link n. http://www.haanja100.ee
	        postParams.putString("link", settings.getString("link_pref", "http://www.haanja100.ee"));
	        postParams.putString("picture", "http://www.hot.ee/p/pinkmehike/IMG_3780.jpg");

	        Request.Callback callback_r= new Request.Callback() {
	            public void onCompleted(Response response) {
	                JSONObject graphResponse = response
	                                           .getGraphObject()
	                                           .getInnerJSONObject();
	                String postId = null;
	                try {
	                    postId = graphResponse.getString("id");
	                } catch (JSONException e) {
	                    Log.i(TAG, "JSON error "+ e.getMessage());
	                }
	                FacebookRequestError error = response.getError();
	                if (error != null) {
	                    Toast.makeText(act.getApplicationContext(), error.getErrorMessage(),
	                         Toast.LENGTH_SHORT).show();
	                    
	                    } else {
	                        //Toast.makeText(act.getApplicationContext(), postId, Toast.LENGTH_LONG).show();
	                        app.customToastLong(act, postId);
	                }
	            }
	        };

	        Request request = new Request(session, "me/feed", postParams, 
	                              HttpMethod.POST, callback_r);

	        RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
	        //Toast.makeText(getApplicationContext(), R.string.edastatud, 5000 ).show();
	        app.customToastLong(act, act.getResources().getString(R.string.edastatud));
	    }

	}
	
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}


	public void shareStory() {
		
		String str=this.getShareText();
		
		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(act)
		.setLink("http://www.haanja100.ee")
		.setName("Neon Runners Club")
		.setCaption("Haanja Jala100")
		.setDescription(str)//.setLink("https://developers.facebook.com/android")
		.setPicture("http://www.hot.ee/p/pinkmehike/IMG_3780.jpg")
		.setApplicationName("HAANJA JALA100")
		.build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
		//Toast.makeText(getApplicationContext(), R.string.jagatud, 5000 ).show();
		app.customToastLong(act, act.getResources().getString(R.string.jagatud));
	}

	/*public void shareWebStory() {
		String str=this.getShareText();
		
		Bundle params = new Bundle();
		params.putString("name", "An example parameter");
		params.putString("link", "https://www.example.com/");
		params.putString("message", str);

		WebDialog feedDialog = (
		        new WebDialog.FeedDialogBuilder(this,
		            Session.getActiveSession(),
		            params)).setOnCompleteListener(new OnCompleteListener(){})
		        .build();
		    feedDialog.show();
	}*/
	
	public void shareGraphStory() {
		String str=this.getShareText();
		OpenGraphObject meal = OpenGraphObject.Factory.createForPost("cooking-app:meal");
		meal.setProperty("title", "Buffalo Tacos");
		meal.setProperty("image", "http://example.com/cooking-app/images/buffalo-tacos.png");
		meal.setProperty("url", "https://example.com/cooking-app/meal/Buffalo-Tacos.html");
		meal.setProperty("description", "Leaner than beef and great flavor.");
		meal.setProperty("message", str);

		OpenGraphAction action = GraphObject.Factory.create(OpenGraphAction.class);
		action.setProperty("meal", meal);

		FacebookDialog shareDialog = new FacebookDialog.OpenGraphActionDialogBuilder(this, action, "cooking-app:cook", "meal")
		        .build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
	}
}
