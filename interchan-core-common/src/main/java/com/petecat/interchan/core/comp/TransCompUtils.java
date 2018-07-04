package com.petecat.interchan.core.comp;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.petecat.interchan.core.entity.RequestModel;
import com.petecat.interchan.protocol.Result;

/** 
 * @ClassName:  TransUtils   
 * @author: admin
 * @date:   2018年4月9日 下午2:11:59   
 */
@Component
public class TransCompUtils {

	 @Autowired(required=false)
     private  RestTemplate restTemplate;

	/**
	 *
	 * 功能描述:
	 * @param model
	 * @return: com.petecat.interchan.protocol.Result<?>
	 * @auther: admin
	 * @date: 2018/05/02 15:47
	 */
	public  Result<?> callSimpleService(RequestModel model){
		  return this.callSimpleService(model,false);
	 }
	

	

	/**
	 *
	 * 功能描述:
	 * @param model
	 * @param headerAddBearer
	 * @return: com.petecat.interchan.protocol.Result<?>
	 * @auther: admin
	 * @date: 2018/05/02 15:47
	 */
	public  Result<?> callSimpleService(RequestModel model,boolean headerAddBearer){
		return callSimpleService(model,headerAddBearer,MediaType.APPLICATION_JSON);
	}
	public  Result<?> callSimpleService(RequestModel model,boolean headerAddBearer,MediaType mediaType){
		   if(restTemplate == null) {
			   return Result.faild("未提供调用请求模板服务类！");
		   }
	       ResponseEntity<Result> response = null;
	       HttpMethod method  = model.getMethod();
		   String url  = model.getUrl();
		   String sufUrl =  model.getSufUrl();
		   String authorization= model.getAuthorization();
		   Object params = model.getParams();
		   HttpHeaders headers = new HttpHeaders();
		  if(StringUtils.isNotBlank(authorization)){
			  if(headerAddBearer) {
				   headers.set("Authorization", "Bearer "+authorization);
			  }else {
			       headers.set("Authorization", authorization);
			  }
		   }
		   if(StringUtils.isNotBlank(sufUrl)) {
			   if(sufUrl.startsWith("/")) {
				   sufUrl = sufUrl.substring(1,sufUrl.length());
			   }
			   url = url.endsWith("/")?url.concat(sufUrl):url.concat("/").concat(sufUrl);
		   }
		   if( method == HttpMethod.GET || method == HttpMethod.DELETE){
	    	      MultiValueMap<String,String> value = new LinkedMultiValueMap<String,String>();
		    	   if(params!=null){
		    		   Map tempData = null;
		    		   if( params instanceof Map) {
		    			   tempData = (Map) params;
			    	   }else if(params instanceof  String ||
							   params.getClass().isPrimitive()){
						   url = url.concat("/").concat(params.toString());
			    	   }else{
						   tempData = BeanMap.create(params);
					   }
		    		   if(!CollectionUtils.isEmpty(tempData)){
		    			   tempData.forEach((k,v)->{
		        			   if(k!=null){
		        				   value.add(k.toString(), v==null?"":v.toString());
		        			   }
		        		   });
		        	   }
		    	   }
	    		   url = UriComponentsBuilder.fromHttpUrl(url).queryParams(value).build().toUriString();
		    	   HttpEntity entity = new HttpEntity(headers);
		    	   response = restTemplate.exchange(
		    			   url, method, entity, Result.class);
			}else if(method == HttpMethod.PUT ||method == HttpMethod.POST){
				 headers.setContentType(mediaType);
				 HttpEntity request = null;
				 if(params != null){
//					 if(params instanceof  String ||
//							 params.getClass().isPrimitive()){
//						 url = url.concat("/").concat(params.toString());
//						 request=new HttpEntity(headers);
//					 }else{
						 request=new HttpEntity(params, headers);
//					 }
				 }else{
					request=new HttpEntity(headers);
				 }
				 response = restTemplate.exchange(
						 url,method, request, Result.class);
			}else{
				return new Result(Result.SYS_RESULT_FAILD, "不支持的请求协议");
			}
			return response.getBody();
		}
	public Result callSimpleService(RequestModel model,Class<?> cls){
		return callSimpleService(model,cls,false);
	}

	public Result callSimpleService(RequestModel model,Class<?> cls,MediaType mediaType){
		return callSimpleService(model,cls,false,mediaType);
	}
	public Result callSimpleService(RequestModel model,Class<?> cls,boolean headerAddBearer){
		return callSimpleService(model,cls,null,headerAddBearer);
	}
	
	public Result callSimpleService(RequestModel model,Class<?> cls,boolean headerAddBearer,MediaType mediaType){
		return callSimpleService(model,cls,null,headerAddBearer,mediaType);
	}
	
	public  Result callSimpleService(
			RequestModel model,
			Class<?> cls,
			PropertyType[] typeArrays,
			boolean headerAddBearer,
			MediaType mediaType){
		Result result = callSimpleService(model,headerAddBearer,mediaType);
		if(cls == null || result.getCode()!=Result.SUCCESS){
			return result;
		}
		if(result.getData() != null ){
			List<PropertyType> types = typeArrays==null?null: Arrays.asList(typeArrays);
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
					for(Object data : datas){
						result.setData(setObjectProperty(data,types));
					}
				}
			}else if(result.getData() instanceof  Map){
				JSONObject obj = new JSONObject();
				obj.putAll((Map<? extends String, ? extends Object>) result.getData());
				if(CollectionUtils.isEmpty(types)){
					result.setData(JSON.toJavaObject((JSONObject)result.getData(), cls));
				}else{
					result.setData(setObjectProperty(JSON.toJavaObject((JSONObject)result.getData(), cls),types));
				}
			}else if(result.getData() instanceof Collection){
				JSONArray jsonArray = new JSONArray();
				jsonArray.addAll((Collection<? extends Object>) result.getData());
				if(CollectionUtils.isEmpty(types)){
					List<?> datas = JSON.parseArray(jsonArray.toJSONString(), cls);
					result.setData(datas);
				}else{
					List<?> datas = JSON.parseArray(jsonArray.toJSONString(), cls);
					for(Object data : datas){
						result.setData(setObjectProperty(data,types));
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * @Title: callSimpleService
	 * @Description: 调用微服务
	 * @param model 方法参数
	 * @param typeArrays 封装的对象如果里面还有属性是对象
	 * @param cls 结果data转换的类
	 * @throws Exception
	 * @return T  返回的信息
	 */
	public  Result callSimpleService(
			RequestModel model,
			Class<?> cls,
			PropertyType[] typeArrays,
			boolean headerAddBearer){
		return callSimpleService(model, cls, typeArrays, headerAddBearer, MediaType.APPLICATION_JSON);
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
	 * @param propertyType 属性类型
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
				Collection realData = (Collection)obj.getClass().newInstance();
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
						System.out.println(data.getName());
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
}
