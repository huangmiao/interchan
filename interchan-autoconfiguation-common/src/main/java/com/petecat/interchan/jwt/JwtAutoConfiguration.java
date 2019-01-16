package com.petecat.interchan.jwt;

import com.petecat.interchan.jwt.model.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
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
@Slf4j
@Configuration
@EnableConfigurationProperties(value = JwtProperties.class)
@AutoConfigureAfter(value = {Jwt.class,Token.class})
@ConditionalOnProperty(prefix = "interchan.jwt", value = "enabled", matchIfMissing = true)
public class JwtAutoConfiguration {

    private final JwtProperties jwtProperties;

    public JwtAutoConfiguration(JwtProperties jwtProperties) {
        log.info("loading jwt");
        this.jwtProperties = jwtProperties;
        log.info("load jwt success");
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtProperties jwtProperties() {
        return this.jwtProperties;
    }
}

