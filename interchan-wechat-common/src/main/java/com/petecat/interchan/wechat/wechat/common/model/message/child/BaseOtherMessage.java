package com.petecat.interchan.wechat.wechat.common.model.message.child;

import com.alibaba.fastjson.annotation.JSONField;
import com.petecat.interchan.wechat.wechat.common.consts.WechatConsts;

/**
 * @author mHuang
 * @version V1.0.0
 * @Description 子消息公共类
 * @date 2015年6月5日 上午10:55:55
 */
public class BaseOtherMessage {

    @JSONField(name = WechatConsts.MEDIA_ID)
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public static BaseOtherMessage setMedia(String mediaId) {
        BaseOtherMessage baseOtherMessage = new BaseOtherMessage();
        baseOtherMessage.setMediaId(mediaId);
        return baseOtherMessage;
    }
}
