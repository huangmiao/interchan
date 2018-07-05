package com.petecat.interchan.swagger;

import java.util.ArrayList;
import java.util.List;

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

@Configuration
@EnableSwagger2
public class Swagger2 {

	@Autowired
	private Environment env;
	
	@Bean
    public Docket createRestApi() {
		ParameterBuilder tokenPar = new ParameterBuilder();  
    	List<Parameter> pars = new ArrayList<>();
    	tokenPar.name("Authorization").description("Bearer 开头加上登录的时候令牌,填写时表示带用户凭证进行访问").modelRef(new ModelRef("string")).parameterType("header").required(false).build();  
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage(env.getProperty("swagger.package")))
                .paths(PathSelectors.any()).build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
    	 return new ApiInfoBuilder()
                 .title(env.getProperty("swagger.title"))
                 .description(env.getProperty("swagger.description"))
                 .contact(env.getProperty("swagger.contact"))
                 .version(env.getProperty("swagger.version"))
                 .build();
    }
}
