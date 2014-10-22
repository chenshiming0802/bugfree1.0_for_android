package com.sprcore.android.mbf.ui;

 
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.sprcore.android.core.tools.PhoneTools;
import com.sprcore.android.core.tools.crash.ExitApplication;
import com.sprcore.android.core.tools.update.UpdateManager;
import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppNavItemModel;
import com.sprcore.android.mbf.base.AppNavModel;
import com.sprcore.android.mbf.fragment.ServcieAssignMeFragment;
import com.sprcore.android.mbf.local.UserSessionLocal;

public class MainActivity extends AppActivity {
	private AppNavModel<AppNavItemModel> navModel = new AppNavModel<AppNavItemModel>();
 
	
	@Override
	protected boolean isNeedLogin() {
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState,Bundle intentExtras) {
		
		if(PhoneTools.isWifiNet(getBaseActivity())){
			UpdateManager manager = new UpdateManager(getBaseActivity());
			manager.checkUpdate(false);			
		}
		
//		//如果没有登录过，在进入登录画面
//		if(!new UserSessionLocal().hasUserLogin(getApplicationContext())){
//			Intent intent = new Intent();
//			intent.setClass(getApplicationContext(), LoginActivity.class);
//			startActivity(intent);		
//			finish();
//		}
		
		setContentView(R.layout.activity_main);
		
		navModel.addNavItem(new AppNavItemModel(getBaseActivity(), R.id.assignMeLayout,new AppNavItemModel.OnClickListener() {
			@Override
			public void onClick(AppNavItemModel nav) {
				setServiceListFragementView("1",null);
			}
		}));
		navModel.addNavItem(new AppNavItemModel(getBaseActivity(), R.id.meCreateLayout,new AppNavItemModel.OnClickListener() {
			@Override
			public void onClick(AppNavItemModel nav) {
				setServiceListFragementView(null,"1");
			}
		}));	
		navModel.addNavItem(new AppNavItemModel(getBaseActivity(), R.id.myProfileLayout,new AppNavItemModel.OnClickListener() {
			@Override
			public void onClick(AppNavItemModel nav) {
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ProfileActivity.PlaceholderFragment f1 = new ProfileActivity.PlaceholderFragment();
				Bundle bundle = new Bundle();
				f1.setArguments(bundle);
				ft.replace(R.id.content_fragment,f1 );
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		        ft.addToBackStack(null);
		        ft.commitAllowingStateLoss();
			}
		}));
		navModel.addNavItem(new AppNavItemModel(getBaseActivity(), R.id.logoutLayout,new AppNavItemModel.OnClickListener() {
			@Override
			public void onClick(AppNavItemModel nav) {
				getBaseActivity().showConfirm("您确认是否注销帐号!",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
    					new UserSessionLocal().clearCurrentUser(getApplicationContext());
    					Intent intent = new Intent();
    					intent.setClass(getApplicationContext(), LoginActivity.class);
    					startActivity(intent);		
    					finish();	
    					ExitApplication.getInstance(getApplicationContext()).exit();
                    }
                });				
			}
		}));	
		navModel.display();	
		
		setServiceListFragementView("1",null);
	}
	
	
	
	
	@Override
	protected void onResume() {
		 
		super.onResume();
		
	}




	/**
	 * framentview显示list UI
	 * @param isAssignMe
	 * @param isMeCreate
	 */
	private void setServiceListFragementView(String isAssignMe,String isMeCreate){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ServcieAssignMeFragment f1 = new ServcieAssignMeFragment();
		Bundle bundle = new Bundle();
		bundle.putString("isAssignMe", isAssignMe);
		bundle.putString("isMeCreate", isMeCreate);
		f1.setArguments(bundle);
		ft.replace(R.id.content_fragment,f1 );
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
	}
 
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			getBaseActivity().showConfirm("您确认是否退出APP!",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	   Intent intent = new Intent(Intent.ACTION_MAIN);  
                       intent.addCategory(Intent.CATEGORY_HOME);  
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
                       startActivity(intent);  
                       android.os.Process.killProcess(android.os.Process.myPid());
                }
            });	
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
