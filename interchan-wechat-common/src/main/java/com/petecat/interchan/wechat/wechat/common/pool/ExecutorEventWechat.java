package com.petecat.interchan.wechat.wechat.common.pool;

import com.petecat.interchan.wechat.common.execute.JDKExecute;
import com.petecat.interchan.wechat.common.execute.SpringExecute;
import com.petecat.interchan.wechat.wechat.common.pool.service.ExecuteService;
import com.petecat.interchan.wechat.wechat.common.pool.thread.ShareThread;
import com.petecat.interchan.wechat.wechat.common.pool.thread.SubscribeThread;
import com.petecat.interchan.wechat.wechat.common.pool.thread.TextThread;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;

/**
 * @author huang.miao
 * @Package: com.petecat.interchan.wechat.common.pool
 * @Description 微信执行事件
 * @date 2017年2月8日 下午2:06:43
 * @group skiper-opensource
 * @since 1.0.0
 */
@Component
public class ExecutorEventWechat {

    @Setter
    @Getter
    private ExecutorService eService;

    public void subscribe(String openId, String status, ExecuteService weChatService) {
        eService.execute(new SubscribeThread(openId, status, weChatService));
    }

    public void subscribeEventOther(String openId, String status, String eventKey, ExecuteService weChatService) {
        eService.execute(new SubscribeThread(openId, status, eventKey, weChatService));
    }

    public void textMsg(String openId, String content, ExecuteService weChatService) {
        eService.execute(new TextThread(openId, content, weChatService));
    }

    public void share(String usrId, String status, String type, String shareName, String uuid, ExecuteService weChatService) {
        eService.execute(new ShareThread(usrId, status, type, shareName, uuid, weChatService));
    }

    /**
     * 其他方式调用线程池处理（异步）
     *
     * @param clazz  需要调用的class（支持直接传class，采用jdk反射调用方式，传入beanName 代表使用spring代码方式）
     *               spring代理方式支持service（dao 自动引入）方式 。 JDK 需要自己做处理
     * @param method 调用的方法
     */
    public void other(final Object clazz, final String method) {
        eService.execute(() -> localCall(clazz,method));
    }

    /**
     * 其他方式调用线程池处理（异步）
     *
     * @param clazz  需要调用的class（支持直接传class，采用jdk反射调用方式，传入beanName 代表使用spring代码方式）
     *               spring代理方式支持service（dao 自动引入）方式 。 JDK 需要自己做处理
     * @param method 调用的方法
     * @param param  传输的值
     */
    public void other(final Object clazz, final String method, final Object param) {
        eService.execute(() -> localCall(clazz, method, param));
    }

    private void localCall(Object clazz, String method, Object... param) {
        if (clazz instanceof Class<?>) {
            try {
                JDKExecute.getMethodToValue((Class<?>) clazz, method, param);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            try {
                SpringExecute.getMethodToValue((String) clazz, method, param);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 其他方式调用线程池处理（异步）
     *
     * @param clazz  需要调用的class（支持直接传class，采用jdk反射调用方式，传入beanName 代表使用spring代码方式）
     *               spring代理方式支持service（dao 自动引入）方式 。 JDK 需要自己做处理
     * @param method 调用的方法
     * @param params 传输的值
     */
    public void other(final Object clazz, final String method, final Object... params) {
        eService.execute(() -> localCall(clazz, method, params));
    }
}
