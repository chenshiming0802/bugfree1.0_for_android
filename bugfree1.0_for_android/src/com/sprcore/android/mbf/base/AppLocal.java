package com.sprcore.android.mbf.base;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sprcore.android.mbf.local.DataBase;

import android.content.ContentValues;

/**
 * 将AppSQLiteOpenHelper抽象对象化方法
 * 使用方法：
	protected AppSQLiteOpenHelper sqLiteOpenHelper() {
		return new XXXSQLiteOpenHelper(AppActivity.getCurrentActivity(),null);
	}	
 * @author chenshiming
 *
 * @param <T>
 */
public abstract class AppLocal<T  extends AppLocalModel> {
 
	protected abstract AppSQLiteOpenHelper sqLiteOpenHelper() ;
	
	private AppSQLiteOpenHelper sqLiteOpenHelper;
	private AppSQLiteOpenHelper getAppSQLiteOpenHelper(){
		if(sqLiteOpenHelper==null){
			sqLiteOpenHelper = sqLiteOpenHelper();
		}
		return sqLiteOpenHelper;
	}
	
	public Integer save(T model){
		try {
			ContentValues values = convertModel2ContentValues(model);
			return getAppSQLiteOpenHelper().insert(values, model.tableName());			
		} catch (Exception e) {
			throw new AppException(e);	
		}
	}
	
	public Integer update2(T model,Integer id){
		try {
			ContentValues values = convertModel2ContentValues(model);
			return getAppSQLiteOpenHelper().update2(values, id, model.tableName());			
		} catch (Exception e) {
			throw new AppException(e);	
		}
	}
	/**
	 * 查询单表，如果查询多表，则使用getAppSQLiteOpenHelper().queryBySql(sql)
	 * @param sql
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	protected List<T> queryBySql(String sql,Class<T> cls) {
		try {			
			List<ContentValues> list = getAppSQLiteOpenHelper().queryBySql(sql);
			List<T> list2 = new ArrayList<T>();
			for(int i=0,j=list.size();i<j;i++){
				list2.add(convertContentValues2Model(list.get(i),cls));	
			}
			return list2;
		} catch (Exception e) {
			throw new AppException(e);	
		}
	}
	protected T getRowBySql(String sql,Class<T> cls) {
		try {			
			ContentValues values = getAppSQLiteOpenHelper().getRowBySql(sql);
			return convertContentValues2Model(values,cls);	
		} catch (Exception e) {
			throw new AppException(e);	
		}
	}
	public T getRowById(Integer id,Class<T> cls)  {
		try {		
			T obj = (T)cls.newInstance();
			ContentValues values = getAppSQLiteOpenHelper().getRowById(id,obj.tableName());
			return convertContentValues2Model(values,cls);
		} catch (Exception e) {
			throw new AppException(e);			
		}
	}
	public void deleteById(Integer id,Class<T> cls) {
		try {
			T obj = (T)cls.newInstance();
			getAppSQLiteOpenHelper().deletedById(id, obj.tableName());		
		} catch (Exception e) {
			throw new AppException(e);	
		}
	}
	
	public List<T> queryAllList(Class<T> cls,String orderBy){
		try {
			T obj = (T)cls.newInstance();
			StringBuffer sb = new StringBuffer();
			sb.append("select t.* from ").append(obj.tableName()).append(" t order by t.id "+orderBy);
			return queryBySql(sb.toString(), cls);
		} catch (Exception e) {
			throw new AppException(e);	
		}		 
	}
	
	public void executeSql(String sql){
		try {
			getAppSQLiteOpenHelper().executeSql(sql);
		} catch (Exception e) {
			throw new AppException(e);	
		}
	}
	
	
	private T convertContentValues2Model(ContentValues values,Class<T> cls)
			throws Exception {		
		if(values==null){
			return null;
		}
		T model = cls.newInstance();
		Iterator<String> it = values.keySet().iterator();
		while(it.hasNext()){
			String fieldName = it.next();
			Object value = values.get(fieldName);	
			Method method = cls.getMethod("set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1),value.getClass() );
			method.invoke(model, value);
		}
		return model;
	}
	
	private ContentValues convertModel2ContentValues(T model)  throws Exception{
		if(model==null){
			return null;
		}
		ContentValues values = new ContentValues();
		Method[] methods = model.getClass().getMethods();
		if(methods!=null){
			for(int i=0,j=methods.length;i<j;i++){
				Method m = methods[i];
				if(m.getParameterTypes().length==0 && m.getName().startsWith("get") ){
					String fieldName = m.getName().substring(3);
					fieldName = fieldName.substring(0,1).toLowerCase()+fieldName.substring(1);
					Object obj = m.invoke(model, null);
					if(obj!=null){
						if (obj instanceof Integer) {
							Integer obj2 = (Integer) obj;
							values.put(fieldName, obj2);	
						}else if (obj instanceof Float) {
							Float obj2 = (Float) obj;
							values.put(fieldName, obj2);	
						}else if (obj instanceof String) {
							String obj2 = (String) obj;
							values.put(fieldName, obj2);	
						}	
					}
					
				}
			}
		}	
		return values;
	}
}
