package com.sprcore.android.mbf.helper.model;

import java.io.File;

import com.sprcore.android.mbf.base.AppSpModel;

public class UploadServiceAttachmentSpModel extends AppSpModel{
	private String bugId;
	private File BugFileName;
	public String getBugId() {
		return bugId;
	}
	public void setBugId(String bugId) {
		this.bugId = bugId;
	}
	public File getBugFileName() {
		return BugFileName;
	}
	public void setBugFileName(File bugFileName) {
		BugFileName = bugFileName;
	}
	
	
}