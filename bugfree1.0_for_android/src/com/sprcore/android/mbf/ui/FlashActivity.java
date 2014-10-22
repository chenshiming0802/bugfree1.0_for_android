package com.sprcore.android.mbf.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.sprcore.android.core.tools.PhoneTools;
import com.sprcore.android.core.tools.update.UpdateManager;
import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppFragment;

public class FlashActivity extends AppActivity {
 
	@Override
	protected boolean isNeedLogin() {	 
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
		setContentView(R.layout.activity_flash);  
		addFragment(R.id.container, new PlaceholderFragment(), intentExtras); 

		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends AppFragment {
		private AlphaAnimation aa;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_flash,
					container, false);
			return rootView;
		}

		@Override
		protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {	
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			
			aa = new AlphaAnimation(0.8f,1.0f);
			aa.setDuration(1000);
			getView().startAnimation(aa);
			aa.setAnimationListener(new AnimationListener()
			{
				@Override
				public void onAnimationEnd(Animation arg0) {
					redirectTo();
				}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationStart(Animation animation) {}
				
			});
		
			//用于调试的窗口
			getView().setOnLongClickListener(new View.OnLongClickListener() {			
				@Override
				public boolean onLongClick(View arg0) {
					aa.setAnimationListener(new AnimationListener()
					{
						@Override
						public void onAnimationEnd(Animation arg0) {}
						@Override
						public void onAnimationRepeat(Animation animation) {}
						@Override
						public void onAnimationStart(Animation animation) {}
						
					});
					Intent i = new Intent();
					i.setClass(getBaseActivity(), TestMainActivity.class);
					startActivity(i);
					return false;
				}
			});
		}
		
	    private void redirectTo(){        
	        Intent intent = new Intent(getBaseActivity(), MainActivity.class);
	        startActivity(intent);
	        getBaseActivity().finish();
	    }
		
	}
}
