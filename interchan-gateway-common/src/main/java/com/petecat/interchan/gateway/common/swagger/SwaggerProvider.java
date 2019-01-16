package com.petecat.interchan.gateway.common.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @package: com.petecat.interchan
 * @author: mhuang
 * @Date: 2019/1/3 14:54
 * @Description: Swagger生成生产的方式
 */
@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {

    public static final String API_URI = "/v2/api-docs";

    @Autowired(required = false)
    private PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        propertiesRouteDefinitionLocator.getRouteDefinitions().subscribe(route -> {
            String host = route.getUri().getHost();
            resources.add(swaggerResource(host,String.format("/%s%s", host, API_URI)));
        });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}