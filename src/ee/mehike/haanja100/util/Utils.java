package ee.mehike.haanja100.util;

import java.io.IOException;
import java.io.InputStream;

//import com.shop.checklist.R;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

//import com.shop.checklist.fragments.AlphaAnimation;
//import com.shop.checklist.fragments.Animation;
//import com.shop.checklist.fragments.LayoutAnimationController;
//import com.shop.checklist.fragments.TranslateAnimation;

public class Utils {
	
	public static void setLayoutAnimation2(ViewGroup panel, Context ctx) {
		  LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(ctx, ee.mehike.haanja100.R.anim.rotate_360);
		  panel.setLayoutAnimation(controller);
		 
		}
	
	public static void setLayoutAnim_slidedownfromtop(ViewGroup panel, Context ctx) {
		 
		  AnimationSet set = new AnimationSet(true);
		 
		  android.view.animation.Animation animation = new android.view.animation.AlphaAnimation(0.0f, 1.0f);
		  animation.setDuration(100);
		  set.addAnimation(animation);
		 
		  animation = new android.view.animation.TranslateAnimation(
		      android.view.animation.Animation.RELATIVE_TO_SELF, 0.0f, android.view.animation.Animation.RELATIVE_TO_SELF, 0.0f,
		      android.view.animation.Animation.RELATIVE_TO_SELF, -1.0f, android.view.animation.Animation.RELATIVE_TO_SELF, 0.0f
		  );
		  animation.setDuration(500);
		  set.addAnimation(animation);
		 
		  android.view.animation.LayoutAnimationController controller =
		      new android.view.animation.LayoutAnimationController(set, 0.25f);
		  panel.setLayoutAnimation(controller);
		 
		}
	
	public static void closeStreamQuietly(InputStream inputStream) {
		 try {
			if (inputStream != null) {
			     inputStream.close();  
			 }
		} catch (IOException e) {
			// ignore exception
		}
	}

	public static boolean isCamera(Context context) {
		PackageManager pm = context.getPackageManager();
		return(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA));
	}
	
	public static void setLayoutAnim_slideupfrombottom(ViewGroup panel, Context ctx) {

		  AnimationSet set = new AnimationSet(true);

		  Animation animation = new AlphaAnimation(0.0f, 1.0f);
		  animation.setDuration(100);
		  set.addAnimation(animation);

		  animation = new TranslateAnimation(
		      Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
		      Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f
		  );
		  animation.setDuration(500);
		  set.addAnimation(animation);

		//  set.setFillBefore(false);
		//  set.setFillAfter(false);

		  LayoutAnimationController controller =
		      new LayoutAnimationController(set, 0.25f);
		  panel.setLayoutAnimation(controller);

		}
	
}
