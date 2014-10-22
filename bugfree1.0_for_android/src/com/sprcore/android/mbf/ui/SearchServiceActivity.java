package com.sprcore.android.mbf.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppAsyncTask;
import com.sprcore.android.mbf.base.AppBaseAdapter;
import com.sprcore.android.mbf.base.AppFragment;
import com.sprcore.android.mbf.base.AppHeaderModel;
import com.sprcore.android.mbf.base.AppPullListViewPtrModel;
import com.sprcore.android.mbf.helper.ServiceHelper;
import com.sprcore.android.mbf.helper.model.QueryServiceListSpModel;
import com.sprcore.android.mbf.helper.model.QueryServiceListSrModel;
import com.sprcore.android.mbf.helper.model.QueryServiceListSrModel.BuginfoModel;

public class SearchServiceActivity extends AppActivity {

	 

	@Override
	protected boolean isNeedLogin() {
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
		setContentView(R.layout.activity_search_service);
		addFragment(R.id.container, new PlaceholderFragment(), intentExtras);			
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends AppFragment {
		private AppHeaderModel headerModel = new AppHeaderModel();
		private AppPullListViewPtrModel listViewModel = new AppPullListViewPtrModel();
		private EditText searchEt;
		private Button searchBt;
 

		@Override
		protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_search_service,
					container, false);		
			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			headerModel.initElementFromFragment(getView());
			headerModel.setTitle("Service搜索");
			headerModel.setBack(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					getBaseActivity().finish();
				}
			});
			
			listViewModel.setAppActivity(getBaseActivity());
			listViewModel
					.setListView((PullToRefreshListView) findViewById(R.id.listView1));
			listViewModel.setBaseAdapter(serviceListAdapter);
			listViewModel.setLoadListener(new AppPullListViewPtrModel.onLoadListener() {		
				@Override
				public void onRefresh() {
					listViewModel.setLoadNextPageIndex(1);
					executeQueryServiceTask();
				}		
				@Override
				public void onMoreLoad(int pageIndex) {
					listViewModel.setLoadNextPageIndex(pageIndex);
					executeQueryServiceTask(); 	
				}
			});


			searchEt = (EditText) findViewById(R.id.searchEt);
			searchBt = (Button) findViewById(R.id.searchBt);
			searchBt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					listViewModel.refreshListView();
				}
			});
		
		}		
		private void executeQueryServiceTask(){
			if(searchEt.getText().toString().trim().length()==0){
				showToast("请输入搜索关键字");
				return;
			}
			new QueryServiceTask().singleExecute(getCurrentFragment());
		}
		
		//copy from ServiceAssignMeFragment#serviceListAdapter
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
		
		
		class QueryServiceTask extends AppAsyncTask<QueryServiceListSrModel>{
			@Override
			public boolean onPreExecute2() {
				return true;
			}

			@Override
			protected QueryServiceListSrModel doInBackground() {
				QueryServiceListSpModel spModel = new QueryServiceListSpModel();
				spModel.setQueryString(searchEt.getText().toString());
				spModel.setPageSize(listViewModel.getLongPageSize());
				spModel.setPageIndex(listViewModel.getLongLoadNextPageIndex());
				return new ServiceHelper().queryServiceList(spModel);
			}
			
			@Override
			protected void onPostExecute(QueryServiceListSrModel srModel) {
				listViewModel.setDatas(srModel.getDatas());
				listViewModel.notifyDataSetChanged();								
			}
			
		}
	}
	
}
