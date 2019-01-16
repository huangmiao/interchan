package com.petecat.interchan.wechat.wechat.common.model.menu;

import com.alibaba.fastjson.annotation.JSONField;
import com.petecat.interchan.wechat.wechat.common.consts.WechatConsts;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author huang.miao
 * @Package: com.petecat.interchan.wechat.common.menu
 * @Description 菜单
 * @date 2016年12月22日 上午10:56:23
 * @group skiper-opensource
 * @since 1.0.0
 */
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = WechatConsts.BUTTON)
    private List<Button> button = new LinkedList<Button>();

    public void buttonAdd(String type, String name, String key) {
        button.add(Button.add(type, name, key));
    }

    public Button buttonAddSub(String subName, String type, String name, String key) {
        return Button.subButton(subName, type, name, key);
    }
}
