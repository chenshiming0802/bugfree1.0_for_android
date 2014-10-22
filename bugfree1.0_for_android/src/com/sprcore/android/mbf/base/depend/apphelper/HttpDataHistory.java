package com.sprcore.android.mbf.base.depend.apphelper;

import java.util.List;
import java.util.Map;

import com.sprcore.android.mbf.base.AppKeyValueModel;
import com.sprcore.android.mbf.base.AppLocalModel;

public class HttpDataHistory extends AppLocalModel{
	
	
	
	@Override
	public Map<String, List<AppKeyValueModel>> dicts() {
		return null;
	}
	@Override
	public List initData() {
		return null;
	}

	
	private Integer id;
	private String urlKey;
	private String urlData;
	private Integer updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrlKey() {
		return urlKey;
	}
	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}
	public String getUrlData() {
		return urlData;
	}
	public void setUrlData(String urlData) {
		this.urlData = urlData;
	}
	public Integer getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}
//	@Override
//	public String tableName() {
//		return "HttpDataHistory";
//	}
//	@Override
//	public String createTableSql() {
//		return "create table if not exists HttpDataHistory("
//				+ "id integer primary key," 
//				+ "urlKey varchar,"
//				+ "urlData varchar,"
//				+ "updateTime integer)";
//	}	
	
	
	
	
}
