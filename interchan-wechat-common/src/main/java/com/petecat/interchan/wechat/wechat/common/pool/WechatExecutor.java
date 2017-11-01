package com.petecat.interchan.wechat.wechat.common.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

/**
 * 
 * @Description 微信处理类（JDK版）
 * @author mHuang
 * @date 2015年6月8日 上午10:45:51 
 * @version V1.0.0
 */
@Component
public class WechatExecutor extends ExecutorEventWechat{

	 
	private ExecutorService executorService = Executors.newCachedThreadPool();

	public WechatExecutor(){
		setEService(executorService);
	}
	
}
