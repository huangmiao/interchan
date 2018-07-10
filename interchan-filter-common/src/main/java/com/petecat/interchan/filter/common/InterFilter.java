package com.petecat.interchan.filter.common;

import com.petecat.interchan.auth.common.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 互联网通用拦截器-只支持boot
 */
@Component
public class InterFilter {

    @Autowired
    private Environment env;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new AuthFilter());
        registrationBean.setUrlPatterns(env.getProperty("filter.includeUrls",Collection.class, Stream.of("/*").collect(Collectors.toList())));
        return registrationBean;
    }
}
