package com.sprcore.android.mbf.helper.model;

import java.util.List;

import com.sprcore.android.mbf.base.AppSrModel;

public class GetBuginfoSrModel extends AppSrModel{
	private String bugTitle;
	private String moduleName;
	private String projectId;
	private String projectName;
	private String bugId;
	private String assignedTo;
	private String assignedToRealUserName;
	private String bugStatus;
	private String openedBy;
	private String openedByRealUserName;
	private String openedDate;
	private String fixedTime;
	private String lastEditedDate;
	private String bugType;
	private String bugSeverity;
	
	private List<BugHistoryModel> comments;
	
	
	public String getBugTitle() {
		return bugTitle;
	}


	public String getBugType() {
		return bugType;
	}


	public void setBugType(String bugType) {
		this.bugType = bugType;
	}


	public String getBugSeverity() {
		return bugSeverity;
	}


	public void setBugSeverity(String bugSeverity) {
		this.bugSeverity = bugSeverity;
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


	public String getBugStatus() {
		return bugStatus;
	}


	public void setBugStatus(String bugStatus) {
		this.bugStatus = bugStatus;
	}


 

 




	public List<BugHistoryModel> getComments() {
		return comments;
	}


	public void setComments(List<BugHistoryModel> comments) {
		this.comments = comments;
	}









	public static class BugHistoryModel{
		private String bugId;
		private String userName;
		private String action;
		private String fullInfo;
		private String actionDate;
		public String getBugId() {
			return bugId;
		}
		public void setBugId(String bugId) {
			this.bugId = bugId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public String getFullInfo() {
			return fullInfo;
		}
		public void setFullInfo(String fullInfo) {
			this.fullInfo = fullInfo;
		}
		public String getActionDate() {
			return actionDate;
		}
		public void setActionDate(String actionDate) {
			this.actionDate = actionDate;
		}
		
		
	}
}