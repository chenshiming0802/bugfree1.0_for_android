package com.sprcore.android.mbf.base;

/**
 * key和value的model，用于spinner和localModel的字典
 * @author chenshiming
 *
 */
public   class AppKeyValueModel {
 
	public AppKeyValueModel(String key,String value){
		this.key = key;
		this.value = value;
	}
	private String key;
	private Object value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}