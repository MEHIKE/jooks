package ee.mehike.haanja100.activity;

import ee.mehike.haanja100.R;
import ee.mehike.haanja100.util.Constants;
import ee.mehike.haanja100.util.Util;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class HaanjaPreferenceActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	public static final String KEY_TIME1_PREFERENCE = "time1_preference";
	public static final String KEY_TIME2_PREFERENCE = "time2_preference"; 
	public static final String KEY_TIME3_PREFERENCE = "time3_preference";
	
    public static final String KEY_KM_PREFERENCE = "km_preference";
    public static final String KEY_HOUR_PREFERENCE = "hour_preference";
    public static final String KEY_SPLIT_PREFERENCE = "split_preference";
    public static final String KEY_SPLITKM_PREFERENCE = "splitkm_preference";
    public static final String KEY_LAPS_PREFERENCE = "laps_preference";
    public static final String KEY_RECORDTIME_PREFERENCE = "recordtime_preference";
    public static final String KEY_RECORDKM_PREFERENCE = "recordkm_preference";
    public static final String KEY_RECORDKMEST_PREFERENCE = "recordkmest_preference";
    public static final String KEY_NAME_PREFERENCE = "name_preference";
    public static final String KEY_COMPNAME_PREFERENCE = "compname_preference";
    public static final String KEY_ISTIMEBASED_PREFERENCE = "istimebased_preference";
    
    public static final String COMPNAME_SUMMARY = "compname_preference";
    public static final String NAME_SUMMARY = "name_preference";

	public static final String KEY_KM1_PREFERENCE = "km1_preference";
	public static final String KEY_KM2_PREFERENCE = "km2_preference"; 
	public static final String KEY_KM3_PREFERENCE = "km3_preference"; 
	public static final String KEY_KMRECORD_PREFERENCE = "kmrecord_preference";
	public static final String KEY_KMRECORDEST_PREFERENCE = "kmrecordest_preference";
	public static final String KEY_TIME_PREFERENCE = "time_preference";
	
	private EditTextPreference km_pref;
	private ListPreference hour_pref;
	private EditTextPreference time1_pref;
	private EditTextPreference time2_pref;
	private EditTextPreference time3_pref;
	private EditTextPreference split_pref;
	private EditTextPreference splitkm_pref;
	private EditTextPreference laps_pref;
	private EditTextPreference recordtime_pref;
	private EditTextPreference recordkm_pref;
	private EditTextPreference recordkmest_pref;
	private EditTextPreference name_pref;
	private EditTextPreference compname_pref;
	private CheckBoxPreference istimebased_pref;
	
	private EditTextPreference time_pref;
	private EditTextPreference km1_pref;
	private EditTextPreference km2_pref;
	private EditTextPreference km3_pref;
	private EditTextPreference kmrecord_pref;

	
	private void enableTimebased(boolean enabled) {
		Preference dists = this.getPreferenceScreen().findPreference("distance_screen");
		Preference hours = this.getPreferenceScreen().findPreference("hour_screen");
		
		Preference times = this.getPreferenceScreen().findPreference("time_screen");
		Preference kms = this.getPreferenceScreen().findPreference("km_screen");

		Preference rtimes = this.getPreferenceScreen().findPreference("recordtime_screen");
		Preference rkms = this.getPreferenceScreen().findPreference("recordkm_screen");

		//kui on timebased
		if (enabled) {
			if (dists!=null) {
				Log.e("PREFERENCE", "sists="+dists.getSummary()+" gettitle="+dists.getTitle());
				dists.setEnabled(false);
			}
			if (hours!=null) {
				Log.e("PREFERENCE", "hours="+hours.getSummary()+" gettitle="+hours.getTitle());
				hours.setEnabled(true);
			}
			if (times!=null) {
				Log.e("PREFERENCE", "times="+times.getSummary()+" gettitle="+times.getTitle());
				times.setEnabled(false);
			}
			if (kms!=null) {
				Log.e("PREFERENCE", "kms="+kms.getSummary()+" gettitle="+kms.getTitle());
				kms.setEnabled(true);
			}
			if (rtimes!=null) {
				Log.e("PREFERENCE", "rtimes="+rtimes.getSummary()+" gettitle="+rtimes.getTitle());
				rtimes.setEnabled(false);
			}
			if (rkms!=null) {
				Log.e("PREFERENCE", "rkms="+rkms.getSummary()+" gettitle="+rkms.getTitle());
				rkms.setEnabled(true);
			}

		} else {  //kui ei ole timebased
			if (dists!=null) {
				Log.e("PREFERENCE", "sists="+dists.getSummary()+" gettitle="+dists.getTitle());
				dists.setEnabled(true);
			}
			if (hours!=null) {
				Log.e("PREFERENCE", "hours="+hours.getSummary()+" gettitle="+hours.getTitle());
				hours.setEnabled(false);
			}
			if (times!=null) {
				Log.e("PREFERENCE", "times="+times.getSummary()+" gettitle="+times.getTitle());
				times.setEnabled(true);
			}
			if (kms!=null) {
				Log.e("PREFERENCE", "kms="+kms.getSummary()+" gettitle="+kms.getTitle());
				kms.setEnabled(false);
			}
			if (rtimes!=null) {
				Log.e("PREFERENCE", "rtimes="+rtimes.getSummary()+" gettitle="+rtimes.getTitle());
				rtimes.setEnabled(true);
			}
			if (rkms!=null) {
				Log.e("PREFERENCE", "rkms="+rkms.getSummary()+" gettitle="+rkms.getTitle());
				rkms.setEnabled(false);
			}
			
		}

	}
	
	@Override
	protected void onCreate(Bundle bundle) {
		if(bundle != null) {
			
			for(String key : bundle.keySet()) {
				Log.v("BUNDLE_PREF", " " + key);
			}
		}
		else {
			Log.v("BUNDLE_PREF", " myBundle is null");
		}
		super.onCreate(bundle);
		addPreferencesFromResource(R.xml.preferences);
		//COMPNAME_SUMMARY
		
		SharedPreferences settings = getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
        //mCurViewMode = mPrefs.getInt("view_mode", DAY_VIEW_MODE);

		/*Bundle b= new Bundle();
		b.putDouble("km_pref", (double) settings.getFloat("km_pref", (float)Constants.HAANJA100_KM));
		b.putLong("split_pref", settings.getLong("split_pref", Constants.HAANJA100_SPLITS));
		b.putLong("time1_pref", settings.getLong("time1_pref", Constants.BEST_TIME_1));
		b.putLong("time2_pref", settings.getLong("time2_pref", Constants.BEST_TIME_2));
		b.putLong("time3_pref", settings.getLong("time3_pref", Constants.BEST_TIME_3));
		//Bundle bundle = savedInstanceState.getExtras();*/

		TextWatcher tw = new TextWatcher() {
		      public int mKey;

		      public void afterTextChanged(Editable ss) {
		    	  //handleTextUpdate(mKey, true);
		    	  double kogus=0;
		    	  double hind=0;
		    	  Log.d("textWatcher","  mKey="+mKey+" ss="+ss);
		    	  if (ss.length()==2) {
		    		  if (!ss.toString().trim().contains(":")) {
		    			  //et_end_time.setText(ss.toString()+":");
		    			  ss.append(":");
		    		  }
		    	  } else {
		    	if (ss.length()==5) {
		    		  if (ss.toString().trim().contains(":") && ss.toString().trim().split(":").length==2 
		    				  && ss.toString().trim().indexOf(":")==2 && ss.toString().trim().lastIndexOf(":")<4) {
		    			  //et_end_time.setText(ss.toString()+":");
		    			  ss.append(":");
		    		  }
		    	}
		    	}
		    		  
		    	  
		    	  //if (e_sum==0)
		    	  //edit_sum.setText(sum);
		    	  
		    	  //if (((EditText)s).equals(edit_sum)) {
		      }

		      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
	          public void onTextChanged(CharSequence s, int start, int before, int count) { }
		      // ...and of course the other required methods
		};

		TextWatcher comp = new TextWatcher() {
		      public int mKey;

		      public void afterTextChanged(Editable ss) {
		    	  //handleTextUpdate(mKey, true);
		    	  //double kogus=0;
		    	  //double hind=0;
		    	  Log.d("textWatcher","  mKey="+mKey+" ss="+ss+" compname_pref summary="+compname_pref.getSummary()+" compname gettext="+compname_pref.getText());
		    	  //if (ss!=null && ss.length()>0 && compname_pref.getSummary().charAt(':')<compname_pref.getSummary().length())
		    	  if (ss!=null && ss.length()>0 ) {
		    		  compname_pref.setSummary(ss);
		  			//compname_pref.setSummary(compname_pref.getSummary().subSequence(0, compname_pref.getSummary().charAt(':'))+": "+ss);
		    	  } else
		  			compname_pref.setSummary(compname_pref.getSummary()+": "+Constants.COMPNAME);
		    	  //_sum.setText(ss);
		    	  
		    	  //if (((EditText)s).equals(edit_sum)) {
		      }

		      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
	          public void onTextChanged(CharSequence s, int start, int before, int count) { }
		      // ...and of course the other required methods
		};

		TextWatcher name = new TextWatcher() {
		      public int mKey;

		      public void afterTextChanged(Editable ss) {
		    	  //handleTextUpdate(mKey, true);
		    	  //double kogus=0;
		    	  //double hind=0;
		    	  Log.d("textWatcher","  mKey="+mKey+" ss="+ss+" name_pref summary="+name_pref.getSummary()+" name gettext="+name_pref.getText());
		    	  //if (ss!=null && ss.length()>0 && compname_pref.getSummary().charAt(':')<compname_pref.getSummary().length())
		    	  if (ss!=null && ss.length()>0 ) {
		    		  name_pref.setSummary(ss);
		  			//compname_pref.setSummary(compname_pref.getSummary().subSequence(0, compname_pref.getSummary().charAt(':'))+": "+ss);
		    	  } else
		  			name_pref.setSummary(name_pref.getSummary()+": "+Constants.NAME);
		    	  //_sum.setText(ss);
		    	  
		    	  //if (((EditText)s).equals(edit_sum)) {
		      }

		      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
	          public void onTextChanged(CharSequence s, int start, int before, int count) { }
		      // ...and of course the other required methods
		};
		
		istimebased_pref = (CheckBoxPreference) getPreferenceScreen().findPreference(KEY_ISTIMEBASED_PREFERENCE);
		//CheckBox istimebased_editText = istimebased_pref.gets.getEditText();
		//name_editText.addTextChangedListener(name);
		//time3_pref.setText(""+bundle.getLong("time3_pref"));
		/*istimebased_pref.setText(""+settings.getString("name_pref", Constants.NAME));
		if (name_pref.getText()!=null || "".equals(name_pref.getText())  || "null".equals(name_pref.getText())) {
			//time3_pref.setText(""+Constants.BEST_TIME_3);
			//time3_pref.setText(""+Util.displayCalcTime(this,Constants.BEST_TIME_3));
			name_pref.setText(""+settings.getString("name_pref", Constants.NAME));
		} else
			name_pref.setText(""+name_pref.getText());
        //time3_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		//name_editText.addTextChangedListener(tw);
		if (name_pref.getText()!=null)
			name_pref.setSummary(name_pref.getSummary()+": "+name_pref.getText());
		else
			name_pref.setSummary(name_pref.getSummary()+": "+Constants.NAME);*/
		
		
		
		
		compname_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_COMPNAME_PREFERENCE);
		EditText compname_editText = compname_pref.getEditText();
		compname_editText.addTextChangedListener(comp);
		//time3_pref.setText(""+bundle.getLong("time3_pref"));
		compname_pref.setText(""+settings.getString("compname_pref", Constants.COMPNAME));
		if (compname_pref.getText()!=null || "".equals(compname_pref.getText())  || "null".equals(compname_pref.getText())) {
			//time3_pref.setText(""+Constants.BEST_TIME_3);
			//time3_pref.setText(""+Util.displayCalcTime(this,Constants.BEST_TIME_3));
			compname_pref.setText(""+settings.getString("compname_pref", Constants.COMPNAME));
		} else
			compname_pref.setText(""+compname_pref.getText());
		this.getResources().getString(R.string.compname_pref_summary);
        //time3_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		//compname_editText.addTextChangedListener(tw);
		if (compname_pref.getText()!=null)
			compname_pref.setSummary(compname_pref.getSummary()+": "+compname_pref.getText());
		else
			compname_pref.setSummary(compname_pref.getSummary()+": "+Constants.COMPNAME);

		km_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_KM_PREFERENCE);
		EditText km_editText = km_pref.getEditText();
		km_pref.setText(""+settings.getFloat("km_pref", (float)Constants.HAANJA100_KM));
		if (km_pref.getText()!=null || "".equals(km_pref.getText())) {
			//km_pref.setText(""+Constants.HAANJA100_KM);
			//km_pref.setText(""+(double)settings.getFloat("km_pref", (float)Constants.HAANJA100_KM));
			//km_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_KM_PREFERENCE);
			km_pref.setText(""+settings.getFloat("km_pref", (float)Constants.HAANJA100_KM));
		}
		//km_editText.setText(km_pref.getText().toString()+": "+km_editText.getText());
		km_editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		if (km_pref.getText()!=null)
			km_pref.setSummary(km_pref.getSummary()+": "+km_pref.getText());
			//km_pref.setSummary(km_pref.getSummary()+": "+km_pref.getText());

		//-----------------
		hour_pref = (ListPreference) getPreferenceScreen().findPreference(KEY_HOUR_PREFERENCE);
		String hour_editText = "";
		if (hour_pref.getValue()!=null)
			hour_editText=hour_pref.getValue();//.getEditText();
		else {
			hour_pref.setValue(""+Constants.JOOKS_HOURS);
			hour_editText=hour_pref.getValue();
		}
		/*hour_pref.setText(""+settings.getFloat("hour_pref", (float)Constants.JOOKS_HOURS));
		if (km_pref.getText()!=null || "".equals(km_pref.getText())) {
			//km_pref.setText(""+Constants.HAANJA100_KM);
			//km_pref.setText(""+(double)settings.getFloat("km_pref", (float)Constants.HAANJA100_KM));
			//km_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_KM_PREFERENCE);
			km_pref.setText(""+settings.getFloat("km_pref", (float)Constants.HAANJA100_KM));
		}
		//km_editText.setText(km_pref.getText().toString()+": "+km_editText.getText());
		km_editText.setInputType(InputType.TYPE_CLASS_NUMBER);*/
		if (hour_pref!=null)
			hour_pref.setSummary(hour_pref.getSummary()+": "+hour_pref.getValue());
			//km_pref.setSummary(km_pref.getSummary()+": "+km_pref.getText());

		//--------------
		
		split_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_SPLIT_PREFERENCE);
		EditText split_editText = split_pref.getEditText();
		split_pref.setText(""+settings.getLong("split_pref", Constants.HAANJA100_SPLITS));
		if (split_pref.getText()!=null || "".equals(split_pref.getText())  || "null".equals(split_pref.getText()) ) {
			//split_pref.setText(""+Constants.HAANJA100_SPLITS);
			//split_pref.setText(""+settings.getLong("split_pref", Constants.HAANJA100_SPLITS));
			//split_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_SPLIT_PREFERENCE);
			split_pref.setText(""+settings.getLong("split_pref", Constants.HAANJA100_SPLITS));
		}  else
			split_pref.setText(""+split_pref.getText());
        split_editText.setInputType(InputType.TYPE_CLASS_NUMBER); 
        if (split_pref.getText()!=null)
        	split_pref.setSummary(split_pref.getSummary()+": "+split_pref.getText());
        else
			split_pref.setSummary(split_pref.getSummary()+": "+Constants.HAANJA100_SPLITS);

        //----------------------
		splitkm_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_SPLITKM_PREFERENCE);
		EditText splitkm_editText = splitkm_pref.getEditText();
		splitkm_pref.setText(""+settings.getFloat("splitkm_pref", (float)Constants.JOOKS_LAPKM));
		if (splitkm_pref.getText()!=null || "".equals(splitkm_pref.getText())  || "null".equals(splitkm_pref.getText()) ) {
			//split_pref.setText(""+Constants.HAANJA100_SPLITS);
			//split_pref.setText(""+settings.getLong("split_pref", Constants.HAANJA100_SPLITS));
			//split_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_SPLIT_PREFERENCE);
			splitkm_pref.setText(""+settings.getFloat("splitkm_pref", (float)Constants.JOOKS_LAPKM));
		}  else
			splitkm_pref.setText(""+splitkm_pref.getText());
        splitkm_editText.setInputType(InputType.TYPE_CLASS_NUMBER); 
        if (splitkm_pref.getText()!=null)
        	splitkm_pref.setSummary(splitkm_pref.getSummary()+": "+splitkm_pref.getText());
        else
			splitkm_pref.setSummary(splitkm_pref.getSummary()+": "+Constants.JOOKS_LAPKM);

        //----------------------
		laps_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_LAPS_PREFERENCE);
		EditText laps_editText = laps_pref.getEditText();
		laps_pref.setText(""+settings.getLong("laps_pref", (long)Constants.JOOKS_LAPS));
		if (laps_pref.getText()!=null || "".equals(laps_pref.getText())  || "null".equals(laps_pref.getText()) ) {
			//split_pref.setText(""+Constants.HAANJA100_SPLITS);
			//split_pref.setText(""+settings.getLong("split_pref", Constants.HAANJA100_SPLITS));
			//split_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_SPLIT_PREFERENCE);
			laps_pref.setText(""+settings.getLong("laps_pref", (long)Constants.JOOKS_LAPS));
		}  else
			laps_pref.setText(""+laps_pref.getText());
        laps_editText.setInputType(InputType.TYPE_CLASS_NUMBER); 
        if (laps_pref.getText()!=null)
        	laps_pref.setSummary(laps_pref.getSummary()+": "+laps_pref.getText());
        else
			laps_pref.setSummary(laps_pref.getSummary()+": "+Constants.JOOKS_LAPS);

        //------------------------
        
        time1_pref = (EditTextPreference)getPreferenceScreen().findPreference(KEY_TIME1_PREFERENCE);
		//time1_pref = (EditTextPreference) findPreference("time1_preference");
		EditText time1_editText = time1_pref.getEditText();
		//time1_pref.setText(""+bundle.getLong("time1_pref")); 
		time1_pref.setText(""+Util.displayCalcTime(this, settings.getLong("time1_pref", Constants.BEST_TIME_1)));
		if (time1_pref.getText()!=null || "".equals(time1_pref.getText()) || "null".equals(time1_pref.getText())) {
			//time1_pref.setText(""+Util.displayCalcTime(this,Constants.BEST_TIME_1));
			time1_pref.setText(""+Util.displayCalcTime(this, settings.getLong("time1_pref", Constants.BEST_TIME_1)));
		} else
			time1_pref.setText(""+time1_pref.getText()); 
		
		if (time1_pref.getText()!=null)
			time1_pref.setSummary(time1_pref.getSummary()+": "+time1_pref.getText());
		else
			time1_pref.setSummary(time1_pref.getSummary()+": "+Util.displayCalcTime(this,Constants.BEST_TIME_1));
		
        //time1_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		time1_editText.addTextChangedListener(tw);
        /*time1_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText());
			}
        });
        time1_pref.onClick(dialog, which)*/
        //time1_editText.ond

		//time1_editText.on
        //--------time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText());
        //time1_pref.setOnPreferenceChangeListener(onPreferenceChangeListener)

		time2_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_TIME2_PREFERENCE);
		EditText time2_editText = time2_pref.getEditText();
		//time2_pref.setText(""+bundle.getLong("time2_pref"));
		time2_pref.setText(""+Util.displayCalcTime(this, settings.getLong("time2_pref", Constants.BEST_TIME_2)));
		Log.i("PREF_TIME2", Util.displayCalcTime(this, settings.getLong("time2_pref", Constants.BEST_TIME_2)));
		if (time2_pref.getText()!=null || "".equals(time2_pref.getText()) || "null".equals(time2_pref.getText())) {
			//time2_pref.setText(""+Constants.BEST_TIME_2);
			Log.i("dd","sees");
			//time2_pref.setText(""+Util.displayCalcTime(this,Constants.BEST_TIME_2));
			time2_pref.setText(""+Util.displayCalcTime(this, settings.getLong("time2_pref", Constants.BEST_TIME_2)));
		}else
			time2_pref.setText(""+time2_pref.getText()); 
		//else
        //time2_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		time2_editText.addTextChangedListener(tw);
		if (time2_pref.getText()!=null)
			time2_pref.setSummary(time2_pref.getSummary()+": "+time2_pref.getText());
		else
			time2_pref.setSummary(time2_pref.getSummary()+": "+Util.displayCalcTime(this,Constants.BEST_TIME_2));
		
		
		//---------------------
		
		time3_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_TIME3_PREFERENCE);
		EditText time3_editText = time3_pref.getEditText();
		//time3_pref.setText(""+bundle.getLong("time3_pref"));
		time3_pref.setText(""+Util.displayCalcTime(this, settings.getLong("time3_pref", Constants.BEST_TIME_3)));
		if (time3_pref.getText()!=null || "".equals(time3_pref.getText())  || "null".equals(time3_pref.getText())) {
			//time3_pref.setText(""+Constants.BEST_TIME_3);
			//time3_pref.setText(""+Util.displayCalcTime(this,Constants.BEST_TIME_3));
			time3_pref.setText(""+Util.displayCalcTime(this, settings.getLong("time3_pref", Constants.BEST_TIME_3)));
		} else
			time3_pref.setText(""+time3_pref.getText());
        //time3_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		time3_editText.addTextChangedListener(tw);
		if (time3_pref.getText()!=null)
			time3_pref.setSummary(time3_pref.getSummary()+": "+time3_pref.getText());
		else
			time3_pref.setSummary(time3_pref.getSummary()+": "+Util.displayCalcTime(this,Constants.BEST_TIME_3));
		//addPreferencesFromResource(R.xml.flightoptions);
	
		//---------------------
		
		km1_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_KM1_PREFERENCE);
		EditText km1_editText = km1_pref.getEditText();
		//km1_editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		//time3_pref.setText(""+bundle.getLong("time3_pref"));
		//try {
			km1_pref.setText(""+settings.getLong("km1_pref", Constants.BEST_KM_1));
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		if (km1_pref.getText()!=null || "".equals(km1_pref.getText())  || "null".equals(km1_pref.getText())) {
			//km1_pref.setText(""+settings.getLong("km1_pref", Constants.BEST_KM_1));
		} else
			km1_pref.setText(""+km1_pref.getText());
		
        //time3_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		//km1_editText.addTextChangedListener(tw);
		if (km1_pref.getText()!=null)
			km1_pref.setSummary(km1_pref.getSummary()+": "+km1_pref.getText());
		else
			km1_pref.setSummary(km1_pref.getSummary()+": "+Constants.BEST_KM_1);

		//---------------------
		
		km2_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_KM2_PREFERENCE);
		EditText km2_editText = km2_pref.getEditText();
		//time3_pref.setText(""+bundle.getLong("time3_pref"));
		//try {
			km2_pref.setText(""+settings.getLong("km2_pref", Constants.BEST_KM_2));
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		if (km2_pref.getText()!=null || "".equals(km2_pref.getText())  || "null".equals(km2_pref.getText())) {
			//time3_pref.setText(""+Constants.BEST_TIME_3);
			//time3_pref.setText(""+Util.displayCalcTime(this,Constants.BEST_TIME_3));
			//km2_pref.setText(""+settings.getLong("km2_pref", Constants.BEST_KM_2));
		} else
			km2_pref.setText(""+km2_pref.getText());
        //time3_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		//km1_editText.addTextChangedListener(tw);
		if (km2_pref.getText()!=null)
			km2_pref.setSummary(km2_pref.getSummary()+": "+km2_pref.getText());
		else
			km2_pref.setSummary(km2_pref.getSummary()+": "+Constants.BEST_KM_2);

		//---------------------
		
		km3_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_KM3_PREFERENCE);
		EditText km3_editText = km1_pref.getEditText();
		//time3_pref.setText(""+bundle.getLong("time3_pref"));
		//km3_pref.setText(""+settings.getLong("km3_pref", Constants.BEST_KM_3));
		//try {
			km3_pref.setText(""+settings.getLong("km3_pref", Constants.BEST_KM_3));
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		if (km3_pref.getText()!=null || "".equals(km3_pref.getText())  || "null".equals(km3_pref.getText())) {
			//time3_pref.setText(""+Constants.BEST_TIME_3);
			//time3_pref.setText(""+Util.displayCalcTime(this,Constants.BEST_TIME_3));
			//km3_pref.setText(""+settings.getLong("km3_pref", Constants.BEST_KM_3));
		} else
			km3_pref.setText(""+km3_pref.getText());
        //time3_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		//km1_editText.addTextChangedListener(tw);
		if (km3_pref.getText()!=null)
			km3_pref.setSummary(km3_pref.getSummary()+": "+km3_pref.getText());
		else
			km3_pref.setSummary(km3_pref.getSummary()+": "+Constants.BEST_KM_3);

		//-------------
		
		recordkm_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_RECORDKM_PREFERENCE);
		EditText recordkm_editText = recordkm_pref.getEditText();
		//time3_pref.setText(""+bundle.getLong("time3_pref"));
		//try {
			recordkm_pref.setText(""+settings.getFloat("recordkm_pref", (float)Constants.BEST_KM_RECORD));
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		if (recordkm_pref.getText()!=null || "".equals(recordkm_pref.getText())  || "null".equals(recordkm_pref.getText())) {
			//time3_pref.setText(""+Constants.BEST_TIME_3);
			//time3_pref.setText(""+Util.displayCalcTime(this,Constants.BEST_TIME_3));
			//recordkm_pref.setText(""+settings.getFloat("recordkm_pref", (float)Constants.BEST_KM_RECORD));
		} else
			recordkm_pref.setText(""+recordkm_pref.getText());
        //time3_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		//recordkm_editText.addTextChangedListener(tw);
		if (recordkm_pref.getText()!=null)
			recordkm_pref.setSummary(recordkm_pref.getSummary()+": "+recordkm_pref.getText());
		else
			recordkm_pref.setSummary(recordkm_pref.getSummary()+": "+Constants.BEST_KM_RECORD);

		//-------------
		
		recordkmest_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_RECORDKMEST_PREFERENCE);
		EditText recordkmest_editText = recordkmest_pref.getEditText();
		//time3_pref.setText(""+bundle.getLong("time3_pref"));
		try {
			recordkmest_pref.setText(""+settings.getFloat("recordkmest_pref", (float)Constants.EST_KM_RECORD));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (recordkmest_pref.getText()!=null || "".equals(recordkmest_pref.getText())  || "null".equals(recordkmest_pref.getText())) {
			//time3_pref.setText(""+Constants.BEST_TIME_3);
			//time3_pref.setText(""+Util.displayCalcTime(this,Constants.BEST_TIME_3));
			//recordkm_pref.setText(""+settings.getFloat("recordkm_pref", (float)Constants.BEST_KM_RECORD));
		} else
			recordkmest_pref.setText(""+recordkmest_pref.getText());
        //time3_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		//recordkm_editText.addTextChangedListener(tw);
		if (recordkmest_pref.getText()!=null)
			recordkmest_pref.setSummary(recordkmest_pref.getSummary()+": "+recordkmest_pref.getText());
		else
			recordkmest_pref.setSummary(recordkmest_pref.getSummary()+": "+Constants.EST_KM_RECORD);

		//-----------------
		recordtime_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_RECORDTIME_PREFERENCE);
		EditText recordtime_editText = recordtime_pref.getEditText();
		//time3_pref.setText(""+bundle.getLong("time3_pref"));
		recordtime_pref.setText(""+settings.getString("record_pref", Constants.RECORD));
		if (recordtime_pref.getText()!=null || "".equals(recordtime_pref.getText())  || "null".equals(recordtime_pref.getText())) {
			//time3_pref.setText(""+Constants.BEST_TIME_3);
			//time3_pref.setText(""+Util.displayCalcTime(this,Constants.BEST_TIME_3));
			recordtime_pref.setText(""+settings.getString("record_pref", Constants.RECORD));
		} else
			recordtime_pref.setText(""+recordtime_pref.getText());
        //time3_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		recordtime_editText.addTextChangedListener(tw);
		if (recordtime_pref.getText()!=null)
			recordtime_pref.setSummary(recordtime_pref.getSummary()+": "+recordtime_pref.getText());
		else
			recordtime_pref.setSummary(recordtime_pref.getSummary()+": "+Constants.RECORD);

		name_pref = (EditTextPreference) getPreferenceScreen().findPreference(KEY_NAME_PREFERENCE);
		EditText name_editText = name_pref.getEditText();
		name_editText.addTextChangedListener(name);
		//time3_pref.setText(""+bundle.getLong("time3_pref"));
		name_pref.setText(""+settings.getString("name_pref", Constants.NAME));
		if (name_pref.getText()!=null || "".equals(name_pref.getText())  || "null".equals(name_pref.getText())) {
			//time3_pref.setText(""+Constants.BEST_TIME_3);
			//time3_pref.setText(""+Util.displayCalcTime(this,Constants.BEST_TIME_3));
			name_pref.setText(""+settings.getString("name_pref", Constants.NAME));
		} else
			name_pref.setText(""+name_pref.getText());
        //time3_editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
		//name_editText.addTextChangedListener(tw);
		if (name_pref.getText()!=null)
			name_pref.setSummary(name_pref.getSummary()+": "+name_pref.getText());
		else
			name_pref.setSummary(name_pref.getSummary()+": "+Constants.NAME);

		enableTimebased(istimebased_pref.isChecked());
		
	}

	
    @Override  
    protected void onResume(){ 
        super.onResume(); 
        // Set up a listener whenever a key changes              
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this); 
    } 

    @Override  
    protected void onPause() {  
        super.onPause(); 
        // Unregister the listener whenever a key changes              
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);      
    }  

    
	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String key) {
		// TODO Auto-generated method stub
		//if (!key.equals(this.KEY_ISTIMEBASED_PREFERENCE))
			//Log.d("PREFERENCES","key="+key+"  arg="+arg0.getString(key, "00:00:00"));

		if (key.equals(KEY_TIME1_PREFERENCE)) {
			Log.d("PREFERENCES","läks sisse...");
            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
			time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+arg0.getString(key, "00:00:00"));
        } else
    		if (key.equals(KEY_TIME2_PREFERENCE)) {
    			Log.d("PREFERENCES","läks sisse...");
                //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
    			time2_pref.setSummary(getResources().getString(R.string.time2_pref_summary)+": "+arg0.getString(key, "00:00:00"));
            } 
    		else
    			if (key.equals(KEY_TIME3_PREFERENCE)) {
    				Log.d("PREFERENCES","läks sisse...");
    	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
    				time3_pref.setSummary(getResources().getString(R.string.time3_pref_summary)+": "+arg0.getString(key, "00:00:00"));
    	        } else
        			if (key.equals(KEY_SPLIT_PREFERENCE)) {
        				Log.d("PREFERENCES","läks sisse...");
        	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
        				split_pref.setSummary(getResources().getString(R.string.split_pref_summary)+": "+arg0.getString(key, "0"));
        	        }  else
            			if (key.equals(KEY_KM_PREFERENCE)) {
            				Log.d("PREFERENCES","läks sisse...");
            	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
            				km_pref.setSummary(getResources().getString(R.string.km_pref_summary)+": "+arg0.getString(key, "0.0"));
            	        }   else
                			if (key.equals(KEY_RECORDTIME_PREFERENCE)) {
                				Log.d("PREFERENCES","läks sisse...");
                	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
                				recordtime_pref.setSummary(getResources().getString(R.string.recordtime_pref_summary)+": "+arg0.getString(key, "00:00:00"));
                	        }    else
                    			if (key.equals(KEY_NAME_PREFERENCE)) {
                    				Log.d("PREFERENCES","läks sisse...");
                    	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
                    				name_pref.setSummary(getResources().getString(R.string.name_pref_summary)+": "+arg0.getString(key, Constants.NAME));
                    	        }   else
                        			if (key.equals(KEY_COMPNAME_PREFERENCE)) {
                        				Log.d("PREFERENCES","läks sisse...");
                        	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
                        				compname_pref.setSummary(getResources().getString(R.string.compname_pref_summary)+": "+arg0.getString(key, Constants.COMPNAME));
                        	        }   else
                            			if (key.equals(KEY_ISTIMEBASED_PREFERENCE)) {
                            				Log.d("PREFERENCES","läks sisse...");
                            	            //compname_pref.setSummary(getResources().getString(R.string.compname_pref_summary)+": "+arg0.getString(key, Constants.COMPNAME));
                            	            //if (istimebased_pref.isChecked()) {
                            	            	this.recreate();
                            	            /*	addPreferencesFromResource(R.xml.timebased_preferences);
                            	            } else {
                            	            	addPreferencesFromResource(R.xml.preferences);
                            	            }*/
                            	        } else
                                			if (key.equals(KEY_RECORDKM_PREFERENCE)) {
                                				Log.d("PREFERENCES","läks sisse...");
                                	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
                                				recordkm_pref.setSummary(getResources().getString(R.string.recordkm_pref_summary)+": "+arg0.getString(key, ""+Constants.BEST_KM_RECORD));
                                	        } else
                                    			if (key.equals(KEY_RECORDKMEST_PREFERENCE)) {
                                    				Log.d("PREFERENCES","läks sisse...");
                                    	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
                                    				recordkmest_pref.setSummary(getResources().getString(R.string.recordkmest_pref_summary)+": "+arg0.getString(key, ""+Constants.EST_KM_RECORD));
                                    	        } else
                                    			if (key.equals(KEY_KM1_PREFERENCE)) {
                                    				Log.d("PREFERENCES","läks sisse...");
                                    	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
                                    				km1_pref.setSummary(getResources().getString(R.string.km1_pref_summary)+": "+arg0.getString(key, ""+Constants.BEST_KM_1));
                                    	        } else
                                        			if (key.equals(KEY_KM2_PREFERENCE)) {
                                        				Log.d("PREFERENCES","läks sisse...");
                                        	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
                                        				km2_pref.setSummary(getResources().getString(R.string.km2_pref_summary)+": "+arg0.getString(key, ""+Constants.BEST_KM_2));
                                        	        } else
                                            			if (key.equals(KEY_KM3_PREFERENCE)) {
                                            				Log.d("PREFERENCES","läks sisse...");
                                            	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
                                            				km3_pref.setSummary(getResources().getString(R.string.km3_pref_summary)+": "+arg0.getString(key, ""+Constants.BEST_KM_3));
                                            	        } else
                                                			if (key.equals(KEY_SPLITKM_PREFERENCE)) {
                                                				Log.d("PREFERENCES","läks sisse...");
                                                	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
                                                				splitkm_pref.setSummary(getResources().getString(R.string.splitkm_pref_summary)+": "+arg0.getString(key, ""+Constants.JOOKS_LAPKM));
                                                	        } else
                                                    			if (key.equals(KEY_LAPS_PREFERENCE)) {
                                                    				Log.d("PREFERENCES","läks sisse...");
                                                    	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
                                                    				laps_pref.setSummary(getResources().getString(R.string.laps_pref_summary)+": "+arg0.getString(key, ""+Constants.JOOKS_LAPS));
                                                    	        } else
                                                        			if (key.equals(KEY_HOUR_PREFERENCE)) {
                                                        				Log.d("PREFERENCES","läks sisse...");
                                                        	            //time1_pref.setSummary(getResources().getString(R.string.time1_pref_summary)+": "+time1_pref.getText()); 
                                                        				hour_pref.setSummary(getResources().getString(R.string.hour_pref_summary)+": "+arg0.getString(key, ""+Constants.JOOKS_HOURS));
                                                        	        }


        /*else if (key.equals(KEY_AN_EDITTEXT_PREFERENCE)) { 
            mEditBoxPreference.setSummary("Current value is " + sharedPreferences.getString(key, ""));  
        } */

	}
	
	@Override
	public void onBackPressed() {
		Log.d("TAGASI_PREF","peaks salvestama uued preferencid");
		Log.d("TAGASI_PREF","pref_time1="+time1_pref.getText());
		Log.d("TAGASI_PREF","pref_time2="+time2_pref.getText());
		Log.d("TAGASI_PREF","pref_time3="+time3_pref.getText());
		Log.d("TAGASI_PREF","pref_km1="+km1_pref.getText());
		Log.d("TAGASI_PREF","pref_km2="+km2_pref.getText());
		Log.d("TAGASI_PREF","pref_km3="+km3_pref.getText());
		Log.d("TAGASI_PREF","pref_km="+km_pref.getText());
		Log.d("TAGASI_PREF","pref_split="+split_pref.getText());
		Log.d("TAGASI_PREF","record_time="+recordtime_pref.getText());
		Log.d("TAGASI_PREF","record_km="+recordkm_pref.getText());
		Log.d("TAGASI_PREF","recordest_km="+recordkmest_pref.getText());
		Log.d("TAGASI_PREF","pref_splitkm="+splitkm_pref.getText());
		Log.d("TAGASI_PREF","pref_laps="+laps_pref.getText());
		Log.d("TAGASI_PREF","pref_hour="+hour_pref);
		Log.d("TAGASI_PREF","name="+name_pref.getText());
		Log.d("TAGASI_PREF","compname="+compname_pref.getText());
		Intent i = new Intent();
		/*Bundle b= new Bundle();
		b.putDouble("km_pref", Double.parseDouble(km_pref.getText()));
		b.putLong("split_pref", Long.parseLong(split_pref.getText()));
		b.putLong("time1_pref", Util.getLongTimeFromstring(time1_pref.getText()));
		b.putLong("time2_pref", Util.getLongTimeFromstring(time2_pref.getText()));
		b.putLong("time3_pref", Util.getLongTimeFromstring(time3_pref.getText()));
		i.putExtras(b);*/
		SharedPreferences settings = getSharedPreferences(Util.PREFERENCE_FILENAME, 0);
		SharedPreferences.Editor ed = settings.edit();
		ed.putFloat("km_pref", (float)Float.parseFloat(km_pref.getText()));
		if ("".equals(km1_pref.getText())) {
			km1_pref.setText(""+Constants.BEST_KM_1);
		}
		ed.putLong("km1_pref", (long)Long.parseLong(km1_pref.getText()));
		if ("".equals(km2_pref.getText())) {
			km2_pref.setText(""+Constants.BEST_KM_2);
		}
		ed.putLong("km2_pref", (long)Long.parseLong(km2_pref.getText()));
		if ("".equals(km3_pref.getText())) {
			km3_pref.setText(""+Constants.BEST_KM_3);
		}
		ed.putLong("km3_pref", (long)Long.parseLong(km3_pref.getText()));
		if ("".equals(split_pref.getText())) {
			split_pref.setText(""+Constants.HAANJA100_SPLITS);
		}
		ed.putLong("split_pref", Long.parseLong(split_pref.getText()));
		if ("".equals(recordtime_pref.getText())) {
			recordtime_pref.setText(""+Constants.RECORD);
		}
		ed.putString("record_pref", recordtime_pref.getText());
		ed.putString("recordtime_pref", recordtime_pref.getText());
		if ("".equals(recordkm_pref.getText())) {
			if (Long.parseLong(hour_pref.getValue().toString())==24)
				recordkm_pref.setText(""+Constants.BEST_KM_RECORD);
			if (Long.parseLong(hour_pref.getValue().toString())==12)
				recordkm_pref.setText(""+Constants.BEST_KM12INDOORM_RECORD);
		}
		ed.putFloat("recordkm_pref", (float)Double.parseDouble(recordkm_pref.getText()));
		if ("".equals(recordkmest_pref.getText())) {
			recordkmest_pref.setText(""+Constants.EST_KM_RECORD);
		}
		ed.putFloat("recordkmest_pref", (float)Double.parseDouble(recordkmest_pref.getText()));
		if ("".equals(splitkm_pref.getText())) {
			splitkm_pref.setText(""+Constants.SPLIT_KM);
		}
		ed.putFloat("splitkm_pref", (float)Double.parseDouble(splitkm_pref.getText()));
		if ("".equals(laps_pref.getText())) {
			laps_pref.setText(""+Constants.JOOKS_LAPS);
		}
		ed.putLong("laps_pref", Long.parseLong(laps_pref.getText()));
		ed.putLong("hours_pref", Long.parseLong(hour_pref.getValue().toString()));
		
		if ("".equals(name_pref.getText())) {
			name_pref.setText(""+Constants.NAME);
		}
		ed.putString("name_pref", name_pref.getText());
		if ("".equals(compname_pref.getText())) {
			compname_pref.setText(""+Constants.COMPNAME);
		}
		ed.putString("compname_pref", compname_pref.getText());
		if ("".equals(time1_pref.getText())) {
			time1_pref.setText(""+Constants.BEST_TIME_1);
		}
		ed.putLong("time1_pref", Util.getLongTimeFromstring("test: "+time1_pref.getText()));
		if ("".equals(time2_pref.getText())) {
			time2_pref.setText(""+Constants.BEST_TIME_2);
		}
		ed.putLong("time2_pref", Util.getLongTimeFromstring("test: "+time2_pref.getText()));
		if ("".equals(time3_pref.getText())) {
			time3_pref.setText(""+Constants.BEST_TIME_3);
		}
		ed.putLong("time3_pref", Util.getLongTimeFromstring("test: "+time3_pref.getText()));
		
		ed.putBoolean("istimebased", istimebased_pref.isChecked());
		ed.commit();
		setResult(RESULT_OK, i);
		super.finish();

		//super.finish();
	}
	
	/*protected void onDialogClosed(boolean positiveResult) { 
        super.onDialogClosed(positiveResult); 
        if (positiveResult) setSummary(getEntry()); 
    } */

	
}
