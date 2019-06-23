package ee.mehike.haanja100.fragments.dialog;

import ee.mehike.haanja100.R;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class HelpDialogFragment extends DialogFragment implements View.OnClickListener {

	public static HelpDialogFragment newInstance(int helpResId) {
		HelpDialogFragment hdf = new HelpDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("help_resource", helpResId);
		hdf.setArguments(bundle);
		return hdf;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		this.setCancelable(true);
		//int style = DialogFragment.STYLE_NORMAL, theme = 0;
		int style = DialogFragment.STYLE_NO_TITLE, theme = 0;
		setStyle(style,theme);
	}

	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
		getDialog().getWindow().setBackgroundDrawableResource(R.drawable.border_pink);
		View vv = inflater.inflate(R.layout.help_dialog, container, false);
		TextView tv = (TextView)vv.findViewById(R.id.helpmessage);
		tv.setText(getActivity().getResources().getText(getArguments().getInt("help_resource")));
		Button closeBtn = (Button)vv.findViewById(R.id.btn_close);
		closeBtn.setOnClickListener(this);
		
		//getDialog().requestWindowFeature(STYLE_NO_TITLE);
		//getDialog().setTitle("Pealkiri");
		
		return vv;
	}

	public void onClick(View v) {
		
		//Animation hyperspaceJump = AnimationUtils.loadAnimation(this.getActivity().getApplicationContext(), R.anim.rotate_360);
		//vv.startAnimation(hyperspaceJump);
		dismiss();
	}
}
