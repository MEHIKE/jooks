package ee.mehike.haanja100.fragments;
//package com.shop.checklist.fragments;

//import android.app.Activity;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.facebook.widget.FacebookDialog;

//import com.shop.checklist.activity.ItemsActivity;
import ee.mehike.haanja100.HaanjaApp;
import ee.mehike.haanja100.MainActivity;
//import com.shop.checklist.adapter.InteractiveArrayAdapter;
import ee.mehike.haanja100.adapter.HaanjaCursorAdapter;
import ee.mehike.haanja100.data.HaanjaTable;
import ee.mehike.haanja100.data.dao.HaanjaDao;
import ee.mehike.haanja100.fragments.dialog.ChangeTimeDialogFragment;
import ee.mehike.haanja100.fragments.dialog.FacebookDialogFragment;
import ee.mehike.haanja100.fragments.dialog.HelpDialogFragment;
//import com.shop.checklist.fragments.dialog.AddTitleDialogFragment;
import ee.mehike.haanja100.model.Model;
import ee.mehike.haanja100.model.Haanja;
import ee.mehike.haanja100.provider.HaanjaContent;
import ee.mehike.haanja100.provider.HaanjaContentProvider;
import ee.mehike.haanja100.util.Constants;
import ee.mehike.haanja100.util.Util;
//import com.shop.checklist.services.BackgroundService;
import ee.mehike.haanja100.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
//import android.widget.Toast;

import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.database.Cursor;

import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.database.Cursor;

public class SplitsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {//,OnQueryTextListener {
	
	/*private static final int METHOD_DRAW_SELECTOR_ON_TOP = 1;
    private static final int METHOD_USE_SELECTOR_AS_BACKGROUND = 2;
    private int mMethod;
    private static final String[] SPECIAL_CHEESE_TAGS = new String[] {
            "'", "-", "y"
    };*/
 
	//private MainActivity myActivity = null;
	boolean mDualPane;
	int mCurCheckPosition = 0;
	private LayoutInflater mInflater;
	private MainActivity myActivity = null;
	private static MainActivity act ;
	
	private HaanjaApp app;
	
	public HaanjaApp getApp() {
		return this.app;
	}
	
	//private SimpleCursorAdapter cursor_adapter;
	
	//static final ArrayList<HashMap<String,String>> list = new ArrayList();//<HashMap<String,String>>();

	/*public void onDialogDone(String tag, boolean cancelled,
			CharSequence message) {
			String s = tag + " responds with: " + message;
			if(cancelled)
			s = tag + " was cancelled by the user";
			Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
			Log.v(MainActivity.TAG, s);
		}*/
	// Creates a new loader after the initLoader () call
/*	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { TitleTable.ID, TitleTable.NAME, TitleTable.DESCRIPTION };
		CursorLoader cursorLoader = new CursorLoader(getActivity(),
				TitlesContentProvider.CONTENT_URI, projection, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		cursor_adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// data is not available anymore, delete reference
		cursor_adapter.swapCursor(null);
	}
	*/
/*	private void populateList() {
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("pen","MONT Blanc");
		temp.put("price", "200.00$");
		temp.put("color", "Silver, Grey, Black");
		list.add(temp);
		HashMap<String,String> temp1 = new HashMap<String,String>();
		temp1.put("pen","Gucci");
		temp1.put("price", "300.00$");
		temp1.put("color", "Gold, Red");
		list.add(temp1);
		HashMap<String,String> temp2 = new HashMap<String,String>();
		temp2.put("pen","Parker");
		temp2.put("price", "400.00$");
		temp2.put("color", "Gold, Blue");
		list.add(temp2);
		HashMap<String,String> temp3 = new HashMap<String,String>();
		temp3.put("pen","Sailor");
		temp3.put("price", "500.00$");
		temp3.put("color", "Silver");
		list.add(temp3);
		HashMap<String,String> temp4 = new HashMap<String,String>();
		temp4.put("pen","Porsche Design");
		temp4.put("price", "600.00$");
		temp4.put("color", "Silver, Grey, Red");
		list.add(temp4);
		}

	private List<Model> getModel() {
		List<Model> list = new ArrayList<Model>();
		list.add(get("Linux"));
		list.add(get("Windows7"));
		list.add(get("Suse"));
		list.add(get("Eclipse"));
		list.add(get("Ubuntu"));
		list.add(get("Solaris"));
		list.add(get("Android"));
		list.add(get("iPhone"));
		// Initially select one of the items
		Log.d("test","getModel() done");
		list.get(1).setSelected(true);
		return list;
	}

	private Model get(String s) {
		return new Model(s);
	}
*/
/*	@Override
	public void onInflate(android.app.Activity myActivity, AttributeSet attrs, Bundle savedInstanceState) {
		Log.v(MainActivity.TAG,"in TitlesFragment onInflate. AttributeSet contains:");
		for(int i=0; i<attrs.getAttributeCount(); i++) {
			Log.v(MainActivity.TAG, " " + attrs.getAttributeName(i) +
					" = " + ("id".equals(attrs.getAttributeName(i))?
							Integer.toHexString(attrs.getAttributeIntValue(i, -1)):
								attrs.getAttributeValue(i)));
		}
		super.onInflate(myActivity, attrs, savedInstanceState);
	}
	
	@Override
	public void onAttach(android.app.Activity myActivity) {
		Log.v(MainActivity.TAG,"in TitlesFragment onAttach; activity is: " + myActivity);
		super.onAttach(myActivity);
		this.myActivity = (MainActivity)myActivity;
	}
*/
	@Override
	public void onCreate(Bundle myBundle) {
		Log.v(MainActivity.TAG,"in TitlesFragment onCreate. Bundle contains:");
		act=this.myActivity;
		if(myBundle != null) {
			for(String key : myBundle.keySet()) {
				Log.v(MainActivity.TAG, " " + key);
			}
		}
		else {
			Log.v(MainActivity.TAG, " myBundle is null");
		}
		super.onCreate(myBundle);
		
		
		this.myActivity = (MainActivity)getActivity();
		app = (HaanjaApp) myActivity.getApplication();
	}

	
	/**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    static SplitsFragment newInstance(int num) {
        SplitsFragment f = new SplitsFragment();

        // Supply num input as an argument.
        /*Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);*/

