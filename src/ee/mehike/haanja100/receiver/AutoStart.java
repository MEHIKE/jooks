package ee.mehike.haanja100.receiver;

//http://www.e-nature.ch/tech/android-sdk-add-a-repeating-alarmmanager-to-the-autostart-displaying-messages-in-the-notification-bar/


import ee.mehike.haanja100.services.StarterService;
import ee.mehike.haanja100.services.WakefulIntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AutoStart extends BroadcastReceiver {

/**
* Listens for Android's BOOT_COMPLETED broadcast and then executes
* the onReceive() method.
*/
@Override
public void onReceive(Context context, Intent inten) {
	Log.d("Autostart", "BOOT_COMPLETED broadcast received. Executing starter service.");
	if (inten.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
        // Set the alarm here.
    }
	WakefulIntentService.acquireStaticLock(context); //acquire a partial WakeLock, lisatud rynno
	Intent intent = new Intent(context, StarterService.class);
	Toast.makeText(context, "AutoStart receiver started", 5000).show();
	context.startService(intent);
}
}