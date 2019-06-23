package ee.mehike.haanja100.fragments.dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import ee.mehike.haanja100.MainActivity;
import ee.mehike.haanja100.R;
import ee.mehike.haanja100.listener.OnTimepickerSetListener;
import ee.mehike.haanja100.listener.OnChangeTimeDialogDoneListener;
import ee.mehike.haanja100.provider.HaanjaContent;
import ee.mehike.haanja100.util.Util;


import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

//http://pastebin.com/bXh4picG
//datepicker teistmooid
public class ChangeTimeDialogFragment extends DialogFragment implements View.OnClickListener, OnTimeSetListener {
//OnDatepickerSetListener,
		//private EditText et_sum;
		//private AutoCompleteTextView et_shop;
		//private AutoCompleteTextView et_account;
		//private Spinner et_type;
		private TextView et_start_time;
		private TextView et_split;
		private EditText et_end_time;
		private EditText et_time;
		
		private long rec_id;
		//private double sum;
		private long start_time;
		private long end_time;
		private long time;
		private long split;
		
	/*	  private PickerView end_hourPicker;
		  private PickerView end_minutePicker;
		  private PickerView end_secondPicker;
		  private Calendar end_time_calendar;

		  private PickerView hourPicker;
		  private PickerView minutePicker;
		  private PickerView secondPicker;
		  private Calendar time_calendar;
*/
		  
		//private boolean isBudget=false;
		/*public static int aasta;
		public static int kuu;
		public static int paev;*/
		
		/*public void onDatepickerSet(String s) {
			et_date.setText(s);
		}*/
		//private String name;
		//private long sid;

		public static ChangeTimeDialogFragment newInstance(long start_time, long end_time, long time, long rec_id, long split) {
			ChangeTimeDialogFragment pdf = new ChangeTimeDialogFragment();
			Bundle bundle = new Bundle();
			Log.e("TIMEDIALOG","newInstance");
			//bundle.putInt("b_type",b_type);
			//bundle.putInt("dialogtype",dialogtype);
			//bundle.putDouble("sum", sum);
			bundle.putLong("rec_id", rec_id);
			bundle.putLong("split", split);
			bundle.putLong("start_time", start_time);
			bundle.putLong("end_time", end_time);
			bundle.putLong("time", time);
			bundle.putLong("date", System.currentTimeMillis());
			//id=-1;
			pdf.setArguments(bundle);
			return pdf;
			
		}


		@Override
		public void onAttach(Activity act) {
			// If the activity you're being attached to has
			// not implemented the OnDialogDoneListener
			// interface, the following line will throw a
			// ClassCastException. This is the earliest you
			// can test if you have a well-behaved activity.
			//MainActivity act1 = (MainActivity) act;
			
			OnChangeTimeDialogDoneListener test = (OnChangeTimeDialogDoneListener)act;
			super.onAttach(act);
		}
	
		@Override
		public void onCreate(Bundle icicle) {
			super.onCreate(icicle);
			this.setCancelable(true);
			int style = DialogFragment.STYLE_NORMAL, theme = 0;
			setStyle(style,theme);
			
			//if (isApp("com.shop.budget")) {
			//this.isBudget=Util.isApp("com.shop.budget", ((Activity)ItemsActivity.items_fragment.getActivity()));
			//Log.d("budgetDialog","isBudget="+this.isBudget);
        		//final Intent intent = new Intent(Intent.ACTION_PICK);
        		//intent.setComponent(new ComponentName("com.shop.budget", "com.shop.budget"));
        		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        		//startActivityForResult(intent, PICK_BUDGET);
        	//}
		}
		
		//private DatePicker dpResult;
		
