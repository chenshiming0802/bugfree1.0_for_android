package com.sprcore.android.mbf.base.depend.apphelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.sprcore.android.mbf.base.AppSQLiteOpenHelper;

public class HistorySQLiteOpenHelper extends AppSQLiteOpenHelper{
	public HistorySQLiteOpenHelper(Context context, CursorFactory factory) {
		super(context, "httphistory.db", factory, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		//历史记录的员工信息
		db.execSQL(new HttpDataHistory().createTableSql());  //最近访问时间
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
}
