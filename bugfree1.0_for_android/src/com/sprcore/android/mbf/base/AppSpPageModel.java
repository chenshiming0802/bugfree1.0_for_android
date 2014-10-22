package com.sprcore.android.mbf.base;

/**
 * 分页的SpModel
 * @author chenshiming
 *
 */
public class AppSpPageModel extends AppSpModel{
	private Long pageSize;
	private Long pageIndex;
	
	public Long getPageSize() {
		return pageSize;
	}
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}
	public Long getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Long pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	
 

}
