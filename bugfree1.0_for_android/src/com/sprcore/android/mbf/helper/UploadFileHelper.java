package com.sprcore.android.mbf.helper;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.codehaus.jackson.map.ObjectMapper;

import com.sprcore.android.mbf.base.AppException;
import com.sprcore.android.mbf.helper.model.UploadServiceAttachmentSpModel;
import com.sprcore.android.mbf.helper.model.UploadServiceAttachmentSrModel;

public class UploadFileHelper {
	/**
	 * 拍照上傳附件
	 * @param spModel
	 * @return
	 */
	public UploadServiceAttachmentSrModel uploadServiceAttachment(UploadServiceAttachmentSpModel spModel){
		UploadServiceAttachmentSrModel srModel = null;
		
		String url = "http://192.168.1.106:8081/service_proxy/service_proxy_withfils2.jsp";
		String service = "uploadBugFile2.php";
		HashMap params = new HashMap();
		params.put("bugId", spModel.getBugId());
		String body = jsonProxyPost(url,service,params,"BugFileName[]",spModel.getBugFileName());	
		try {
			srModel = new ObjectMapper().readValue(body,UploadServiceAttachmentSrModel.class);
		} catch (Exception e) {
			srModel = new UploadServiceAttachmentSrModel();
			srModel.setResultFlag("1");
			srModel.setResultMessage(e.getMessage());
		}
		return srModel;
	}
	
	
	
	public static String jsonProxyPost(String url, String service,
			HashMap params,String fileFieldName,File file) {
		
		String returnMessage = null;
		try {
			returnMessage = proxyPost(url, service, params,fileFieldName,file);
		} catch (Exception e) {
			// e.printStackTrace();
			returnMessage = "{\"resultFlag\":\"1\",\"resultMessage\":\"Proxy connect reject!\"}";
		}
		System.out.println("jsonProxy return:"+returnMessage);
		return returnMessage;

	}
	
	public static String proxyPost(String url, String service, HashMap params,String fileFieldName,File file)
			throws Exception {
		params = (params==null)?new HashMap():params;
		
		params.put("_SERVICE_", service);	
		
		System.out.println("jsonProxyPost:" + url   + "  params:" + params);
		
		String response = null;
		HttpClient client = new HttpClient();
		PostMethod method = new UTF8PostMethod(url);

		Iterator it = params.keySet().iterator();
		//NameValuePair[] pairs = new NameValuePair[params.size()];
		Part[] pairs = null;
		int index = 0;
		if(file!=null){
			pairs = new Part[params.size()+1];
			pairs[0] = new FilePart(fileFieldName, file);
			System.out.println(fileFieldName+"==="+file);
			index++;
		}else{
			pairs = new Part[params.size()];
		}
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = (String) params.get(key);
			//pairs[i] = new NameValuePair(key, value);
			pairs[index] = new StringPart(key,value, "UTF-8");
			index++;
		}
		
//		method.setRequestBody(pairs);
 
		method.setRequestEntity(new MultipartRequestEntity(pairs,method.getParams()));	
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				response = method.getResponseBodyAsString();
			}else{
				System.out.println("HTTP Status ERROR:"+method.getStatusCode());
			}
		} catch(Exception e){
			throw new AppException(e);
		}finally {
		 
			method.releaseConnection();
		}
		return response;
	}	
	
	 static class UTF8PostMethod extends PostMethod{
		    public UTF8PostMethod(String url){ 
		        super(url);
		    }
		    public String getRequestCharSet() {
		     return "UTF-8";
		    }
		}
}
