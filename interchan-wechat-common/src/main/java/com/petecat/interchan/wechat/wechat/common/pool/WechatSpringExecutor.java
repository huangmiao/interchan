package com.petecat.interchan.wechat.wechat.common.pool;

import com.petecat.interchan.wechat.spring.SpringContextUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;

/**
 * @author mHuang
 * @version V1.0.0
 * @Description 微信处理类（Spring版）
 * @date 2015年6月8日 上午10:45:51
 */
public class WechatSpringExecutor extends ExecutorEventWechat {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor = SpringContextUtil.getBean("threadPoolTaskExecutor", ThreadPoolTaskExecutor.class);

    private ExecutorService executorService = threadPoolTaskExecutor.getThreadPoolExecutor();

    public WechatSpringExecutor() {
        this.setEService(executorService);
    }
}