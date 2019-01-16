package com.petecat.interchan.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mhuang
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Autowired
    private Environment env;

    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder sourcePar = new ParameterBuilder();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description(env.getProperty("swagger.authorization.description", "Bearer 开头加上登录的时候令牌,填写时表示带用户凭证进行访问")).modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        sourcePar.name("source").description(env.getProperty("swagger.source.description", "用户来源")).modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        pars.add(sourcePar.build());
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage(env.getProperty("swagger.package")))
                .paths(PathSelectors.any()).build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(env.getProperty("swagger.title"))
                .description(env.getProperty("swagger.description"))
                .version(env.getProperty("swagger.version"))
                .build();
    }
}
