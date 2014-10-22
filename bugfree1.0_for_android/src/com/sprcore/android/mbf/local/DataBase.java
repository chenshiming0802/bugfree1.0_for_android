package com.sprcore.android.mbf.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.sprcore.android.mbf.base.AppSQLiteOpenHelper;
import com.sprcore.android.mbf.local.model.CommonReply;
import com.sprcore.android.mbf.local.model.UserHistory;

public class DataBase extends AppSQLiteOpenHelper {
 
	public DataBase(Context context, CursorFactory factory) {
		super(context, "mbf.db", factory, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//历史记录的员工信息
		db.execSQL(new UserHistory().createTableSql());  //最近访问时间
		db.execSQL(new CommonReply().createTableSql());  //常见回复
		db.execSQL(new CommonReply().initDataSql()); 
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

}
