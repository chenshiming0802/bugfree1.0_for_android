package com.sprcore.android.mbf.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.view.View;
import android.view.ViewGroup;

public class AppSwitchViewModel {
	private Map<String, View> viewMap = new HashMap<String, View>();

	// public void addView(int viewId,View view){
	// viewMap.put(viewId+"", view);
	// }
	public void AddChildrenFromParent(View parent) {
		ViewGroup parent2 = (ViewGroup)parent;
		for (int i = 0, j = parent2.getChildCount(); i < j; i++) {
			View child = parent2.getChildAt(i);
			viewMap.put(child.getId() + "", child);
		}
	}

	public void display(int viewId) {
		Iterator<String> it = viewMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			View value = viewMap.get(key);
			if (value != null) {
				if(key.equals(viewId+"")){
					value.setVisibility(View.VISIBLE);
				}else{
					value.setVisibility(View.GONE);
				}
			}
		}
	}

}
