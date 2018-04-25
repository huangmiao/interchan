package com.petecat.interchan.pay.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @ClassName:  AliPayDTO   
 * @Description:支付宝支付
 * @author: mhuang
 * @date:   2018年4月18日 下午3:14:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AliPayDTO extends BasePayDTO{

	private String mode; //请求方式
	
	private String rsaPublicKey;//公匙
	
	private String rsaPrivateKey;//私匙
	
	
}
