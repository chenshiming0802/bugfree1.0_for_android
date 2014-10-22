package com.sprcore.android.mbf.base;

import java.util.ArrayList;
import java.util.List;

import com.sprcore.android.mbf.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class AppBaseAdapter extends BaseAdapter {
	private Context context;
	private List<Object> list;
	private LayoutInflater inflater;

	//设置context和数据list
	public void setContextAndList(Context context, List list) {
		this.context = context;
		
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<>();
		}
	} 
	
	public void setTextViewText(AppActivity view,int id,String text){
		TextView textView = (TextView)view.findViewById(id);
		textView.setText(text);
	}	
	public void setTextViewText(View view,int id,String text){
		TextView textView = (TextView)view.findViewById(id);
		textView.setText(text);
	}
	public void setTextTypeface(View view,int id){
		TextView textView = (TextView)view.findViewById(id);
		textView.setTypeface(AppActivity.TYPE_FACE);
	}
	
	/**
	 * 包含如下代码：
	 * View view = getInflater().inflate(R.layout.xxx,null);
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(inflater==null){
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		}
		return null;
	}
	@Override
	public int getCount() {
		return getList().size();
	}

	@Override
	public Object getItem(int arg0) {
		return getList().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public Context getContext() {
		return context;
	}

	public List<Object> getList() {
		if(list==null){
			list = new ArrayList<>();
		}
		return list;
	}

	public LayoutInflater getInflater() {
		return inflater;
	}
	public void setList(List list) {
		this.list = list;
	}

 
	/**
	 * 默认版本的listItem#theme_listitem
	 * @return
	 */
	public ThemeListItem createThemeListItem(){
		View view = getInflater().inflate(R.layout.theme_listitem,
				null);	
		ThemeListItem listItem = new ThemeListItem();
		listItem.init(view);
		return listItem;
	}

	
	public static class ThemeListItem{
		private View view;
		private TextView title1 ;
		private TextView title2Pic;
		private TextView title2;
		private TextView rightInfo;
		private TextView arrow;
		
		public void init(View view){
			this.view = view;
			title1 = (TextView)view.findViewById(R.id.title1);
			title1.setVisibility(View.GONE);
			title1.setText("");
			
			title2Pic = (TextView)view.findViewById(R.id.title2Pic);
			title2Pic.setVisibility(View.GONE);
			
			title2 = (TextView)view.findViewById(R.id.title2);
			title2.setVisibility(View.GONE);
			title2.setText("");
			
			rightInfo = (TextView)view.findViewById(R.id.rightInfo); 	
			rightInfo.setVisibility(View.GONE);
			rightInfo.setText("");
			
			arrow = (TextView)view.findViewById(R.id.arrow); 	
			arrow.setVisibility(View.GONE);		
		}
		
		public void setTitle1(String str){
			title1.setVisibility(View.VISIBLE);
			title1.setText(str);
		}
		
		public void setTitle2(String str){
			title2.setVisibility(View.VISIBLE);
			title2.setText(str);
		}		
		/**
		 * 如果str为null，则显示默认的二级title图标
		 * @param str
		 */
		public void setTitle2Pic(String str){
			title2Pic.setVisibility(View.VISIBLE);
			title2Pic.setTypeface(AppActivity.TYPE_FACE);		
			if(str!=null){
				title2Pic.setText(str);
			}
		}		
		public void setRightInfo(String str){
			rightInfo.setVisibility(View.VISIBLE);
			rightInfo.setText(str);
		}	
		
		public void setArrow(){
			arrow.setVisibility(View.VISIBLE);
			arrow.setTypeface(AppActivity.TYPE_FACE);	
		}

		public View getView() {
			return view;
		}	
		
		
		
	}
}
