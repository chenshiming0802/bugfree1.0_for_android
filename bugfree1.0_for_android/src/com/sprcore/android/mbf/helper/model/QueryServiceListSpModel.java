package com.sprcore.android.mbf.helper.model;

import com.sprcore.android.mbf.base.AppSpPageModel;

public class QueryServiceListSpModel extends AppSpPageModel {
//	private String userName;
	//是否指派给我
	private String isAssignMe;
	//是否我创建
	private String isMeCreate;
	//自定义查询条件ID
	private String queryId;
	//查询关键字
	private String queryString;
	
	
	public String getIsAssignMe() {
		return isAssignMe;
	}
	public void setIsAssignMe(String isAssignMe) {
		this.isAssignMe = isAssignMe;
	}
	public String getIsMeCreate() {
		return isMeCreate;
	}
	public void setIsMeCreate(String isMeCreate) {
		this.isMeCreate = isMeCreate;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
//	public String getUserName() {
//		return userName;
//	}
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
 
	
	

}