		// display current date
		public void setCurrentDateOnView(View v) {
	 
			et_start_time = (TextView) v.findViewById(R.id.d_start_time);
			et_end_time = (EditText) v.findViewById(R.id.d_end_time);
			et_time = (EditText) v.findViewById(R.id.time);
			et_split = (TextView) v.findViewById(R.id.d_split);
			//dpResult = (DatePicker) v.findViewById(R.id.dpResult);
	 
			Calendar cal = null;//Calendar.getInstance();
			cal=Util.getCalendarFromTime(et_start_time.getText().toString());
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int min = cal.get(Calendar.MINUTE);
			int sec = cal.get(Calendar.SECOND);
	 
			// set current date into textview
			/*et_date.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(day).append(".").append(month+1).append(".")
				.append(year).append(""));*/

	        /*mCalendar.set(Calendar.YEAR, year);
	        mCalendar.set(Calendar.MONTH, month);
	        mCalendar.set(Calendar.DAY_OF_MONTH, day);
	        formatter = new SimpleDateFormat("dd.MM.yyyy");*/
	        //setTitle(formatter.format(mCalendar.getTime()));
	        //--------------et_date.setText(Util.getCalendarText(cal));
			// set current date into datepicker
			//dpResult.init(year, month, day, null);
	 
		}
	
		/*public void addListenerOnEndButton(View v) {
			 
			Button b_e_time = (Button) v.findViewById(R.id.b_end_time);
	 
			b_e_time.setOnClickListener(new View.OnClickListener() {
	 
				@Override
				public void onClick(View v) {
	 
					//getActivity().showDialog(999);
//--					showTimePickerDialog(v);
					//onCreateDialog(1);
	 
				}
	 
			});
	 
		}

		public void addListenerOnTimeButton(View v) {
			 
			Button b_time = (Button) v.findViewById(R.id.b_time);
	 
			b_time.setOnClickListener(new View.OnClickListener() {
	 
				@Override
				public void onClick(View v) {
	 
					//getActivity().showDialog(999);
//--					showTimePickerDialog(v);
					//onCreateDialog(1);
	 
				}
	 
			});
	 
		}*/

		private static final String[] HAANJA_PROJECTION = new String[] {
		        HaanjaContent.Haanja.HaanjaColumns._ID,
		        HaanjaContent.Haanja.HaanjaColumns.START_TIME,
		        HaanjaContent.Haanja.HaanjaColumns.END_TIME,
		        HaanjaContent.Haanja.HaanjaColumns.TIME,
		        HaanjaContent.Haanja.HaanjaColumns.SPLIT
			};



		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
			getDialog().requestWindowFeature(STYLE_NO_TITLE);
			View v = inflater.inflate(R.layout.add_changetime_dialog, container, false);
			//TextView tv = (TextView)v.findViewById(R.id.prompttitlemessage);
			//tv.setText(getArguments().getString("prompt_title"));
			Button dismissBtn = (Button)v.findViewById(R.id.btn_dismiss);
			dismissBtn.setOnClickListener(this);
			Button saveBtn = (Button)v.findViewById(R.id.btn_confirm);
			saveBtn.setOnClickListener(this);
			Button helpBtn = (Button)v.findViewById(R.id.btn_help);
			helpBtn.setOnClickListener(this);
			
			
			rec_id = getArguments().getLong("rec_id");
			//id = getArguments().getLong("id");
			split = getArguments().getLong("split");
			start_time = getArguments().getLong("start_time");
			end_time = getArguments().getLong("end_time");
			time = getArguments().getLong("time");

			et_split = (TextView)v.findViewById(R.id.d_split);
			et_split.setText(String.valueOf(rec_id));
			
			et_start_time = (TextView)v.findViewById(R.id.d_start_time);
			et_start_time.setText(Util.displayCalcTime(getActivity(), start_time));
			
			//--Cursor cursor = getActivity().getContentResolver().query(ShopsContent.Shops.CONTENT_URI, SHOPS_PROJECTION, null, null, "name");
			//myShopsResourceAdapter = new TagsResourceAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, cursor); 
			//--myShopsAdapter = new ShopsAdapter(getActivity(), cursor);

			et_end_time = (EditText)v.findViewById(R.id.d_end_time);
			et_end_time.setHint(Util.displayCalcTime(getActivity(), end_time));
			//et_end_time.setHint(Util.displayCalcTime(getActivity(), end_time));
			
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
			et_end_time.addTextChangedListener(tw);
			et_end_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					
			    	  /*et_time = (EditText) getActivity().findViewById(R.id.time);
			    	  et_start_time = (TextView) getActivity().findViewById(R.id.start_time);
			    	  et_end_time = (EditText) getActivity().findViewById(R.id.end_time);*/
			    			  //PriceConverter.mPriceFormatter.format(sum/kogus);
			    	  long time=Util.getLongTimeFromstring("test: "+et_time.getHint().toString().trim());
			    	  long start_time=Util.getLongTimeFromstring("test: "+et_start_time.getText().toString().trim());
			    	  long end_time=Util.getLongTimeFromstring("test: "+et_end_time.getText().toString().trim());
			    	  
