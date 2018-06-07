package com.petecat.interchan.pay.common.dto;

import lombok.Data;

@Data
public class BasePayDTO {

	private String mode; //方式
	
	private String mchId; //商户id
	
	private String notifyUrl;//通知url
	
	private String tradeNo;//订单号
	
	private String amount;//价钱
	
	private String subject;//商品名
	
	private String timeout;//超时时间
	
	private String body;//内容
	
	private String proxyIp;//代理ip
	
	private int proxyPort;//代理得端口
}