        return f;
    }
    
	@Override
	public View onCreateView(LayoutInflater myInflater,
			ViewGroup container, Bundle myBundle) {
		Log.v(MainActivity.TAG,"in TitlesFragment onCreateView. container is "+ container);
		//return super.onCreateView(myInflater, container, myBundle);
		//View v = myInflater.inflate(R.layout.titles, container, false);
		/*FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		//ft.addToBackStack("details");
		ft.replace(R.id.test, this);
		ft.commit();*/
        /*View tv = v.findViewById(R.id.text);
        ((TextView)tv).setText("Fragment #" + mNum);*/
        //return v;
		return super.onCreateView(myInflater, container, myBundle);
	}

	public HaanjaCursorAdapter mAdapter;

	/*private void stopService() {
		Log.v("stopservice", "Stopping service...");
		if(getActivity().stopService(new Intent(MainActivity.tf.getActivity(), BackgroundService.class)))
			Log.v("stopservice", "stopService was successful");
		else
			Log.v("stopService", "stopService was unsuccessful");
	}*/
	
	public static Cursor mCursorAutoComplete;
	
	@Override
	public void onActivityCreated(Bundle savedState) {
		Log.v(MainActivity.TAG,"in TitlesFragment onActivityCreated. savedState contains:");
		setEmptyText(getActivity().getResources().getString(R.string.poleveel));
		
		/*if(savedState != null) {
			for(String key : savedState.keySet()) {
				Log.v(MainActivity.TAG, " " + key);
			}
		}
		else {
			Log.v(MainActivity.TAG, " savedState is null");
		}
		super.onActivityCreated(savedState);
		// 	Populate list with your static array of titles.
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1,Shakespeare.TITLES));
		if (savedState != null) {
			// Restore last state for checked position.
			mCurCheckPosition = savedState.getInt("curChoice", 0);
		}
		// Get your ListFragment’s ListView and update it
		ListView lv = getListView();
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv.setSelection(mCurCheckPosition);
		// Activity is created, fragments are available
		// Go ahead and populate the details fragment
		myActivity.showDetails(mCurCheckPosition);*/
		
	super.onActivityCreated(savedState);

	// this is really important in order to save the state across screen
    // configuration changes for example
    setRetainInstance(true);

    // LoaderManager.enableDebugLogging(true);
    
        // Populate list with our static array of titles.
        /*setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_checked,
        		//R.layout.two_line_list_item,
                Shakespeare.TITLES));
*/
	//	populateList();
        /*SimpleAdapter adapter = new SimpleAdapter(
        		getActivity(),
        		list,
        		R.layout.three_line_list_item,
        		new String[] {"pen","price","color"},
        		new int[] {R.id.text1,R.id.text2, R.id.text3}

        		);*/
        /*SimpleAdapter adapter = new SimpleAdapter(
        		getActivity(),
        		list,
        		R.layout.advanced_list_item,
        		new String[] {"pen","price","color"},
        		new int[] {R.id.text_1,R.id.text_2, R.id.text_3}

        		);*/
		//ArrayAdapter<Model> adapter = new InteractiveArrayAdapter(getActivity(), getModel());
    showDialog();

    /*if (MainActivity.mSort)
    	mCursorAutoComplete = getActivity().getContentResolver().
			query(HaanjaContent.Haanja.CONTENT_URI, this.HAANJA_SUMMARY_PROJECTION, null, null, HaanjaContent.Haanja.HaanjaColumns.SPLIT+" DESC");
    else
    	mCursorAutoComplete = getActivity().getContentResolver().
		query(HaanjaContent.Haanja.CONTENT_URI, this.HAANJA_SUMMARY_PROJECTION, null, null, HaanjaContent.Haanja.HaanjaColumns.SPLIT+" ASC");
    */
    
    //ArrayAdapter<Title> a_adapter =  
    //		new InteractiveArrayAdapter(getActivity(), 
    //				this.myActivity.getApp().getDataManager().getTitleHeaders());// getContacts();
	//this.myActivity.startManagingCursor(mCursorAutoComplete);
	
 // Create an empty adapter we will use to display the loaded data.
    mAdapter = new HaanjaCursorAdapter(this.myActivity,
            R.layout.advanced_list_haanja, 
            null, //mCursorAutoComplete,
            new String[] { HaanjaTable.HaanjaColumns.SPLIT, 
    		HaanjaTable.HaanjaColumns.START_TIME,
    		HaanjaTable.HaanjaColumns.END_TIME,
    		HaanjaTable.HaanjaColumns.TIME,
    		HaanjaTable.HaanjaColumns.AVG_PACE,
    		HaanjaTable.HaanjaColumns.LAP_PACE},
            new int[] { R.id.split_nr, R.id.start_time, R.id.end_time, R.id.time, 
    		R.id.av_pace, R.id.av_split_pace }, 0); 

    
    //Toast.makeText(getActivity(), "mAdapter tehtud", 5000).show();
    
    //mAdapter.setViewBinder(new TitleViewBinder());
    
		/*String[] from = new String[] { TitleTable.NAME, TitleTable.DESCRIPTION };
		// Fields on the UI to which we map
		int[] to = new int[] { R.id.text_1, R.id.text_2 };

			//http://mobile.tutsplus.com/tutorials/android/android-sdk_loading-data_cursorloader/
		
		getActivity().getSupportLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this, R.layout.advanced_list_item, null, from,
				to, 0);*/

		mInflater = LayoutInflater.from(this.myActivity);
		
        //FancyAdapter mFancyAdapter = new FancyAdapter(list); 
        
		
		//View header = getActivity().getLayoutInflater().inflate(R.layout.titlebar, null);
		//------------------------------View header = mInflater.inflate(R.layout.titlebar, null);
		//View footer = getActivity().getLayoutInflater().inflate(R.layout.searchbar, null);
		//View footer = mInflater.inflate(R.layout.searchbar, null);
	    //-----------------------------   ListView listView = getListView();
	    //-----------------------------    listView.addHeaderView(header);
			//listView.addFooterView(footer);
		//----------------------------	listView.setDividerHeight(2);
		//----------------------------	listView.setClickable(true);
		//----------------------------	listView.setLongClickable(true);
			//registerForContextMenu(listView);
			/*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
	            public void onItemClick(AdapterView<?> av, View v, int i, long l) {
					//Toast.makeText(getActivity(), "test", 1000);
					Log.d("uus", " av="+av+" v="+v+" i="+i+" l="+l);
				}


			});*/ 
			
		ListView listView = getListView();
		listView.setClickable(true);
		listView.setLongClickable(true);
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        	@Override
        	public boolean onItemLongClick(AdapterView<?> av, View v, int i, long rec_id) {
        	// TODO Auto-generated method stub
        		//Toast.makeText(act.getApplicationContext(), "longClick", 1000).show();
        		
        		//app.customToastLong(act, "Pikk vajutus");
        		
        		Animation hyperspaceJump = AnimationUtils.loadAnimation(app.getApplicationContext(), R.anim.hyperspace_jump);
    			v.startAnimation(hyperspaceJump);
        		
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				long start_time=Util.getLongTimeFromstring(((TextView)v.findViewById(R.id.start_time)).getText().toString());
						//Util.getLongTime(((TextView)v.findViewById(R.id.start_time)).getText().toString());
				long end_time=Util.getLongTimeFromstring(((TextView)v.findViewById(R.id.end_time)).getText().toString());
				long time=Util.getLongTimeFromstring(((TextView)v.findViewById(R.id.time)).getText().toString());
				long split_nr=Long.parseLong(((TextView)v.findViewById(R.id.split_nr)).getText().toString().split(":")[1]);
				Log.d("uus"," rec_id="+rec_id+" start_time="+Util.displayCalcTime(getActivity(), start_time)+" end_time="+Util.displayCalcTime(getActivity(), end_time)+" time="+Util.displayCalcTime(getActivity(), time));
				
				//ainult siis teeme, kui on viimane rida
				Cursor c = HaanjaApp.dataManager.getHaanjaCursor();
				long last_split=-1;
        		if (c.getCount()>0) {
        			c.moveToLast();
        			last_split = c.getInt(c.getColumnIndex(HaanjaContent.Haanja.HaanjaColumns.SPLIT));
        		}
        		c.close();
        		c=null;
        		if (split_nr==last_split) {
        			//ChangeTimeDialogFragment pdf = ChangeTimeDialogFragment.newInstance( start_time, end_time, time, rec_id, split_nr );
        							//Bundle bundle=new Bundle();
        							//bundle.putString("name", search_item.getText().toString());
        							//pdf.setArguments(bundle);
        			//pdf.show(ft, "test show");
        							//return;
        		} else {
        			//FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(act)
        	        //.setLink("veel").setName("name").setCaption("TEST").setDescription("test")//.setLink("https://developers.facebook.com/android")
        	        //.build();
        			//act.uiHelper.trackPendingDialogCall(shareDialog.present());
        			
        			/*Session session = Session.getActiveSession();

        		    if (session != null){

        		        // Check for publish permissions    
        		    	
        		        List<String> permissions = session.getPermissions();
        		        //if (!isSubsetOf(Arrays.asList("user_likes", "user_status"), permissions)) {
        		            //pendingPublishReauthorization = true;
        		            Session.NewPermissionsRequest newPermissionsRequest = new Session
        		                    .NewPermissionsRequest(act, Arrays.asList("user_likes", "user_status"));
        		        session.requestNewPublishPermissions(newPermissionsRequest);
        		          //  return true;
        		        //}
        		        
        			Bundle params=new Bundle();
        			params.putString("message", "This string will appear as the status message");
        		    params.putString("link", "This is the URL to go to");
        		    params.putString("name", "This will appear beside the picture");
        		    params.putString("caption", "This will appear under the title");
        		    params.putString("description", "This will appear under the caption");
        		    params.putString("picture", "This is the image to appear in the post");
        	
        		    Request.Callback callback= new Request.Callback() {
        	            public void onCompleted(Response response) {
        	                JSONObject graphResponse = response
        	                                           .getGraphObject()
        	                                           .getInnerJSONObject();
        	                String postId = null;
        	                try {
        	                    postId = graphResponse.getString("id");
        	                } catch (JSONException e) {
        	                    Log.i("MUU",
        	                        "JSON error "+ e.getMessage());
        	                }
        	                FacebookRequestError error = response.getError();
        	                if (error != null) {
        	                    Toast.makeText(getActivity()
        	                         .getApplicationContext(),
        	                         error.getErrorMessage(),
        	                         Toast.LENGTH_SHORT).show();
        	                    } else {
        	                        Toast.makeText(getActivity()
        	                             .getApplicationContext(), 
        	                             postId,
        	                             Toast.LENGTH_LONG).show();
        	                }
        	            }
        	        };

        	        Request request = new Request(session, "me/feed", params, 
        	                              HttpMethod.POST, callback);
        	        
        	        
        	        RequestAsyncTask task = new RequestAsyncTask(request);
        	        task.execute();
        	        }
        	        */
        	        
        		}
    				//FragmentTransaction hft = getActivity().getSupportFragmentManager().beginTransaction();
    								//ft.remove(((DetailsActivity)getActivity()).details_fragment);
    								// in this case, you want to show the help text, but
    								// come back to the previous dialog when you're done
    				//hft.addToBackStack(null);
    								//	null represents no name for the back stack transaction
    				//HelpDialogFragment hdf = HelpDialogFragment.newInstance(R.string.help2);
    				//hdf.show(ft, MainActivity.TAG);
    								//return;
    								//}
        			String bravo = "this";
        			
        	    	//FragmentTransaction 
        	    	ft = ((FragmentActivity)act).getSupportFragmentManager().beginTransaction();
        			Util.recalculateAll(getActivity());
        			Util.summaryFields(myActivity);

        	    	String fbtext = act.getSplitText(rec_id);
        	    	Log.e("SplitText", "FB text="+fbtext);
        	    	
        	    	//FacebookDialogFragment pdf = FacebookDialogFragment.newInstance( R.string.facebook, rec_id );
        	    	FacebookDialogFragment pdf = FacebookDialogFragment.newInstance( R.string.facebook, rec_id, fbtext );
        			//Bundle bundle=new Bundle();
        			//bundle.putString("name", search_item.getText().toString());
        			//pdf.setArguments(bundle);
        			pdf.show(ft, "test show");
        			//return;

//act.publishStory();
        	return true;
        	}
       	});

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> av, View v, int i, long rec_id) {
				//Toast.makeText(getActivity(), "test", 1000);
				
				//Animation hyperspaceJump = AnimationUtils.loadAnimation(app.getApplicationContext(), R.anim.rotate_360);
    			//v.startAnimation(hyperspaceJump);
    			
				Log.d("uus", " av="+av+" v="+v+" i="+i+" rec_id="+rec_id);
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				long start_time=Util.getLongTimeFromstring(((TextView)v.findViewById(R.id.start_time)).getText().toString());
						//Util.getLongTime(((TextView)v.findViewById(R.id.start_time)).getText().toString());
				long end_time=Util.getLongTimeFromstring(((TextView)v.findViewById(R.id.end_time)).getText().toString());
				long time=Util.getLongTimeFromstring(((TextView)v.findViewById(R.id.time)).getText().toString());
				long split_nr=Long.parseLong(((TextView)v.findViewById(R.id.split_nr)).getText().toString().split(":")[1]);
				Log.d("uus"," rec_id="+rec_id+" start_time="+Util.displayCalcTime(getActivity(), start_time)+" end_time="+Util.displayCalcTime(getActivity(), end_time)+" time="+Util.displayCalcTime(getActivity(), time));
				
				//ainult siis teeme, kui on viimane rida
				Cursor c = HaanjaApp.dataManager.getHaanjaCursor();
				long last_split=-1;
        		if (c.getCount()>0) {
        			c.moveToLast();
        			last_split = c.getInt(c.getColumnIndex(HaanjaContent.Haanja.HaanjaColumns.SPLIT));
        		}
        		c.close();
        		c=null;
        		//if (split_nr==last_split) {
        			ChangeTimeDialogFragment pdf = ChangeTimeDialogFragment.newInstance( start_time, end_time, time, rec_id, split_nr );
        			//Bundle bundle=new Bundle();
        			//bundle.putString("name", search_item.getText().toString());
        			//pdf.setArguments(bundle);
        			pdf.show(ft, "test show");
        			return;
        		/*} else {
    				FragmentTransaction hft = getActivity().getSupportFragmentManager().beginTransaction();
    				//ft.remove(((DetailsActivity)getActivity()).details_fragment);
    				// in this case, you want to show the help text, but
    				// come back to the previous dialog when you're done
    				hft.addToBackStack(null);
    				//	null represents no name for the back stack transaction
    				HelpDialogFragment hdf = HelpDialogFragment.newInstance(R.string.help2);
    				hdf.show(ft, MainActivity.TAG);
    				return;
        		}*/
			}


		});
		
			Button mBtnRecalculate = (Button) this.myActivity.findViewById(R.id.bt_recalculate);
	        mBtnRecalculate.setOnClickListener(new View.OnClickListener() {
	       	 
	            @Override
	            public void onClick(View v) {
	                // first time
	            	Log.d("recalculate","onClick arvuta kõik uuesti üle");
	            	
	            	Animation hyperspaceJump = AnimationUtils.loadAnimation(app.getApplicationContext(), R.anim.rotate_360);
	    			v.startAnimation(hyperspaceJump);
	    			
        			//joonistame uuesti listi
	            	Util.recalculateAll(getActivity());
	            	Util.summaryFields(myActivity);
        			mAdapter.getCursor().requery();
        			mAdapter.notifyDataSetChanged();
        			getActivity().getContentResolver().notifyChange(HaanjaContent.Haanja.CONTENT_URI, null);
        			mAdapter.notifyDataSetChanged();
        			Log.d(MainActivity.TAG, "üle arvutatud kõik");
        			//Toast.makeText(getActivity(), "üle arvutatud kõik", 5000).show();
        			
	            	//stopService();
	            }
	        });

			Button mBtnDelete = (Button) this.myActivity.findViewById(R.id.bt_delete);
	        mBtnDelete.setOnClickListener(new View.OnClickListener() {
	       	 
	            @Override
	            public void onClick(View v) {
	                // first time
	            	Log.d("delete","onClick delete viimane rida");
	            	Animation hyperspaceJump = AnimationUtils.loadAnimation(app.getApplicationContext(), R.anim.hyperspace_jump);
	    			v.startAnimation(hyperspaceJump);
	            	//stopService();
	        		Cursor c = HaanjaApp.dataManager.getHaanjaCursor();
	        		if (c.getCount()>0) {
	        			c.moveToLast();
	        			int id = c.getInt(c.getColumnIndex(HaanjaContent.Haanja.HaanjaColumns._ID));
	        			
	        			Haanja haanja_prev = Util.buildHaanjaFromCursor(c);
	        			Log.d("haanja_prev","haanja_prev values="+Util.haanjaValues(haanja_prev)+" {n haanja_prev ise="+haanja_prev);
	        			
	        			//Haanja haanja=new Haanja();
	        			//haanja.setStart_time(System.currentTimeMillis());
	        			//haanja.setSplit(1);
	        			//int count = getActivity().getContentResolver().delete(HaanjaContent.Haanja.CONTENT_URI, "id="+id, selectionArgs);
	        			int count = getActivity().getContentResolver().delete(Uri.parse(HaanjaContent.Haanja.CONTENT_URI+"/"+id), null, null);
	        			
	        			//mAdapter = (InteractiveArrayAdapter)getListAdapter();
	        			//long id=Integer.parseInt(uri.getLastPathSegment());
	        			
	        			mAdapter.getCursor().requery();
	        			mAdapter.notifyDataSetChanged();
	        			getActivity().getContentResolver().notifyChange(HaanjaContent.Haanja.CONTENT_URI, null);
	        			mAdapter.notifyDataSetChanged();
	        			
	        			Log.d(MainActivity.TAG, "kustutatud rida id = "+id+" count="+count);
	        			//Toast.makeText(getActivity(), "kustutatud rida id = "+id+" count="+count, 5000).show();

	        			Button mBtnSplit = (Button) getActivity().findViewById(R.id.bt_split);
	        			c.close();
	        			c = HaanjaApp.dataManager.getHaanjaCursor();
	        			if (c.getCount()==0) {
	        				mBtnSplit.setText(getResources().getText(R.string.add_start));
	        				SharedPreferences settings = getActivity().getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
			       	    	//if (!settings.contains("user_id")) {
			       	     	Log.i("HaanjaPrefs", "tegevused prefferences failiga, svaed settings");
			       	     	SharedPreferences.Editor setEditor = settings.edit();
			       	     	setEditor.putLong("future_time", 0);
			       	     	setEditor.putLong("splits", 0);
			       	     	setEditor.putFloat("runned_km", 0);
			       	     	setEditor.putLong("start_time_long", 0);
			       	     	setEditor.putString("start_time", "");
			       	     	setEditor.putLong("normalized_start_time_long", 0);
			       	     	setEditor.putString("normalized_start_time", "");
			       	     	setEditor.putString("future_avglap_finish_time", "" );
			       	     	setEditor.putString("future_lastlap_finish_time", "");
			       	     	setEditor.putString("future_avglap_finish_km", "" );
			       	     	setEditor.putString("future_lastlap_finish_km", "");
			       	     	setEditor.putLong("future_lastlap_finish_time_long", 0);
			       	     	setEditor.putString("future_lastlap_finish_datetime", "");
			       	     	setEditor.putLong("last_time", 1000*60*60);
			       	     	/*setEditor.putLong("future_time", 0);
			       	     	setEditor.putLong("splits", 0);
			       	     	setEditor.putLong("start_time_long", System.currentTimeMillis());
			       	     	setEditor.putString("start_time", Util.displayCalcTime(getActivity().getApplicationContext(), System.currentTimeMillis()));
			       	     	setEditor.putLong("normalized_start_time_long", Util.normalize_time(System.currentTimeMillis()));
			       	     	setEditor.putString("normalized_start_time", Util.displayCalcTime(getActivity().getApplicationContext(), Util.normalize_time(System.currentTimeMillis())));
			       	     	setEditor.putString("future_avglap_finish_time", Util.displayCalcTime(getActivity().getApplicationContext(), -1) );
			       	     	setEditor.putString("future_lastlap_finish_time", Util.displayCalcTime(getActivity().getApplicationContext(), -1));
			       	     	setEditor.putLong("future_lastlap_finish_time_long", -1);
			       	     	setEditor.putString("future_lastlap_finish_datetime", Util.displayCalcTime(getActivity().getApplicationContext(), (Util.normalize_time(System.currentTimeMillis()))));*/
			       	     	setEditor.commit();
			       	     	
	        				//Toast.makeText(this, "Pole veel ridu", 5000).show();
	        				//siin oleks vaja teha teised nupud mitteaktiivseks
	        				
	        			}
	        			else {
	        				mBtnSplit.setText(getResources().getText(R.string.add_split));
	        				//Toast.makeText(this, "Read olemas", 5000).show();
	        			}
	        			//c.close();
	        			//c=null;
	        			Util.recalculateAll(getActivity());
	        			Util.summaryFields(myActivity);
	        			//View.getsetText(getResources().getText(R.string.add_split));
	        		} else {
	        			
	        		}
	        		c.close();
	        		c=null;
	            }
	        });

	        //Cursor cur = (Cursor)MainActivity.tf.mAdapter.getCursor();
			Button mBtnSplit = (Button) this.myActivity.findViewById(R.id.bt_split);
			if (mBtnSplit==null)
				Log.e("TitlesFragment","pole nuppu layouti peal sellise id-ga R.id.bt_split");
			
			
			//mBtnSplit.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(android.R.drawable.ic_input_add), null, null, null);
	        mBtnSplit.setOnClickListener(new View.OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	                // first time
	            	//Log.d("testike","onClick uus");
	            	//ArrayAdapter<Model> adapter1 = (InteractiveArrayAdapter) getListAdapter();
	        		//ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
	        		Log.d(MainActivity.TAG, "onClick");
	        		
	        		//Log.d("test", "add new title");
	        			
/*	        			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
	        			AddTitleDialogFragment pdf = AddTitleDialogFragment.newInstance("Enter Something");
	        			pdf.show(ft, "test show");
*/	        			
	        			/*if (adapter1.getCount() > 0) {
	        				Model comment = (Model) adapter1.getItem(0);
	        				Log.d("test", comment.getName());
	        				//datasource.deleteComment(comment);
	        				//adapter = (InteractiveArrayAdapter)getListAdapter();
	        				//selle asemele tuleb uue lisamine
	        				
	        				adapter1.add(comment);
	        			}
	        			
	        		adapter1.notifyDataSetChanged();*/
	        		
	        		//siin peab lisama rea.
	        		//kui pole ühtegi rida, siis start rida. kui 15 olemas, siis ütleb seda et rohkem ei saa
	        		//kui on, siis võtame eelmise rea, lõpetame selle ja lisame uue (kui pole 15nes)
	        		Cursor c = HaanjaApp.dataManager.getHaanjaCursor();
	        		if (c.getCount()==0) {
	        			Haanja haanja=new Haanja();
	        			haanja.setStart_time(System.currentTimeMillis());
	        			haanja.setSplit(1);
	        			Uri uri = getActivity().getContentResolver().insert(HaanjaContent.Haanja.CONTENT_URI, 
	                					Util.haanjaValues(haanja));
	        			//mAdapter = (InteractiveArrayAdapter)getListAdapter();
	        			long id=Integer.parseInt(uri.getLastPathSegment());
	        			
	        			mAdapter.getCursor().requery();
	        			mAdapter.notifyDataSetChanged();
	        			getActivity().getContentResolver().notifyChange(HaanjaContent.Haanja.CONTENT_URI, null);
	        			mAdapter.notifyDataSetChanged();
	        			
	        			Log.d(MainActivity.TAG, "tehtud start rida id = "+id);
	        			//Toast.makeText(getActivity(), "Tehtud start rida id = "+id, 5000).show();
	        			
	        			//View.getsetText(getResources().getText(R.string.add_split));
	        		} else {
	        			//kui on start antud ja üks ring vähemalt läbi
	        			if (!c.moveToLast()) Log.e("ei suuda", "ei saa viimasele minna");
	        			int id = c.getInt(c.getColumnIndex(HaanjaContent.Haanja.HaanjaColumns._ID));
	        			//ehitame valmis haanja varasema. objekti
	        			Haanja haanja_prev = Util.buildHaanjaFromCursor(c);
	        			//leiame praeguse aja ja aegade vahe
	        			
	        			SharedPreferences settings = getActivity().getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
	        			if (!settings.getBoolean("istimebased", true)) { 
	        				long split=settings.getLong("split_pref", Constants.HAANJA100_SPLITS);
	        						
	        			
	        			//end time muudetakse ainult siis kui on väiksem 15st või puudub
	        			if (haanja_prev.getSplit()<split || haanja_prev.getEnd_time()<=0) {
	        				haanja_prev.setEnd_time(System.currentTimeMillis());
	        				haanja_prev.setTime(haanja_prev.getEnd_time()-haanja_prev.getStart_time());
	        			}
	        			} else { //kui on ajabaasil siis peaks kontrollima muud... - kas keskmise kiiruse järgi on reaalne läbida?
	        				//ilmselt seda ei saa kontrollida, kuna kunagi ei tea, äkki tuleb viimasel ringil kiireim keskmine - ei oska eeldada kindlalt
	        				haanja_prev.setEnd_time(System.currentTimeMillis());
	        				haanja_prev.setTime(haanja_prev.getEnd_time()-haanja_prev.getStart_time());
	        			}
	        			
	        			//teeme uue objekti ja algväärtustame
	        			Haanja haanja_last = new Haanja();
	        			//haanja_last.setStart_time(haanja_prev.getEnd_time()+1000);
	        			haanja_last.setStart_time(haanja_prev.getEnd_time());
	        			long split=settings.getLong("split_pref", Constants.HAANJA100_SPLITS);
	        			long laps=settings.getLong("laps_pref", Constants.JOOKS_LAPS);
	        			if (!settings.getBoolean("istimebased", true)) { 
	        				haanja_last.setSplit(haanja_prev.getSplit()+1);
	        			} else {  //ajabaasil õib olla rohkem splitte
	        				haanja_last.setSplit(haanja_prev.getSplit()+1);
	        			}
	        			Log.d("haanja_prev","haanja_prev values="+Util.haanjaValues(haanja_prev)+" {n haanja_prev ise="+haanja_prev);
	        			
	        			//kirjutame vana baasi
	        			
	        			long count = getActivity().getContentResolver().update(Uri.parse(HaanjaContent.Haanja.CONTENT_URI+"/"+id), Util.haanjaValues(haanja_prev), null, null); 
	        			Log.d(MainActivity.TAG, "vana rida parandatud,  id = "+id+" split="+haanja_prev.getSplit()+"  muudetud ridu="+count);
	        			
	        			//lisame uue rea ka baasi kui on vähem kui 15ringi
	        			if (haanja_last.getSplit()<=15) {
	        				Uri uri = getActivity().getContentResolver().insert(HaanjaContent.Haanja.CONTENT_URI, Util.haanjaValues(haanja_last));
	        				//mAdapter = (InteractiveArrayAdapter)getListAdapter();
	        				id=Integer.parseInt(uri.getLastPathSegment());
	        			} else {
		        			Log.d(MainActivity.TAG, "viimane ring, rohkem ei lisa");
		        			//Toast.makeText(getActivity(), "Tehtud viimane ring, rohkem ei lisa", 5000).show();
	        			}

	        			//joonistame uuesti listi
	        			mAdapter.getCursor().requery();
	        			mAdapter.notifyDataSetChanged();
	        			getActivity().getContentResolver().notifyChange(HaanjaContent.Haanja.CONTENT_URI, null);
	        			mAdapter.notifyDataSetChanged();
	        			Log.d(MainActivity.TAG, "tehtud uus lisarida rida id = "+id+" split="+haanja_last.getSplit());
	        			//Toast.makeText(getActivity(), "Tehtud uus lisarida id = "+id+" split="+haanja_last.getSplit(), 5000).show();
	        			
	        		}
	        		c.close();
	        		c=null;
	        		Button mBtnSplit = (Button) getActivity().findViewById(R.id.bt_split);
	        		mBtnSplit.setText(getResources().getText(R.string.add_split));
	        		
	        		Util.recalculateAll(getActivity());
	        		Util.summaryFields(myActivity);
	        		
	            }
	        });

			
			
		setListAdapter(mAdapter);
		//setListAdapter(a_adapter);
		// Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
		// LoaderManager.enableDebugLogging(true);
		 //getListView().invalidateViews();
		/*LoaderManager lm = getLoaderManager();
		if (lm.getLoader(0) != null) {
			lm.initLoader(0, null, this);
		} */
		
		setListShown(false);
		getLoaderManager().initLoader(1, null, this); 
		//getListView().setAdapter(adapter); 
 
        
		
        /*ListView list = getListView();
        
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("veel", "Item on positsioonil " + position + " clicked");
				Toast.makeText(getActivity(),
						"Item in position " + position + " clicked",
						Toast.LENGTH_LONG).show();
				// Return true to consume the click event. In this case the
				// onListItemClick listener is not called anymore.
				return true;
			}
		});*/
		
		/*m_orders = new ArrayList<Order>();
		this.m_adapter = new OrderAdapter(this.getActivity(), R.layout.advanced_list_item, m_orders);
		Log.d("test",this.m_adapter.toString());
        //setListAdapter(this.m_adapter);

        viewOrders = new Runnable(){
        	@Override
        	public void run() {
        		getOrders();
        	}
        };
        Thread thread =  new Thread(null, viewOrders, "MagentoBackground");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(this.getActivity(),    "Please wait...", "Retrieving data ...", true);

        setListAdapter(this.m_adapter);*/
        
        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        //View detailsFrame = this.myActivity.findViewById(R.id.items);
        //mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
		mDualPane = false;

        Log.v(MainActivity.TAG,"in SplitsFragment onActivityCreated. dualPane="+mDualPane);
        
        if (savedState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedState.getInt("curChoice", 0);
        }


        
        /*if (mDualPane) {
            // In dual-pane mode, list view highlights selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }*/
        MainActivity.tf=this;
        //listView.invalidateViews();
	}

	// If non-null, this is the current filter the user has provided.
    String mCurFilter;
    
    /*public boolean onQueryTextChange(String newText) {
        // Called when the action bar search text has changed.  Update
        // the search filter, and restart the loader to do a new query
        // with this filter.
        mCurFilter = !TextUtils.isEmpty(newText) ? newText : null;
        getLoaderManager().restartLoader(0, null, this);
        return true;
    }

    @Override public boolean onQueryTextSubmit(String query) {
        // Don't care about this.
        return true;
    } */
    
 // These are the Contacts rows that we will retrieve.
    static final String[] HAANJA_SUMMARY_PROJECTION = new String[] {
        HaanjaTable.HaanjaColumns._ID,
        HaanjaTable.HaanjaColumns.SPLIT,
        HaanjaTable.HaanjaColumns.START_TIME,
        HaanjaTable.HaanjaColumns.END_TIME,
        HaanjaTable.HaanjaColumns.TIME,
        HaanjaTable.HaanjaColumns.DELETED,
        HaanjaTable.HaanjaColumns.AVG_PACE,
        HaanjaTable.HaanjaColumns.LAP_PACE,
    }; 
    
	//private boolean mFirstRun = true; 
	
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.
        Uri baseUri;
        if (mCurFilter != null) {
            //baseUri = Uri.withAppendedPath(TitlesContentProvider.CONTENT_URI, Uri.encode(mCurFilter));
        	baseUri = Uri.withAppendedPath(HaanjaContent.Haanja.CONTENT_URI, Uri.encode(mCurFilter));
        } else {
            //baseUri = TitlesContentProvider.CONTENT_URI;
        	baseUri = HaanjaContent.Haanja.CONTENT_URI;
        }

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        /*String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + Contacts.DISPLAY_NAME + " != '' ))";*/
        //new CursorLoader()
        if (MainActivity.mSort) {
        	return new CursorLoader(this.myActivity, baseUri,
                HAANJA_SUMMARY_PROJECTION, null, null,
                HaanjaTable.HaanjaColumns.SPLIT + " DESC");//" ASC");
        } else {
        	return new CursorLoader(this.myActivity, baseUri,
                    HAANJA_SUMMARY_PROJECTION, null, null,
                    HaanjaTable.HaanjaColumns.SPLIT + " ASC");//" ASC");
        }
    } 
    
	/**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    public void buildUI(int index) {
        mCurCheckPosition = index;
        Log.v("BuildUI","in SplitsFragment buildUI method");
        //if (mDualPane) {
        	Log.v("BuildUI","in SplitsFragment buildUI method = dualpane = true, index="+index);
            // We can display everything in-place with fragments.
            // Have the list highlight this item and show the data.
            //getListView().setItemChecked(index, true);

            // Check what fragment is shown, replace if needed.
            SplitsFragment splits = (SplitsFragment)
                    getFragmentManager().findFragmentById(R.id.splits_fragment);
            //if (splits == null || splits.getShownIndex() != index) {
                // Make new fragment to show this selection.
                splits = SplitsFragment.newInstance(index);
                

                // Execute a transaction, replacing any existing
                // fragment with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.splits_fragment, splits);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            //}

        //} 
                /*else {
        	Log.v("BuildUI","in SplitsFragment Showdetails method  dualpane != true (false)");
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }*/
    }
    

    private class TitleViewBinder implements SimpleCursorAdapter.ViewBinder {
    	 
        @Override
        public boolean setViewValue(View view, Cursor cursor, int index) {
        	Log.d("test", "test");
            if (index == cursor.getColumnIndex(HaanjaTable.HaanjaColumns.SPLIT)) {
                // get a locale based string for the date
                //DateFormat formatter = android.text.format.DateFormat
                  //      .getDateFormat(getActivity().getApplicationContext());
                //long date = cursor.getLong(index);
                //Date dateObj = new Date(date * 1000);
                //((TextView) view).setText(formatter.format(dateObj));
            	Log.d("test", "view="+view+" index="+index);
            	//Toast.makeText(getActivity(), "text", 1000); 
            	return true;
            } else {
                return false;
            }
        }
    }
        
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
    	hideDialog();
        mAdapter.swapCursor(data);
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
        //mAdapter.notifyDataSetChanged();
        //getListView().invalidateViews();
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
    	//showDialog();
    	hideDialog();
        mAdapter.swapCursor(null);
    } 
	private ProgressDialog m_ProgressDialog = null; 
    //private ArrayList<Order> m_orders = null;
    //private OrderAdapter m_adapter;
    //private Runnable viewOrders;
    
	/*private void getOrders(){
        try{
            m_orders = new ArrayList<Order>();
            Order o1 = new Order();
            o1.setOrderName("SF services");
            o1.setOrderStatus("Pending");
            o1.setOrderMuu("muu");
            Order o2 = new Order();
            o2.setOrderName("SF Advertisement");
            o2.setOrderStatus("Completed");
            o2.setOrderMuu("Muu");
            m_orders.add(o1);
            m_orders.add(o2);
               Thread.sleep(2000);
            Log.i("ARRAY", ""+ m_orders.size());
          } catch (Exception e) { 
            Log.e("BACKGROUND_PROC", e.getMessage());
          }
          getActivity().runOnUiThread(returnRes);
      }
	
	private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
            if(m_orders != null && m_orders.size() > 0){
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_orders.size();i++)
                m_adapter.add(m_orders.get(i));
            }
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
        }
      };
	*/
    /*private class FancyAdapter extends BaseAdapter {

        private String[] mData;//SimpleAdapter ss = new SimpleAdapter(mActivity, null, mCurCheckPosition, data, null);
        //BaseAdapter ba = new BaseAdapter();

        public FancyAdapter(String[] data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.length;
        }

        @Override
        public String getItem(int position) {
            return mData[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView result;

            if (convertView == null) {
                result = (TextView) getActivity().getLayoutInflater().inflate(R.layout.three_line_list_item, parent, false);
                // Set the text color to black here in order to reuse the
                // test_item layout. The preferred way to do it is obviously to
                // set it in the XML possibly via a text appearance.
                // Here we are using a plain Color but keep in mind you can use
                // a ColorStateList if you want your text color to change
                // depending on the current state of the itemview.
                result.setTextColor(Color.BLACK);
            } else {
                result = (TextView) convertView;
            }

            final String cheese = getItem(position);

            result.setText(cheese);

            int normalId;
            int specialId;

            switch (mMethod) {
                case METHOD_USE_SELECTOR_AS_BACKGROUND:
                    // The two following resource identifiers refer to
                    // StateListDrawables.
                    normalId = R.drawable.list_item_selector_normal;
                    specialId = R.drawable.list_item_selector_special;
                    break;
                case METHOD_DRAW_SELECTOR_ON_TOP:
                default:
                    normalId = R.drawable.list_item_background_normal;
                    specialId = R.drawable.list_item_background_special;
                    break;
            }

            // Change the background of this itemview depending on whether the
            // underlying cheese is special or not.
            result.setBackgroundResource(isSpecial(cheese) ? specialId : normalId);

            return result;
        }
*/
        /**
         * Stupid method considering if a cheese is special or not. The
         * algorithm is not the important thing in this tip ^^.
         * 
         * @param cheese The cheese to analyze
         * @return true if that cheese is important else it returns false.
         */
        /*private boolean isSpecial(String cheese) {
            if (cheese != null) {
                for (String tag : SPECIAL_CHEESE_TAGS) {
                    if (cheese.contains(tag)) {
                        return true;
                    }
                }
            }

            return false;
        }

    } */
    
    ////FancyAdaptaer

	@Override
	public void onStart() {
		Log.v(MainActivity.TAG, "in TitlesFragment onStart");
		super.onStart();
	}
	
	@Override
	public void onResume() {
		Log.v(MainActivity.TAG, "in TitlesFragment onResume");
		super.onResume();
	}
	
	@Override
	public void onPause() {
		Log.v(MainActivity.TAG, "in TitlesFragment onPause");
		super.onPause();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.v(MainActivity.TAG, "in TitlesFragment onSaveInstanceState    curChekPosition="+mCurCheckPosition);
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", mCurCheckPosition);
	}

