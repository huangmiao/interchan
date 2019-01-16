package com.petecat.interchan.wechat.wechat.pay.utils;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 微信支付Utils
 *
 * @author Administrator
 */
public class PayUtils {


    public static String requestUrl(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (StringUtils.isNotBlank(queryString)) {
            return request.getRequestURL().append('?').append(request.getQueryString()).toString();
        }
        return request.getRequestURL().toString();
    }

    public static Map<String, String> parseXml(String request) {
        Map<String, String> map = new HashMap<>();
        try {
            // 读取输入流
            AtomicReference<Document> document = new AtomicReference<>(DocumentHelper.parseText(request));
            parse(map, document.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private static void parse(Map<String, String> map, Document document) {
        // 得到xml根元素
        Element root = document.getRootElement();

        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        elementList.forEach(e -> map.put(e.getName(), e.getText()));
    }

}
