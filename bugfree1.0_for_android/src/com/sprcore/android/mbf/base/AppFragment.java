package com.sprcore.android.mbf.base;

 
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sprcore.android.core.tools.PhoneTools;

public abstract class AppFragment extends Fragment {
	protected LayoutInflater mInflater;  
 
	public AppFragment getCurrentFragment(){
		return this;
	}
	/**
	 * @return
	 */
	public AppActivity getBaseActivity(){ 
		return (AppActivity)getActivity();
	}
 
	
	public View findViewById(int id){
		return getView().findViewById(id);
	}
	public void showToast(String message){
		getBaseActivity().showToast(message);
	}
	
	public static boolean IS_CURRENT_NETWORD_STATUS = false;
	public static boolean IS_LAST_NETWORD_STATUS = false;
	
	protected abstract void onCreate(Bundle savedInstanceState,Bundle intentExtras);
	@Override
	public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IS_CURRENT_NETWORD_STATUS = PhoneTools.checkNetWork(getActivity());
        if(IS_CURRENT_NETWORD_STATUS!=IS_LAST_NETWORD_STATUS){
        	if(IS_CURRENT_NETWORD_STATUS){
        		//联网模式
        		showToast("联网模式");
        	}else{
        		//本地模式
        		showToast("本地模式");
        	}
        }
        IS_LAST_NETWORD_STATUS = IS_CURRENT_NETWORD_STATUS;
//        Bundle intentExtras = null;
        Bundle intentExtras = getArguments();
        //extras = getActivity().getIntent().getExtras();
        if(intentExtras==null){
        	intentExtras = new Bundle();
        }
		onCreate(savedInstanceState,intentExtras);
	}	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,
			 Bundle savedInstanceState) {
		this.mInflater = inflater;
		return super.onCreateView(inflater, container, savedInstanceState);
	}	
	
 
 	
	
	
	private ProgressDialog pBar;
	public void loading(boolean isLoad){
		if (isLoad) {
			pBar = ProgressDialog.show(getBaseActivity(), null, "Please wait...", true, true);
		}else{
			pBar.dismiss();
		}
	}

	public void setTextTypeface(int id){
		TextView textView = (TextView)findViewById(id);
		textView.setTypeface(AppActivity.TYPE_FACE);
	}
	
//	private Map viewVisiableMap = new HashMap<>();
//	public void loading(boolean isLoad) {
//		View loadUi = getView().findViewById(R.id.loadui);
//		LinearLayout headUi = (LinearLayout)getView().findViewById(R.id.headUi);
//		ViewParent topui = headUi.getParent();
//		if (isLoad) {
			//loading，则隐藏除了header以外的所有viewGroup
//			if (topui instanceof ViewGroup) {
//				ViewGroup topui2 = (ViewGroup) topui;
//				for (int i = 0, j = topui2.getChildCount(); i < j; i++) {
//					View child = topui2.getChildAt(i);
//					if (child.getId()==headUi.getId()){	
//						continue;
//					}
//					viewVisiableMap.put(child.getId(), child.getVisibility());
//					child.setVisibility(View.GONE);
//				}
//			}
//			loadUi.setVisibility(View.VISIBLE);	
//		} else {
//			//loading，则还原显示所有viewGroup
//			if (topui instanceof ViewGroup) {
//				ViewGroup topui2 = (ViewGroup) topui;
//				for (int i = 0, j = topui2.getChildCount(); i < j; i++) {
//					View child = topui2.getChildAt(i);
//					if (child.getId()==headUi.getId()){	
//						continue;
//					}
//					int visible = (int) viewVisiableMap.get(child.getId());
//					child.setVisibility(visible);
//				}
//			}
//			loadUi.setVisibility(View.GONE);			
//		}
//	}
	/**
	 * 适合于触发式的调用后台进程，错误了不对用户产生视觉效果
	 * usage:
				UserHistory userHistory = new UserHistory();
				userHistory.setUserName(model.getUserName());
				startThread(userHistory, new AppRunnable<UserHistory>() {
					@Override
					public void run(UserHistory params) throws Exception{
						new UserHistoryLocal().createOrUpdateUserHistory(params);
					}	
				});
	 * @param params
	 * @param runnable
	 */
	public void startThread(Object params,AppRunnable runnable){
		runnable.setParams(params);
		runnable.start();
	}
	/**
	 * startThread方法使用
	 * @author chenshiming
	 *
	 * @param <T>
	 */
	public static abstract class AppRunnable<T> extends Thread{
		private T params;
		
		public abstract void run(T params) throws Exception;
		public void onException(Exception e){
			AppException appException = null;
			if (e instanceof AppException) {
				appException = (AppException) e;	
			}else{
				appException = new AppException(e);
			}
			//TODO 错误消息通知
		}
		@Override
		public void run() {
			try {				
				run(params);	
			} catch (Exception e) {
				onException(e);
			}
		}
		public void setParams(T params) {
			this.params = params;
		}
	}
}
