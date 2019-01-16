package com.petecat.interchan.wechat.common.execute;

import com.petecat.interchan.wechat.spring.SpringContextUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * spring反射
 *
 * @author mHuang
 */
public class SpringExecute extends InvokeExecute {


    /**
     * @param beanName   调用的Spring的beanString
     * @param methodName 调用的方法
     * @param params     调用的参数。
     * @return 返回类型
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMethodToValue(String beanName,
                                         String methodName,
                                         Object... params)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException {
        Object bean = SpringContextUtil.getBean(beanName);
        if (params != null && params.length > 0) {
            Class<?>[] clazzes = getClasses(params);
            Method method = bean.getClass().getMethod(methodName, clazzes);
            return (T) method.invoke(bean, params);
        } else {
            Method method = bean.getClass().getMethod(methodName);
            return (T) method.invoke(bean);
        }
    }

}
