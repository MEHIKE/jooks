package ee.mehike.haanja100.listener;

import android.support.v4.app.DialogFragment;

/*
* An interface implemented typically by an activity
* so that a dialog can report back
* on what happened.
*/
public interface OnTimepickerSetListener {
	
	public void onTimepickerSet(String date);
	public DialogFragment onCreateDialog(int id);
}
