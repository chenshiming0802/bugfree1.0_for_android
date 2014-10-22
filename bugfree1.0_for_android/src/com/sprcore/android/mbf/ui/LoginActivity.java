package com.sprcore.android.mbf.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppAsyncTask;
import com.sprcore.android.mbf.base.AppFragment;
import com.sprcore.android.mbf.base.AppHeaderModel;
import com.sprcore.android.mbf.helper.UserHelper;
import com.sprcore.android.mbf.helper.model.DoCheckLoginSpModel;
import com.sprcore.android.mbf.helper.model.DoCheckLoginSrModel;
import com.sprcore.android.mbf.local.UserSessionLocal;
import com.sprcore.android.mbf.local.model.UserSummary;

public class LoginActivity extends AppActivity {

	@Override
	protected boolean isNeedLogin() {
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState,Bundle intentExtras) {
		setContentView(R.layout.activity_login);
		addFragment(R.id.container, new PlaceholderFragment(), intentExtras);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends AppFragment {
		private AppHeaderModel headerModel = new AppHeaderModel();
		private EditText userNameEt;
		private EditText passwordEt;
		private Button loginBt;

			
		@Override
		protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
	
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_login,
					container, false); 
			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {

			super.onActivityCreated(savedInstanceState);
			headerModel.initElementFromFragment(getView());
			headerModel.setTitle("登录客户服务系统");
			
			userNameEt = (EditText) findViewById(R.id.userNameEt);
			passwordEt = (EditText) findViewById(R.id.passwordEt);
			loginBt = (Button) findViewById(R.id.loginBt);
			loginBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					new LoginTask().singleExecute(getCurrentFragment());
				}
			});
		}

		class LoginTask extends AppAsyncTask<DoCheckLoginSrModel> {

			@Override
			public boolean onPreExecute2() {
				loginBt.setText("登录中...");
				loginBt.setClickable(false);
				return true;
			}

			@Override
			protected DoCheckLoginSrModel doInBackground() {
				DoCheckLoginSpModel spModel = new DoCheckLoginSpModel();
				spModel.setUserName(userNameEt.getText().toString());
				spModel.setUserPassword(passwordEt.getText().toString());
				DoCheckLoginSrModel srModel = new UserHelper().doCheckLogin(spModel);
				if (isSuccess(srModel)) {
					UserSummary userModel = new UserSummary();
					userModel.setUserName(srModel.getUserName());
					userModel.setUserEmail(srModel.getBugUserEmail());
					userModel.setUserRealName(srModel.getUserRealName());
					userModel.setUcore1(srModel.getUcore1());
					new UserSessionLocal().saveCurrentUser(getBaseActivity(),
							userModel);
				}
				return srModel;
			}

			@Override
			protected void onPostExecute(DoCheckLoginSrModel srModel) {
				if (isSuccess(srModel)) {
					getBaseActivity().showToast("欢迎您回来！");
					Intent intent = new Intent();
					intent.setClass(getBaseActivity(), MainActivity.class);
					startActivity(intent);
					getBaseActivity().finish();
				} else {
					getBaseActivity().showToast(srModel.getResultMessage());
					loginBt.setText("重新登录");
					loginBt.setClickable(true);
				}
			}
		}
	}
}
