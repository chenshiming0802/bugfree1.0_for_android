package com.sprcore.android.mbf.helper;

import com.sprcore.android.mbf.base.AppHelper;
import com.sprcore.android.mbf.helper.model.DoCheckLoginSpModel;
import com.sprcore.android.mbf.helper.model.DoCheckLoginSrModel;
import com.sprcore.android.mbf.helper.model.QueryUsersSpModel;
import com.sprcore.android.mbf.helper.model.QueryUsersSrModel;

public class UserHelper extends AppHelper {
	public QueryUsersSrModel queryUsers(QueryUsersSpModel spModel) {
		QueryUsersSrModel srModel = (QueryUsersSrModel) httpPost(
				"queryUsers2.php", spModel, QueryUsersSrModel.class,true);
		return srModel;
	}

	/**
	 * 帐号登录校验
	 * @param spModel
	 * @return
	 */
	public DoCheckLoginSrModel doCheckLogin(DoCheckLoginSpModel spModel) {
		DoCheckLoginSrModel srModel = (DoCheckLoginSrModel) httpPost(
				"dologin2.php", spModel, DoCheckLoginSrModel.class, false);
		return srModel;
	}
}
