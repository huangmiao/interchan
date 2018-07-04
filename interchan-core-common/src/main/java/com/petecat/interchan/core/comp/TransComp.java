/**  
 * All rights Reserved, Designed By http://www.pete-cat.com/
 * @Title:  TransComp.java   
 * @Package com.petecat.interchan.core.comp   
 * @Description:TODO(用一句话描述该文件做什么)   
 * @author: 成都皮特猫科技     
 * @date:2017年7月28日 下午10:42:21   
 * @version V1.0 
 * @Copyright: 2017 www.pete-cat.com Inc. All rights reserved. 
 * 注意：本内容仅限于成都皮特猫信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */  
package com.petecat.interchan.core.comp;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.petecat.interchan.core.exception.BusinessException;
import com.petecat.interchan.protocol.Result;

/**   
 * @ClassName:  TransComp   
 * @Description:信息交互
 * @author: admin
 * @date:   2017年7月28日 下午10:42:21   
 */
@Component("transComp")
public class TransComp implements InitializingBean{

	private RestTemplate restTemplate;
	
	@Autowired
	private Environment environment;
	
	public Result<?> uploadFile(String url,
			Map<String,Object> params,
			String fieldName,
			File[] files,
			Class<?> cls,
			List<PropertyType> types) throws IOException{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();  
		
		for(int i = 0;i<files.length; i++){
			ByteArrayResource contentsAsResource = new ByteArrayResource(FileCopyUtils.copyToByteArray(files[0])){
	            @Override
	            public String getFilename(){
	                return files[0].getName();
	            }
	        };
	        form.add(fieldName, contentsAsResource);
		}
		
		
		if(!CollectionUtils.isEmpty(params)){
			params.forEach((key,value)->{
				form.add(key, value);
			});
		}
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(form, headers);
		
		ResponseEntity<String> responseBody = restTemplate.exchange(url,HttpMethod.POST, requestEntity, String.class);
		String resultStr = responseBody.getBody();
		Result result = JSON.parseObject(resultStr,Result.class);
		if(cls == null ){
			return result;
		}
	   if(result.getData() != null ){
		 if(result.getData() instanceof JSONObject){
			 if(CollectionUtils.isEmpty(types)){
			     result.setData(JSON.toJavaObject((JSONObject)result.getData(), cls));
			 }else{
				 result.setData(setObjectProperty(JSON.toJavaObject((JSONObject)result.getData(), cls),types));
			 }
		}else if(result.getData() instanceof JSONArray){
			 if(CollectionUtils.isEmpty(types)){
				 List<?> datas = JSON.parseArray(((JSONArray)result.getData()).toJSONString(), cls);
				 result.setData(datas);
			 }else{
				 List<?> datas = JSON.parseArray(((JSONArray)result.getData()).toJSONString(), cls);
				 datas.forEach((data)->{
					 result.setData(setObjectProperty(data,types));
				 });
			 }
		  }
		}
	  return result;
	}
	/**   
	 * @Title: callMicService   
	 * @Description: 调用微服务  
	 * @param method 方法种类
	 * @param serviceId 服务ID 配置文件配置的
	 * @param params 传输参数，如果是put和post会变成json,如果是delete或者get 必须是map才会转换为参数
	 * @param cls 结果data转换的类
	 * @throws Exception 
	 * @return T  返回的信息
	 */
	@SuppressWarnings("unchecked")
	public  Result<?> callMicService(
			HttpMethod method,
			String serviceId,
			String token,
			Object params,
			Class<?> cls,
			List<PropertyType> types) throws Exception{
		String jsonStr = callMicService(method,serviceId,token,params);
		if(StringUtils.isBlank(jsonStr) ){
			throw new BusinessException(Result.SYS_RESULT_FAILD, "no result data");
		}
		Result<Object> result = JSON.parseObject(jsonStr, Result.class);
		if(cls == null ){
			return result;
		}
	   if(result.getData() != null ){
		 if(result.getData() instanceof JSONObject){
			 if(CollectionUtils.isEmpty(types)){
			     result.setData(JSON.toJavaObject((JSONObject)result.getData(), cls));
			 }else{
				 result.setData(setObjectProperty(JSON.toJavaObject((JSONObject)result.getData(), cls),types));
			 }
		}else if(result.getData() instanceof JSONArray){
			 if(CollectionUtils.isEmpty(types)){
				 List<?> datas = JSON.parseArray(((JSONArray)result.getData()).toJSONString(), cls);
				 result.setData(datas);
			 }else{
				 List<?> datas = JSON.parseArray(((JSONArray)result.getData()).toJSONString(), cls);
				 datas.forEach((data)->{
					 result.setData(setObjectProperty(data,types));
				 });
			 }
		  }
		}
	  return result;
	}
	
