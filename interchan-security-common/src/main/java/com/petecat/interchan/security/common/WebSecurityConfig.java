package com.petecat.interchan.security.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@Order(-1)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> includeUrls =  env.getProperty("security.includeUrls",List.class, Stream.of("/monitor").collect(Collectors.toList()));

        http.authorizeRequests().antMatchers(includeUrls.toArray(new String[includeUrls.size()])).hasRole("ADMIN")
                .anyRequest().permitAll().and()
                .formLogin().loginPage(env.getProperty("security.loginUrl","/login")).permitAll().and()
                .logout().permitAll().and().cors().and().csrf().disable();
    }
}
