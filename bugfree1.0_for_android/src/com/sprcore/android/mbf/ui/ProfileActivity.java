package com.sprcore.android.mbf.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppAsyncTask;
import com.sprcore.android.mbf.base.AppFragment;
import com.sprcore.android.mbf.base.AppHeaderModel;
import com.sprcore.android.mbf.base.AppSrModel;
import com.sprcore.android.mbf.base.depend.apphelper.HttpDataHistoryLocal;
import com.sprcore.android.mbf.local.UserHistoryLocal;
import com.sprcore.android.mbf.local.UserSessionLocal;
import com.sprcore.android.mbf.local.model.UserSummary;

public class ProfileActivity extends AppActivity {

	@Override
	protected boolean isNeedLogin() {
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
		setContentView(R.layout.activity_profile);
		addFragment(R.id.container, new PlaceholderFragment(), intentExtras);			
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends AppFragment {
		private AppHeaderModel headerModel = new AppHeaderModel();
		private TextView realUserName;
		private TextView userName;
		private TextView email;
		private TextView mobile;
		private TextView cacheSize;
		private View cacheLayout;
		private View commonReplyLayout;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			View rootView = inflater.inflate(R.layout.fragment_profile,
					container, false);
			return rootView;
		}

		@Override
		protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {

			
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			headerModel.initElementFromFragment(getView());
			headerModel.setTitle("我的信息");
			
			realUserName = (TextView)findViewById(R.id.realUserName);
			realUserName.setText("");
			
			userName = (TextView)findViewById(R.id.userName);
			userName.setText("");
			
			email = (TextView)findViewById(R.id.email);
			email.setText("");
			
			mobile = (TextView)findViewById(R.id.mobile);
			mobile.setText("");
			
			cacheSize = (TextView)findViewById(R.id.cacheSize);
			//cacheSize.setText("");
			
			setTextTypeface(R.id.arrow);
			setTextTypeface(R.id.arrow2);
			
			cacheLayout = (View)findViewById(R.id.cacheLayout);
			cacheLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					new ClearCacheTask().singleExecuteWithConfirm(getCurrentFragment(), "清空后本地模式就不能访问任何数据，您确定要清空数据缓存？");
				}
			});
			
			commonReplyLayout = (View)findViewById(R.id.commonReplyLayout);
			commonReplyLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent it = new Intent();
					it.setClass(getBaseActivity(), CommonReplyActivity.class);
					startActivity(it);
				}
			});
			new LoadTask().singleExecute(getCurrentFragment());
		}
		
		class LoadTask extends AppAsyncTask<AppSrModel>{
			@Override
			public boolean onPreExecute2() {
				return true;
			}
			@Override
			protected AppSrModel doInBackground() {
				AppSrModel srModel = new AppSrModel();
				srModel.setResultFlag("0");
				
				UserSummary user = new UserSessionLocal().loadCurrentUser(getBaseActivity());
				realUserName.setText(user.getUserRealName());
				userName.setText("帐号:"+user.getUserName());
				email.setText(user.getUserEmail());
				mobile.setText("TODO");
				
				return srModel;
			}

			@Override
			protected void onPostExecute(AppSrModel srModel) {
				showFailResultMessage(getBaseActivity(), srModel);
			}	
		}
		class ClearCacheTask extends AppAsyncTask<AppSrModel>{
			@Override
			protected AppSrModel doInBackground() {
				AppSrModel srModel = new AppSrModel();
				srModel.setResultFlag("0");
				new HttpDataHistoryLocal().deleteAll();//删除历史浏览记录
				new UserHistoryLocal().deleteUserHistory();//删除员工查询记录
				return srModel;
			}

			@Override
			public boolean onPreExecute2() {
				return true;
			}

			@Override
			protected void onPostExecute(AppSrModel srModel) {
				if(isSuccess(srModel)){
					getBaseActivity().showToast("数据缓存清除成功！");
				}else{
					showFailResultMessage(getBaseActivity(), srModel);
				}
				
			}
		}
	}
}