			    	  Log.d("detailsfragment","time enne="+Util.displayCalcTime(getActivity(), time)+"   end_time="+Util.displayCalcTime(getActivity(), end_time));
			    	  time = end_time-start_time;
			    	  Log.d("detailsfragment","time pärast="+Util.displayCalcTime(getActivity(), time)+" ehk="+time);
			    	  //if (e_sum==0)
			    	  if (time>0 && start_time>0 && end_time>0) {
			    		  et_time.setHint(Util.displayCalcTime(getActivity(), time));
			    		  et_time.setText(Util.displayCalcTime(getActivity(), time));
			    	  }
			    	  //if (((EditText)s).equals(edit_sum)) {
			    		  //Toast.makeText(getActivity(), "s=edit_hind="+hind, 2000);
			    	  //}
					
				}
			});

			//--et_end_time.setClickable(true);
			//setCurrentDateOnView(v);
			//--addListenerOnEndButton(v);

			et_time = (EditText)v.findViewById(R.id.d_time);
			et_time.setHint(Util.displayCalcTime(getActivity(), time));
			et_time.addTextChangedListener(tw);
			et_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					
			    	  /*et_time = (EditText) getActivity().findViewById(R.id.time);
			    	  et_start_time = (TextView) getActivity().findViewById(R.id.start_time);
			    	  et_end_time = (EditText) getActivity().findViewById(R.id.end_time);*/
			    			  //PriceConverter.mPriceFormatter.format(sum/kogus);
			    	  long time=Util.getLongTimeFromstring("test: "+et_time.getText().toString().trim());
			    	  long start_time=Util.getLongTimeFromstring("test: "+et_start_time.getText().toString().trim());
			    	  long end_time=Util.getLongTimeFromstring("test: "+et_end_time.getText().toString().trim());
			    	  
			    	  Log.d("detailsfragment","time enne="+Util.displayCalcTime(getActivity(), end_time)+"   end_time="+Util.displayCalcTime(getActivity(), end_time));
			    	  end_time = start_time+time;
			    	  Log.d("detailsfragment","time pärast="+Util.displayCalcTime(getActivity(), end_time)+" ehk="+end_time);
			    	  //if (e_sum==0)
			    	  if (time>0 && start_time>0 && end_time>0) {
			    		  et_end_time.setHint(Util.displayCalcTime(getActivity(), end_time));
			    		  et_end_time.setText(Util.displayCalcTime(getActivity(), end_time));
			    		  et_time.setHint(Util.displayCalcTime(getActivity(), time));
			    	  }
			    	  //if (((EditText)s).equals(edit_sum)) {
			    		  //Toast.makeText(getActivity(), "s=edit_hind="+hind, 2000);
			    	  //}
					
				}
			});
			
			/*Button b_end = (Button)v.findViewById(R.id.b_end_time);
			b_end.setVisibility(View.GONE);
			Button b_time = (Button)v.findViewById(R.id.b_time);
			b_time.setVisibility(View.GONE);*/
			
			//--et_time.setClickable(true);
			//--setCurrentDateOnView(v);
			//--addListenerOnTimeButton(v);

			//et_date.callOnClick();
			
			//TableRow et_row = (TableRow)v.findViewById(R.id.accountRow);
