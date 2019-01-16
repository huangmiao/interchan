package com.petecat.interchan.pay.common.wechat.utils;

import java.util.SortedMap;

/**
 * @ClassName: ConfigUtil
 * @Description:微信得配置
 * @author: mhuang
 * @date: 2018年4月17日 下午2:51:26
 */
public class WechatConfigUtil {

    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 基础参数
     *
     * @param packageParams void
     * @Author 科帮网
     * @Date 2017年7月31日 更新日志
     * 2017年7月31日  科帮网 首次创建
     */
    public static void commonParams(SortedMap<Object, Object> packageParams, String appId, String mchId) {
        // 账号信息
        // 生成随机字符串
        String currTime = PayCommonUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = PayCommonUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;
        packageParams.put("appid", appId);// 公众账号ID
        packageParams.put("mch_id", mchId);// 商户号
        packageParams.put("nonce_str", nonce_str);// 随机字符串
    }
}
