package com.sprcore.android.core.tools;

import com.sprcore.android.mbf.base.AppDevelopError;

public class DevelopTools {

	public static void assertNotNull(Object obj,String message){
		if(obj==null){
			throw new AppDevelopError(message);
		}
	}

}
