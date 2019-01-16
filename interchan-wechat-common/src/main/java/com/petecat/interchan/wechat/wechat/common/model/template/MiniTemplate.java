package com.petecat.interchan.wechat.wechat.common.model.template;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huang.miao
 * @Package: com.petecat.interchan.wechat.common.model.template
 * @Description 小程序模板管理
 * @date 2017年2月8日 上午9:39:56
 * @group skiper-opensource
 * @since 1.0.0
 */
@Data
public class MiniTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    private String touser;//发送的openid

    @JSONField(name = "template_id")
    private String templateId = "";//模板id


    private String page; //跳转的页面

    @JSONField(name = "form_id")
    private String formId; //表单场景


    @JSONField(name = "emphasis_keyword")
    private String emphasisKeyword; //高亮

    private String color;//默认颜色

    private Map<String, Map<String, String>> data = new HashMap<>();

    private final static String DEFAULT_COLOR = "#173177";
    private final static String FIRST = "first";
    private final static String KEYWORD = "keyword";
    private final static String VALUE = "value";
    private final static String COLOR = "color";
    private final static String REMARK = "remark";

    public void addKeyword(Integer index, String value) {
        addKeyword(index, value, DEFAULT_COLOR);
    }

    public void addOther(String name, String value) {
        addOther(name, value, DEFAULT_COLOR);
    }

    public void addOther(String name, String value, String color) {
        addDataMap(name, value, color);
    }

    public void addKeyword(Integer index, String value, String color) {
        addDataMap(KEYWORD + index, value, color);
    }

    public void addRemark(String value) {
        addRemark(value, DEFAULT_COLOR);
    }

    public void addRemark(String value, String color) {
        addDataMap(REMARK, value, color);
    }

    public void addFirst(String value) {
        addFirst(value, DEFAULT_COLOR);
    }

    public void addFirst(String value, String color) {
        addDataMap(FIRST, value, color);
    }

    private void addDataMap(String type, String value, String color) {
        Map<String, String> map = new HashMap<>();
        map.put(VALUE, value);
        map.put(COLOR, color);
        data.put(type, map);
    }
}
