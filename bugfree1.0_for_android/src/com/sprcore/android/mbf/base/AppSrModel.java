package com.sprcore.android.mbf.base;

/**
 * 注意点：<BR>
 * 1. 如果内嵌model，则定义在该Model中，见QueryServiceListSrModel<BR>
 * @author chenshiming
 *
 */
public class AppSrModel {
	private String resultFlag;
	private String resultMessage;
	private String resultMessageCode;
	public String getResultMessageCode() {
		return resultMessageCode;
	}
	public void setResultMessageCode(String resultMessageCode) {
		this.resultMessageCode = resultMessageCode;
	}
	public String getResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	
	
}
