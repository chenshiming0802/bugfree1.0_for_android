package com.sprcore.android.mbf.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.sprcore.android.core.tools.DevelopTools;

/**
 * 每次运行Task必须实例化 
 * new ServiceListTask().singleExecute();
 * 不能使用  execute方法
 * @author chenshiming
 *
 */
public abstract class AppAsyncTask<T extends AppSrModel> extends AsyncTask<Void, Void, Boolean> {
	private Map<String, String> currentRunningTask = new HashMap<>();
	private AppFragment appFragment ;
	private T srModel ;  
	
	public AppAsyncTask<T> getCurrentAppAsyncTask(){
		return this;
	}
	
	public Boolean getDoInBackgroundReturnBySrModel() {
		if (srModel != null && srModel.getResultFlag() != null
				&& srModel.getResultFlag().equals("0")) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	protected final Boolean doInBackground(Void... arg0){
		if(!canRun){
			return Boolean.FALSE;
		}
		try {
			srModel = doInBackground();
			DevelopTools.assertNotNull(srModel, this.getClass().getSimpleName()+"的doInBackground必须将非空值写入setSrModel");
		} catch (Exception e) {
			AppException e2 = null;
			if (e instanceof AppException) {
				e2 = (AppException) e;
			}else{
				e2 = new AppException(e);
			}
			
			try {				
				srModel = (T)e2.getSrModel((Class)getGenericType());
				 
			} catch (Exception e3) {
				throw new AppDevelopError("在doInBackground中根据异常获取srModel出现错误",e3);
			}

		}
		return getDoInBackgroundReturnBySrModel();
	}
	protected abstract T doInBackground();
	
	/**
	 * 得到本类 T 的泛型类型
	 * @return
	 */
	private Type getGenericType() {
 
		Type type = super.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType ptype = ((ParameterizedType) type);
			Type[] args = ptype.getActualTypeArguments();
			return args[0];
		}
		return null;
	}
	
	
	
	
	
	public void showFailResultMessage(AppActivity activity,T srModel){
		if(activity==null){
			return ;
		}
		if(srModel==null || srModel.getResultFlag()==null){
			activity.showToast("系统错误，srModel为空或其resultFlage为空");//开发人员编码错误
		}else if(srModel.getResultFlag().equals("1")){
			activity.showToast("操作失败，失败原因："+srModel.getResultMessage());//业务错误
		}else{
			//activity.showToast("操作成功");
		}
		
	}
	
	public boolean isSuccess(T srModel){
		if(srModel!=null && srModel.getResultFlag()!=null && srModel.getResultFlag().equals("0")){
			return true;
		}else{
			return false;
		}
	}
	//是否可以运行，在onPreExecute2返回false，则不运行onPostExecute
	private boolean canRun = true;
	@Override
	protected final void onPreExecute(){
		if(appFragment==null){		
			Log.e("AppAsyncTask", "AppAsyncTask should call singleExecute,not execute");
		}
		canRun = onPreExecute2();
		if(canRun){
			appFragment.loading(true);//设置load界面
			currentRunningTask.put(this.getClass().getName(), "1");//设置当前task在运行
		}

		
		
	}
	public abstract boolean onPreExecute2();
	
	@Override
	protected final void onPostExecute(Boolean result){
		if(!canRun){
			return ;
		}
		appFragment.loading(false);//取消load界面
		currentRunningTask.remove(this.getClass().getName());//设置当前task没有运行
		showFailResultMessage(appFragment.getBaseActivity(),srModel);
		onPostExecute(srModel);
	}
	protected abstract void onPostExecute(T srModel);
 
	/**
	 * 如果上次运行未结束，则此次运行请求不执行
	 */
	public void singleExecute(AppFragment fragment){
		this.appFragment = fragment;
		if(!currentRunningTask.containsKey(this.getClass().getName())){
			super.execute();
		}else{
			fragment.getBaseActivity().showToast("您操作的太快了，系统跟不上您的速度了!");
		}
	}
 
 
	public void singleExecuteWithConfirm(AppFragment fragment,String confirmMessage){
		this.appFragment = fragment;
		if(!currentRunningTask.containsKey(this.getClass().getName())){
			this.appFragment.getBaseActivity().showConfirm(confirmMessage, new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					getCurrentAppAsyncTask().execute();
				}
			} );
		}else{
			fragment.getBaseActivity().showToast("您操作的太快了，系统跟不上您的速度了!");
		}
	}
}
