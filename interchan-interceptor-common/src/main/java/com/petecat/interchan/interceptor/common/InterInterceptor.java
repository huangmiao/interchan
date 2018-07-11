package com.petecat.interchan.interceptor.common;

import com.petecat.interchan.auth.common.interceptor.OpAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 拦截器配置
 */
@Configuration
public class InterInterceptor extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> includeUrls =  env.getProperty("intercptor.includeUrls",List.class);
        List<String> excludeUrls = env.getProperty("intercptor.excludeUrls",List.class);
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new OpAuthInterceptor());
;        if(!CollectionUtils.isEmpty(excludeUrls)){
            interceptorRegistration.excludePathPatterns(excludeUrls.toArray(new String[excludeUrls.size()]));
        }
        if(!CollectionUtils.isEmpty(includeUrls)){
            interceptorRegistration.addPathPatterns(includeUrls.toArray(new String[includeUrls.size()]));
        }
        super.addInterceptors(registry);
    }
}