/*	@Override
	public void onClick(View view) {
		
	}*/
	
	// Will be called via the onClick attribute
	// of the buttons in main.xml
/*	public void onClick(View view) {
		//@SuppressWarnings("unchecked")
//		ArrayAdapter<Model> adapter = (InteractiveArrayAdapter) getListAdapter();
		//ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
		Log.d(MainActivity.TAG, "onClick");
		Toast.makeText(getActivity(), "test", 1000);
		
		//Comment comment = null;
		switch (view.getId()) {
		case R.id.bt_add_title:
			String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
			int nextInt = new Random().nextInt(3);
			// Save the new comment to the database
			//comment = datasource.createComment(comments[nextInt]);
			//adapter.add(comment);
			break;
		case R.id.bt_sort_titles:
			if (getListAdapter().getCount() > 0) {
				//comment = (Comment) getListAdapter().getItem(0);
				//datasource.deleteComment(comment);
				//adapter.remove(comment);
			}
			break;
		case R.id.bt_style_titles:
			Log.d("test", "clean");
			if (getListAdapter().getCount() > 0) {
				Model comment = (Model) getListAdapter().getItem(0);
				Log.d("test", comment.getName());
				//datasource.deleteComment(comment);
				//adapter = (InteractiveArrayAdapter)getListAdapter();
				//adapter.remove(comment);
			}
			break;

		}
	//	adapter.notifyDataSetChanged();
	}
*/
	// Will be called via the onClick attribute
	// of the buttons in main.xml
	/*public void onSetSort(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Model> adapter = (InteractiveArrayAdapter) getListAdapter();
		//ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
		Log.d(MainActivity.TAG, "onClick");
		
			Log.d("test", "clean");
			if (getListAdapter().getCount() > 0) {
				Model comment = (Model) getListAdapter().getItem(0);
				Log.d("test", comment.getName());
				//datasource.deleteComment(comment);
				//adapter = (InteractiveArrayAdapter)getListAdapter();
				adapter.remove(comment);
			}
		adapter.notifyDataSetChanged();
	}*/

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		super.onListItemClick(l, v, pos, id);
		Log.v(MainActivity.TAG,"in TitlesFragment onListItemClick. pos = "+ pos);
		/*myActivity.showDetails(pos);
		mCurCheckPosition = pos;*/
		/*CheckedTextView tt = (CheckedTextView) v
				.findViewById(R.id.checkedTextView1);
				if (!tt.isChecked()) {
				tt.setChecked(true);
				tt.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
				} else {
				tt.setChecked(false);
				tt.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
				}*/
		//super.onListItemClick(l, v, pos, id);
		//getListView().onItemClick(l, v, pos, id);
			// Get the item that was clicked
			/*Object o = this.getListAdapter().getItem(pos);
			String keyword = o.toString();*/
			
			//Toast.makeText(this.getActivity(), "You selected: pos=" + pos+" id="+id+" v="+v+" l="+l, Toast.LENGTH_SHORT).show();

		showDetails(pos);
	}
	
	@Override
	public void onAttach(Activity myActivity) {
		Log.v(MainActivity.TAG,"in TitlesFragment onAttach; activity is: " + myActivity);
		super.onAttach(myActivity);
		this.myActivity = (MainActivity)myActivity;
	}

	@Override
	public void onInflate(Activity myActivity, AttributeSet attrs, Bundle savedInstanceState) {
		Log.v(MainActivity.TAG,"in TitlesFragment onInflate. AttributeSet contains:");
		for(int i=0; i<attrs.getAttributeCount(); i++) {
			Log.v(MainActivity.TAG, " " + attrs.getAttributeName(i) +
					" = " + ("id".equals(attrs.getAttributeName(i))?
							Integer.toHexString(attrs.getAttributeIntValue(i, -1)):
								attrs.getAttributeValue(i)));
		}
		super.onInflate(myActivity, attrs, savedInstanceState);
	}
	

	
	@Override
	public void onStop() {
		Log.v(MainActivity.TAG, "in TitlesFragment onStop");
		super.onStop();
	}
	
	@Override
	public void onDestroyView() {
		Log.v(MainActivity.TAG, "in TitlesFragment onDestroyView");
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		Log.v(MainActivity.TAG, "in TitlesFragment onDestroy");
		super.onDestroy();
	}
	
	@Override
	public void onDetach() {
		Log.v(MainActivity.TAG, "in TitlesFragment onDetach");
		super.onDetach();
		//myActivity = null;
	}

	/**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        mCurCheckPosition = index;
        Log.v(MainActivity.TAG,"in TitlesFragment showDetails index="+index);
        if (mDualPane) {
        	Log.v(MainActivity.TAG,"in TitlesFragment showDetails,   mDualPane=true");
            // We can display everything in-place with fragments.
            // Have the list highlight this item and show the data.
/*            getListView().setItemChecked(index, true);

            // Check what fragment is shown, replace if needed.
            ItemsFragment details = (ItemsFragment)
                    getFragmentManager().findFragmentById(R.id.items);
            if (details == null || details.getShownIndex() != index) {
            	Log.v(MainActivity.TAG,"in TitlesFragment showDetails    details==null või index!=index ");
                // Make new fragment to show this selection.
                details = ItemsFragment.newInstance(index);

                // Execute a transaction, replacing any existing
                // fragment with this one inside the frame.
                FragmentTransaction ft
                        = getFragmentManager().beginTransaction();
                ft.replace(R.id.items, details);
                ft.setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }*/

        } else {
        	Log.v(MainActivity.TAG,"in TitlesFragment showDetails,   mDualPane=false, index="+index);
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            //intent.setClass(this.myActivity, ItemsActivity.class);
            intent.putExtra("index", index);
            
            startActivity(intent);
        }
    }

	public static class MyAlertDialog extends DialogFragment {
		/*
		 * All subclasses of Fragment must include a public empty constructor.
		 * The framework will often re-instantiate a fragment class when needed,
		 * in particular during state restore, and needs to be able to find this
		 * constructor to instantiate it. If the empty constructor is not
		 * available, a runtime exception will occur in some cases during state
		 * restore.
		 */
		public MyAlertDialog() {
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			ProgressDialog progress = new ProgressDialog(getActivity());
			//progress.setMessage(getString(R.string.loading));
			progress.setMessage("loading...");
			return progress;
		}
	}

	private void showDialog() {
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}

		// Create and show the dialog.
		DialogFragment newFragment = new MyAlertDialog();
		newFragment.show(ft, "dialog");
	}

	private void hideDialog() {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				if (getActivity()!=null) {
				FragmentTransaction ft = getActivity().getSupportFragmentManager()
						.beginTransaction();
				Fragment prev = getActivity().getSupportFragmentManager()
						.findFragmentByTag("dialog");
				if (ft!=null && prev != null) {
					ft.remove(prev).commit();
				}
				}
			}
		});
	} 
	
	private final Handler mHandler = new Handler();
	
}
