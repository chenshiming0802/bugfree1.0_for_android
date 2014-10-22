package com.sprcore.android.mbf.local.model;

import java.util.List;
import java.util.Map;

import com.sprcore.android.mbf.base.AppKeyValueModel;
import com.sprcore.android.mbf.base.AppLocalModel;

public class UserHistory extends AppLocalModel{
	public Map<String, List<AppKeyValueModel>> dicts(){
		return null;
	}
	
	@Override
	public List initData() {
		return null;
	}

	private Integer id;
	private String userName;
	private String realName;
	private Integer lastTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getLastTime() {
		return lastTime;
	}
	public void setLastTime(Integer lastTime) {
		this.lastTime = lastTime;
	}
	
//	public String tableName(){
//		return this.getClass().getSimpleName();
//	}
//	@Override
//	public String createTableSql() {
//		return "create table if not exists UserHistory("
//				+ "id integer primary key," 
//				+ "userName varchar,"
//				+ "realName varchar,"
//				+ "lastTime integer)";
//	}
//	

}
