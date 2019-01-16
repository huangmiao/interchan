package com.petecat.interchan.pay.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WechatPayDTO extends BasePayDTO{

	private String appId;
	//公众id
	
	private String apiKey;
	//支付密匙

	private String ip;
	//支付得ip地址
	
	private String openId;
	//jsapi 支付的时候使用
	
}
