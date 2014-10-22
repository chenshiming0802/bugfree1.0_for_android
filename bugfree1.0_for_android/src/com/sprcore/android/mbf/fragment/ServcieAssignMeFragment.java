package com.sprcore.android.mbf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sprcore.android.mbf.base.AppAsyncTask;
import com.sprcore.android.mbf.base.AppBaseAdapter;
import com.sprcore.android.mbf.base.AppFragment;
import com.sprcore.android.mbf.base.AppHeaderModel;
import com.sprcore.android.mbf.base.AppPullListViewPtrModel;
import com.sprcore.android.mbf.helper.ServiceHelper;
import com.sprcore.android.mbf.helper.model.QueryServiceListSpModel;
import com.sprcore.android.mbf.helper.model.QueryServiceListSrModel;
import com.sprcore.android.mbf.helper.model.QueryServiceListSrModel.BuginfoModel;
import com.sprcore.android.mbf.ui.R;
import com.sprcore.android.mbf.ui.SearchServiceActivity;
import com.sprcore.android.mbf.ui.ServiceActivity;

public class ServcieAssignMeFragment extends AppFragment {
	private AppHeaderModel headerModel = new AppHeaderModel();
	private AppPullListViewPtrModel listViewModel = new AppPullListViewPtrModel();
	private String isAssignMe;
	private String isMeCreate;
	private String queryId;
	private String queryName;
	
	@Override
	public void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
		isAssignMe = intentExtras.getString("isAssignMe");//是否指派我的任务
		isMeCreate = intentExtras.getString("isMeCreate");//是否我创建的任务
		queryId = intentExtras.getString("queryId");//查询条件ID
		queryName = intentExtras.getString("queryName");//查询条件ID的名称
		
		//如果都没有参数，则默认指派给我
		if(isAssignMe==null && isMeCreate==null && queryId==null){
			isAssignMe = "1";
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		return inflater.inflate(R.layout.fragment_servcie_list, container, false);
	}
	
	
	
	
	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//head init
		headerModel.initElementFromFragment(getView());		
		if(isAssignMe!=null && isAssignMe.equals("1")){
			headerModel.setTitle("我的任务列表");
		}else if(isMeCreate!=null && isMeCreate.equals("1")){
			headerModel.setTitle("我创建的任务列表");
		}else if(queryName!=null && queryId!=null){
			headerModel.setTitle("自定义查询-"+queryName);
		}
		headerModel.setRefresh(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				listViewModel.refreshListView();			
			}
		});	
		headerModel.setSearch(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getBaseActivity(), SearchServiceActivity.class);
				startActivity(intent);
			}
		});	
		
		listViewModel.setAppActivity(getBaseActivity());
		listViewModel.setListView((PullToRefreshListView)findViewById(R.id.listView1));
		listViewModel.setBaseAdapter(serviceListAdapter);
		listViewModel.setLoadListener(new AppPullListViewPtrModel.onLoadListener() {		
			@Override
			public void onRefresh() {
				listViewModel.setLoadNextPageIndex(1);
				new ServiceListTask().singleExecute(getCurrentFragment());
			}		
			@Override
			public void onMoreLoad(int pageIndex) {
				listViewModel.setLoadNextPageIndex(pageIndex);
				new ServiceListTask().singleExecute(getCurrentFragment());	 	
			}
		});
		listViewModel.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				BuginfoModel model = (BuginfoModel)listViewModel.getDatas().get(position-1);	
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle bundle = new Bundle();
				bundle.putString("bugId", model.getBugId());
				intent.putExtras(bundle);
				intent.setClass(getBaseActivity(), ServiceActivity.class);
				startActivity(intent);		
				//finish();
			}
		});
		new ServiceListTask().singleExecute(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		
		
	}
	private AppBaseAdapter serviceListAdapter = new AppBaseAdapter(){
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			super.getView(position, convertView, parent);
			BuginfoModel model = (BuginfoModel)getList().get(position);		
			
			View view = getInflater().inflate(R.layout.listitem_servcie_list,parent,false);
			setTextViewText(view,R.id.bugTitle,model.getBugId()+" "+model.getBugTitle());
			setTextViewText(view,R.id.bugInfo1,model.getProjectName()+model.getModuleName());
			setTextViewText(view,R.id.bugInfo2,model.getOpenedByRealUserName()+" "+model.getOpenedDate()+" 创建");
			setTextViewText(view,R.id.bugInfo3,"期望 "+model.getFixedTime()+"完成");
			String rightInfo =  model.getBugStatus()+"\n"+model.getAssignedToRealUserName();//+"\n"+"TODO";
			setTextViewText(view,R.id.bugStatus,rightInfo);
			
			setTextTypeface(view,R.id.bugInfo1Pic);
			setTextTypeface(view,R.id.bugInfo2Pic);
			setTextTypeface(view,R.id.bugInfo3Pic);
			setTextTypeface(view,R.id.arrow);
			return view;
		}	
	};
	
	class ServiceListTask extends AppAsyncTask<QueryServiceListSrModel>{	
		
		@Override
		public boolean onPreExecute2() {
			return true;
		}
		@Override
		protected QueryServiceListSrModel doInBackground() {
			QueryServiceListSpModel spModel = new QueryServiceListSpModel();
			spModel.setPageSize(listViewModel.getLongPageSize());
			spModel.setPageIndex(listViewModel.getLongLoadNextPageIndex());
			spModel.setIsAssignMe(isAssignMe);
			spModel.setIsMeCreate(isMeCreate);
			spModel.setQueryId(queryId);
//			spModel.setUserName(new UserSessionLocal().loadCurrentUserName(getBaseActivity()));
			return new ServiceHelper().queryServiceList(spModel);
		}
		@Override
		protected void onPostExecute(QueryServiceListSrModel srModel) {	
			listViewModel.setDatas(srModel.getDatas());
			listViewModel.notifyDataSetChanged();
		}	
	};

 


}
