package com.sprcore.android.mbf.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppAsyncTask;
import com.sprcore.android.mbf.base.AppBaseAdapter;
import com.sprcore.android.mbf.base.AppFragment;
import com.sprcore.android.mbf.base.AppHeaderModel;
import com.sprcore.android.mbf.base.AppPullListViewPtrModel;
import com.sprcore.android.mbf.base.AppSrModel;
import com.sprcore.android.mbf.helper.UserHelper;
import com.sprcore.android.mbf.helper.model.QueryUsersSpModel;
import com.sprcore.android.mbf.helper.model.QueryUsersSrModel;
import com.sprcore.android.mbf.helper.model.QueryUsersSrModel.UserModel;
import com.sprcore.android.mbf.local.UserHistoryLocal;
import com.sprcore.android.mbf.local.model.UserHistory;

public class SearchUserActivity extends AppActivity {
 
	@Override
	protected boolean isNeedLogin() {
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState,Bundle intentExtras) {
		setContentView(R.layout.activity_search_user);
		addFragment(R.id.container, new PlaceholderFragment(), intentExtras);
	}
 

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends AppFragment {
		public static final int REQUEST_CODE_SERVICEDETAIL = 1;
		private AppHeaderModel headerModel = new AppHeaderModel();
		private AppPullListViewPtrModel listViewModel = new AppPullListViewPtrModel();
		private EditText searchEt;

		private Button searchBt;
		private Button clearHistoryBt;
		
		public void onCreate(Bundle savedInstanceState,Bundle intentExtras) {

		}
	 
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_search_user,
					container, false);
			return rootView;
		}
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			headerModel.initElementFromFragment(getView());
			headerModel.setTitle("员工搜索");
			headerModel.setBack(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					getBaseActivity().finish();
				}
			});
			
		
			listViewModel.setAppActivity(getBaseActivity());
			listViewModel
					.setListView((PullToRefreshListView) findViewById(R.id.listView1));
			listViewModel.setBaseAdapter(userListAdapter);
			listViewModel.setLoadListener(new AppPullListViewPtrModel.onLoadListener() {		
				@Override
				public void onRefresh() {
					listViewModel.setLoadNextPageIndex(1);
					new SearchUserTask().singleExecute(getCurrentFragment());
				}		
				@Override
				public void onMoreLoad(int pageIndex) {
					listViewModel.setLoadNextPageIndex(pageIndex);
					new SearchUserTask().singleExecute(getCurrentFragment());	 	
				}
			});
			listViewModel.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					QueryUsersSrModel.UserModel model = (QueryUsersSrModel.UserModel)listViewModel.getDatas().get(arg2-1);
					//通知副节点
					Intent it = new Intent();
					it.putExtra("userName", model.getUserName()); 
					it.putExtra("realName", model.getRealName()); 
					
					//保存员工visit历史
					UserHistory userHistory = new UserHistory();
					userHistory.setUserName(model.getUserName());
					userHistory.setRealName(model.getRealName());
					userHistory.setLastTime(Integer.valueOf(UserHistory.currentTime()));
					startThread(userHistory, new AppRunnable<UserHistory>() {
						@Override
						public void run(UserHistory params){
							new UserHistoryLocal().createOrUpdateUserHistory(params);
						}	
					});
					
					
					getBaseActivity().setResult(Activity.RESULT_OK, it);
					getBaseActivity().finish();
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
			
			clearHistoryBt = (Button)findViewById(R.id.clearHistoryBt);
			clearHistoryBt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					getBaseActivity().showConfirm("您确认是否删除历史列表数据!",new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int whichButton) {
	    					new ClearUserHistorytask().singleExecute(getCurrentFragment());	                    	
	                    }
	                });		
				}
			});
			new QueryLocalUserTask().singleExecute(this);
		}
		
		/**
		 * 清空历史
		 * @author chenshiming
		 *
		 */
		class ClearUserHistorytask extends AppAsyncTask<AppSrModel>{
			@Override
			public boolean onPreExecute2() {
				return true;
			}
			@Override
			protected AppSrModel doInBackground() {
				AppSrModel srModel = new AppSrModel();
				
				UserHistoryLocal local = new UserHistoryLocal() ;
				local.deleteUserHistory();
				srModel.setResultFlag("0");
				return srModel;
			}

			@Override
			protected void onPostExecute(AppSrModel srModel) {
				//清空数据后，清空页面显示的数据
				listViewModel.setDatas(null);
				listViewModel.notifyDataSetChanged();
			}
			
		}
		
		//查询本地存储的历史记录
		class QueryLocalUserTask extends SearchUserTask{
			@Override
			protected QueryUsersSrModel doInBackground() {
				QueryUsersSrModel srModel = new QueryUsersSrModel();
				List<UserHistory> list = new UserHistoryLocal().queryUserHistory();	
				List<UserModel> datas = new ArrayList<QueryUsersSrModel.UserModel>();
				for(int i=0,j=list.size();i<j;i++){
					UserHistory model = list.get(i);
					QueryUsersSrModel.UserModel data = new UserModel();
					data.setUserName(model.getUserName());
					data.setRealName(model.getRealName());
					datas.add(data);
				}
				srModel.setDatas(datas);
				srModel.setResultFlag("0");
				return srModel;
			}	
		}

		class SearchUserTask extends AppAsyncTask<QueryUsersSrModel> {
			protected QueryUsersSrModel srModel;
			@Override
			public boolean onPreExecute2() {
				return true;
			}

			@Override
			protected QueryUsersSrModel doInBackground() {
				QueryUsersSpModel spModel = new QueryUsersSpModel();
				spModel.setQueryString(searchEt.getText().toString());
				spModel.setPageSize(listViewModel.getLongPageSize());
				spModel.setPageIndex(listViewModel.getLongLoadNextPageIndex());
				srModel = new UserHelper().queryUsers(spModel);
				return srModel;
			}

			@Override
			protected void onPostExecute(QueryUsersSrModel srModel) {
				listViewModel.setDatas(srModel.getDatas());
				listViewModel.notifyDataSetChanged();
			}

		}

		private AppBaseAdapter userListAdapter = new AppBaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				super.getView(position, convertView, parent);
				QueryUsersSrModel.UserModel model = (QueryUsersSrModel.UserModel) getList()
						.get(position);
				
				View view = getInflater().inflate(R.layout.listitem_user_search,
						null);
				setTextViewText(view, R.id.userName, model.getRealName() + "<"
						+ model.getUserName() + ">");
				return view;

			}

		};
	}
	
	
	
}
