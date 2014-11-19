package com.sprcore.android.mbf.local;

import java.util.List;

import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppLocal;
import com.sprcore.android.mbf.base.AppSQLiteOpenHelper;
import com.sprcore.android.mbf.local.model.UserHistory;

public class UserHistoryLocal extends AppLocal<UserHistory> {

	@Override
	protected AppSQLiteOpenHelper sqLiteOpenHelper() {
		return new DataBase(AppActivity.getCurrentActivity(),null);
	}
	
	
	public List<UserHistory> queryUserHistory(String type) {
		return (List<UserHistory>) queryBySql(
				"select t.* from UserHistory t where t.type='"+type+"' order by t.lastTime desc",
				UserHistory.class); 
	}

	/**
	 * 新增或修改员工历史记录
	 * @param model
	 * @throws Exception
	 */
	public void createOrUpdateUserHistory(String type,UserHistory model) {
		model.setType(type);
		UserHistory userHistory = getRowBySql(
				"select t.* from UserHistory t where t.userName='"
						+ model.getUserName() + "' and t.type='"+type+"'", UserHistory.class);
		if (userHistory == null) {
			save(model);
		} else {
			update2(model, userHistory.getId());
		}
	}
	
	/**
	 * 删除员工历史数据
	 * @throws Exception
	 */
	public void deleteUserHistory(String type){
		List<UserHistory> userHistories = queryUserHistory(type);
		for(int i=0,j=userHistories.size();i<j;i++){
			deleteById(userHistories.get(i).getId(),UserHistory.class);
		}
	}

	
	public void deleteAll(){
		deleteUserHistory(UserHistory.TYPE_USER);
		deleteUserHistory(UserHistory.TYPE_PROJECTMODULE);
	}
}
