package com.sprcore.android.mbf.base;

public class AppException extends RuntimeException {

	private Exception e;
	
	public AppException(Exception e){
		this.e = e;
		this.e.printStackTrace();
	}
	
	public AppSrModel getSrModel(Class cls){
		try {
			AppSrModel srModel = (AppSrModel)cls.newInstance();
			srModel.setResultFlag("1");
			srModel.setResultMessageCode("excetption_"+e.getClass().getSimpleName());
			srModel.setResultMessage(e.getMessage());	
			return srModel;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}	
}
