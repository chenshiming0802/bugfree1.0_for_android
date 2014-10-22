package com.sprcore.android.mbf.base;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppNavItemModel {
	private AppNavModel parent;
	private int itemLayoutId;
	private LinearLayout itemLayout;
	private View lineView;
	private TextView picView;
	private TextView labelView;
	private OnClickListener listener;


	public AppNavItemModel(Activity view,int itemLayoutId,OnClickListener listener){
		this.itemLayoutId = itemLayoutId;
		this.itemLayout = (LinearLayout)view.findViewById(itemLayoutId);
		this.lineView = itemLayout.getChildAt(0);
		this.picView = (TextView)itemLayout.getChildAt(1);
		this.labelView = (TextView)itemLayout.getChildAt(2);		
		this.listener = listener;
		
		picView.setTypeface(AppActivity.TYPE_FACE);
		itemLayout.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				parent.setNavItemActivie(getAppNavItemModel());//�޸�nav����ʽ����ǰΪactive��������ʧЧ
				getListener().onClick(getAppNavItemModel());
			}
		});
	}
	
	public void setParent(AppNavModel parent) {
		this.parent = parent;
	}	
	

	public int getItemLayoutId() {
		return itemLayoutId;
	}

	public void setActive(boolean isActive){	
		if(isActive){
			lineView.setBackgroundColor(Color.parseColor("#999999"));
			picView.setTextColor(Color.parseColor("red"));
			labelView.setTextColor(Color.parseColor("red"));	
		}else{
			lineView.setBackgroundColor(Color.parseColor("#999999"));
			picView.setTextColor(Color.parseColor("#999999"));
			labelView.setTextColor(Color.parseColor("#999999"));	
			
//			lineView.setBackgroundColor(Color.parseColor("#ff00ff"));
//			picView.setTextColor(Color.parseColor("#ff00ff"));
//			labelView.setTextColor(Color.parseColor("#ff00ff"));	
		}
	}	
	
	public static interface OnClickListener{
		public void onClick(AppNavItemModel nav);
	}
	
	private AppNavItemModel getAppNavItemModel(){
		return this;
	}

	private OnClickListener getListener() {
		return listener;
	}
		
}