//			et_row.setVisibility(View.VISIBLE);
//			et_row.setVisibility(View.GONE);
			
			//et_sum.setEnabled(true);
			Log.d("TimeDialog"," split="+split+"  et_start_time="+et_start_time+"  et_end_time="+et_end_time+"  icicle="+icicle+"  time="+et_time);
			if(icicle != null) {
				et_split.setText(icicle.getCharSequence("et_split"));
				
				et_start_time.setText(icicle.getCharSequence("et_start_time"));
				
				et_end_time.setText(icicle.getCharSequence("et_end_time"));
				
				et_time.setText(icicle.getCharSequence("et_time"));
				
				//et_type.setText(icicle.getCharSequence("et_type"));
				//et_.setSelection((int)icicle.getInt("et_type"), true);
			} else {
				//vaja selectida välja õige ja see sisse panna
				//Uri baseUri = TitlesContentProvider.CONTENT_URI;
				//ShoppingApp app = (ShoppingApp)getActivity().getApplication();
				//SQLiteDatabase db = app.getDatabase();
				//ContentResolver cr = app.getContentResolver();
				//String[] projection = { ItemTable.ItemColumns.NAME, ItemTable.ItemColumns.DESCRIPTION};
				
				//Uri uri =null;
				
				et_split.setText(""+split);
				
				//et_shop.setSelection((int)shop_id);
		
				//if (et_account!=null)
				//	et_account.setSelection((int)account_id);
				//et_shop.setText(""+shop);
				
				//et_date.setText(""+date);
				
				//et_type.setText(""+b_type);
				//et_type.setSelection((int)b_type, true);
			}
			getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			return v;
		}
		
