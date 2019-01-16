package com.petecat.interchan.wechat.wechat.common.pool.thread;

import com.petecat.interchan.wechat.wechat.common.pool.service.ExecuteService;

/**
 * @author mHuang
 * @version V1.0.0
 * @Description 基础线程
 * @date 2015年6月8日 上午10:41:20
 */
public abstract class BaseThread implements Runnable {

    protected String openId;

    protected ExecuteService weChatService;

    public abstract void run();

    public BaseThread(String openId, ExecuteService weChatService) {
        this.openId = openId;
        this.weChatService = weChatService;
    }

    public BaseThread(ExecuteService weChatService) {
        this.weChatService = weChatService;
    }
}
