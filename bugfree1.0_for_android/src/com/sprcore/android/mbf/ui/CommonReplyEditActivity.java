package com.sprcore.android.mbf.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppAsyncTask;
import com.sprcore.android.mbf.base.AppFragment;
import com.sprcore.android.mbf.base.AppHeaderModel;
import com.sprcore.android.mbf.base.AppKeyValueModel;
import com.sprcore.android.mbf.base.AppSpinnerModel;
import com.sprcore.android.mbf.base.AppSrModel;
import com.sprcore.android.mbf.local.CommonReplyLocal;
import com.sprcore.android.mbf.local.model.CommonReply;

public class CommonReplyEditActivity extends AppActivity {

 

	@Override
	protected boolean isNeedLogin() {
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
		setContentView(R.layout.activity_common_reply_edit);
		addFragment(R.id.container, new PlaceholderFragment(), intentExtras);	
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends AppFragment {
		private AppHeaderModel appHeaderModel = new AppHeaderModel();
		private AppSpinnerModel type = new AppSpinnerModel();;
		private EditText content;
		private String id;
		
		private Button addBt;
		private Button editBt;
		private Button deleteBt;
		private Button cancelBt;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_common_reply_edit, container, false);
			return rootView;
		}

		@Override
		protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {	
			this.id = intentExtras.getString("id");
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			appHeaderModel.initElementFromFragment(getView());
			appHeaderModel.setTitle("常见回复管理");
			appHeaderModel.setBack(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					getBaseActivity().finish();
				}
			});
			type.setSpinner((Spinner)findViewById(R.id.type));		 
			List<AppKeyValueModel> dataModelsList = new CommonReply().dicts().get("type");//获取type字典信息
			type.setDatas(dataModelsList);
			type.setDefaultAdapter(getBaseActivity());
			content = (EditText)findViewById(R.id.content);
			
			addBt = (Button)findViewById(R.id.addCommonReplyBt);
			addBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					new SaveTask().singleExecute(getCurrentFragment());	
				}
			});
			
			editBt = (Button)findViewById(R.id.editCommonReplyBt);
			editBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					new SaveTask().singleExecute(getCurrentFragment());		
				}
			});
			
			deleteBt = (Button)findViewById(R.id.deleteCommonReplyBt);
			deleteBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					new DelTask().singleExecuteWithConfirm(getCurrentFragment(),"您确定要执行删除操作吗？");		
				}
			});
			
			cancelBt = (Button)findViewById(R.id.cancelCommonReplyBt);
			cancelBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					getBaseActivity().finish();		
				}
			});
			
			if(this.id!=null){
				//过有id，则是修改画面，否则为新增画面
				//new LoadDataTask().singleExecute(getCurrentFragment());
				addBt.setVisibility(View.GONE);
				editBt.setVisibility(View.VISIBLE);
				deleteBt.setVisibility(View.VISIBLE);
				cancelBt.setVisibility(View.VISIBLE);	
			}else{
				addBt.setVisibility(View.VISIBLE);
				editBt.setVisibility(View.GONE);
				deleteBt.setVisibility(View.GONE);
				cancelBt.setVisibility(View.VISIBLE);
			
			}
			
		}
		

		@Override
		public void onResume() {
			if(id!=null){				
				new LoadDataTask().singleExecute(getCurrentFragment());
			}
			super.onResume();
		}


		class LoadDataTask extends AppAsyncTask<AppSrModel>{
			private CommonReply commonReply;
			
			@Override
			public boolean onPreExecute2() {
				return true;
			}

			@Override
			protected AppSrModel doInBackground() {
				AppSrModel srModel = new AppSrModel();
				srModel.setResultFlag("0");
				commonReply = new CommonReplyLocal().getRowById(new Integer(id), CommonReply.class);
				if(commonReply==null){
					srModel.setResultFlag("1");
					srModel.setResultMessage("数据读取异常，请重新进入该画面");
				}
				return srModel;
			}

			@Override
			protected void onPostExecute(AppSrModel srModel) {
				type.setCurrentValue(commonReply.getType());
				content.setText(commonReply.getContent());
			}		
		}
		class SaveTask extends AppAsyncTask<AppSrModel>{
			public boolean onPreExecute2() {			
				return true;
			}
			
			@Override
			protected AppSrModel doInBackground() {
				AppSrModel srModel = new AppSrModel();
				srModel.setResultFlag("0");
				CommonReply model = new CommonReply();
				model.setType(type.getCurrentDataModel().getKey());
				model.setContent(content.getText().toString());
				
				model.setUpdateTime(CommonReply.currentTime());
				if(id!=null){
					//如果ID存在，则为修改
					new CommonReplyLocal().update2(model, new Integer(id));
				}else{
					//否则为新增
					model.setCreateTime(CommonReply.currentTime());
					new CommonReplyLocal().save(model);
				}
				return srModel;
			}

			@Override
			protected void onPostExecute(AppSrModel srModel) {		
				if(isSuccess(srModel)){
					showToast("保存成功!");
					getBaseActivity().finish();
				}else{
					showToast("保存失败,失败原因："+srModel.getResultMessage());
				}
			}
		}
		class DelTask extends AppAsyncTask<AppSrModel>{

			@Override
			public boolean onPreExecute2() {
				return true;
			}

			@Override
			protected AppSrModel doInBackground() {
				AppSrModel srModel = new AppSrModel();
				srModel.setResultFlag("0");
				if(id!=null){
					new CommonReplyLocal().deleteById(new Integer(id), CommonReply.class);
				}
				return srModel;
			}

			@Override
			protected void onPostExecute(AppSrModel srModel) {
				if(isSuccess(srModel)){
					showToast("保存成功!");
					getBaseActivity().finish();
				}else{
					showToast("删除失败,失败原因："+srModel.getResultMessage());
				}
			}
			
		}
		
		
		
	}
}
