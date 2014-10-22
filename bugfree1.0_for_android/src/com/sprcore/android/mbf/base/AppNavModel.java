package com.sprcore.android.mbf.base;

import java.util.ArrayList;
import java.util.List;

public class AppNavModel<T extends AppNavItemModel> {

	private List<T> itemList = new ArrayList<T>();
	private int currentIndex = 0;
	
	public void addNavItem(T navItem){
		navItem.setParent(this);
		itemList.add(navItem);
	}
	
	public void display(){
		setNavItemActivie(itemList.get(currentIndex));
	}
	
	public void setNavItemActivie(T item){
		for(int i=0,j=itemList.size();i<j;i++){
			if(itemList.get(i).getItemLayoutId()==item.getItemLayoutId()){
				currentIndex = i;
				itemList.get(i).setActive(true);
			}else{
				itemList.get(i).setActive(false);
			}
		}
	}
	
	
	
}
