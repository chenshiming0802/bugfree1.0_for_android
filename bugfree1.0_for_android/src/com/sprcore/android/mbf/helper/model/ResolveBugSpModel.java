package com.sprcore.android.mbf.helper.model;

import com.sprcore.android.mbf.base.AppSpModel;

public class ResolveBugSpModel extends AppSpModel{
	private String bugId;
//	private String userName;
	private String resolution;
	private String notes;
	public String getBugId() {
		return bugId;
	}
	public void setBugId(String bugId) {
		this.bugId = bugId;
	}
//	public String getUserName() {
//		return userName;
//	}
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
}