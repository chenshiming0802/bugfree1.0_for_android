package com.sprcore.android.mbf.base;

import java.util.ArrayList;
import java.util.List;

import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.widget.ListView;

public  class AppListViewModel {
 
	public void notifyDataSetChanged(){
		this.getBaseAdapter().notifyDataSetChanged();
//		if(isExpandHeight){
//			//高度自适应
//			try {		
//				int cnt = getDatas().size();
//				int h = 0;
//				for(int i=0; i<cnt; i++) {
//					View item = baseAdapter.getView(i, null, listView);
//					item.measure(0, 0);
//					h += item.getMeasuredHeight();
//				}
//				LayoutParams params = (LayoutParams) listView.getLayoutParams();
//				params.height  = (listView.getDividerHeight() * (cnt - 1)) + h +1200;
//				listView.setLayoutParams(params);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		LayoutParams params = (LayoutParams) listView.getLayoutParams();
//		params.height += 200;
//		listView.setLayoutParams(params);
		
	}
	//传入数据
	private AppActivity appActivity;	
	private ListView listView;
	private AppBaseAdapter baseAdapter;
	private List datas;
	private boolean isExpandHeight = false;//是否自适应高度
	//设置acitivty
	public void setAppActivity(AppActivity appActivity) {
		this.appActivity = appActivity;
	}
	public AppBaseAdapter getBaseAdapter() {
		return baseAdapter;
	}
	public void setBaseAdapter(AppBaseAdapter baseAdapter) {
		this.baseAdapter = baseAdapter;
		this.baseAdapter.setContextAndList(appActivity,getDatas());
		listView.setAdapter(this.baseAdapter);
	}
	public List getDatas() {
		return datas;
	}
	public void setDatas(List datas) {
		this.datas = datas;
		if(this.datas==null){
			this.datas = new ArrayList<>();
		}		
		baseAdapter.setList(this.datas);
	}
	public ListView getListView() {
		return listView;
	}
	public void setListView(ListView listView) {
		this.listView = listView;
	}
	/**
	 *  对xml的要求：
            <com.sprcore.android.core.tools.ListViewForScrollView style="@style/theme_listview"
                android:id="@+id/commentLv" />
            <LinearLayout style="@style/theme_body_line" android:layout_height="60dp"/>
	 * @param isExpandHeight
	 */
	public void setExpandHeight(boolean isExpandHeight) {
		this.isExpandHeight = isExpandHeight;
	}

}
