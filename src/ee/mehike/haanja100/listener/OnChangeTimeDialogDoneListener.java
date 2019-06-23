package ee.mehike.haanja100.listener;

/*
* An interface implemented typically by an activity
* so that a dialog can report back
* on what happened.
*/
public interface OnChangeTimeDialogDoneListener {
	
	public void onChangeTimeDialogDone(String tag, boolean cancelled, CharSequence end_time, 
			CharSequence time, long split, long rec_id);
	public void onChangeTimeDialogDone(String tag, boolean cancelled, long end_time, 
			long time, long split, long rec_id);

	//public void onBudgetDialogDone(String tag, boolean cancelled, CharSequence sum_text, 
	//		CharSequence shopname, long shop_id, long title, int type, String date, long account_id);
}
