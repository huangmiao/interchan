package com.petecat.interchan.wechat.wechat.common.pool;

import java.util.concurrent.ExecutorService;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.petecat.interchan.wechat.spring.SpringContextUtil;

/**
 * 
 * @Description 微信处理类（Spring版）
 * @author mHuang
 * @date 2015年6月8日 上午10:45:51 
 * @version V1.0.0
 */
public class WechatSpringExecutor extends ExecutorEventWechat{
	
	private ThreadPoolTaskExecutor threadPoolTaskExecutor= SpringContextUtil.getBean("threadPoolTaskExecutor",ThreadPoolTaskExecutor.class);
	
	private ExecutorService executorService = threadPoolTaskExecutor.getThreadPoolExecutor();

	public WechatSpringExecutor(){
		this.setEService(executorService);
	}
}