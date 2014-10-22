package com.sprcore.android.mbf.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppAsyncTask;
import com.sprcore.android.mbf.base.AppBaseAdapter;
import com.sprcore.android.mbf.base.AppFragment;
import com.sprcore.android.mbf.base.AppHeaderModel;
import com.sprcore.android.mbf.base.AppListViewModel;
import com.sprcore.android.mbf.base.AppSrModel;
import com.sprcore.android.mbf.local.CommonReplyLocal;
import com.sprcore.android.mbf.local.model.CommonReply;

public class CommonReplyActivity extends AppActivity {

	
	@Override
	protected boolean isNeedLogin() {
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
		setContentView(R.layout.activity_common_reply);
		addFragment(R.id.container, new PlaceholderFragment(), intentExtras);		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends AppFragment {
		private AppHeaderModel headerModel = new AppHeaderModel();
		private AppListViewModel listViewModel = new AppListViewModel();
		@Override
		protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			View rootView = inflater.inflate(R.layout.fragment_common_reply,
					container, false);
			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			headerModel.initElementFromFragment(getView());
			headerModel.setTitle("常见回复管理");
			headerModel.setBack(new View.OnClickListener() {	
				@Override
				public void onClick(View arg0) {
					getBaseActivity().finish();
				}
			});
			headerModel.setAdd(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent();
					intent.setClass(getBaseActivity(), CommonReplyEditActivity.class);
					startActivity(intent);
				}
			});
			listViewModel.setAppActivity(getBaseActivity());
			listViewModel.setListView((ListView)findViewById(R.id.listView1));
			listViewModel.setBaseAdapter(listApdater);
//			listViewModel.setDatas(null);
			listViewModel.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					CommonReply model = (CommonReply)listViewModel.getDatas().get(position);
					Intent intent = new Intent();
					intent.setClass(getBaseActivity(), CommonReplyEditActivity.class);
					Bundle bundle = new Bundle();
					//bundle.putString("id", model.getId().toString());
					intent.putExtra("id", model.getId().toString());
					startActivity(intent);
				}
			});
			
		}

		@Override
		public void onResume() {
			super.onResume();
			new LoadDataTask().singleExecute(getCurrentFragment());
		}



		class LoadDataTask extends AppAsyncTask<AppSrModel>{ 
			@Override
			public boolean onPreExecute2() {
				return true;
			}
			@Override
			protected AppSrModel doInBackground() {
				AppSrModel srModel = new AppSrModel();
				srModel.setResultFlag("0");
				try {
					List list = new CommonReplyLocal().queryAllList(CommonReply.class, "desc");
					listViewModel.setDatas(list);
				} catch (Exception e) {
					srModel.setResultFlag("1");
				}
				return srModel;
			}
			@Override
			protected void onPostExecute(AppSrModel srModel) {
				listViewModel.notifyDataSetChanged();
			}
		}
		
		private AppBaseAdapter listApdater = new AppBaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				super.getView(position, convertView, parent);
				CommonReply model = (CommonReply)listViewModel.getDatas().get(position);
				
				ThemeListItem themeListItem = createThemeListItem();
				String typeValue = model.dictValue("type", model.getType());
				
				themeListItem.setTitle1(typeValue+" : "+model.getContent());
				return themeListItem.getView();		
			}		
		};
		
		
		
	}
}