	/**   
	 * @Title: setObjectProperty   
	 * @Description: 设置对象属性值
	 * @param obj 对象
	 * @param types
	 * @return void     
	 */
	private Object setObjectProperty(Object obj, List<PropertyType> types) {
		if(!CollectionUtils.isEmpty(types)){
			types.forEach((data)->{
				try {
					analizeObjectProperty(obj,data);
				} catch (Exception e) {
					
				}
			});
		}
		return obj;
	}
	

	/**   
	 * @Title: analizeObjectProperty   
	 * @Description: 设置对象属性值
	 * @param obj 对象
	 * @param type 属性类型 
	 * @return void     
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	private Object analizeObjectProperty(Object obj,PropertyType propertyType) throws Exception {
		Object temp = obj;
		if(propertyType == null  || propertyType.getClass()==null
				|| obj.getClass().isPrimitive() 
				|| obj instanceof String
				|| obj == null){
			return obj;
		}else{
			 if(obj instanceof JSONObject){
				 temp = setObjectProperty(JSON.toJavaObject((JSONObject)obj, propertyType.getCls()),
						 propertyType.getTypes());
			}else if(obj instanceof JSONArray){
				List<Object> datas = (List<Object>) JSONArray.toJavaObject((JSONArray)obj, propertyType.getCls());
				datas.forEach((data)->{
					setObjectProperty(data,propertyType.getTypes());
				});
				temp = datas;
			}
			 else if( obj instanceof Map){
				@SuppressWarnings("rawtypes")
				Map tempData = (Map)obj;
				if(tempData.containsKey(propertyType.getPropertyName())){
					Object v = tempData.get(propertyType.getPropertyName());
					tempData.put(propertyType.getPropertyName(),
							this.analizeObjectProperty(v, propertyType));
				}
				temp = tempData;
			}else if(obj instanceof Collection){
				@SuppressWarnings("rawtypes")
				Collection tempData = (Collection)obj;
				Collection<Object> realData = (Collection<Object>)obj.getClass().newInstance();
				tempData.forEach((data)->{
					try {
						realData.add(analizeObjectProperty(data,propertyType));
					} catch (Exception e) {
						
					}
				});
				temp = realData;
			}else {//认为是bean
				try{
					BeanInfo info = Introspector.getBeanInfo(obj.getClass());
					Optional<PropertyDescriptor> propertyDes = Arrays.stream(info.getPropertyDescriptors()).filter((data)->{
						if(data.getName().equals(propertyType.getPropertyName())
								&& data.getWriteMethod()!=null){
								return true;
						}
						return false;
					}).findFirst();
				   if(propertyDes.isPresent()){
					   Object val = propertyDes.get().getReadMethod().invoke(obj);
					   temp = this.analizeObjectProperty(val, propertyType);
					   propertyDes.get().getWriteMethod().invoke(obj,temp);
				   }
				}catch(Exception e){}
			}
		}
		return temp;
	}
	

	/**   
	 * @Title: callMicService   
	 * @Description: 返回json数据  
	 * @param method 方法种类
	 * @param serviceId 服务ID 配置文件配置的
	 * @param params 传输参数，如果是put和post会变成json,如果是delete或者get 必须是map才会转换为参数
	 * @throws Exception
	 * @return String     
	 */
	public String callMicService(
			HttpMethod method,
			String serviceId,
			String token,
			Object params) throws Exception{
	   String serviceContent =  this.environment.getProperty(serviceId);
	   ResponseEntity<String> response = null;
	   if(StringUtils.isBlank(serviceContent)){
			throw new BusinessException(Result.SYS_RESULT_FAILD, "不存在"+serviceId+"的服务信息");
	   }
	   String jsonStr = null;
	   HttpHeaders headers = new HttpHeaders();
	  if(StringUtils.isNotBlank(token)){
		   headers.set("Authorization", "Bearer "+token);
	   }
	   if( method == HttpMethod.GET || method == HttpMethod.DELETE){
    	   MultiValueMap<String,String> value = new LinkedMultiValueMap<String,String>();
    	   if(params!=null && params instanceof Map){
    		   Map<?, ?> tempData = (Map<?, ?>) params;
    		   if(!CollectionUtils.isEmpty(tempData)){
    			   tempData.forEach((k,v)->{
        			   if(k!=null){
        				   value.add(k.toString(), v==null?"":v.toString()); 
        			   }
        		   });
        	   }
    		   serviceContent = UriComponentsBuilder.fromHttpUrl(serviceContent).queryParams(value).build().toUriString();
    	   }
    	   HttpEntity<?> entity = new HttpEntity<>(headers);
    	   response = restTemplate.exchange(
    			  serviceContent, method, entity, String.class);
		}else if(method == HttpMethod.PUT ||method == HttpMethod.POST){
			 headers.setContentType(MediaType.APPLICATION_JSON);
			 HttpEntity<Object> request = null;
			 if(params != null){
				request=new HttpEntity<Object>(params, headers);
			 }else{
				request=new HttpEntity<Object>(headers);
			 }
			 response = restTemplate.exchange(
					 serviceContent,method, request, String.class);
		}else{
			throw new BusinessException(Result.SYS_RESULT_FAILD, "不支持的请求协议");
		}
	    if(response.getStatusCode() != HttpStatus.OK){
	    	throw new BusinessException(Result.SYS_RESULT_FAILD, jsonStr);
	    }
		return response.getBody();
	}
	

