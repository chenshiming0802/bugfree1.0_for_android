package com.sprcore.android.mbf.local;

import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppLocal;
import com.sprcore.android.mbf.base.AppSQLiteOpenHelper;
import com.sprcore.android.mbf.local.model.CommonReply;

public class CommonReplyLocal extends AppLocal<CommonReply> {

	protected AppSQLiteOpenHelper sqLiteOpenHelper() {
		return new DataBase(AppActivity.getCurrentActivity(),null);
	}	
	
	
 

}
