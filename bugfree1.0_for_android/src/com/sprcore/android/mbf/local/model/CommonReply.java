package com.sprcore.android.mbf.local.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sprcore.android.mbf.base.AppKeyValueModel;
import com.sprcore.android.mbf.base.AppLocalModel;

public class CommonReply extends AppLocalModel {
	public final static String DICT_TYPE_EDITBUG="editbug";
	public final static String DICT_TYPE_FIXBUG="fixbug";
	
	@Override
	public Map<String, List<AppKeyValueModel>> dicts() {
		Map<String, List<AppKeyValueModel>> map = new HashMap<String, List<AppKeyValueModel>>();

		List<AppKeyValueModel> type = new ArrayList<AppKeyValueModel>();
		type.add(new AppKeyValueModel(DICT_TYPE_EDITBUG, "更新事件"));
		type.add(new AppKeyValueModel(DICT_TYPE_FIXBUG, "解决事件"));
		map.put("type", type);

		return map;
	}
	
	
	@Override
	public List initData() {
		List list = new ArrayList();
		if(true){
			CommonReply model = new CommonReply();
			model.setType("editbug");
			model.setContent("正在处理中，请等候！");
			model.setCreateTime(currentTime());
			model.setUpdateTime(currentTime());
			list.add(model);
		}
		if(true){
			CommonReply model = new CommonReply();
			model.setType("editbug");
			model.setContent("该问题需要修改代码，请修改下完成时间，再指派给我！");
			model.setCreateTime(currentTime());
			model.setUpdateTime(currentTime());
			list.add(model);
		}	
		if(true){
			CommonReply model = new CommonReply();
			model.setType("editbug");
			model.setContent("阅知！");
			model.setCreateTime(currentTime());
			model.setUpdateTime(currentTime());
			list.add(model);
		}			
		if(true){
			CommonReply model = new CommonReply();
			model.setType("editbug");
			model.setContent("审批通过，请尽快执行！");
			model.setCreateTime(currentTime());
			model.setUpdateTime(currentTime());
			list.add(model);
		}	
		
		if(true){
			CommonReply model = new CommonReply();
			model.setType("fixbug");
			model.setContent("已经处理，请确认！");
			model.setCreateTime(currentTime());
			model.setUpdateTime(currentTime());
			list.add(model);
		}	
		if(true){
			CommonReply model = new CommonReply();
			model.setType("fixbug");
			model.setContent("已经执行完成，请确认！");
			model.setCreateTime(currentTime());
			model.setUpdateTime(currentTime());
			list.add(model);
		}		
		return list;
	}


	private Integer id;
	private String type;  
	private String content;
	private Integer createTime;
	private Integer updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	public Integer getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	

}
