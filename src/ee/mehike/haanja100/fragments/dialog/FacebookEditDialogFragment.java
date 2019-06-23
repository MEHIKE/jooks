package ee.mehike.haanja100.fragments.dialog;

import ee.mehike.haanja100.R;
import ee.mehike.haanja100.listener.OnFbDialogDoneListener;
import ee.mehike.haanja100.util.AnimUtils;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FacebookEditDialogFragment extends DialogFragment implements View.OnClickListener {

	public static FacebookEditDialogFragment newInstance(int fbResId, long rec_id, String text) {
		FacebookEditDialogFragment hdf = new FacebookEditDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("fb_resource", fbResId);
		bundle.putLong("rec_id", rec_id);
		bundle.putString("fb_text", text);
		hdf.setArguments(bundle);
		return hdf;
	}

	public static FacebookEditDialogFragment newInstance(int fbResId, long rec_id) {
		FacebookEditDialogFragment hdf = new FacebookEditDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("fb_resource", fbResId);
		bundle.putLong("rec_id", rec_id);
		bundle.putString("fb_text", null);
		hdf.setArguments(bundle);
		return hdf;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		this.setCancelable(true);
		//int style = DialogFragment.STYLE_NORMAL, theme = 0;
		int style = DialogFragment.STYLE_NO_TITLE, theme = 0;
		//int style = DialogFragment.STYLE_NORMAL, theme = 0;
		setStyle(style,theme);
	}

	@Override
	public void onAttach(Activity act) {
		// If the activity you're being attached to has
		// not implemented the OnDialogDoneListener
		// interface, the following line will throw a
		// ClassCastException. This is the earliest you
		// can test if you have a well-behaved activity.
		//MainActivity act1 = (MainActivity) act;
		
		OnFbDialogDoneListener test = (OnFbDialogDoneListener)act;
		super.onAttach(act);
	}
	
	View vv;
	String fb_text;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
		getDialog().requestWindowFeature(STYLE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawableResource(R.drawable.border_pink);
		
		rec_id = getArguments().getLong("rec_id");
		fb_text = getArguments().getString("fb_text");
		
		
		vv = inflater.inflate(R.layout.fbedit_dialog, container, false);

		TextView tt = (TextView)vv.findViewById(R.id.fbtitle);
		//tt.setText(getActivity().getResources().getText(getArguments().getInt("fb_title")));

		EditText te = (EditText)vv.findViewById(R.id.fbedit);
		
		TextView tv = (TextView)vv.findViewById(R.id.fbmessage);
		//tv.setText(getActivity().getResources().getText(getArguments().getInt("fb_resource")));
		//tv.setText(getActivity().getResources().getText(getArguments().getInt("fb_resource"))+"\n "+fb_text);
		tv.setText("\n "+fb_text);
	
		Button dismissBtn = (Button)vv.findViewById(R.id.btn_dismiss);
		dismissBtn.setOnClickListener(this);
		Button saveBtn = (Button)vv.findViewById(R.id.btn_send);
		saveBtn.setOnClickListener(this);
		
		//getDialog().requestWindowFeature(STYLE_NO_TITLE);
		getDialog().setTitle("Pealkiri");
		
		ee.mehike.haanja100.util.Utils.setLayoutAnim_slidedownfromtop((ViewGroup)vv, this.getActivity().getApplicationContext());
		return vv;
	}

	@Override
	public void onCancel(DialogInterface di) {
		Log.v(ee.mehike.haanja100.MainActivity.TAG, "in onCancel () of PDF");
		super.onCancel (di);
	}

	@Override
	public void onDismiss(DialogInterface di) {
		Log.v(ee.mehike.haanja100.MainActivity.TAG, "in onDismiss() of PDF");
		

		super.onDismiss(di);
	}
	
	private long rec_id;
	
	public void onClick(View v) {
		//dismiss();
		OnFbDialogDoneListener act = (OnFbDialogDoneListener)getActivity();
		if (v.getId() == R.id.btn_send) {
			//TextView tv = (TextView)getView().findViewById(R.id.edit_title);
			//TextView tvd = (TextView)getView().findViewById(R.id.edit_title_desc);
			TextView tv = (TextView)vv.findViewById(R.id.fbmessage);
			EditText te = (EditText)vv.findViewById(R.id.fbedit);
			act.onFbDialogDone(this.getTag(), false, te.getText()+"\n----------->\n"+tv.getText(), rec_id);
			//getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			//AnimUtils.setLayoutAnim_slideupfrombottom((ViewGroup)vv, this.getActivity().getApplicationContext());
			//ee.mehike.haanja100.util.Utils.setLayoutAnimation2((ViewGroup)vv, this.getActivity().getApplicationContext());
			/*try {
				Thread.sleep(1000);
			} catch (Exception e) {
				
			}*/
			dismiss();
			return;
		} else
		if (v.getId() == R.id.btn_dismiss) {
			act.onFbDialogDone(this.getTag(), true, null, rec_id);
			//getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			//AnimUtils.setLayoutAnim_slideupfrombottom((ViewGroup)vv, this.getActivity().getApplicationContext());
			//ee.mehike.haanja100.util.Utils.setLayoutAnim_slideupfrombottom((ViewGroup)vv, this.getActivity().getApplicationContext());
			//ee.mehike.haanja100.util.Utils.setLayoutAnimation2((ViewGroup)vv, this.getActivity().getApplicationContext());
			/*try {
				Thread.sleep(1000);
			} catch (Exception e) {
				
			}*/

			dismiss();
			return;
		}
	}
}
