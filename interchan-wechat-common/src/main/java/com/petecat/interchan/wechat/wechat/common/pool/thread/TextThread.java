package com.petecat.interchan.wechat.wechat.common.pool.thread;

import com.petecat.interchan.wechat.wechat.common.pool.service.ExecuteService;

/**
 * @author mHuang
 * @version V1.0.0
 * @Description 文本消息
 * @date 2015年6月8日 上午11:38:35
 */
public class TextThread extends BaseThread {

    private String content;

    public TextThread(String openId, String content, ExecuteService weChatService) {
        super(openId, weChatService);
        this.content = content;
    }

    @Override
    public void run() {//文本消息保存的接口
        weChatService.saveOpTextSend(openId, content);
    }
}
