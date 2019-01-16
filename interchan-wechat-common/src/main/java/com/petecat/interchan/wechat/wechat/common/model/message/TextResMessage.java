package com.petecat.interchan.wechat.wechat.common.model.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.petecat.interchan.wechat.wechat.common.consts.WechatConsts;
import com.petecat.interchan.wechat.wechat.common.model.message.child.Content;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author mHuang
 * @version V1.0.0
 * @Description 文本响应消息
 * @date 2015年6月4日 下午4:41:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TextResMessage extends BaseMessage {

    private static final long serialVersionUID = 1L;

    @JSONField(serialize = false)
    private String content;

    @JSONField(name = WechatConsts.TEXT)
    private Content contentes;


    public TextResMessage() {
        setMsgType(WechatConsts.TEXT);
    }


    public TextResMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName);
        setMsgType(WechatConsts.TEXT);
    }

    //////////////////////////////////json///////////////////////////////
    public TextResMessage(String toUser) {
        super(toUser);
    }

    public void saveJSON(String toUser, String content) {
        setToUserName(toUser);
        if (contentes == null)
            contentes = new Content();
        contentes.setContent(content);
    }
}