/*		public void showTimePickerDialog(View v) {
			Log.d("showTimePickerDialog","algus="+et_end_time.getText().toString().trim()+"  split="+et_end_time.getText().toString().trim().split(".").length);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			//ft.remove(this);
			Bundle bundle=new Bundle();
			bundle.putString("test", "test");
			//String[] str=et_date.getText().toString().trim().split(".");
			
			Calendar cal=Util.getCalendar(et_end_time.getText().toString());
			bundle.putInt("tund", Util.getHour(cal));
			bundle.putInt("minut", Util.getMinute(cal));
			bundle.putInt("sekund", Util.getSecond(cal));
			
			DialogFragment newFragment = new TimePickerDialogFragment(this, bundle);
			newFragment.setArguments(bundle);
			newFragment.show(ft, "dialog");
		}*/
	
		@Override
		public void onSaveInstanceState(Bundle icicle) {
			//icicle.putCharSequence("sum", et.getText());
			//icicle.putCharSequence("title", etd.getText());
			
			rec_id = getArguments().getLong("rec_id");
			//id = getArguments().getLong("id");
			split = getArguments().getLong("split");
			start_time = getArguments().getLong("start_time");
			end_time = getArguments().getLong("end_time");
			time = getArguments().getLong("time");
			
			icicle.putLong("rec_id", rec_id);
			icicle.putCharSequence("et_end_time", et_end_time.getText());
			icicle.putCharSequence("et_time", et_time.getText());
			icicle.putCharSequence("et_start_time", et_start_time.getText());
			icicle.putLong("start_time", start_time);
			icicle.putLong("end_time", end_time);
			icicle.putLong("time", time);
			//icicle.putCharSequence("et_shop", et_shop.getText());
			icicle.putLong("split", split);
			//icicle.putString("name", name);
			super.onPause();
		}
	
		@Override
		public void onCancel(DialogInterface di) {
			Log.v(MainActivity.TAG, "in onCancel () of PDF");
			super.onCancel (di);
		}
	
		@Override
		public void onDismiss(DialogInterface di) {
			Log.v(MainActivity.TAG, "in onDismiss() of PDF");
			super.onDismiss(di);
		}
	
		public void onClick(View v) {
			Log.d("addBudgetDialog","onClick");
			OnChangeTimeDialogDoneListener act = (OnChangeTimeDialogDoneListener)getActivity();
			if (v.getId() == R.id.btn_confirm) {
				EditText ev_end_time = (EditText)getView().findViewById(R.id.d_end_time);
				TextView ev_start_time = (TextView)getView().findViewById(R.id.d_start_time);
				/*AutoCompleteTextView ev_shop = (AutoCompleteTextView)getView().findViewById(R.id.edit_shop);
				Cursor poss=((ShopsAdapter)ev_shop.getAdapter()).getCursor();
				if (poss!=null && poss.getCount()>0) {
					shop_id=poss.getLong(0);
				} else {
					shop_id=0;
				}*/

				/*AutoCompleteTextView ev_account = (AutoCompleteTextView)getView().findViewById(R.id.edit_account);
				if (this.isBudget) {
					Cursor a_poss=((AccountsAdapter)ev_account.getAdapter()).getCursor();
					if (a_poss!=null && a_poss.getCount()>0) {
						account_id=a_poss.getLong(0);
					} else {
						account_id=0;
					}
				}
				
				Spinner ev_type = (Spinner)getView().findViewById(R.id.edit_type);*/
				
				EditText ev_time = (EditText)getView().findViewById(R.id.d_time);
				
				TextView ev_split = (TextView)getView().findViewById(R.id.d_split);
				
				Log.d("testiks","ev_end_time="+ev_end_time.getText());
				Log.d("testiks","ev_time="+ev_time.getText());

		    	  long time=Util.getLongTimeFromstring("test: "+ev_time.getText().toString().trim());
		    	  long start_time=Util.getLongTimeFromstring("test: "+ev_start_time.getText().toString().trim());
		    	  long end_time=Util.getLongTimeFromstring("test: "+ev_end_time.getText().toString().trim());
		    	  
		    	  Log.d("detailsfragment","time enne="+Util.displayCalcTime(getActivity(), end_time)+"   end_time="+Util.displayCalcTime(getActivity(), end_time));
		    	  if (time>0)
		    		  end_time = start_time+time;
		    	  else if (end_time>0)
		    		  time=end_time-start_time;

				
				//Log.d("testiks","ev_type="+ev_type.getSelectedItemPosition());
				//Log.d("testiks","ev_date="+ev_date.getText().toString());
				//Log.d("testiks","act="+act);
				//Log.d("testiks","getTag="+this.getTag());
				//Log.d("testiks","shop_id="+shop_id);
				//Log.d("testiks","title="+title);
				//act.onChangeTimeDialogDone(this.getTag(), false, ev_end_time.getText(), ev_time.getText(), split, rec_id); 
		    	  act.onChangeTimeDialogDone(this.getTag(), false, end_time, time, split, rec_id);
						//, ev_type.getSelectedItemPosition(), ev_date.getText().toString(), account_id);
				getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
				dismiss();
				return;
			}
			if (v.getId() == R.id.btn_dismiss) {
				//act.onChangeTimeDialogDone(this.getTag(), true, null, null, 0, 0);
				act.onChangeTimeDialogDone(this.getTag(), true, 0, 0, 0, 0);
				getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
				dismiss();
				return;
			}
			if (v.getId() == R.id.btn_help) {
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.remove(this);
				// in this case, you want to show the help text, but
				// come back to the previous dialog when you're done
				ft.addToBackStack(null);
				//	null represents no name for the back stack transaction
				HelpDialogFragment hdf = HelpDialogFragment.newInstance(R.string.help3);
				hdf.show(ft, MainActivity.TAG);
				return;	
			}
			/*if (v.getId()== R.id.b_date) {
				et_date.setText("test="+aasta);
				Log.d("test","test="+aasta);
				return;
			}*/
		}


		
	    @Override
	    //android.widget.TimePicker arg0, int arg1, int arg2)
		public void onTimeSet(TimePicker view, int hour, int minute) {
	    // do stuff with the date the user selected
			Log.d("timepickler","hour="+hour+"  minute="+minute);
//--			if (!onTimeChanged(hour, minute)){
				//Calendar cal = Util.get
//--et_end_time.setText(new StringBuilder().append(Util.getHourStrInt(hour)).append(".").append(Util.getMinuteStrInt(minute)).append(".");
//--			}
		}

	    private int minYear;
	    private int minMonth;
	    private int minDay;

	    private int maxYear;
	    private int maxMonth;
	    private int maxDay;

	    private Calendar mCalendar;
	    private SimpleDateFormat formatter;

	    

	 /*   public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
            Date maxDate = new Date(mYearMax, mMonthMax, mDayMax, 0, 0, 0);
            Date selectedDate = new Date(i, i1, i2, 0, 0, 0);
            if(selectedDate.after(maxDate)){
                datePicker.updateDate(mYearMax, mMonthMax, mDayMax);                                        

            }

        }*/

	    //@Override
	    //public void onDateChanged(DatePicker view, int year,int month, int day){
