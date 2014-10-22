package com.sprcore.android.mbf.ui;

import com.sprcore.android.mbf.base.AppActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ExitActivity extends AppActivity {

 

	@Override
	protected boolean isNeedLogin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
		getBaseActivity().finish();
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//System.exit(0);// 直接结束程序
		android.os.Process.killProcess(android.os.Process.myPid());
		// 或者下面这种方式
		// android.os.Process.killProcess(android.os.Process.myPid());
	}	
	
}
