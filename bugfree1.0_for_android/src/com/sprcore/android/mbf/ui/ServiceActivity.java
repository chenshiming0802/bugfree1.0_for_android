package com.sprcore.android.mbf.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppAsyncTask;
import com.sprcore.android.mbf.base.AppBaseAdapter;
import com.sprcore.android.mbf.base.AppFragment;
import com.sprcore.android.mbf.base.AppHeaderModel;
import com.sprcore.android.mbf.base.AppKeyValueModel;
import com.sprcore.android.mbf.base.AppListViewModel;
import com.sprcore.android.mbf.base.AppSpinnerModel;
import com.sprcore.android.mbf.base.AppSwitchViewModel;
import com.sprcore.android.mbf.helper.ServiceHelper;
import com.sprcore.android.mbf.helper.model.EditSubmitBuginfoSpModel;
import com.sprcore.android.mbf.helper.model.EditSubmitBuginfoSrModel;
import com.sprcore.android.mbf.helper.model.GetBuginfoSpModel;
import com.sprcore.android.mbf.helper.model.GetBuginfoSrModel;
import com.sprcore.android.mbf.helper.model.GetBuginfoSrModel.BugHistoryModel;
import com.sprcore.android.mbf.helper.model.ResolveBugSpModel;
import com.sprcore.android.mbf.helper.model.ResolveBugSrModel;
import com.sprcore.android.mbf.local.CommonReplyLocal;
import com.sprcore.android.mbf.local.model.CommonReply;

public class ServiceActivity extends AppActivity {

