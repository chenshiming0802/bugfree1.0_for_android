package com.sprcore.android.mbf.local.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sprcore.android.mbf.base.AppKeyValueModel;
import com.sprcore.android.mbf.base.AppLocalModel;

/**
 * 用户存储搜索的历史数据，非user
 * @author chenshiming
 *
 */
public class UserHistory extends AppLocalModel{
	public final static String TYPE_USER = "user";
	public final static String TYPE_PROJECTMODULE = "projectmodule";
	@Override
	public Map<String, List<AppKeyValueModel>> dicts() {
		Map<String, List<AppKeyValueModel>> map = new HashMap<String, List<AppKeyValueModel>>();

		List<AppKeyValueModel> type = new ArrayList<AppKeyValueModel>();
		type.add(new AppKeyValueModel(TYPE_USER, "员工"));
		type.add(new AppKeyValueModel(TYPE_PROJECTMODULE, "所属项目"));
		map.put("type", type);

		return map;
	}
	
	@Override
	public List initData() {
		return null;
	}

	private Integer id;
	private String userName;
	private String realName;
	private String type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
