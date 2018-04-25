package com.petecat.interchan.pay.common;

import java.util.Map;
import java.util.SortedMap;

import org.junit.Test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.petecat.interchan.pay.common.ali.AlipayServer;
import com.petecat.interchan.pay.common.wechat.WechatPayServer;

public class Ceshi {
	
	@Test
	public void setUp(){
		//支付步骤
		AlipayClient client = AlipayServer.getClient("商户号", "商户私匙", "商户公匙");
		try {
			String body = AlipayServer.createAppPay(client, "内容", 
					"商品的标题/交易标题/订单标题/订单关键字等", 
					"订单号", 
					null,//					"该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。注：若为空，则默认为15d。",
					"1.00",//					"支付金额。单位元。精确到分。两位小数", 
					"通知处理得url");
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		
		SortedMap<Object, Object> params = WechatPayServer.createCommonParmas("公众号", "商户号");
		try {
			Map map = WechatPayServer.createAppPay(params, 
					"订单号", 
					"传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。", 
					"支付金额，单位为分。", 
					"支付得密匙", 
					"用户端实际ip", 
					"通知处理得url");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
