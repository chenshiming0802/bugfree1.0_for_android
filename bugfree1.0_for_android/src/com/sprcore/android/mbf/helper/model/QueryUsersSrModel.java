package com.sprcore.android.mbf.helper.model;

import java.util.List;

import com.sprcore.android.mbf.base.AppSrModel;

public class QueryUsersSrModel extends AppSrModel{
	private List<UserModel> datas;
	
	public List<UserModel> getDatas() {
		return datas;
	}

	public void setDatas(List<UserModel> datas) {
		this.datas = datas;
	}

	public static class UserModel{
		private String userName;
		private String realName;
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
	}
	
}