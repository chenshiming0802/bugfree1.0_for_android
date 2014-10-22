package com.sprcore.android.mbf.helper.model;

import com.sprcore.android.mbf.base.AppSpPageModel;

public class QueryUsersSpModel extends AppSpPageModel{
	private String queryString;

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
}