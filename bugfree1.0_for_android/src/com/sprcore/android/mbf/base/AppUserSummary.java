package com.sprcore.android.mbf.base;

/**
 * usersmmary的父类
 * @author chenshiming
 *
 * @param <T>
 */
public abstract class AppUserSummary {
	private String userName;
	private String userRealName;
	private String userEmail;
	private String ucore1;
 

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUcore1() {
		return ucore1;
	}

	public void setUcore1(String ucore1) {
		this.ucore1 = ucore1;
	}

 

	 
	

}
