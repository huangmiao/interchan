package com.petecat.interchan.wechat.wechat.common.pool;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mHuang
 * @version V1.0.0
 * @Description 微信处理类（JDK版）
 * @date 2015年6月8日 上午10:45:51
 */
@Component
public class WechatExecutor extends ExecutorEventWechat {


    private ExecutorService executorService = Executors.newCachedThreadPool();

    public WechatExecutor() {
        setEService(executorService);
    }

}
