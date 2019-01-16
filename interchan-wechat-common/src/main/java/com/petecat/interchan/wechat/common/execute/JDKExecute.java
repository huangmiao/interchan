package com.petecat.interchan.wechat.common.execute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * JDK反射封裝
 *
 * @author mHuang
 */
public class JDKExecute extends InvokeExecute {


    /**
     * @param clazz      调用的class类
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
    public static <T> T getMethodToValue(Class<?> clazz,
                                         String methodName,
                                         Object... params)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException {
        if (params != null && params.length > 0) {
            Class<?>[] clazzes = getClasses(params);
            Method method = clazz.getMethod(methodName, clazzes);
            return (T) method.invoke(clazz.newInstance(), params);
        } else {
            Method method = clazz.getMethod(methodName);
            return (T) method.invoke(clazz.newInstance());
        }
    }
}
