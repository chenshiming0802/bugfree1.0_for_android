package com.sprcore.android.mbf.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppSessionLocal;
import com.sprcore.android.mbf.local.model.UserSummary;

public class UserSessionLocal extends AppSessionLocal{
	private static UserSummary currentUser = null;
	
	/**
	 * 是否当前登录状态
	 * @param context
	 * @return
	 */
	public boolean hasUserLogin(Context context){
		UserSummary user = loadCurrentUser(context);
		if(user!=null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 保存当前用户信息
	 * @param content
	 * @param user
	 */
	public void saveCurrentUser(Context content,UserSummary user){
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(content).edit();
		editor.putString("userName", user.getUserName());
		editor.putString("userEmail", user.getUserEmail());
		editor.putString("userRealName", user.getUserRealName());
		editor.putString("ucore1", user.getUcore1());
		editor.commit();	
	}
	/**
	 * 读取/加载当前用户信息
	 * @param context
	 * @return
	 */
	public UserSummary loadCurrentUser(Context context){
		if(currentUser==null || currentUser.getUserName()==null || currentUser.getUcore1().trim().length()==0){
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(context);
			currentUser = new UserSummary();
			currentUser.setUserName(prefs.getString("userName", null));
			currentUser.setUserEmail(prefs.getString("userEmail", null));
			currentUser.setUserRealName(prefs.getString("userRealName", null));
			currentUser.setUcore1(prefs.getString("ucore1", null));
			if(currentUser.getUserName()==null || currentUser.getUcore1().trim().length()==0){
				return null;
			}
		}
		AppActivity.setUserSummary(currentUser);
		return currentUser;
	}
	public String loadCurrentUserName(Context context){
		UserSummary userModel = loadCurrentUser(context);
		if(userModel!=null){
			return userModel.getUserName();
		}else{
			return null;
		}
	}
	
	/**
	 * 清空当前用户信息
	 * @param context
	 */
	public void clearCurrentUser(Context context){
		currentUser = null;
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putString("userName", null);
		editor.putString("userEmail", null);
		editor.putString("userRealName", null);
		editor.putString("ucore1", null);
		editor.commit();	
		AppActivity.setUserSummary(null);
	}
}
