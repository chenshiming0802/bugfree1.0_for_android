package com.sprcore.android.mbf.base;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Usage:
 * 	1. 编写类继承AppSQLiteOpenHelper
 * 	2. 构造函数
  			public XXXXSQLiteOpenHelper(Context context,CursorFactory factory) {
				super(context, name, factory, 2);
			}
 *  3. 实现onCreate，参照本类注释
 *  4. 外部调用 myHelper = new XXXXSQLiteOpenHelper(this, "my.db", null);
 * @author chenshiming
 *
 */
public abstract class AppSQLiteOpenHelper extends SQLiteOpenHelper {
	private Context context;
	public Context getContext() {
		return context;
	}

 
	/*
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists hero_info("
				+ "id integer primary key," + "name varchar,"
				+ "level integer)");
	}
	 */
	


	public AppSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	protected Integer insert(ContentValues model, String tableName) {
		SQLiteDatabase db = getWritableDatabase();
		long id = db.insert(tableName, "id", model);
		db.close();
		return Integer.valueOf((int)id);
	}
 
	protected Integer update2(ContentValues model, Integer id, String tableName) {
		SQLiteDatabase db = getWritableDatabase();
		// 更新前，先把model根据数据库的值补全完整
		Cursor cursor = db.rawQuery("select t.* from " + tableName
				+ " t where t.id=" + id, null);
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			for (int i = 0, j = cursor.getColumnCount(); i < j; i++) {
				String key = cursor.getColumnName(i);
				// 如果需要更新的值，则不把数据库的值覆盖
				if (model.containsKey(key)) {
					continue;
				}
				switch (cursor.getType(i)) {
				case Cursor.FIELD_TYPE_STRING:
					model.put(key, cursor.getString(i));
					break;
				case Cursor.FIELD_TYPE_INTEGER:
					model.put(key, cursor.getInt(i));
					break;
				case Cursor.FIELD_TYPE_FLOAT:
					model.put(key, cursor.getFloat(i));
					break;
				case Cursor.FIELD_TYPE_BLOB:
					model.put(key, cursor.getBlob(i));
					break;
				}

			}
			break;
		}
		Integer ret = db.update(tableName, model, "id=" + id, null);
		cursor.close();
		db.close();
		return ret;
	}

	protected List<ContentValues> queryBySql(String sql) {
		List<ContentValues> list = new ArrayList<ContentValues>();
		SQLiteDatabase db = getReadableDatabase();
		Log.i("AppSQLiteOpenHelper#sql", sql);
		Cursor cursor = db.rawQuery(sql, null);
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			ContentValues model = getContentValuesFromCursor(cursor);
			list.add(model);
		}
		cursor.close();
		db.close();
		return list;
	}

	protected ContentValues getRowBySql(String sql) {
		List<ContentValues> list = queryBySql(sql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	protected ContentValues getRowById(Integer id, String tableName) {
		return getRowBySql("select t.* from " + tableName + " t where t.id="
				+ id);
	}
	
	protected void deletedById(Integer id,String tableName){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+tableName+" where id="+id.toString());
		db.close();
	}
	
	public void executeSql(String sql){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(sql);
		db.close();		
	}

	private ContentValues getContentValuesFromCursor(Cursor cursor) {
		ContentValues model = new ContentValues();
		for (int i = 0, j = cursor.getColumnCount(); i < j; i++) {
			String key = cursor.getColumnName(i);
			switch (cursor.getType(i)) {
			case Cursor.FIELD_TYPE_STRING:
				model.put(key, cursor.getString(i));
				break;
			case Cursor.FIELD_TYPE_INTEGER:
				model.put(key, Integer.valueOf(cursor.getInt(i)));
				break;
			case Cursor.FIELD_TYPE_FLOAT:
				model.put(key, Float.valueOf(cursor.getInt(i)));
				break;
			case Cursor.FIELD_TYPE_BLOB:
				model.put(key, cursor.getBlob(i));
				break;
			}
		}
		return model;
	}

}