	@Override
	protected boolean isNeedLogin() {
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState,Bundle intentExtras) {
		setContentView(R.layout.activity_service);
		addFragment(R.id.container, new PlaceholderFragment(), intentExtras);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends AppFragment {
		private AppHeaderModel headerModel = new AppHeaderModel();
		private TextView butTitle;
		private TextView bugProject;
		private TextView bugType;
		private TextView finishTime;
		private TextView bugImport;
		private TextView bugAssignTo;
		private TextView bugCreateUser;

		
		private AppListViewModel commentLv = new AppListViewModel();
		private Button editBt;
		private Button fixBt;
		private Button editBlockCancelBt;
		private Button fixBlockCancelBt;

		private String bugId;
		private AppSpinnerModel fixTypeSpinnerModel = new AppSpinnerModel();
		private AppSpinnerModel editBlockCommonReplyEt = new AppSpinnerModel();
		private AppSpinnerModel fixBlockCommonReplyEt = new AppSpinnerModel();
		private EditText replyContentEt;
		private AppSwitchViewModel switchViewModel = new AppSwitchViewModel();
		private TextView replyAssignUserEt;
		private TextView fixBlockFixContentEt;
		private Button fixBlockSubmitBt;
		private Button editBlockSubmitBt;

		private String selectAssignUserName;

		@Override
		public void onCreate(Bundle savedInstanceState,Bundle intentExtras) {
			bugId = intentExtras.getString("bugId");// bugId
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_service,
					container, false);
			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			headerModel.initElementFromFragment(getView());
			headerModel.setTitle("Serivce详情");
			headerModel.setBack(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					getBaseActivity().finish();
				}
			});
			headerModel.setRefresh(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					new LoadBuginfoTask().singleExecute(getCurrentFragment());
				}
			});

			butTitle = (TextView) findViewById(R.id.butTitle);
			butTitle.setText("");
			bugProject = (TextView) findViewById(R.id.bugProject);
			bugProject.setText("");
			bugType = (TextView) findViewById(R.id.bugType);
			bugType.setText("");			
			finishTime = (TextView) findViewById(R.id.finishTime);
			finishTime.setText("");		
			bugImport = (TextView) findViewById(R.id.bugImport);
			bugImport.setText("");				
			bugAssignTo = (TextView) findViewById(R.id.bugAssignTo);
			bugAssignTo.setText("");
			bugCreateUser = (TextView) findViewById(R.id.bugCreateUser);
			bugCreateUser.setText("");
			
			commentLv.setAppActivity(getBaseActivity());
			commentLv.setListView((ListView) findViewById(R.id.commentLv));
			commentLv.setBaseAdapter(commentAdapter);
			commentLv.setExpandHeight(true);

			editBt = (Button) findViewById(R.id.editBt);
			editBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					switchViewModel.display(R.id.editBlock);
				}
			});

			editBlockCancelBt = (Button) findViewById(R.id.editBlockCancelBt);
			editBlockCancelBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					switchViewModel.display(R.id.buttonBlock);
				}
			});
			fixBlockCancelBt = (Button) findViewById(R.id.fixBlockCancelBt);
			fixBlockCancelBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					switchViewModel.display(R.id.buttonBlock);
				}
			});

			fixBt = (Button) findViewById(R.id.fixBt);
			fixBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					switchViewModel.display(R.id.fixBlock);
				}
			});

			fixTypeSpinnerModel
					.setSpinner((Spinner) findViewById(R.id.fixBlockFixTypeSpinner));
			List<AppKeyValueModel> dataModelsList = new ArrayList<AppKeyValueModel>();
			dataModelsList.add(new AppKeyValueModel("Fixed", "Fixed"));
			dataModelsList.add(new AppKeyValueModel("By Design", "By Design"));
			dataModelsList.add(new AppKeyValueModel("Duplicate", "Duplicate"));
			dataModelsList.add(new AppKeyValueModel("External", "External"));
			dataModelsList.add(new AppKeyValueModel("Not Repro", "Not Repro"));
			dataModelsList.add(new AppKeyValueModel("Postponed", "Postponed"));
			dataModelsList.add(new AppKeyValueModel("Will not Fix", "Will not Fix"));
			fixTypeSpinnerModel.setDatas(dataModelsList);
			fixTypeSpinnerModel.setDefaultAdapter(getBaseActivity());
			fixTypeSpinnerModel.display();
				
			replyContentEt = (EditText) findViewById(R.id.replyContentEt);

			switchViewModel.AddChildrenFromParent(findViewById(R.id.bottomFl));
			switchViewModel.display(R.id.buttonBlock);

			replyAssignUserEt = (TextView) findViewById(R.id.replyAssignUserEt);
			replyAssignUserEt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					startActivityForResult(new Intent(getBaseActivity(),
							SearchUserActivity.class),
							SearchUserActivity.PlaceholderFragment.REQUEST_CODE_SERVICEDETAIL);
				}
			});

			fixBlockFixContentEt = (EditText) findViewById(R.id.fixBlockFixContentEt);

			fixBlockSubmitBt = (Button) findViewById(R.id.fixBlockSubmitBt);
			fixBlockSubmitBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (fixTypeSpinnerModel.getCurrentDataModel() == null) {
						showToast("请选择解决方案");
						return;
					}
					new FixSubmitTask().singleExecute(getCurrentFragment());
				}
			});

			editBlockSubmitBt = (Button) findViewById(R.id.editBlockSubmitBt);
			editBlockSubmitBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (replyAssignUserEt.getText().toString().length() == 0) {
						showToast("请选择指派人");
						return;
					}
					if (replyContentEt.getText().toString().length() == 0) {
						showToast("请选择处理意见");
						return;
					}
					new EditSubmitTask().singleExecute(getCurrentFragment());
				}
			});
			
			editBlockCommonReplyEt.setSpinner((Spinner)findViewById(R.id.editBlockCommonReplyEt));
			editBlockCommonReplyEt.setDatas(new ArrayList());
			editBlockCommonReplyEt.setDefaultAdapter(getBaseActivity());
			editBlockCommonReplyEt.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					AppKeyValueModel model = editBlockCommonReplyEt.getDatas().get(position);
					replyContentEt.setText(model.getValue().toString());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {				
				}
			});
			editBlockCommonReplyEt.display();

			fixBlockCommonReplyEt.setSpinner((Spinner)findViewById(R.id.fixBlockCommonReplyEt));
			fixBlockCommonReplyEt.setDatas(new ArrayList());
			fixBlockCommonReplyEt.setDefaultAdapter(getBaseActivity());
			fixBlockCommonReplyEt.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					AppKeyValueModel model = fixBlockCommonReplyEt.getDatas().get(position);
					fixBlockFixContentEt.setText(model.getValue().toString());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {				
				}
			});
			fixTypeSpinnerModel.display();
			new LoadBuginfoTask().singleExecute(getCurrentFragment());

		}

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if (requestCode == SearchUserActivity.PlaceholderFragment.REQUEST_CODE_SERVICEDETAIL) {
				// 选择员工指派人
				switch (resultCode) {
				case Activity.RESULT_OK:
					selectAssignUserName = data.getStringExtra("userName");
					replyAssignUserEt.setText(data.getStringExtra("realName")
							+ "<" + data.getStringExtra("userName") + ">");
					showToast(selectAssignUserName + " selected");
					break;
				}
			}
		}

		/**
		 * 读取Buginfo信息，包括评论清单
		 * 
		 * @author chenshiming
		 * 
		 */
		class LoadBuginfoTask extends AppAsyncTask<GetBuginfoSrModel> {
 
			@Override
			protected GetBuginfoSrModel doInBackground() {
				GetBuginfoSpModel spModel = new GetBuginfoSpModel();
				spModel.setBugId(bugId);

				GetBuginfoSrModel srModel = new ServiceHelper().getBuginfo(spModel);
				commentLv.setDatas(srModel.getComments());
				
				List<CommonReply> replyList = new CommonReplyLocal().queryAllList(CommonReply.class, "desc");
				List<AppKeyValueModel> editDataModelList =new ArrayList<AppKeyValueModel>();
				List<AppKeyValueModel> fixDataModelList =new ArrayList<AppKeyValueModel>();
				for(int i=0,j=replyList.size();i<j;i++){
					CommonReply reply = replyList.get(i);
					if(reply.getType()!=null && reply.getType().equals(CommonReply.DICT_TYPE_EDITBUG)){					
						editDataModelList.add(new AppKeyValueModel(reply.getId()+"",reply.getContent()));				
					}else if(reply.getType()!=null && reply.getType().equals(CommonReply.DICT_TYPE_FIXBUG)){
						fixDataModelList.add(new AppKeyValueModel(reply.getId()+"",reply.getContent()));			
					}
				}
				editBlockCommonReplyEt.setDatasWithEmptyLine(editDataModelList, null);
				fixBlockCommonReplyEt.setDatasWithEmptyLine(fixDataModelList,null);
				return srModel;
			}

			@Override
			public boolean onPreExecute2() {
				return true;
			}

			@Override
			protected void onPostExecute(GetBuginfoSrModel srModel) {	
				headerModel.setTitle(srModel.getBugId()+" 详情");
				butTitle.setText(srModel.getBugTitle());
				bugProject.setText("项目模块："+srModel.getProjectName() + srModel.getModuleName());
				bugType.setText("事件类型："+srModel.getBugType());
				finishTime.setText("期望时间："+srModel.getFixedTime());
				bugImport.setText("严重程度："+srModel.getBugSeverity());
				bugAssignTo.setText("指派人员："+srModel.getAssignedToRealUserName()+"<"+srModel.getAssignedTo()+">");
				bugCreateUser.setText("由谁创建："+srModel.getOpenedByRealUserName()+"<"+srModel.getOpenedBy()+">");	
				
				selectAssignUserName = srModel.getAssignedTo();
				replyAssignUserEt.setText(srModel.getAssignedToRealUserName()
						+ "<" + srModel.getAssignedTo() + ">");
				commentLv.notifyDataSetChanged();
				
				editBlockCommonReplyEt.notifyDataSetChanged();
				fixBlockCommonReplyEt.notifyDataSetChanged();
			}
		}

		/**
		 * 更新事件任务
		 * 
		 * @author chenshiming
		 * 
		 */
		class EditSubmitTask extends AppAsyncTask<EditSubmitBuginfoSrModel> {
			@Override
			public boolean onPreExecute2() {
				return true;
			}
			
			@Override
			protected EditSubmitBuginfoSrModel doInBackground() {
				EditSubmitBuginfoSpModel spModel = new EditSubmitBuginfoSpModel();
				spModel.setAction("Edited");
				if (selectAssignUserName != null
						&& selectAssignUserName.trim().length() > 0) {
					spModel.setAssignedTo(selectAssignUserName);
				}
				spModel.setBugId(bugId);
				spModel.setNotes(replyContentEt.getText().toString());
//				spModel.setUserName(new UserSessionLocal()
//						.loadCurrentUserName(getBaseActivity()));

				EditSubmitBuginfoSrModel srModel = new ServiceHelper().editSubmitBuginfo(spModel);
				return srModel;
			}

			@Override
			protected void onPostExecute(EditSubmitBuginfoSrModel srModel) {
				showFailResultMessage(getBaseActivity(), srModel);
				if (srModel.getResultFlag().equals("0")) {
					switchViewModel.display(R.id.buttonBlock);
				}
				new LoadBuginfoTask().singleExecute(getCurrentFragment());
			}
		}

		/**
		 * 解决事件
		 */
		class FixSubmitTask extends AppAsyncTask<ResolveBugSrModel> {
			private ResolveBugSrModel srModel;
		
			@Override
			public boolean onPreExecute2() {
				return true;
			}

			@Override
			protected ResolveBugSrModel doInBackground() {
				ResolveBugSpModel spModel = new ResolveBugSpModel();
				spModel.setBugId(bugId);
				spModel.setResolution(fixTypeSpinnerModel.getCurrentDataModel()
						.getKey());
				spModel.setNotes(fixBlockFixContentEt.getText().toString());
//				spModel.setUserName(new UserSessionLocal()
//						.loadCurrentUserName(getBaseActivity()));

				srModel = new ServiceHelper().resolveBug(spModel);
				return srModel;
			}

			@Override
			protected void onPostExecute(ResolveBugSrModel srModel) {
				if (srModel.getResultFlag().equals("0")) {
					switchViewModel.display(R.id.buttonBlock);
				}
				new LoadBuginfoTask().singleExecute(getCurrentFragment());
			}

		}

		/**
		 * 评论列表Adapter
		 */
		private AppBaseAdapter commentAdapter = new AppBaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				super.getView(position, convertView, parent);
				BugHistoryModel model = (GetBuginfoSrModel.BugHistoryModel) getList()
						.get(position);

				View view = getInflater().inflate(
						R.layout.listitem_service_detail, null);
				setTextViewText(view, R.id.createInfo,model.getActionDate()+" "+model.getAction()+" by "+model.getUserName());
				TextView content = (TextView)view.findViewById(R.id.content);
				content.setText(Html.fromHtml(model.getFullInfo()));
				return view;
			}

		};

	}
}
