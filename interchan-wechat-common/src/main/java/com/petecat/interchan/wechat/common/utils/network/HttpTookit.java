package com.petecat.interchan.wechat.common.utils.network;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpTookit {

private static final String APPLICATION_JSON = "application/json";
    
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    public static String postFile(String url, File f) throws IOException {
    	CloseableHttpClient httpclient = null;
		CloseableHttpResponse responses = null;
		String response = null;
    	if (url == null || f == null) {
	    	System.out.println("URL或者文件为NULL");
	    	return response;
    	}
    	httpclient = HttpClients.createDefault();  
    	HttpPost httpPost = new HttpPost(url);
    	try {
	    	FileEntity entity = new FileEntity(f, "binary/octet-stream");
	    	httpPost.setEntity(entity);
	    	responses = httpclient.execute(httpPost);
	    	HttpEntity responseEntity = responses.getEntity();
	        response = EntityUtils.toString(responseEntity);
	        EntityUtils.consume(responseEntity);  
    	} catch (Exception e) {
    		System.out.println(e.toString());
    	} finally {
    		if (responses != null && responses.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
    			// 打印调试信息,上传的url和上传的文件大小
				System.out.println(MessageFormat.format(
					"upload picture success! url = [{0}],file size = [{1}]", url,
				f.length()));
					return response;
			}else{
				System.out.println(responses.getStatusLine().getStatusCode());
			}
    		if(responses != null){
				responses.close();
				responses = null;
			}
			if (httpclient != null){
				httpclient.close();
				httpclient = null;
			}
    	}
		return response;
    }
    	
    public static String httpGetWithJSON(String url) throws Exception{
    	CloseableHttpClient httpclient = null;
		CloseableHttpResponse responses = null;
		String response = null;
		try{
			httpclient = HttpClients.createDefault();  
	        HttpGet httpGet = new HttpGet(url);
	        httpGet.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
	        
	        responses = httpclient.execute(httpGet);
	        HttpEntity entity = responses.getEntity();
	        response = EntityUtils.toString(entity);
	        EntityUtils.consume(entity);  
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(responses != null){
					responses.close();
					responses = null;
				}
				if (httpclient != null){
					httpclient.close();
					httpclient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        return response;
    }
    
    public static String httpPostMultipartWithJSON(String url, JSONObject json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
//        String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
        
        CloseableHttpClient httpclient = null;
		CloseableHttpResponse responses = null;
		String response = null;
		try{
			httpclient = HttpClients.createDefault();  
	        HttpPost httpPost = new HttpPost(url);
	        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	        Set<String> keys = json.keySet();
	        builder.setContentType(ContentType.MULTIPART_FORM_DATA);
	        for(String key:keys){
//	        	if(key.contains("file")){
//	        		builder.addPart(key, contentBody)
//	        		builder.addBinaryBody(key, json.getString(key), ContentType.MULTIPART_FORM_DATA, "a.png");
//	        	}else{
	        		builder.addPart(key, new StringBody(json.getString(key)));
//	        	}
        		
	        }
	       
	        httpPost.setEntity(builder.build());
	        responses = httpclient.execute(httpPost);
	        HttpEntity entity = responses.getEntity();
	        response = EntityUtils.toString(entity);
	        EntityUtils.consume(entity);  
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(responses != null){
					responses.close();
					responses = null;
				}
				if (httpclient != null){
					httpclient.close();
					httpclient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        return response;
    }
    
    public static String httpPostWithJSON(String url, String json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
//        String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
        
        CloseableHttpClient httpclient = null;
		CloseableHttpResponse responses = null;
		String response = null;
		try{
			httpclient = HttpClients.createDefault();  
	        HttpPost httpPost = new HttpPost(url);
	        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
	        
	        StringEntity se = new StringEntity(json,"UTF-8");
	        se.setContentType(CONTENT_TYPE_TEXT_JSON);
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
//	        se.setContentEncoding("UTF-8");
	        httpPost.setEntity(se);
	        responses = httpclient.execute(httpPost);
	        HttpEntity entity = responses.getEntity();
	        response = EntityUtils.toString(entity);
	        EntityUtils.consume(entity);  
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(responses != null){
					responses.close();
					responses = null;
				}
				if (httpclient != null){
					httpclient.close();
					httpclient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        return response;
    }

    public static String httpDelWithJSON(String url) throws Exception {
    	CloseableHttpClient httpclient = null;
		CloseableHttpResponse responses = null;
		String response = null;
		try{
			httpclient = HttpClients.createDefault();  
	        HttpDelete httpDelete = new HttpDelete(url);
	        httpDelete.setHeader("Accept-Encoding", "gzip, deflate");
	        httpDelete.setHeader("Accept-Language", "zh-CN");
	        httpDelete.setHeader("Accept", "application/json, application/xml, text/html, text/*, image/*, */*");
	        
	        responses = httpclient.execute(httpDelete);
	        HttpEntity entity = responses.getEntity();
	        response = EntityUtils.toString(entity);
	        EntityUtils.consume(entity);  
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(responses != null){
					responses.close();
					responses = null;
				}
				if (httpclient != null){
					httpclient.close();
					httpclient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        return response;
    }
}
