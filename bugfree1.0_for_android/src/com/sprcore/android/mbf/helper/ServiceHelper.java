package com.sprcore.android.mbf.helper;

import com.sprcore.android.mbf.base.AppHelper;
import com.sprcore.android.mbf.helper.model.DoCheckLoginSpModel;
import com.sprcore.android.mbf.helper.model.DoCheckLoginSrModel;
import com.sprcore.android.mbf.helper.model.EditSubmitBuginfoSpModel;
import com.sprcore.android.mbf.helper.model.EditSubmitBuginfoSrModel;
import com.sprcore.android.mbf.helper.model.GetBuginfoSpModel;
import com.sprcore.android.mbf.helper.model.GetBuginfoSrModel;
import com.sprcore.android.mbf.helper.model.QueryServiceListSpModel;
import com.sprcore.android.mbf.helper.model.QueryServiceListSrModel;
import com.sprcore.android.mbf.helper.model.ResolveBugSpModel;
import com.sprcore.android.mbf.helper.model.ResolveBugSrModel;


public class ServiceHelper extends AppHelper{
	/**
	 * 获取service清单
	 * @param spModel
	 * @return
	 */
	public QueryServiceListSrModel queryServiceList(QueryServiceListSpModel spModel){	
		QueryServiceListSrModel srModel = (QueryServiceListSrModel)httpPost("buginfos2.php",spModel,QueryServiceListSrModel.class,true);
		return srModel;
	} 
	

	
	/**
	 * 根据bugId获取buginfo的详情
	 * @param spModel
	 * @return
	 */
	public GetBuginfoSrModel getBuginfo(GetBuginfoSpModel spModel){
		GetBuginfoSrModel srModel = (GetBuginfoSrModel)httpPost("buginfo2.php",spModel,GetBuginfoSrModel.class,true);
		if(srModel!=null && srModel.getBugSeverity()!=null){
			if(srModel.getBugSeverity().equals("1")){
				srModel.setBugSeverity("紧急重要");
			}else if(srModel.getBugSeverity().equals("2")){
				srModel.setBugSeverity("不紧急重要");
			}else if(srModel.getBugSeverity().equals("3")){
				srModel.setBugSeverity("紧急不重要");
			}else if(srModel.getBugSeverity().equals("4")){
				srModel.setBugSeverity("不紧急不重要");
			}
		}
		if(srModel!=null && srModel.getBugType()!=null){
			if(srModel.getBugType().equals("TrackThings")){
				srModel.setBugType("事务跟踪/咨询");
			}else if(srModel.getBugType().equals("AppManage")){
				srModel.setBugType("应用维护/管理");
			}else if(srModel.getBugType().equals("CodeError")){
				srModel.setBugType("Bug修改");
			}else if(srModel.getBugType().equals("DesignChange")){
				srModel.setBugType("功能变更");
			}else if(srModel.getBugType().equals("NewFeature")){
				srModel.setBugType("新增功能");
			}else if(srModel.getBugType().equals("CheckData")){
				srModel.setBugType("数据修改");
			}else if(srModel.getBugType().equals("StatisticsData")){
				srModel.setBugType("数据统计");
			}else if(srModel.getBugType().equals("DeployReport")){
				srModel.setBugType("应用部署");
			}else if(srModel.getBugType().equals("SystemFailure")){
				srModel.setBugType("系统故障");
			}else if(srModel.getBugType().equals("Others")){
				srModel.setBugType("其他");
			}
		}		
		return srModel;
	}
	/**
	 * 更新buginfi信息（回复）
	 * @param spModel
	 * @return
	 */
	public EditSubmitBuginfoSrModel editSubmitBuginfo(EditSubmitBuginfoSpModel spModel){
		EditSubmitBuginfoSrModel srModel = (EditSubmitBuginfoSrModel)httpPost("updateBug2.php",spModel,EditSubmitBuginfoSrModel.class,false);
		return srModel;
	}
	/**
	 * 解决事件
	 * @param spModel
	 * @return
	 */
	public ResolveBugSrModel resolveBug(ResolveBugSpModel spModel) {
		ResolveBugSrModel srModel = (ResolveBugSrModel) httpPost(
				"resolveBug2.php", spModel, ResolveBugSrModel.class,false);
		return srModel;
	}
}