/*	    public boolean onTimeChanged(int year,int month){
	        boolean beforeMinDate=false;
	        boolean afterMaxDate=false;
	        month=month+1;

	        mCalendar = Calendar.getInstance();
	        
		    minYear=2000;//Constant.minYear;
		    minMonth=1;//Constant.minMonth;
		    minDay=1;//Constant.minDay;

		    mCalendar.setTime(new Date());
		    //set up maxdate
		    //mCalendar.add(Calendar.YEAR, Constant.maxBirthdayYearSinceNow);
		    maxYear=mCalendar.get(Calendar.YEAR);
		    maxMonth=mCalendar.get(Calendar.MONTH)+1;
		    maxDay=mCalendar.get(Calendar.DATE);
	        
	        if(year<minYear){
	            beforeMinDate=true;
	        }
	        else if(year==minYear){
	            if(month<minMonth){
	                beforeMinDate=true;
	            }
	            else if(month==minMonth){
	                if(day<minDay){
	                    beforeMinDate=true;
	                }
	            }
	        }

	        if(!beforeMinDate){
	            if(year>maxYear){
	                afterMaxDate=true;
	            }
	            else if(year==maxYear){
	                if(month>maxMonth){
	                    afterMaxDate=true;
	                }
	                else if(month==maxMonth){
	                    if(day>maxDay){
	                        afterMaxDate=true;
	                    }
	                }
	            }
	        }

	        //need set invalid date to mindate or maxdate
	        if(beforeMinDate || afterMaxDate){
	        	//Log.d("date","beforeMinDate || afterMaxDate="+(beforeMinDate || afterMaxDate));
	            if(beforeMinDate){
	                year=minYear;
	                month=minMonth-1;
	                day=minDay;
	            }
	            else{
	                year=maxYear;
	                month=maxMonth;
	                day=maxDay;
	            }
	            //view.updateDate(year,  month,  day);
	        }
	        Log.d("date","beforeMinDate || afterMaxDate="+(beforeMinDate || afterMaxDate));
	        //display in view title
	        mCalendar.set(Calendar.YEAR, year);
	        mCalendar.set(Calendar.MONTH, month);
	        mCalendar.set(Calendar.DAY_OF_MONTH, day);
	        formatter = new SimpleDateFormat("dd.MM.yyyy");
	        //setTitle(formatter.format(mCalendar.getTime()));
	        et_date.setText(formatter.format(mCalendar.getTime()));
	        return (beforeMinDate || afterMaxDate);
	    }
*/

	    //public static AddBudgetDialogFragment.DatePickerFragment mmFragment;
	    //originaal DatePicker-> uuem ver.
		//http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.1_r1/android/widget/DatePicker.java
