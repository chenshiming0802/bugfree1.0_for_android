package com.sprcore.android.mbf.helper.model;

import java.util.List;

import com.sprcore.android.mbf.base.AppSrModel;

public class QueryServiceListSrModel extends AppSrModel {
	private List<BuginfoModel> datas;
		
	
 
	public List<BuginfoModel> getDatas() {
		return datas;
	}
	public void setDatas(List<BuginfoModel> datas) {
		this.datas = datas;
	}

	public static class BuginfoModel {
		private String bugTitle;
		private String moduleName;
		private String projectId;
		private String projectName;
		private String bugId;
		private String bugContent;
		private String assignedTo;
		private String assignedToRealUserName;
		private String assignedDate;
		private String bugStatus;
		private String openedBy;
		private String openedByRealUserName;
		private String openedDate;
		private String fixedTime;
		private String lastEditedDate;
		public String getBugTitle() {
			return bugTitle;
		}
		public String getOpenedBy() {
			return openedBy;
		}
		public void setOpenedBy(String openedBy) {
			this.openedBy = openedBy;
		}
		public String getOpenedByRealUserName() {
			return openedByRealUserName;
		}
		public void setOpenedByRealUserName(String openedByRealUserName) {
			this.openedByRealUserName = openedByRealUserName;
		}
		public String getOpenedDate() {
			return openedDate;
		}
		public void setOpenedDate(String openedDate) {
			this.openedDate = openedDate;
		}
		public String getFixedTime() {
			return fixedTime;
		}
		public void setFixedTime(String fixedTime) {
			this.fixedTime = fixedTime;
		}
		public String getLastEditedDate() {
			return lastEditedDate;
		}
		public void setLastEditedDate(String lastEditedDate) {
			this.lastEditedDate = lastEditedDate;
		}
		public void setBugTitle(String bugTitle) {
			this.bugTitle = bugTitle;
		}
		public String getModuleName() {
			return moduleName;
		}
		public void setModuleName(String moduleName) {
			this.moduleName = moduleName;
		}
		public String getProjectId() {
			return projectId;
		}
		public void setProjectId(String projectId) {
			this.projectId = projectId;
		}
		public String getProjectName() {
			return projectName;
		}
		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
		public String getBugId() {
			return bugId;
		}
		public void setBugId(String bugId) {
			this.bugId = bugId;
		}
		public String getBugContent() {
			return bugContent;
		}
		public void setBugContent(String bugContent) {
			this.bugContent = bugContent;
		}
		public String getAssignedTo() {
			return assignedTo;
		}
		public void setAssignedTo(String assignedTo) {
			this.assignedTo = assignedTo;
		}
		public String getAssignedToRealUserName() {
			return assignedToRealUserName;
		}
		public void setAssignedToRealUserName(String assignedToRealUserName) {
			this.assignedToRealUserName = assignedToRealUserName;
		}
		public String getAssignedDate() {
			return assignedDate;
		}
		public void setAssignedDate(String assignedDate) {
			this.assignedDate = assignedDate;
		}
		public String getBugStatus() {
			return bugStatus;
		}
		public void setBugStatus(String bugStatus) {
			this.bugStatus = bugStatus;
		}	
	}
}
