package com.petecat.interchan.wechat.wechat.common.pool.thread;

import com.petecat.interchan.wechat.wechat.common.pool.service.ExecuteService;

/**
 * @author mHuang
 * @version V1.0.0
 * @Description 扫码
 * @date 2015年6月8日 上午10:37:31
 */
public class ScanThread extends BaseThread {


    private String eventKey;//关注带事件

    public ScanThread(String openId, String eventKey, ExecuteService weChatService) {
        super(openId, weChatService);
        this.eventKey = eventKey;
    }

    @Override
    public void run() {
        synchronized (openId) {//关注后开启线程来处理关注数据
            weChatService.scan(openId, eventKey);
        }
    }

}
