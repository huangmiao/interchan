package com.petecat.interchan.auth.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	private final int TOKEN_HEADER_LENGTH = 7;
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
				Jwt jwt = getMapper(Jwt.class, request);
				String auth = request.getHeader(jwt.getType());
				if(StringUtils.isBlank(auth)){
					httpRequest.putHeader(Global.GLOBAL_HEADER, JSON.toJSONString(globalHeader));
				}else if(StringUtils.length(auth) > TOKEN_HEADER_LENGTH){
					String token = StringUtils.substringAfter(auth, jwt.getHeaderName());
					try {
						Claims claims = JwtHelper.parseJWT(token, jwt.getBase64Secret());
						if(claims != null){
							globalHeader.setToken(token);
							globalHeader.setType((String)claims.get("type"));
							globalHeader.setCompanyId((String)claims.get(Global.COMPANY_ID));
							globalHeader.setUserId((String) claims.get(Global.USERID));
							httpRequest.putHeader(Global.GLOBAL_HEADER, JSON.toJSONString(globalHeader));
						}else{
							throw new BusinessException(Result.TOKEN_IS_VALID,JSON.toJSONString(Result.tokenValid()));
						}
					} catch(ExpiredJwtException e){
	                	logger.error("token已过期:{}",e);
	                	throw new BusinessException(Result.TOKEN_EXPIRED,JSON.toJSONString(Result.tokenExpired()));  
	                } catch (Exception e) {
	                	logger.error("token异常:{}",e);
	                	throw new BusinessException(Result.TOKEN_IS_VALID,JSON.toJSONString(Result.tokenValid()));  
					}
				}else{
					logger.error("token:{}无效，长度不一致",auth);
					throw new BusinessException(Result.TOKEN_IS_VALID,JSON.toJSONString(Result.tokenValid()));   
				}
				chain.doFilter(httpRequest, httpResponse);
			}catch(BusinessException e){
				Result<?> result = DataUtils.copyTo(e, Result.class);
				response.getWriter().write(JSON.toJSONString(result));
			}
			
		}
	}

	@Override
	public void destroy() {
		
	}
	
	private <T> T getMapper(Class<T> clazz,HttpServletRequest request)
	  {
	    BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
	    return factory.getBean(clazz);
	  }
}
