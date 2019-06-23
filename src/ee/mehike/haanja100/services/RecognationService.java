package ee.mehike.haanja100.services;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

	/**
	 * Service that receives ActivityRecognition updates. It receives updates
	 * in the background, even if the main Activity is not visible.
	 * 
	 * http://www.javacodegeeks.com/2013/10/android-activity-recognition.html
	 * 
	 * http://developer.android.com/training/location/activity-recognition.html
	 * 
	 * http://tsicilian.wordpress.com/2013/09/23/android-activity-recognition/
	 * 
	 * http://java.dzone.com/articles/android-geofencing-google-maps
	 * 
	 */
	public class RecognationService extends IntentService {  
	  
		public RecognationService(String name) {
			super(name);
			// TODO Auto-generated constructor stub
		}

	//..
	  /**
	     * Called when a new activity detection update is available.
	     */
	    @Override
	    protected void onHandleIntent(Intent intent) {
	         //...
	          // If the intent contains an update
	        if (ActivityRecognitionResult.hasResult(intent)) {
	            // Get the update
	            ActivityRecognitionResult result = 
	              ActivityRecognitionResult.extractResult(intent);

	             DetectedActivity mostProbableActivity 
	                    = result.getMostProbableActivity();

	             // Get the confidence % (probability)
	            int confidence = mostProbableActivity.getConfidence();

	            // Get the type 
	            int activityType = mostProbableActivity.getType();
	           /* types:
	            * DetectedActivity.IN_VEHICLE
	            * DetectedActivity.ON_BICYCLE
	            * DetectedActivity.ON_FOOT
	            * DetectedActivity.STILL
	            * DetectedActivity.UNKNOWN
	            * DetectedActivity.TILTING
	            */
	            // process
	        }
	    }
	}