/*		public class TimePickerDialogFragment extends DialogFragment {
		    private DialogFragment mFragment;
		    int paev=0;
		    int kuu=0;
		    int aasta=0;

		    public DatePickerDialogFragment(DialogFragment callback, Bundle bundle) {
				if (bundle!=null) {
					
					Log.d("bundle","bundle="+bundle.getString("test"));
					paev=bundle.getInt("paev");
					kuu=bundle.getInt("kuu");
					aasta=bundle.getInt("aasta");
				}
				mCalendar = Calendar.getInstance();
				
			    //set up mindate


			    //set up init date
			    //mCalendar.set(Calendar.YEAR, year);
			    //mCalendar.set(Calendar.MONTH, monthOfYear);
			    //mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			  //set up date display format
			    //formatter = new SimpleDateFormat("MM/dd/yyyy");
			    //setTitle(formatter.format(mCalendar.getTime()));

		    	mFragment = callback;
		    }

		     public Dialog onCreateDialog(Bundle savedInstanceState) {
		    	 
					final Calendar c = Calendar.getInstance();
					int year = c.get(Calendar.YEAR);
					int month = c.get(Calendar.MONTH);
					int day = c.get(Calendar.DAY_OF_MONTH);
					
					if (savedInstanceState!=null) {
						Log.d("test","test="+savedInstanceState.getString("test"));
					}
					if (!Util.testDate(paev, kuu, aasta)) {
						paev = Util.getDay(Util.getCurrentCalendar());
						kuu = Util.getMonth(Util.getCurrentCalendar());
						aasta = Util.getYear(Util.getCurrentCalendar());
					}
					Log.d("datepicker","paev="+paev+" kuu="+kuu+" aasta="+aasta);
					DatePickerDialog dp =null;//new DatePickerDialog(getActivity(), (OnDateSetListener) mFragment, year, month, day);
					if (paev>0)
						try {
							dp = new DatePickerDialog(getActivity(), (OnDateSetListener) mFragment, aasta, kuu, paev);
						} catch (IllegalArgumentException e) {
							dp = new DatePickerDialog(getActivity(), (OnDateSetListener) mFragment, year, month, day);
						}
					else
						dp = new DatePickerDialog(getActivity(), (OnDateSetListener) mFragment, year, month, day);
					dp.setTitle("Kuupäev siia:");
					
		    	 return dp;
		     }
		     

		}
*/

		/*@Override
		public Dialog onCreateDialog(int i) {
			// 	Use the current date as the default date in the picker
			//if (!=null)
				Log.d("datePicker","bundle="+i);
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}*/

		
/*		public static class DatePickerFragment extends DialogFragment
		implements DatePickerDialog.OnDateSetListener {

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// 	Use the current date as the default date in the picker
				if (savedInstanceState!=null)
					Log.d("datePicker","bundle="+savedInstanceState);
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);

				// Create a new instance of DatePickerDialog and return it
				return new DatePickerDialog(getActivity(), this, year, month, day);
			}


			public void onDateSet(DatePicker view, int year, int month, int day) {
				// Do something with the date chosen by the user
				//OnDatepickerSetListener act = (OnDatepickerSetListener)this;
				//act.onDatepickerSet(this.getTag(), false, tv.getText(), tvd.getText(), id);
				//act.onDatepickerSet(day+"."+month+"."+year);
			
				//getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
				paev=day;
				kuu=month;
				aasta=year;
				//updateDate();
				//dismiss();
				//return;

			}
		
		}*/

		 /**
		  * http://www.java2s.com/Open-Source/Android/App/kraigsandroid/com/angrydoughnuts/android/alarmclock/TimePickerDialog.java.htm
		   * Helper class that wraps up the view elements of each number picker
		   * (plus/minus button, text field, increment picker).
		   */
/*		  private final class PickerView {
		    private int calendarField;
		    private String formatString;
		    private EditText text = null;
		    private Increment increment = null;
		    private Button incrementValueButton = null;
		    private Button plus = null;
		    private Button minus = null;
*/
		    /**
		     * Construct a numeric picker for the supplied calendar field and formats
		     * it according to the supplied format string.
		     * @param calendarField
		     * @param formatString
		     */
/*		    public PickerView(int calendarField, String formatString) {
		      this.calendarField = calendarField;
		      this.formatString = formatString;
		    }
*/
		    /**
		     * Inflates the ViewStub for this numeric picker.
		     * @param parentView
		     * @param resourceId
		     * @param showIncrement
		     * @param defaultIncrement
		     */
