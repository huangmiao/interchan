package com.petecat.interchan.pay.common.wechat;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.petecat.interchan.pay.common.dto.WechatPayDTO;
import com.petecat.interchan.pay.common.exception.InterchanPayException;
import com.petecat.interchan.pay.common.wechat.utils.CommonUtil;
import com.petecat.interchan.pay.common.wechat.utils.HttpUtil;
import com.petecat.interchan.pay.common.wechat.utils.PayCommonUtil;
import com.petecat.interchan.pay.common.wechat.utils.WechatConfigUtil;
import com.petecat.interchan.pay.common.wechat.utils.XMLUtil;

/**
 * 
 * @ClassName:  WechatPayServer   
 * @Description:微信支付服务
 * @author: mhuang
 * @date:   2018年4月17日 上午11:43:29
 */
public class WechatPayServer {

	private final static String APP_MODE = "APP";
	
	private final static List<String> SUPPORT_MODE_LIST = Stream.of(APP_MODE).collect(Collectors.toList());
	public static Map<?, ?> payment(WechatPayDTO dto) throws Exception {
		SortedMap<Object, Object> packageParams = createCommonParmas(dto.getAppId(),dto.getMchId());
		if(SUPPORT_MODE_LIST.contains(dto.getMode())){
			if(APP_MODE.equals(dto.getMode())){
				return createAppPay(packageParams, dto.getTradeNo(), dto.getSubject(), dto.getAmount(), dto.getApiKey(), dto.getIp(), dto.getNotifyUrl());
			}
		}
		throw new InterchanPayException(500,"暂不支持支付"+dto.getMode()+"方式");
	}
	
	public static SortedMap<Object, Object> createCommonParmas(String appId,String mchId){
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		WechatConfigUtil.commonParams(packageParams,appId,mchId);
		return packageParams;
	}
	
	public static Map<?, ?> createAppPay(SortedMap<Object, Object> packageParams,
			String tradeNo,String subject,String totalFee,String apiKey,
			String ip,String notifyUrl) throws Exception{
		return createAppPay(packageParams, tradeNo, subject, totalFee, apiKey, ip, notifyUrl, null, 0);
	}
	
	public static Map<?, ?> createAppPay(SortedMap<Object, Object> packageParams,
		String tradeNo,String subject,String totalFee,String apiKey,
		String ip,String notifyUrl,String proxyIp,int proxyPort) throws Exception{
		// 账号信息
		packageParams.put("body", subject);// 商品描述
		packageParams.put("out_trade_no", tradeNo);// 商户订单号
		totalFee =  CommonUtil.subZeroAndDot(totalFee);
		packageParams.put("total_fee", totalFee);// 总金额
		//H5支付要求商户在统一下单接口中上传用户真实ip地址 spbill_create_ip
		packageParams.put("spbill_create_ip", ip);// 发起人IP地址
		packageParams.put("notify_url", notifyUrl);// 回调地址
		packageParams.put("trade_type", APP_MODE);// 交易类型
		
		String sign = PayCommonUtil.createSign("UTF-8", packageParams, apiKey); //密匙
		packageParams.put("sign", sign);// 签名
		String requestXML = PayCommonUtil.getRequestXml(packageParams);
		String resXml = HttpUtil.postData(WechatConfigUtil.UNIFIED_ORDER_URL, requestXML,null,proxyIp,proxyPort);
		return XMLUtil.doXMLParse(resXml);
	}
}