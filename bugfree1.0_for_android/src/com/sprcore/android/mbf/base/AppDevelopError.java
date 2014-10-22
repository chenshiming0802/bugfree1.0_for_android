package com.sprcore.android.mbf.base;

import android.util.Log;

public class AppDevelopError extends RuntimeException {
	private String message;

	public AppDevelopError(String message) {
		Log.i("AppDevelopError", message);
		this.message = message;
	}
	public AppDevelopError(String message,Exception e) {
		Log.i("AppDevelopError", message);
		this.message = message;
		e.printStackTrace();
	}

	public String getMessage() {
		return message;
	}

}
