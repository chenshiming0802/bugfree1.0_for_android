package com.sprcore.android.mbf.base;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

/**
 * 数据库model，仅支持 String , Integer 和 Float型
 * @author chenshiming
 *
 */
public abstract class AppLocalModel {
	/**
	 * 字典信息，返回<字段，字典清单>
	 * usage:
		Map<String, List<AppKeyValueModel>> map = new HashMap<String, List<AppKeyValueModel>>();

		List<AppKeyValueModel> type = new ArrayList<AppKeyValueModel>();
		type.add(new AppKeyValueModel("editbug", "更新事件"));
		type.add(new AppKeyValueModel("fixbug", "解决事件"));
		map.put("type", type);

		return map;  
	 * @return
	 */
	public abstract Map<String,List<AppKeyValueModel>> dicts();
	/**
	 * 根据字段名、字典名获取字典显示值
	 * @param fieldName
	 * @param dictKey
	 * @return
	 */
	public String dictValue(String fieldName,String dictKey){
		Map<String,List<AppKeyValueModel>> map = dicts();
		if(map!=null && map.containsKey(fieldName)){
			List<AppKeyValueModel> list = map.get(fieldName);
			for(int i=0,j=list.size();i<j;i++){
				AppKeyValueModel model = list.get(i);
				if(model.getKey().equals(dictKey)){
					return (String)model.getValue();
				}
			}
		}
		return null;
	}
	//获取对应的表名
	public String tableName(){
		return this.getClass().getSimpleName();
	}
	public static Integer currentTime(){
		return Integer.valueOf((int)System.currentTimeMillis()/1000);
	}
	/**
	 * 初始数据，返回List<当前Model>
	 * @return
	 */
	public abstract List initData();
	public String initDataSql(){
		StringBuffer allSql = new StringBuffer();
		List list = initData();
		if(list!=null){
			String rowsSep = "";
			for(int i=0,j=list.size();i<j;i++){
				try {					
					AppLocalModel model = (AppLocalModel)list.get(i);
					StringBuffer sb = new StringBuffer();
					sb.append("insert into ").append(tableName()).append(" ( ");
					StringBuffer sb2 = new StringBuffer();
					sb2.append("( ");
					
					String sep = "";
					Method[] methods = this.getClass().getMethods();
					for (int ii = 0, jj = methods.length; ii < jj; ii++) {
						Method m = methods[ii];
						if (m.getParameterTypes().length == 0 && m.getName().startsWith("get")) {
							String fieldName = m.getName().substring(3);
							fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
							Object obj = m.getReturnType();
							Object value = m.invoke(model, new Object[]{});
							if (fieldName.equals("id")) {
								continue;
							}
							if (obj.equals(Integer.class)) {
								sb.append(sep).append(fieldName);
								sb2.append(sep).append(value);
								sep = ",";	
							}else if (obj.equals(Float.class)) {
								sb.append(sep).append(fieldName);
								sb2.append(sep).append(value);
								sep = ",";	
							}else if (obj.equals(String.class)) {
								String value2 = (String)value;
								sb.append(sep).append(fieldName);
								sb2.append(sep).append("'").append(value2.replaceAll("'", "''")).append("'");								
								sep = ",";		
							}else{
								continue;
							} 
						}
					}		
					sb.append(" ) values");	
					sb2.append(" )");
 
					if(i==0){
						allSql.append(sb);
					}
					allSql.append(rowsSep).append(sb2);
					rowsSep = ",";
					
				} catch (Exception e) {
					throw new AppDevelopError(this.getClass()+" Init Data error");
				}
					
			}
		} 
		Log.i("initDataSql",allSql.toString());
		return allSql.toString();
	}
 
	public String createTableSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table if not exists ").append(tableName()).append("(");
		String sep = "";
		Method[] methods = this.getClass().getMethods();
		for (int i = 0, j = methods.length; i < j; i++) {
			Method m = methods[i];
			if (m.getParameterTypes().length == 0 && m.getName().startsWith("get")) {
				String fieldName = m.getName().substring(3);
				fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
				Object obj = m.getReturnType();
				if (obj.equals(Integer.class)) {
					sb.append(sep+" " + fieldName + " integer ");sep = ",";
				}else if (obj.equals(Float.class)) {
					sb.append(sep+" " + fieldName + " float ");sep = ",";
				}else if (obj.equals(String.class)) {
					sb.append(sep+" " + fieldName + " varchar ");sep = ",";
				}
				
				if (fieldName.equals("id")) {
					sb.append(" primary key ");
				}
				
			}
		}
		sb.append(")");
		String ret = sb.toString();
		Log.i("AppLocalModel#createSql", ret);
		return ret;
	}


 
}
