package com.sprcore.android.mbf.helper.model;

import com.sprcore.android.mbf.base.AppSpPageModel;

public class QueryUsersSpModel extends AppSpPageModel{
	private String queryString;
	private String type;

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}