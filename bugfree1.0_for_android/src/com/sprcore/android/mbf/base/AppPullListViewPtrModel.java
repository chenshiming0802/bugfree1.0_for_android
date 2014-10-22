package com.sprcore.android.mbf.base;

import java.util.ArrayList;
import java.util.List;

import android.text.format.DateUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Ptr为 PullToRefresh
 * @author chenshiming
 *
 */
public class AppPullListViewPtrModel extends AppPullListViewModel{
	
 
	//设置数据已经变化，通知listView重新显示数据
	public void notifyDataSetChanged(){
		baseAdapter.notifyDataSetChanged();
		listView.onRefreshComplete();
		
		switch (getListViewAction()) {
		case LISTVIEW_ACTION_REFRESH://刷新则最后停留在第一行
			//listView.setSelection(0);
			break;
		case LISTVIEW_ACTION_LOADMORE:
			break;
		}		
		
		listView.setTag(AppPullListViewPtrModel.LISTVIEW_DATA_MORE);
		//如果是数据读取完了，则显示数据已经读取完
		if(lastCountSize<getPageSize()){
			setListViewStatus(LISTVIEW_DATA_FULL);
			listView.setMode(Mode.PULL_FROM_START);
		}else{
			listView.setMode(Mode.BOTH);
			setListViewStatus(LISTVIEW_DATA_MORE);
		}
		if(getDatas().size()==0){
			setListViewStatus(LISTVIEW_DATA_EMPTY);
		}
		
	}	
	//设置acitivty
	public void setAppActivity(AppActivity appActivity) {
		this.appActivity = appActivity;
	}
	//设置adapter
	public void setBaseAdapter(AppBaseAdapter baseAdapter) {
		this.baseAdapter = baseAdapter;
		this.baseAdapter.setContextAndList(appActivity,getDatas());
		listView.setAdapter(this.baseAdapter);
	}
	//设置数据集合
	public void setDatas(List newDatas) {
		if(newDatas==null){
			newDatas = new ArrayList<>();
		}
		switch(getListViewAction()){
		case LISTVIEW_ACTION_LOADMORE:
			//如果加载更多，则数据叠加如datas
			if(this.datas==null){
				this.datas = new ArrayList<>();
			}		
			lastCountSize = newDatas.size();
			this.datas.addAll(newDatas);
			baseAdapter.setList(this.datas);			
			break;
		case LISTVIEW_ACTION_REFRESH:
		case LISTVIEW_ACTION_NULL:
			this.datas = newDatas;
			if(this.datas==null){
				this.datas = new ArrayList<>();
			}		
			lastCountSize = this.datas.size();
			baseAdapter.setList(this.datas);
			break;
		}
	}
 
	
	//读取listView控件
	public PullToRefreshListView getListView() {
		return listView;
	}
	/**
	 * 更新listView的task，必须调用该方法，而不是直接调用task
	 */
	public void refreshListView(){
		loadListener.onRefresh();
		setListViewAction(LISTVIEW_ACTION_REFRESH);
		setListViewStatus(LISTVIEW_DATA_LOADING);		
	}
	
	//设置listView控件
	public void setListView(PullToRefreshListView listView) {
		this.listView = listView;
		
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = ""+DateUtils.formatDateTime(
						appActivity, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				loadListener.onRefresh();
				setListViewAction(LISTVIEW_ACTION_REFRESH);
				setListViewStatus(LISTVIEW_DATA_LOADING);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = ""+DateUtils.formatDateTime(
						appActivity, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				int pageIndex = getDatas().size() / 5 +1;
				if(getListViewStatus()!=LISTVIEW_DATA_FULL){
					loadListener.onMoreLoad(pageIndex);
					setListViewAction(LISTVIEW_ACTION_LOADMORE);
					setListViewStatus(LISTVIEW_DATA_LOADING);					
				}
			}
		});
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
			@Override
			public void onLastItemVisible() {
				if(getListViewStatus()==LISTVIEW_DATA_FULL){
					appActivity.showToast("记录全部读取完!");
				}
			}
		});
		listView.setMode(Mode.BOTH);
 
		
 	}
	//设置loadMore和refresh的触发动作
	public void setLoadListener(onLoadListener loadListener) {
		this.loadListener = loadListener;
	}
	//设置分页大小
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
 
	
	//listView当前的动作状态
	private int listViewAction = LISTVIEW_ACTION_NULL;
	//listView当前的数据状态
	private int listViewStatus = LISTVIEW_DATA_MORE;
	public int getListViewAction() {
		return listViewAction;
	}
	public void setListViewAction(int listViewAction) {
		this.listViewAction = listViewAction;
	}
	public int getListViewStatus() {
		return listViewStatus;
	}
	public void setListViewStatus(int listViewStatus) {
		this.listViewStatus = listViewStatus;
	}
	
	
	private int loadNextPageIndex = 1;
	//设置读取的数据页数
	public void setLoadNextPageIndex(int loadNextPageIndex) {
		this.loadNextPageIndex = loadNextPageIndex;
	}
	public int getLoadNextPageIndex() {
		return loadNextPageIndex;
	}
	public Long getLongLoadNextPageIndex(){
		return Long.valueOf(this.getLoadNextPageIndex());
	}
	//读取数据集合
	public List getDatas() {
		if(datas==null){
			datas = new ArrayList<>();
		}
		return datas;
	}

	
	

	public int getPageSize() {
		if(pageSize==0){
			pageSize = PAGE_SIZE;
		}
		return pageSize;
	}
	public Long getLongPageSize(){
		return Long.valueOf(this.getPageSize());
	}



	//必要传入数据
	private AppActivity appActivity;
	//必要传入数据
	private PullToRefreshListView listView;
	//必要传入数据
	private AppBaseAdapter baseAdapter;
	//必要传入数据
	private List datas;
	
	//必要传入数据
	private onLoadListener loadListener;
	//可选传入参数
	private int pageSize;
	//非外部传入数据:最近一次载入数据记录数
	private int lastCountSize;
	

}

