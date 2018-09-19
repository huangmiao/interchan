package com.petecat.interchan.auth.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.mhuang.common.util.DataUtils;
import com.mhuang.common.webmvc.WebRequestHeader;
import com.petecat.interchan.core.constans.Global;
import com.petecat.interchan.core.exception.BusinessException;
import com.petecat.interchan.core.local.GlobalHeaderThreadLocal;
import com.petecat.interchan.jwt.JwtHelper;
import com.petecat.interchan.jwt.model.Jwt;
import com.petecat.interchan.protocol.GlobalHeader;
import com.petecat.interchan.protocol.Result;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * 
 * @ClassName:  AuthFilter   
 * @Description:通用权限处理 
 * @author: mhuang
 * @date:   2017年10月27日 上午10:29:24
 */
public class AuthFilter implements Filter{

	private Logger logger  = LoggerFactory.getLogger(getClass());

	@Setter
	@Getter
	private List<String> excludeUrl = new ArrayList<>();

	private final int TOKEN_HEADER_LENGTH = 7;

	private final String SOURCE_FIELD = "source";
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(req instanceof HttpServletRequest) {
			try{
				HttpServletRequest request = (HttpServletRequest) req;
				WebRequestHeader httpRequest = new WebRequestHeader(request);
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				logger.info("请求的URL:{},请求类型:{}",request.getRequestURL().toString(),request.getMethod());
				GlobalHeader globalHeader = new GlobalHeader();
				globalHeader.setIp(IpUtils.getIp(request));
				globalHeader.setSource(request.getHeader("source"));
				Jwt jwt = getMapper(Jwt.class, request);
				String auth = request.getHeader(jwt.getType());

				if(StringUtils.isBlank(auth)){
					GlobalHeaderThreadLocal.set(globalHeader);
					httpRequest.putHeader(Global.GLOBAL_HEADER, JSON.toJSONString(globalHeader));
				}else if(StringUtils.indexOf(auth,"Basic") == 0){
					GlobalHeaderThreadLocal.set(globalHeader);
					httpRequest.putHeader(Global.GLOBAL_HEADER, JSON.toJSONString(globalHeader));
				}else if(StringUtils.length(auth) > TOKEN_HEADER_LENGTH){
					logger.debug("当前调用的token:{}",auth);
					String token = StringUtils.substringAfter(auth, jwt.getHeaderName());
					try {
						Map<String,Object> claimMap = JwtHelper.parseJWT(token, jwt.getBase64Secret());
						if(claimMap != null){
							globalHeader.setToken(token.trim());
							globalHeader.setType((String) claimMap.get("type"));
							globalHeader.setCompanyId((String)claimMap.get(Global.COMPANY_ID));
							globalHeader.setUserId((String) claimMap.get(Global.USERID));
							GlobalHeaderThreadLocal.set(globalHeader);
							httpRequest.putHeader(Global.GLOBAL_HEADER, JSON.toJSONString(globalHeader));
						}else{
							throw new BusinessException(Result.TOKEN_IS_VALID,Result.TOKEN_IS_VALID_MSG);
						}
					} catch(ExpiredJwtException e){
	                	logger.error("token已过期:{}",e);
	                	throw new BusinessException(Result.TOKEN_EXPIRED,Result.TOKEN_EXPIRED_MSG);  
	                } catch (Exception e) {
	                	logger.error("token异常:{}",e);
	                	throw new BusinessException(Result.TOKEN_IS_VALID,Result.TOKEN_IS_VALID_MSG);  
					}
				}else{
					logger.error("token:{}无效，长度不一致",auth);
					throw new BusinessException(Result.TOKEN_IS_VALID,Result.TOKEN_IS_VALID_MSG);   
				}
				chain.doFilter(httpRequest, httpResponse);
			}catch(BusinessException e){
				response.setContentType("text/json; charset=utf-8");
				Result<?> result = DataUtils.copyTo(e, Result.class);
				response.getWriter().write(JSON.toJSONString(result));
			}
		}
	}

	@Override
	public void destroy() {
		GlobalHeaderThreadLocal.remove();
	}
	
	private <T> T getMapper(Class<T> clazz,HttpServletRequest request)
	  {
	    BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
	    return factory.getBean(clazz);
	  }
}
