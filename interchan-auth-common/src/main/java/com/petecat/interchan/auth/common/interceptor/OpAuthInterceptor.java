package com.petecat.interchan.auth.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.alibaba.fastjson.JSON;
import com.petecat.interchan.auth.common.constant.AuthConstant;
import com.petecat.interchan.core.constans.Global;
import com.petecat.interchan.core.exception.BusinessException;
import com.petecat.interchan.protocol.GlobalHeader;
import com.petecat.interchan.protocol.Result;
import com.petecat.interchan.protocol.auth.AuthExcludeUrl;
import com.petecat.interchan.protocol.auth.AuthUrl;
import com.petecat.interchan.redis.commands.IRedisExtCommands;

/**
 * 
 * @ClassName:  AuthInterceptor   
 * @Description:拦截
 * @author: mhuang
 * @date:   2017年8月15日 下午8:30:51
 */
public class OpAuthInterceptor implements HandlerInterceptor{

	private Logger logger  = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(!(handler instanceof ResourceHttpRequestHandler)){
			       logger.debug("请求的URL:{},请求类型:{}",request.getRequestURL().toString(),request.getMethod());
               try {
			   	boolean flag = this.checkUrlIsNotLogin(getUri(request),request);
			    if(!flag) {
					        String headJson = request.getHeader(Global.GLOBAL_HEADER);
							if(StringUtils.isNotBlank(headJson)){
								GlobalHeader globalHeader = JSON.parseObject(headJson,GlobalHeader.class);
								if(StringUtils.isNotBlank(globalHeader.getToken())) {
									 flag =	this.checkUrlPower(globalHeader.getUserId(),globalHeader.getToken(),request);
									if(!flag) {
										 throw new BusinessException(Result.SYS_FAILD,"您没有权限访问!");
									}
								}else {
									//检查是否在排除的路径中
									 flag = this.checkUrlIsNotLogin(getUri(request),request);
									if(!flag) {
										   throw new BusinessException(Result.SYS_FAILD,"您没有权限访问!");
									 }
								}
						  }else {
							  throw new BusinessException(Result.SYS_FAILD,"您没有权限访问!");
						  }
			         }
					}catch (BusinessException e) {
						logger.error("检查权限异常{}",e);
						this.writeJson(e.getCode(),e.getMessage(), response);
						return false;
					}catch (Exception e) {
						this.writeJson(Result.SYS_FAILD,"授权失败！", response);
						logger.error("检查权限异常{}",e);
						return false;
					}
				
		}
		return true;
	}
	

	/** 
	 * 检查地址是否不需要登录   
	 * @Title: checkUrlIsNotLogin
	 * @param uri
	 * @param request
	 * @return boolean     
	 */
	private boolean checkUrlIsNotLogin(String uri, HttpServletRequest httpRequest) {
		  //检查路径权限
		  IRedisExtCommands repository =
						this.getMapper(IRedisExtCommands.class, httpRequest);
		 boolean filterFlag = false;
		 logger.debug("开始检查请求的URI:{}是否不需要登录",uri);
		 if(repository!=null) {
			   String cacheExcludeKey = AuthConstant.NOT_LOGIN_VIST_URLS_CACHEKEY;
				List<AuthExcludeUrl> vos =
						repository.hgetList(AuthConstant.AUTH_DICT_KEY,cacheExcludeKey,AuthExcludeUrl.class);
				if(!CollectionUtils.isEmpty(vos)){
					filterFlag =
							vos.parallelStream().filter(vo ->
							(StringUtils.isNotBlank(vo.getUrl())
							&& (uri.startsWith(vo.getUrl()) || "*".equals(vo.getUrl())))).findFirst().isPresent();
				}
		 }
		 return filterFlag;
	}



	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	private <T> T getMapper(Class<T> clazz,HttpServletRequest request)
	  {
	    BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
	    return factory.getBean(clazz);
	  }
	

	/**   
	 * @param i 
	 * @Title: writeJson   
	 * @Description: 写返回数据  
	 * @param content
	 * @param response
	 * @return void     
	 * @throws Exception 
	 */
	private void writeJson(int code, String message, HttpServletResponse response) {
		try {
			Result result = new Result();
			result.setCode(code);
			result.setMessage(message);
			response.setContentType("text/html;charset=utf-8");
			response.setStatus(403);
			response.getWriter().write(JSON.toJSONString(result));
			response.getWriter().flush();
		} catch (Exception e) {
		}
	}

	/**   
	 * @Title: checkUrlPower   
	 * @Description:检查路径权限
	 * @return void     
	 */
	@SuppressWarnings("unchecked")
	private boolean checkUrlPower(String userId,String token,HttpServletRequest httpRequest){
		//检查权限排除路径地址
		String url = getUri(httpRequest);
		logger.debug("开始检查请求的URI:{}是否具有访问权限",url);
	    //检查路径权限
		IRedisExtCommands repository =
				this.getMapper(IRedisExtCommands.class, httpRequest);
		boolean filterFlag = false;
		if(repository!=null){
			String cacheExcludeKey = AuthConstant.EXCLUDE_VIST_URLS_CACHEKEY;
			List<AuthExcludeUrl> vos =
					repository.hgetList(AuthConstant.AUTH_DICT_KEY,cacheExcludeKey,AuthExcludeUrl.class);
			if(!CollectionUtils.isEmpty(vos)){
				filterFlag =
						vos.parallelStream().filter(vo ->
						(StringUtils.isNotBlank(vo.getUrl())
						&& (url.startsWith(vo.getUrl()) || "*".equals(vo.getUrl())))).findFirst().isPresent();
			}
			if(!filterFlag){
				String cacheKey = AuthConstant.USER_VIST_ONLY_URL_CACHEKEY;
				List<String> values = repository.hgetList(cacheKey,userId,String.class);
				if(!CollectionUtils.isEmpty(values)){
					filterFlag =
							values.parallelStream().filter(vo ->
							(StringUtils.isNotBlank(vo)
							&& (url.equals(vo)))).findFirst().isPresent();
			}
		}
	  }
	 return filterFlag;
	}


    /**
     * Retrieves the current request servlet path.
     * Deals with differences between servlet specs (2.2 vs 2.3+)
     *
     * @param request the request
     * @return the servlet path
     */
    public static String getServletPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        
        String requestUri = request.getRequestURI();
        // Detecting other characters that the servlet container cut off (like anything after ';')
        if (requestUri != null && servletPath != null && !requestUri.endsWith(servletPath)) {
            int pos = requestUri.indexOf(servletPath);
            if (pos > -1) {
                servletPath = requestUri.substring(requestUri.indexOf(servletPath));
            }
        }
        
        if (null != servletPath && !"".equals(servletPath)) {
            return servletPath;
        }
        
        int startIndex = request.getContextPath().equals("") ? 0 : request.getContextPath().length();
        int endIndex = request.getPathInfo() == null ? requestUri.length() : requestUri.lastIndexOf(request.getPathInfo());

        if (startIndex > endIndex) { // this should not happen
            endIndex = startIndex;
        }

        return requestUri.substring(startIndex, endIndex);
    }

    /**
     * Gets the uri from the request
     *
     * @param request The request
     * @return The uri
     */
    public static String getUri(HttpServletRequest request) {
        // handle http dispatcher includes.
        String uri = (String) request
                .getAttribute("javax.servlet.include.servlet_path");
        if (uri != null) {
            return uri;
        }

        uri = getServletPath(request);
        if (uri != null && !"".equals(uri)) {
            return uri;
        }

        uri = request.getRequestURI();
        return uri.substring(request.getContextPath().length());
    }
}
