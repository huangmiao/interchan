package com.petecat.interchan.wechat.wechat.common.model.message.child;

import com.alibaba.fastjson.annotation.JSONField;
import com.petecat.interchan.wechat.wechat.common.consts.WechatConsts;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * @author mHuang
 * @version V1.0.0
 * @Description 图文
 * @date 2015年6月4日 下午5:03:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XStreamAlias(WechatConsts.ITEM)
public class Article extends BaseChildMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = WechatConsts.PICURL)
    private String picUrl;

    private String url;

    public static Article getArticle(String title, String descption, String picUrl, String url) {
        Article article = new Article();
        article.setTitle(title);
        article.setDescption(descption);
        article.setPicUrl(picUrl);
        article.setUrl(url);
        return article;
    }
}
