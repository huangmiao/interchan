package com.petecat.interchan.security.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @package: com.petecat.interchan.security.common
 * @author: mhuang
 * @Date: 2018/12/25 19:02
 * @Description: 实现权限拦截
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("loading WebSecurityConfig config");
        List<String> includeUrls = env.getProperty("interchan.security.includeUrls", List.class, Stream.of("/monitor/**").collect(Collectors.toList()));
        http.authorizeRequests().antMatchers(includeUrls.toArray(new String[includeUrls.size()])).hasRole("ADMIN")
                .anyRequest().permitAll().and()
                .cors().and()
                .httpBasic().and()
                .csrf().disable();
        log.info("load WebSecurityConfig config success");
    }
}