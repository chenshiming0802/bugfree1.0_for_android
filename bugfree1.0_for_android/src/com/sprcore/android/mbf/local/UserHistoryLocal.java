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
	
	
	public List<UserHistory> queryUserHistory() {
		return (List<UserHistory>) queryBySql(
				"select t.* from UserHistory t order by t.lastTime desc",
				UserHistory.class); 
	}

	/**
	 * 新增或修改员工历史记录
	 * @param model
	 * @throws Exception
	 */
	public void createOrUpdateUserHistory(UserHistory model) {
		UserHistory userHistory = getRowBySql(
				"select t.* from UserHistory t where t.userName='"
						+ model.getUserName() + "'", UserHistory.class);
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
	public void deleteUserHistory(){
		List<UserHistory> userHistories = queryUserHistory();
		for(int i=0,j=userHistories.size();i<j;i++){
			deleteById(userHistories.get(i).getId(),UserHistory.class);
		}
	}

}
