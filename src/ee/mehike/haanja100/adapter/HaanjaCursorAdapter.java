package ee.mehike.haanja100.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ee.mehike.haanja100.data.HaanjaTable;
import ee.mehike.haanja100.fragments.dialog.HelpDialogFragment;
import ee.mehike.haanja100.util.Constants;
import ee.mehike.haanja100.util.Util;
//import com.shop.checklist.listener.OnTitleClickedListener;
import ee.mehike.haanja100.MainActivity;
import ee.mehike.haanja100.R;
import ee.mehike.haanja100.R.id;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HaanjaCursorAdapter extends SimpleCursorAdapter {


    //public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("HH:mm, dd/MM");
	public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("dd.MM.yyy  HH:mm:ss");
	public static final SimpleDateFormat SHORT_DATEFORMAT = new SimpleDateFormat("dd.MM HH:mm:ss");
	public static final SimpleDateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat PACEFORMAT = new SimpleDateFormat("mm:ss,S");
	
    private final Context ctx;
    private final int layout;
    private final LayoutInflater vi;
    //public final OnTitleClickedListener clickListener;

	private static class ViewHolder {
		protected final LinearLayout ll;
		protected final TextView split_nr;
		protected final TextView time;
		protected final TextView start_time;
		protected final TextView end_time;
		protected final TextView split_km;
		protected final TextView av_split_pace;
		protected final TextView av_pace;
		protected final long id;
		protected final long l_start_time;
		protected final long l_end_time;
		protected final long l_split_nr;
		protected final String avg_pace;
		protected final String lap_pace;
		public ViewHolder(LinearLayout ll, TextView split_nr, TextView time, TextView start_time, TextView end_time, TextView split_km, TextView av_split_pace, TextView av_pace, 
				long id, long l_start_time, long l_end_time, long l_split_nr, String avg_pace, String lap_pace) {
			this.ll = ll;
			this.split_nr=split_nr;
			this.time=time;
			this.start_time=start_time;
			this.end_time=end_time;
			this.split_km=split_km;
			this.av_split_pace=av_split_pace;
			this.av_pace=av_pace;
			this.id=id;
			this.l_start_time=l_start_time;
			this.l_end_time=l_end_time;
			this.l_split_nr=l_split_nr;
			this.avg_pace = avg_pace;
			this.lap_pace = lap_pace;
	    } 
	}
	
    public HaanjaCursorAdapter(Context context, int layout, Cursor c, String[] s, int[] i, int flags) {
            super(context, layout, c, s, i, flags);
            this.ctx = context;
            this.layout = layout;
            this.vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
            //this.clickListener = (OnTitleClickedListener)context;
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        populateView(view, cursor, context); 
    }

    @Override
    public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
            //final View view = LayoutInflater.from(context).inflate(this.layout, parent, false);
    	final View view = vi.inflate(this.layout, parent, false);
        //final ViewHolder viewHolder = new ViewHolder();
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.titles_center_layout);
        //CheckBox checkbox = (CheckBox) view.findViewById(R.id.check);
        TextView split_nr = (TextView) view.findViewById(R.id.split_nr);
        TextView av_pace = (TextView) view.findViewById(R.id.av_pace);
        TextView split_km = (TextView) view.findViewById(R.id.split_km);
        TextView av_split_pace = (TextView) view.findViewById(R.id.av_split_pace);
        TextView time = (TextView) view.findViewById(R.id.time);
        
        TextView start_time = (TextView) view.findViewById(R.id.start_time);
        TextView end_time = (TextView) view.findViewById(R.id.end_time);
        
        Log.e("splits", "anmdeväli tabelis="+HaanjaTable.HaanjaColumns.START_TIME+" index="+cursor.getColumnIndex(HaanjaTable.HaanjaColumns.START_TIME));
        
        final ViewHolder viewHolder = new ViewHolder(ll, split_nr, time, start_time, end_time, 
        		split_km, av_split_pace, av_pace, 
        		cursor.getLong(cursor.getColumnIndex(HaanjaTable.HaanjaColumns._ID)),
        		cursor.getLong(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.START_TIME)),
        		cursor.getLong(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.END_TIME)),
        		cursor.getLong(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.SPLIT)),
        		cursor.getString(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.AVG_PACE)),
        		cursor.getString(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.LAP_PACE))
        		);
    	view.setTag(viewHolder);
    	
    	populateView(view, cursor, context); 
        return view;
    }

    /**
     * Formats and displays the date
     * 
     * @param view
     * @param circumference
     */
    private void displayDate(View view, String strDate) {
            final long timestamp = Long.parseLong(strDate);
            final String formattedDate = DATEFORMAT.format(new Date(timestamp));
            final TextView t2 = (TextView) view.findViewById(R.id.time);
            t2.setText(formattedDate);
    }

    /**
     * Formats and displays the time
     * 
     * @param view
     * @param circumference
     */
    private void displayTime(View view, String strDate, int view_id) {
            final long timestamp = Long.parseLong(strDate);
            final String formattedTime = TIMEFORMAT.format(new Date(timestamp));
            final TextView t1 = (TextView) view.findViewById(view_id);
            t1.setText(formattedTime);
    }
    
    /**
     * Formats and displays the date
     * 
     * @param view
     * @param circumference
     */
    private String displayDate(long date) {
            //final long timestamp = Long.parseLong(strDate);
    	long now = System.currentTimeMillis();
    	//java.text.DateFormat formatter = android.text.format.DateFormat.getTimeFormat(this.ctx.getApplicationContext());
    	java.text.DateFormat formatter = android.text.format.DateFormat.getDateFormat(this.ctx.getApplicationContext());
    	Date dateObj = new Date(date * 1000);
    	if (date<=0) {
    			Calendar cal = Calendar.getInstance();
    		    //SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
    			//return formatter.format(cal.getTime());
    			return DATEFORMAT.format(cal.getTime());
    		    //return DATEFORMAT.format(cal.getTime());
    		} else {
    			//final String formattedDate = DATEFORMAT.format(new Date(date));
    			//return formatter.format(dateObj);
    			return DATEFORMAT.format(dateObj);
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
    private String displayTime(long date) {
            //final long timestamp = Long.parseLong(strDate);
    	long now = System.currentTimeMillis();
    	//java.text.DateFormat formatter = android.text.format.DateFormat.getTimeFormat(this.ctx.getApplicationContext());
    	java.text.DateFormat formatter = android.text.format.DateFormat.getDateFormat(this.ctx.getApplicationContext());
    	//Date dateObj = new Date(date * 1000);
    	Date dateObj = new Date(date);
    	Log.d("AEG", "date="+dateObj+" now="+now+" date="+date);
    	if (date<=0) {
    			Calendar cal = Calendar.getInstance();
    		    //SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
    			//return formatter.format(cal.getTime());
    			return TIMEFORMAT.format(cal.getTime());
    		    //return DATEFORMAT.format(cal.getTime());
    		} else {
    			//final String formattedDate = DATEFORMAT.format(new Date(date));
    			//return formatter.format(dateObj);
    			Log.d("AEG", "timeformat="+TIMEFORMAT.format(dateObj));
    			Log.d("AEG", "dateformat="+DATEFORMAT.format(dateObj));
    			return TIMEFORMAT.format(dateObj);
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
/*    private String displayCalcTime(long date) {
            //final long timestamp = Long.parseLong(strDate);
    	long now = System.currentTimeMillis();
    	//java.text.DateFormat formatter = android.text.format.DateFormat.getTimeFormat(this.ctx.getApplicationContext());
    	java.text.DateFormat formatter = android.text.format.DateFormat.getDateFormat(this.ctx.getApplicationContext());
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
    			return TIMEFORMAT.format(cal.getTime());
    		    //return DATEFORMAT.format(cal.getTime());
    		} else {
    			//final String formattedDate = DATEFORMAT.format(new Date(date));
    			//return formatter.format(dateObj);
    			Log.d("AEG", "timeformat="+TIMEFORMAT.format(dateObj));
    			Log.d("AEG", "dateformat="+DATEFORMAT.format(dateObj));
    			//return TIMEFORMAT.format(dateObj);
    			return Util.getNr(h)+":"+Util.getNr(m)+":"+Util.getNr(s);
            	//return formattedDate;
    		}
        //DateFormat formatter = android.text.format.DateFormat.getDateFormat(getActivity().getApplicationContext());
      //long date = cursor.getLong(index);
      //Date dateObj = new Date(date * 1000);
      //((TextView) view).setText(formatter.format(dateObj));

    }

*/

    private void populateView(final View view, final Cursor cursor, final Context context) {
    	final ViewHolder viewHolder = (ViewHolder) view.getTag(); 
    	if ((cursor != null) && !cursor.isClosed()) { 
            final Long time = cursor.getLong(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.TIME));
            final long id = cursor.getLong(cursor.getColumnIndex(HaanjaTable.HaanjaColumns._ID));
            final Long start_time = cursor.getLong(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.START_TIME));
            Long end_time = cursor.getLong(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.END_TIME));
            final long split_nr = cursor.getLong(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.SPLIT));
            
            String a_pace = cursor.getString(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.AVG_PACE));
            String l_pace = cursor.getString(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.LAP_PACE));
            if (a_pace==null) a_pace=" - ";
            if (l_pace==null) l_pace=" - ";
            
            
          /*  viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	//Intent intent= getActivity().getIntent();
                	//  String msg = intent.getStringExtra("sampleData");
                	//  msg += ", Added at Third";
                	//  intent.putExtra("returnedData", msg);
                	//  getActivity().setResult(ItemsActivity.RESULT_CANCELED, intent);
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
            });*/
            
            //final int checked = cursor.getInt(cursor.getColumnIndex(HaanjaTable.HaanjaColumns.CHECKED));
            //displayPercentChange(view, percent);
            /*final ViewHolder viewHolder = new ViewHolder();
            viewHolder.ll = (LinearLayout) view.findViewById(R.id.titles_center_layout);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
            viewHolder.name = (TextView) view.findViewById(R.id.text_1);
            viewHolder.desc = (TextView) view.findViewById(R.id.text_2);*/
            //if (checked==1 && viewHolder.checkbox!=null)
            //	viewHolder.checkbox.setChecked(true);
            SharedPreferences setting = ctx.getSharedPreferences(Constants.PREFERENCE_FILENAME, 0);
            long laps=setting.getLong("laps_pref", 1);
            double split_km=1;
            /*if (!setting.getBoolean("istimebased", true)) { 
            	split_km=(double)(setting.getFloat("splitkm_pref", (float)Constants.JOOKS_LAPKM));
            } else {*/
            	split_km=Util.getSplitKm(context);
            //}

            if (laps>1)
            	viewHolder.split_nr.setText(this.ctx.getResources().getString(R.string.ringe)+split_nr*laps);
            else
            	viewHolder.split_nr.setText(this.ctx.getResources().getString(R.string.ring)+split_nr*laps);
            //if (viewHolder.desc!=null)
            //	viewHolder.desc.setText(description);
            /*if (viewHolder.time!=null)
            	viewHolder.time.setText(date);*/
            Log.d("praegune", "praegune aeg="+displayTime(System.currentTimeMillis())+"   aegise="+System.currentTimeMillis()+" praegune päev="+displayDate(System.currentTimeMillis()));
            
            
            if (viewHolder.split_km!=null)
            	if (end_time>0)
            		viewHolder.split_km.setText(this.ctx.getResources().getString(R.string.labitud)+" "+Util.round(split_km*split_nr,2)+"km");
            	else
            		viewHolder.split_km.setText(this.ctx.getResources().getString(R.string.varsti)+" "+Util.round(split_km*split_nr,2)+"km");

            //if (viewHolder.start_time!=null) {
            	if (split_nr==1) {
            		Log.d("start", "start_time="+start_time);
            		viewHolder.start_time.setText(this.ctx.getResources().getString(R.string.start)+" "+Util.displayCalcDate(this.ctx, start_time, true));//displayTime(start_time));
            		//viewHolder.end_time.setTextColor(android.)
            	} else
            		viewHolder.start_time.setText(this.ctx.getResources().getString(R.string.start)+" "+Util.displayCalcDate(this.ctx, start_time, true));//displayTime(start_time));
            //}

           // if (viewHolder.end_time!=null) {
            	if (end_time<=0) {
            		
            		viewHolder.end_time.setText(this.ctx.getResources().getString(R.string.lopp)+" - ");
            		//viewHolder.end_time.setTextColor(android.)
            	} else {
            		Log.d("end", "end_time="+end_time);
            		viewHolder.end_time.setText(this.ctx.getResources().getString(R.string.lopp)+" "+Util.displayCalcDate(this.ctx, end_time, true));//displayTime(end_time));
            	}
            //}

            //if (viewHolder.time!=null) {
            	if (end_time<=0) {
            		end_time=System.currentTimeMillis();
            		Log.d("time","start_time="+displayTime(start_time)+"  end_time="+displayTime(end_time)+"  aja arvutus="+(((end_time-start_time)/1000/60) % 60));
            		if ((((end_time-start_time)/1000/60) % 60)>20)
            			viewHolder.time.setText(this.ctx.getResources().getString(R.string.kulunud)+" "+Util.displayCalcTime(this.ctx.getApplicationContext(), (end_time-start_time)));
            		else
            			if (time>0)
            				viewHolder.time.setText(this.ctx.getResources().getString(R.string.eeldatav)+" "+Util.displayCalcTime(this.ctx.getApplicationContext(), time));
            			else
            				if ((((end_time-start_time)/1000/60) % 60)<=1)
            					viewHolder.time.setText(this.ctx.getResources().getString(R.string.laksalles));
            				else
            					viewHolder.time.setText(this.ctx.getResources().getString(R.string.kulunud)+" "+Util.displayCalcTime(this.ctx.getApplicationContext(), (end_time-start_time)));
            	} else {
            		if (split_nr==setting.getLong("split_pref", Constants.HAANJA100_SPLITS))
            			viewHolder.time.setText(this.ctx.getResources().getString(R.string.finish)+" "+Util.displayCalcTime(this.ctx.getApplicationContext(), (end_time-start_time)));	
            		else
            			viewHolder.time.setText(this.ctx.getResources().getString(R.string.aeg)+" "+Util.displayCalcTime(this.ctx.getApplicationContext(), (end_time-start_time)));
            	}
            	Log.d("aeg", "aeg="+(end_time-start_time));
            	
            //}
            //if (viewHolder.av_pace!=null)
            	viewHolder.av_pace.setText(this.ctx.getResources().getString(R.string.kesk)+" "+a_pace);
            //if (viewHolder.av_split_pace!=null)
            	viewHolder.av_split_pace.setText(this.ctx.getResources().getString(R.string.ring)+" "+l_pace);

            
            //komm välja hetkel
            /*final OnHaanjaClickedListener clickListener = (OnHaanjaClickedListener)context;
            
            //getActivity().registerForContextMenu(viewHolder.name);
            
            viewHolder.ll.setOnClickListener(new View.OnClickListener() {
				@Override
	            public void onClick(View v) {
					//Toast.makeText(getActivity(), "test", 1000);
					clickListener.onClicked(true, id);
					Log.d("klick", " v="+v+" id="+id);
				}
			});
            
            
            viewHolder.ll.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
	            public boolean onLongClick(View v) {
					//Toast.makeText(getActivity(), "test", 1000);
					clickListener.onLongClicked(true, id, viewHolder.name);
					Log.d("long klick", " v="+v+" id="+id);
					//registerForContextMenu(v);

					return true;
				}
			});
			*/
			
            //viewHolder.ll.registerForContextMenu(viewHolder.ll);
            //t.setText(formatDateFromString(date));
    	}
    	
    }
}


