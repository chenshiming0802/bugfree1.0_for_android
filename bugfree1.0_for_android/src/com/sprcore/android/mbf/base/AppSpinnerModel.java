package com.sprcore.android.mbf.base;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

public class AppSpinnerModel {
	private AppKeyValueModel currentDataModel; //当前选择值
	private Spinner spinner;
	private List<AppKeyValueModel> datas;
	//	private ArrayAdapter<DataModel> adapter;
//	private AppBaseAdapter adapter;
	public void notifyDataSetChanged(){
		this.adapter.notifyDataSetChanged();
	}
	public Spinner getSpinner() {
		return spinner;
	}
	
	public void display(){
		adapter.notifyDataSetChanged();
	}

	public void setSpinner(Spinner spinner) {
		this.spinner = spinner;
		this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {  		  
		    @Override  
		    public void onItemSelected(AdapterView<?> parent, View view,  
		                    int position, long id) {  
		    	currentDataModel = datas.get(position);
		    }  
		    @Override  
		    public void onNothingSelected(AdapterView<?> parent) {  
		    	return;
		    }  
		});  
	}

	public AppKeyValueModel getCurrentDataModel() {
		return currentDataModel;
	}
	/**
	 * 设置默认值
	 * @param key
	 */
	public void setCurrentValue(String key){
		for(int i=0,j=datas.size();i<j;i++){
			if(datas.get(i).getKey().equals(key)){
				spinner.setSelection(i);
			}
		}
	}

	public List<AppKeyValueModel> getDatas() {
		if(datas==null){
			datas = new ArrayList<AppKeyValueModel>();
		}
		return datas;
	}

	public void setDatas(List<AppKeyValueModel> datas) {
		this.datas = datas;
		adapter.setList(getDatas());
	}
	/**
	 * 增加空行，如果model为null则默认置空行
	 * @param datas
	 * @param model
	 */
	public void setDatasWithEmptyLine(List<AppKeyValueModel> datas,AppKeyValueModel model) {
		this.datas = new ArrayList<>();
		if(model==null){
			model = new AppKeyValueModel("","(空)");
		}
		this.datas.add(model);
		this.datas.addAll(datas);
		
		adapter.setList(getDatas());
	}
	
	public AppBaseAdapter getAdapter() {
		return adapter;
	}
	/**
	 * 需先设置datas数据
	 * @param activity
	 */
	public void setDefaultAdapter(AppActivity activity){
//		if(datas==null){
//			throw new AppDevelopError("AppSpinnerModel在调用setDefaultAdapter之前先要调用addDatas方法，来设置下拉选项数据！");
//		}
		//adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item); 
		adapter.setContextAndList(activity, getDatas());
 
		spinner.setAdapter(adapter);
		
	}
//	public void setDefaultAdapter(AppActivity activity){
//		if(datas==null){
//			throw new AppDevelopError("AppSpinnerModel在调用setDefaultAdapter之前先要调用addDatas方法，来设置下拉选项数据！");
//		}
//		
//		ArrayAdapter<DataModel> adapter = new ArrayAdapter<DataModel>(
//				activity, android.R.layout.simple_spinner_item,
//				datas);
//		adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item); 
//		spinner.setAdapter(adapter);  
//		this.adapter = adapter;
//	}
//	public void setAdapter(ArrayAdapter<DataModel> adapter) {
//		this.adapter = adapter;
//	}
	private AppBaseAdapter adapter = new AppBaseAdapter(){
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			super.getView(position, convertView, parent);
			AppKeyValueModel model = getDatas().get(position);
			View view = getInflater().inflate(android.R.layout.simple_list_item_1,parent,false);
			TextView text = (TextView)view.findViewById(android.R.id.text1);
			text.setText(model.getValue().toString());
			return view;
		}
	};



}