/*		    public void inflate(View parentView, int resourceId, boolean showIncrement, IncrementValue defaultIncrement) {
		      final ViewStub stub = (ViewStub) parentView.findViewById(resourceId);
		      final View view = stub.inflate();
		      text = (EditText) view.findViewById(R.id.time_value);
		      text.setOnFocusChangeListener(new TextChangeListener());
		      text.setOnEditorActionListener(new TextChangeListener());

		      increment = new Increment(defaultIncrement);
		      incrementValueButton = (Button) view.findViewById(R.id.time_increment);

		      incrementValueButton.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {
		          increment.cycleToNext();
		          Editor editor = prefs.edit();
		          editor.putInt(INCREMENT_PREF, increment.value.ordinal());
		          editor.commit();
		          pickerRefresh();
		        }
		      });

		      if (showIncrement) {
		        incrementValueButton.setVisibility(View.VISIBLE);
		      } else {
		        incrementValueButton.setVisibility(View.GONE);
		      }

		      plus = (Button) view.findViewById(R.id.time_plus);
		      TimeIncrementListener incrementListener = new TimeIncrementListener();
		      plus.setOnClickListener(incrementListener);
		      plus.setOnTouchListener(incrementListener);
		      plus.setOnLongClickListener(incrementListener);
		      minus = (Button) view.findViewById(R.id.time_minus);
		      TimeDecrementListener decrementListener= new TimeDecrementListener();
		      minus.setOnClickListener(decrementListener);
		      minus.setOnTouchListener(decrementListener);
		      minus.setOnLongClickListener(decrementListener);

		      pickerRefresh();
		    }

		    public void pickerRefresh() {
		      int fieldValue = calendar.get(calendarField);
		      if (calendarField == Calendar.HOUR && fieldValue == 0) {
		        fieldValue = 12;
		      }
		      text.setText(String.format(formatString, fieldValue));
		      incrementValueButton.setText("+/- " + increment.nextValue().value());
		      plus.setText("+" + increment.value());
		      minus.setText("-" + increment.value());
		      dialogRefresh();
		    }

		    private final class Increment {
		      private IncrementValue value;
		      public Increment(IncrementValue value) {
		        this.value = value;
		      }
		      public IncrementValue nextValue() {
		        int nextIndex = (value.ordinal() + 1) % IncrementValue.values().length;
		        return IncrementValue.values()[nextIndex];
		      }
		      public void cycleToNext() {
		        value = nextValue();
		      }
		      public int value() {
		        return value.value();
		      }
		    }
*/
		    /**
		     * Listener that figures out what the next value should be when a numeric
		     * picker plus/minus button is clicked.  It will round up/down to the next
		     * interval increment then increment by the increment amount on subsequent
		     * clicks.
		     */
/*		    private abstract class TimeAdjustListener implements
		      View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {
		      protected abstract int sign();

		      private void adjust() {
		        int currentValue = calendar.get(calendarField);
		        int remainder = currentValue % increment.value();
		        if (remainder == 0) {
		          calendar.roll(calendarField, sign() * increment.value());
		        } else {
		          int difference;
		          if (sign() > 0) {
		            difference = increment.value() - remainder;
		          } else {
		            difference = -1 * remainder;
		          }
		          calendar.roll(calendarField, difference);
		        }
		        pickerRefresh();
		      }

		      private Handler handler = new Handler();
		      private Runnable delayedAdjust = new Runnable() {
		        @Override
		        public void run() {
		          adjust();
		          handler.postDelayed(delayedAdjust, 150);
		        }
		      };

		      @Override
		      public void onClick(View v) {
		        adjust();
		      }

		      @Override
		      public boolean onLongClick(View v) {
		        delayedAdjust.run();
		        return false;
		      }

		      @Override
		      public boolean onTouch(View v, MotionEvent event) {
		        if (event.getAction() == MotionEvent.ACTION_UP) {
		          handler.removeCallbacks(delayedAdjust);
		        }
		        return false;
		      }
		    }

		    private final class TimeIncrementListener extends TimeAdjustListener {
		      @Override
		      protected int sign() {
		        return 1;
		      }
		    }
		    private final class TimeDecrementListener extends TimeAdjustListener {
		      @Override
		      protected int sign() { return -1; }
		    }
*/
		    /**
		     * Listener to handle direct user input into the time picker text fields.
		     * Updates after the editor confirmation button is picked or when the
		     * text field loses focus.
		     */
/*		    private final class TextChangeListener implements OnFocusChangeListener, OnEditorActionListener {
		      private void handleChange() {
		        try {
		          int newValue = Integer.parseInt(text.getText().toString());
		          if (calendarField == Calendar.HOUR &&
		              newValue == 12 &&
		              calendar.get(Calendar.AM_PM) == Calendar.AM) {
		            calendar.set(Calendar.HOUR_OF_DAY, 0);
		          } else if (calendarField == Calendar.HOUR &&
		              newValue == 12 &&
		              calendar.get(Calendar.AM_PM) == Calendar.PM) {
		            calendar.set(Calendar.HOUR_OF_DAY, 12);
		          } else {
		            calendar.set(calendarField, newValue);
		          }
		        } catch (NumberFormatException e) {}
		        pickerRefresh();
		      }
		      @Override
		      public void onFocusChange(View v, boolean hasFocus) {
		        handleChange();
		      }
		      @Override
		      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        handleChange();
		        return false;
		      }
		    }
		  }
*/

}
