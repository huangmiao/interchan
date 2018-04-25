package com.petecat.interchan.pay.common.ali;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.petecat.interchan.pay.common.dto.AliPayDTO;
import com.petecat.interchan.pay.common.exception.InterchanPayException;

/**
 * 
 * @ClassName:  AlipayServer   
 * @Description: 阿里支付服务
 * @author: mhuang
 * @date:   2018年4月17日 上午10:56:32
 */
public class AlipayServer {

	private static final String GATEWAY = "https://openapi.alipay.com/gateway.do";
	private static final String FORMAT = "json";
	private static final String CHARSET = "UTF-8";
	private static final String SIGN_TYPE = "RSA2";
	private static final String PRODUCT_CODE = "QUICK_MSECURITY_PAY";
	
	private final static String APP_MODE = "app";
	public static String payment(AliPayDTO dto) throws Exception {
		AlipayClient client = getClient(dto.getMchId(),dto.getRsaPrivateKey(),dto.getRsaPublicKey());
		if(APP_MODE.equals(dto.getMode())){
			return createAppPay(client,dto.getBody(),dto.getSubject(),dto.getTradeNo(),dto.getTradeNo(),dto.getTimeout(),dto.getAmount(),dto.getNotifyUrl());
		}
		throw new InterchanPayException(500,"暂不支持支付"+dto.getMode()+"方式");
	}
	
	public static String createAppPay(AlipayClient alipayClient,String body,String subject,String tradeNo,String timeout,String amount,String notifyUrl) throws AlipayApiException{
		return createAppPay(alipayClient, body, subject, tradeNo, timeout,amount, PRODUCT_CODE,notifyUrl); 
	}
	
	public static String createAppPay(AlipayClient alipayClient,String body,String subject,String tradeNo,String timeout,String amount,String productCode,String notifyUrl) throws AlipayApiException{
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(body);
		model.setSubject(subject);
		model.setOutTradeNo(tradeNo);
		model.setTimeoutExpress(timeout);
		model.setTotalAmount(amount);
		model.setProductCode(productCode);
		request.setBizModel(model);
		request.setNotifyUrl(notifyUrl);
        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        return response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
	}
	
	public static AlipayClient getClient(String appId,String rsaPrivateKey,String rsaPublicKey){
		return getClient(GATEWAY, appId, rsaPrivateKey, FORMAT, CHARSET, rsaPublicKey, SIGN_TYPE);
	}
	
	public static AlipayClient getClient(String gateway,String appId,String rsaPrivateKey,String format,String charset,String rsaPublicKey,String signType){
		return new DefaultAlipayClient(gateway, appId, rsaPrivateKey, format, charset, rsaPublicKey, signType);
	}
}
