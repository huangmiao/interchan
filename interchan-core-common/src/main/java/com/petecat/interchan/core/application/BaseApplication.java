package com.petecat.interchan.core.application;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 
 * @ClassName:  BaseApplication   
 * @Description:跨域问题解决
 * @author: mhuang
 * @date:   2018年7月2日 下午1:51:50
 */
public class BaseApplication {
	
	private CorsConfiguration buildConfig() {  
	    CorsConfiguration corsConfiguration = new CorsConfiguration();  
	    corsConfiguration.addAllowedOrigin("*");  
	    corsConfiguration.addAllowedHeader("*");  
	    corsConfiguration.addAllowedMethod("*");
	    return corsConfiguration;
	}  

	/** 
	 * 跨域过滤器 
	 * @return 
	 */  
	@Bean  
	public CorsFilter corsFilter() {  
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
	    source.registerCorsConfiguration("/**", buildConfig()); 
	    return new CorsFilter(source);  
	}  
}
