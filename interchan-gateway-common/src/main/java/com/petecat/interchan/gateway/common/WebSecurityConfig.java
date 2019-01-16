package com.petecat.interchan.gateway.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @package: com.petecat.interchan
 * @author: mhuang
 * @Date: 2019/1/3 13:54
 * @Description:权限过滤
 */
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Autowired
    private Environment env;

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        List<String> includeUrls = env.getProperty("interchan.security.includeUrls", List.class,
                Stream.of("/monitor/**").collect(Collectors.toList()));
        http.authorizeExchange().pathMatchers(includeUrls.toArray(new String[includeUrls.size()])).hasRole("ADMIN")
                .anyExchange().permitAll().and().cors().and()
                .httpBasic().and()
                .csrf().disable();
        return http.build();
    }
}
