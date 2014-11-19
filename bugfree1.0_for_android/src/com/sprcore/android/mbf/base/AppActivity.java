package com.sprcore.android.mbf.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.sprcore.android.core.tools.crash.ExitApplication;
import com.sprcore.android.mbf.local.UserSessionLocal;
import com.sprcore.android.mbf.ui.LoginActivity;
import com.sprcore.android.mbf.ui.R;
import com.sprcore.android.mbf.ui.SearchServiceActivity.PlaceholderFragment;
 
/**
 * 可以存放项目的全局对象
 * @author chenshiming
 *
 */
public abstract class AppActivity extends Activity{
	private static AppActivity currentActivity ;
	//获取系统当前过得activity
	public static AppActivity getCurrentActivity() {
		return currentActivity;
	}
	private static void setCurrentActivity(AppActivity currentActivity) {
		AppActivity.currentActivity = currentActivity;
	}
	public static Typeface TYPE_FACE = null;
	private void initFontType() {
		if(TYPE_FACE==null){
			TYPE_FACE = Typeface.createFromAsset(getApplicationContext().getAssets(),
					"fontawesome-webfont.ttf");
		}
	}	
	
	
	//当前的登录用户信息
	private static AppUserSummary userSummary;
	public static AppUserSummary getUserSummary() {
		return userSummary;
	}
	public static void setUserSummary(AppUserSummary userSummary) {
		AppActivity.userSummary = userSummary;
	}
	//是否允许登录
	protected abstract boolean isNeedLogin();
	
	public AppActivity getBaseActivity(){
		return this;
	}
 
	/**
	 * 如果是fragment页面，则调用如下代码：
	 * <pre>
		setContentView(R.layout.activity_search_service);
		addFragment(R.id.container, new PlaceholderFragment(), intentExtras);	
		</pre>		 
	 * @param savedInstanceState
	 * @param intentExtras
	 */
	protected abstract void onCreate(Bundle savedInstanceState,Bundle intentExtras);
	
	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setCurrentActivity(this);
		initFontType();//初始化font-awesome
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制屏幕垂直（不支持横屏）
		
		ExitApplication.getInstance(this).addActivity(this).onCreate();
//		StatService.setAppChannel(this, "test", true);
//		StatService.setOn(this, StatService.EXCEPTION_LOG);
//		StatService.setDebugOn(false);
		
		//如果该acitivity需要登录，则校验登录状态
		if(isNeedLogin() && !new UserSessionLocal().hasUserLogin(getBaseActivity())){
			//setUserSummary(new UserSessionLocal().loadCurrentUser(getBaseContext()));
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(intent);		
			finish();	
			return;
		}
		if(isNeedLogin()){
			new UserSessionLocal().loadCurrentUser(getBaseContext());
		}
		
		
		Intent intent = getIntent();
		Bundle intentExtras = intent.getExtras();
		if(intentExtras==null){
			intentExtras = new Bundle();
		}
		onCreate(savedInstanceState,intentExtras);
		
	}


 
 
	/**
	 * 
	 * @param message
	 * <pre>
	 * usage:
	 * 	((BaseActivity)getActivity()).showToast("Hello World!"); 
	 * </pre>
	 */
	public void showToast(String message){
		Toast toast = Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG);
		toast.show();
	}
	
	public void showConfirm(String message,DialogInterface.OnClickListener okListener){
		new AlertDialog.Builder(getBaseActivity())
        .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle("确认提示")
        .setMessage(message)
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        })	               
        .setPositiveButton("OK", okListener)
        .show();
	}
	
	private AppFragment currentFragment;
	public AppFragment getCurrentFragment() {
		return currentFragment;
	}
	protected void addFragment(int containerId,AppFragment fragment,Bundle bundle) {
		fragment.setArguments(bundle);
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		fragment.setArguments(bundle);
		ft.replace(containerId,fragment );
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
        currentFragment = fragment;

	}
	
 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		getBaseActivity().finish();
		return super.onKeyDown(keyCode, event);
	}
 
}
