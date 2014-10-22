package com.sprcore.android.mbf.base;

import android.view.View;
import android.widget.TextView;

import com.sprcore.android.mbf.ui.R;

public class AppHeaderModel {
	
	private TextView headTitleTv;
	private TextView previewPicTv;
	private TextView refreshTv;
	private TextView searchTv;
	private TextView addTv;
	private TextView pic1;
	private TextView pic2;
 
	
	public void initElementFromFragment(View view){
		headTitleTv = (TextView)view.findViewById(R.id.headTitleTv);
		headTitleTv.setVisibility(View.GONE);
		
		previewPicTv = (TextView)view.findViewById(R.id.previewPicTv);
		previewPicTv.setTypeface(AppActivity.TYPE_FACE);
		previewPicTv.setVisibility(View.GONE);
 
		refreshTv = (TextView)view.findViewById(R.id.refreshTv);
		refreshTv.setTypeface(AppActivity.TYPE_FACE);
		refreshTv.setVisibility(View.GONE);
		
		searchTv = (TextView)view.findViewById(R.id.searchTv); 
		searchTv.setTypeface(AppActivity.TYPE_FACE);
		searchTv.setVisibility(View.GONE);
	
		addTv = (TextView)view.findViewById(R.id.addTv); 
		addTv.setTypeface(AppActivity.TYPE_FACE);
		addTv.setVisibility(View.GONE);
		
		
		pic1 = (TextView)view.findViewById(R.id.pic1); 
		pic1.setTypeface(AppActivity.TYPE_FACE);
		pic1.setVisibility(View.GONE);
		
		pic1 = (TextView)view.findViewById(R.id.pic2);
		pic1.setTypeface(AppActivity.TYPE_FACE);
		pic1.setVisibility(View.GONE);
 
	}
 	
	
	public void setTitle(String title){
		headTitleTv.setVisibility(View.VISIBLE);
		headTitleTv.setText(title);
	}
	
	public void setBack(View.OnClickListener listener){
		previewPicTv.setVisibility(View.VISIBLE);
		previewPicTv.setOnClickListener(listener);
	}
	
	public void setRefresh(View.OnClickListener listener){
		refreshTv.setVisibility(View.VISIBLE);
		refreshTv.setOnClickListener(listener);
	}
	
	public void setSearch(View.OnClickListener listener){
		searchTv.setVisibility(View.VISIBLE);
		searchTv.setOnClickListener(listener);
	}
 
	public void setAdd(View.OnClickListener listener){
		addTv.setVisibility(View.VISIBLE);
		addTv.setOnClickListener(listener);
	}
	
	
	public void setPic1(String string,View.OnClickListener listener){
		refreshTv.setText(string);
		refreshTv.setVisibility(View.VISIBLE);
		refreshTv.setOnClickListener(listener);
	}
	
	public void setPic2(String string,View.OnClickListener listener){
		refreshTv.setText(string);
		refreshTv.setVisibility(View.VISIBLE);
		refreshTv.setOnClickListener(listener);
	}	
}
