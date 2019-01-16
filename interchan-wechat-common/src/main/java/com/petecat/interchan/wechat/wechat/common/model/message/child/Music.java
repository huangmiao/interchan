package com.petecat.interchan.wechat.wechat.common.model.message.child;

import com.alibaba.fastjson.annotation.JSONField;
import com.petecat.interchan.wechat.wechat.common.consts.WechatConsts;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * @author mHuang
 * @version V1.0.0
 * @Description 响应音乐消息
 * @date 2015年6月5日 下午1:53:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Music extends BaseChildMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = WechatConsts.MUSICURL)
    private String musicUrl;

    @JSONField(name = WechatConsts.HQMUSICURL)
    private String hQMusicUrl;

    @JSONField(name = WechatConsts.THUMB_MEDIA_URL)
    private String thumbMediaId;

    public static Music setMusicMessage(String title, String descption, String musicUrl, String hQMusicUrl, String thumbMediaId) {
        Music music = new Music();
        music.setTitle(title);
        music.setDescption(descption);
        music.setMusicUrl(hQMusicUrl);
        music.setHQMusicUrl(hQMusicUrl);
        music.setThumbMediaId(thumbMediaId);
        return music;
    }
}
