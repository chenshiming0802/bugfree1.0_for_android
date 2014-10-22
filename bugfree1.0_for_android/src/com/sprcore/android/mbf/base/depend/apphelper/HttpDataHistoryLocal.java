package com.sprcore.android.mbf.base.depend.apphelper;

import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppLocal;
import com.sprcore.android.mbf.base.AppSQLiteOpenHelper;

public class HttpDataHistoryLocal extends AppLocal<HttpDataHistory> {
	
	protected AppSQLiteOpenHelper sqLiteOpenHelper() {
		return new HistorySQLiteOpenHelper(AppActivity.getCurrentActivity(),null);
	}	
	
	
	/**
	 * 根据Url信息，保存还是更新
	 * @param model
	 * @throws Exception
	 */
	public void saveOrUpdate(HttpDataHistory model)  {
		HttpDataHistory dbModel = getRowBySql(
				"select t.* from HttpDataHistory t where t.urlKey='"
						+ model.getUrlKey() + "'", HttpDataHistory.class);
		if (dbModel == null) {
			save(model);
		} else {
			update2(model, dbModel.getId());
		}
	}
	
	public HttpDataHistory getRowByUrlKey(String urlKey) {
		return getRowBySql("select t.* from HttpDataHistory t where t.urlKey='"+urlKey+"'", HttpDataHistory.class);
	}
 
	public void deleteAll(){
		executeSql("delete from HttpDataHistory");
	}
}
