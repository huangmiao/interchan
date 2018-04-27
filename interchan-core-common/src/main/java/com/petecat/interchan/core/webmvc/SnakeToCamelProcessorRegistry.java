package com.petecat.interchan.core.webmvc;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * 
 * @ClassName:  SnakeToCamelProcessorRegistry   
 * @Description:下划线转驼峰注册器
 * @author: mhuang
 * @date:   2018年4月27日 下午2:58:16
 */
public class SnakeToCamelProcessorRegistry implements ApplicationContextAware, BeanFactoryPostProcessor {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
        List<HandlerMethodArgumentResolver> resolvers = requestMappingHandlerAdapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> newResolvers = resolvers.parallelStream().collect(Collectors.toList());
        SnakeToCamelModelAttributeMethodProcessor processor = new SnakeToCamelModelAttributeMethodProcessor(true);
        processor.setApplicationContext(applicationContext);
        newResolvers.add(0, processor);
        requestMappingHandlerAdapter.setArgumentResolvers(Collections.unmodifiableList(newResolvers));
    }
}