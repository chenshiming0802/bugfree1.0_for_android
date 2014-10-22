package com.sprcore.android.mbf.base;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.sprcore.android.mbf.base.depend.apphelper.HttpDataHistory;
import com.sprcore.android.mbf.base.depend.apphelper.HttpDataHistoryLocal;

/**
 * 使用Sp和Sr模式
 * 方法必须返回srModel
 * @author chenshiming
 *
 */
public abstract class AppHelper {

	private final static String URL = "http://testenv.bsp.bsteel.net/baosteel_cas2/service_proxy2.jsp";
	private final static String DEBUG_URL = "http://192.168.1.101/BugFree/rest/";
	private final static boolean DEBUG = false;
	
	public boolean isSuccess(AppSrModel srModel){
		if(srModel!=null && srModel.getResultFlag()!=null && srModel.getResultFlag().equals("1")){
			return true;
		}else{
			return false;
		}
	}

//	protected Object httpPost(String service,AppSpModel spModel,Class cls){
//		return httpPost(service,spModel,cls,false);
//	}
	/**
	 * HTTP获取数据
	 *    如果联网，则每次数据缓存
	 *    如果本地，则从本地获取数据
	 * @param service
	 * @param spModel
	 * @param cls
	 * @param isCache
	 * @return
	 */
	protected Object httpPost(String service,AppSpModel spModel,Class cls,boolean isCache){		
		String realUrl = URL;
		if(DEBUG){
			realUrl = DEBUG_URL+service;
		}	
		
		AppSrModel srModel = null;
		try {	
			LinkedMultiValueMap<String, String> formData  = getLinkedMultiValueMap(spModel);
			formData.add("_SERVICE_", service);	
			String urlInfoKey = formData.getFirst(LINKEDMAP_URLINFO_KEY);		
			Log.i("hp_url", realUrl + "?_SERVICE_=" + service +"&" + urlInfoKey);
			formData.remove(LINKEDMAP_URLINFO_KEY);
			
			
			String body = null;
			if(isCache && !AppFragment.IS_CURRENT_NETWORD_STATUS){
				//如果需要缓存&本地状态 ==》则读取本地缓存数据
				Log.i("hp_type","本地模式");
				HttpDataHistory model = new HttpDataHistoryLocal().getRowByUrlKey(urlInfoKey);
				if(model!=null){		
					body = model.getUrlData();
				}
			}else{
				Log.i("hp_type","网络模式");
				body = connectServlet(realUrl,formData);
			}
							
			// 如果需要缓存&联网模式 ==》缓存数据
			if (isCache && AppFragment.IS_CURRENT_NETWORD_STATUS) {
				HttpDataHistory hhModel = new HttpDataHistory();
				hhModel.setUpdateTime(Integer.valueOf(HttpDataHistory.currentTime()));
				hhModel.setUrlData(body);
				hhModel.setUrlKey(urlInfoKey);
				new HttpDataHistoryLocal().saveOrUpdate(hhModel);
			}
			if(body!=null){
				Log.i("hp_return", body);
				srModel = new ObjectMapper().readValue(body,cls);
			}else{
				Log.i("hp_return", "Null");
			}
			
			
			
			if(srModel==null){
				srModel = (AppSrModel)cls.newInstance();
				srModel.setResultFlag("1");
				if(isCache && !AppFragment.IS_CURRENT_NETWORD_STATUS){
					srModel.setResultMessage("当前为本地模式，无法显示数据，请开打网络后再试试!");
				}else{
					srModel.setResultMessage("无法从服务器获取数据，可能问题来自于网络，也可能服务器在休息!");
				}						
			}		
		} catch (Exception e) {
			e.printStackTrace();
			try {
				srModel = (AppSrModel)cls.newInstance();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			srModel.setResultFlag("1");
			srModel.setResultMessage(e.getMessage());
		}			
		return srModel;
	}
	
	
	public String connectServlet(String realUrl, LinkedMultiValueMap<String, String> formData)
			throws Exception {
		HttpHeaders requestHeaders = new HttpHeaders();
		if(AppActivity.getUserSummary()!=null && AppActivity.getUserSummary().getUcore1()!=null){			
			requestHeaders.set("ucore1",AppActivity.getUserSummary().getUcore1());//帐号名加密数据
			Log.i("hp_ucore1",AppActivity.getUserSummary().getUcore1());
		}
		//requestHeaders.setContentType(MediaType.TEXT_PLAIN);
		requestHeaders.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8"));
		requestHeaders.setAcceptCharset(Collections.singletonList(Charset.forName("UTF-8")));
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		LinkedMultiValueMap map = new LinkedMultiValueMap();
 
		HttpEntity<?> requestEntity = new HttpEntity<Object>(formData, requestHeaders);
		
		RestTemplate restTemplate = new RestTemplate(true);
// 		ResponseEntity<String> response = restTemplate.postForEntity(realUrl,
// 				formData, String.class);
 		ResponseEntity<String> response = restTemplate.exchange(realUrl, HttpMethod.POST, requestEntity, String.class);
 		
		return response.getBody();
	}
	
	
 
	

	private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	//用于方法getLinkedMultiValueMap保存urlinfo的参数名，为计算缓存的key用
	private static final String LINKEDMAP_URLINFO_KEY = "LINKEDMAP_URLINFO_KEY";
	private LinkedMultiValueMap<String, String> getLinkedMultiValueMap(AppSpModel spModel) throws Exception{
		StringBuffer urlInfoSb = new StringBuffer();
		String sep = "";
		LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		if(spModel!=null){
			Method[] methods = spModel.getClass().getMethods();
			if(methods!=null){
				for(int i=0,j=methods.length;i<j;i++){
					Method m = methods[i];
					if(m.getParameterTypes().length==0 && m.getName().startsWith("get") ){
						String fieldName = m.getName().substring(3);
						fieldName = fieldName.substring(0,1).toLowerCase()+fieldName.substring(1);
						if(!fieldName.equals("class")){							
							Object obj = m.invoke(spModel, new Object[]{});
							if(obj!=null){
								String value = null;
								if (obj instanceof Date) {
									Date val2 = (Date) obj;
									value = simpleDateFormat.format(val2);							
								}else{
									value = obj.toString();
								}	
								urlInfoSb.append(sep).append(fieldName).append("=").append(value);
								sep = "&";
								formData.add(fieldName,value);
							}
						}						
					}
				}
			}
		}
		formData.add(LINKEDMAP_URLINFO_KEY,urlInfoSb.toString());
		return formData;
	}
}
