package com.sprcore.android.mbf.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sprcore.android.core.tools.wordpress.ImageUtils;
import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppAsyncTask;
import com.sprcore.android.mbf.base.AppFragment;
import com.sprcore.android.mbf.base.AppHeaderModel;
import com.sprcore.android.mbf.helper.ServiceHelper;
import com.sprcore.android.mbf.helper.model.AddServiceSpModel;
import com.sprcore.android.mbf.helper.model.AddServiceSrModel;
import com.sprcore.android.mbf.ui.ServiceActivity.PlaceholderFragment.UploadPicTask;

public class AddServiceActivity extends AppActivity {

 

	@Override
	protected boolean isNeedLogin() {
 
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
		setContentView(R.layout.activity_add_service);
		addFragment(R.id.container, new PlaceholderFragment(), intentExtras);	
		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends AppFragment {
		private AppHeaderModel headerModel = new AppHeaderModel();
		private TextView bugInfo;
		private TextView assignUser;
		private String selectAssignUserName;
		private String selectProjectId;
		private String selectModuleId;
		private Button addBt;
		
		private EditText bugTitleEt;
		private EditText notesEt;
		@Override
		protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			View rootView = inflater.inflate(R.layout.fragment_add_service,
					container, false);
			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			headerModel.initElementFromFragment(getView());
			headerModel.setTitle("创建事件");
			
			bugInfo = (TextView)findViewById(R.id.bugInfo);
			bugInfo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					startActivityForResult(new Intent(getBaseActivity(),
							SearchProjectActivity.class),
							SearchProjectActivity.PlaceholderFragment.REQUEST_CODE_SERVICEDETAIL);
				}
			});
			assignUser = (TextView)findViewById(R.id.assignUser);
			assignUser.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					startActivityForResult(new Intent(getBaseActivity(),
							SearchUserActivity.class),
							SearchUserActivity.PlaceholderFragment.REQUEST_CODE_SERVICEDETAIL);
				}
			});
			
			bugTitleEt = (EditText)findViewById(R.id.bugTitleEt);
			notesEt = (EditText)findViewById(R.id.notesEt);
			
			
			addBt = (Button)findViewById(R.id.addBt);
			addBt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					new AddServiceTask().singleExecute(getCurrentFragment());
					
				}
			});
			
		}

		
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if(resultCode != RESULT_OK) return;
			
			if (requestCode == SearchUserActivity.PlaceholderFragment.REQUEST_CODE_SERVICEDETAIL) {
				// 选择员工指派人
				selectAssignUserName = data.getStringExtra("userName");
				assignUser.setText(data.getStringExtra("realName")
						+ "<" + data.getStringExtra("userName") + ">");
			}else if (requestCode == SearchProjectActivity.PlaceholderFragment.REQUEST_CODE_SERVICEDETAIL) {
				// 选择员工指派人
				String tt = data.getStringExtra("userName");
				String[] tts = tt.split(",");
				selectProjectId = tts[0];
				selectModuleId = (tts.length>1)?tts[1]:"";
				bugInfo.setText(data.getStringExtra("realName")
						+ "<" + data.getStringExtra("userName") + ">");
			} 
				
		}
		
		public class AddServiceTask extends AppAsyncTask<AddServiceSrModel>{

			@Override
			protected AddServiceSrModel doInBackground() {
				AddServiceSpModel spModel = new AddServiceSpModel();
				spModel.setAssignedTo(selectAssignUserName);
				spModel.setBugTitle(bugTitleEt.getText().toString());
				spModel.setModuleId(selectModuleId);
				spModel.setNotes(notesEt.getText().toString());
				spModel.setProjectId(selectProjectId);
				return new ServiceHelper().addService(spModel);
			}

			@Override
			public boolean onPreExecute2() {
				if(bugTitleEt.getText().toString().length()==0){
					showToast("请填写事件标题");
					return false;
				}
				if(selectProjectId==null || selectProjectId.length()==0){
					showToast("请填写事件项目");
					return false;
				}		
				if(notesEt.getText().toString().length()==0){
					showToast("请填写事件项目");
					return false;
				}				
				return true;
			}

			@Override
			protected void onPostExecute(AddServiceSrModel srModel) {
				if(isSuccess(srModel)){
					showToast("事件创建成功，事件号:#"+srModel.getBugId());
					
					Intent intent = new Intent();
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Bundle bundle = new Bundle();
					bundle.putString("bugId", srModel.getBugId());
					intent.putExtras(bundle);
					intent.setClass(getBaseActivity(), ServiceActivity.class);
					startActivity(intent);	
					
					getBaseActivity().finish();
				}else{
					showToast("创建失败，失败原因:"+srModel.getResultMessage());
				}
			}
			
		}
	}
}
