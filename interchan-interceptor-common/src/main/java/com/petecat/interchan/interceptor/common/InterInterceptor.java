package com.petecat.interchan.interceptor.common;

import com.petecat.interchan.auth.common.interceptor.OpAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 拦截器配置
 */
@Configuration
public class InterInterceptor extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> includeUrls =  env.getProperty("intercptor.includeUrls",List.class,Stream.of("/**").collect(Collectors.toList()));
        registry.addInterceptor(new OpAuthInterceptor()).addPathPatterns(includeUrls.toArray(new String[includeUrls.size()]));
        super.addInterceptors(registry);
    }
}
