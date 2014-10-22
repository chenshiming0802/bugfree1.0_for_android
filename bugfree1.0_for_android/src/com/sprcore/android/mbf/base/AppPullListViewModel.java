package com.sprcore.android.mbf.base;

public abstract class AppPullListViewModel {
	//可选传入参数
	public final static int PAGE_SIZE = 8;
	
	public final static int LISTVIEW_ACTION_NULL = 0x01;//初始状态
	public final static int LISTVIEW_ACTION_REFRESH = 0x02;//刷新动作
	public final static int LISTVIEW_ACTION_SCROLL = 0x03;//滑动动作
	public final static int LISTVIEW_ACTION_LOADMORE = 0x04;//读取更多

	public final static int LISTVIEW_DATA_MORE = 0x01;//应该还有更多数据可以读取
	public final static int LISTVIEW_DATA_LOADING = 0x02;//正在加载数据状态
	public final static int LISTVIEW_DATA_FULL = 0x03;//数据已经全部下载完毕的状态
	public final static int LISTVIEW_DATA_EMPTY = 0x04;//当前没有数据的状态
	
	public interface onLoadListener{
		/**
		 * 刷新数据-往上滑动到顶，运行刷新
		 */
		public void onRefresh();
		/**
		 * 刷新数据-往下运行到底，读取更多数据
		 */
		public void onMoreLoad(int pageIndex);
	}
 

}
