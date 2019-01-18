package com.petecat.interchan.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @package: com.petecat.interchan.jwt
 * @author: mhuang
 * @Date: 2018/12/27 15:20
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(value = JwtProperties.class)
@ConditionalOnProperty(prefix = "interchan.jwt", value = "enabled", matchIfMissing = true)
public class JwtAutoConfiguration {

    private final JwtProperties jwtProperties;

    public JwtAutoConfiguration(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtProperties jwtProperties() {
        return this.jwtProperties;
    }
}

