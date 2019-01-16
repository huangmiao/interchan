package com.petecat.interchan.wechat.wechat.common.model.message;

import com.petecat.interchan.wechat.wechat.common.consts.WechatConsts;
import com.petecat.interchan.wechat.wechat.common.model.message.child.BaseChildMessage;
import com.petecat.interchan.wechat.wechat.common.model.message.child.BaseOtherMessage;
import com.petecat.interchan.wechat.wechat.common.model.message.child.Music;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author mHuang
 * @version V1.0.0
 * @Description 其他响应消息
 * @date 2015年6月4日 下午5:05:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OtherResMessage<T> extends BaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private T otherMessage;

    public OtherResMessage() {

    }

    public OtherResMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName);
    }

    /**
     * @param mediaId
     * @param msgType
     * @Description 根据类型进行添加
     * @author mHuang
     */
    @Deprecated
    public void saveType(String mediaId, String msgType) {
        baseSave(mediaId, msgType);
    }

    /**
     * @param mediaId
     * @Description 响应图片消息
     * @author mHuang
     */
    public void saveImage(String mediaId) {
        baseSave(mediaId, WechatConsts.IMAGE);
    }

    /**
     * @Description 响应语音消息
     * @author mHuang
     */
    public void saveVoice(String mediaId) {
        baseSave(mediaId, WechatConsts.VOICE);
    }

    /**
     * @param mediaId
     * @Description 响应视频消息
     * @author mHuang
     */
    public void saveVideo(String mediaId, String title, String descption) {
        baseSaveTitle(mediaId, title, descption, WechatConsts.VIDEO);
    }

    /**
     * @param title
     * @param descption
     * @param musicUrl
     * @param hQMusicUrl
     * @param thumbMediaId
     * @param msgType
     * @Description 响应音乐消息
     * @author mHuang
     */
    @SuppressWarnings("unchecked")
    public void saveMusic(String title, String descption, String musicUrl, String hQMusicUrl, String thumbMediaId) {
        setMsgType(WechatConsts.MUSIC);
        otherMessage = (T) Music.setMusicMessage(title, descption, musicUrl, hQMusicUrl, thumbMediaId);
    }

    @SuppressWarnings("unchecked")
    private void baseSave(String mediaId, String msgType) {
        setMsgType(msgType);
        otherMessage = (T) BaseOtherMessage.setMedia(mediaId);
    }

    @SuppressWarnings("unchecked")
    private void baseSaveTitle(String mediaId, String title, String descption, String msgType) {
        setMsgType(msgType);
        otherMessage = (T) BaseChildMessage.setChildMessage(mediaId, title, descption);
    }
}
