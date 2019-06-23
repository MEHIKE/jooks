package ee.mehike.haanja100.listener;

/*
* An interface implemented typically by an activity
* so that a dialog can report back
* on what happened.
*/
public interface OnFbDialogDoneListener {
	public void onFbDialogDone(String tag, boolean cancelled, CharSequence message, long rec_id);
}