	/**   
	 * <p>Title: afterPropertiesSet</p>   
	 * <p>Description: </p>   
	 * @throws Exception   
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()   
	 */  
	@Override
	public void afterPropertiesSet() throws Exception {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		//是否开启代理
		String serviceProxyFlag = environment.getProperty("SYSTEM_SERVICE_PROXY_FLAG");
		String maxSendCount = environment.getProperty("SYSTEM_SERVICE_MAX_SEND_COUNT");
		String readTimeout = environment.getProperty("SYSTEM_SERVICE_READ_TIMEOUT");
		String connectTimeout = environment.getProperty("SYSTEM_SERVICE_CONNECT_TIMEOUT");
		if("1".equals(serviceProxyFlag)){
			String proxyIp = environment.getProperty("SYSTEM_SERVICE_PROXY_IP");
			int proxyPort = Integer.parseInt(environment.getProperty("SYSTEM_SERVICE_PROXY_PORT"));
			Proxy proxy =  new Proxy(Proxy.Type.HTTP,new InetSocketAddress(proxyIp,proxyPort));
			requestFactory.setProxy(proxy);
		}
		requestFactory.setReadTimeout(Integer.parseInt(readTimeout==null?"30000":readTimeout));//超时时间30秒
		requestFactory.setConnectTimeout(Integer.parseInt(connectTimeout==null?"5000":connectTimeout));//连接超时时间5秒
		ThreadPoolTaskExecutor excutor = new ThreadPoolTaskExecutor();
		//最大允许200个连接
		excutor.setMaxPoolSize(Integer.parseInt(maxSendCount==null?"300":maxSendCount));
		excutor.initialize();
		requestFactory.setTaskExecutor(excutor);
		this.restTemplate = new RestTemplate(requestFactory);
	}
}
