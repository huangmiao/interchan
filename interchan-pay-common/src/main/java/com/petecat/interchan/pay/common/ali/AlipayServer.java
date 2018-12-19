package com.petecat.interchan.pay.common.ali;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.petecat.interchan.pay.common.dto.AliPayDTO;
import com.petecat.interchan.pay.common.dto.AliRefundDTO;
import com.petecat.interchan.pay.common.dto.AliTransDTO;
import com.petecat.interchan.pay.common.exception.InterchanPayException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

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
			return createAppPay(client,
					dto.getBody(),
					dto.getSubject(),
					dto.getTradeNo(),dto.getTimeout(),dto.getAmount(),dto.getNotifyUrl());
		}
		throw new InterchanPayException(500,"暂不支持支付"+dto.getMode()+"方式");
	}

	/**
	 * 单笔退款
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public static String refundOrder(AliRefundDTO dto) throws Exception {
		try {
		AlipayClient alipayClient = getClient(dto.getMchId(),dto.getRsaPrivateKey(),dto.getRsaPublicKey());
		AlipayTradeRefundModel model = new AlipayTradeRefundModel();
		model.setOutTradeNo(dto.getTradeNo()); //与预授权转支付商户订单号相同，代表对该笔交易退款
		model.setRefundAmount(dto.getAmount());
		model.setRefundReason(dto.getSubject());
		model.setOutRequestNo(dto.getOutRequestNo());//标识一次退款请求，同一笔交易多次退款需要保证唯一，如部分退款则此参数必传。
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizModel(model);

			AlipayTradeRefundResponse response = alipayClient.execute(request);
			System.out.println(response.getMsg()+"\n");
			System.out.println(response.getBody());
//			if(!response.isSuccess())
//				throw new InterchanPayException(500,"退款错误,调用退款失败");
			return response.getBody();

		}catch (Exception e){
			throw new InterchanPayException(500,"退款错误,调用退款失败");
		}
	}

	public static String fundTrans(AliTransDTO dto) throws Exception{

		AlipayClient alipayClient =  getClient(dto.getMchId(),dto.getRsaPrivateKey(),dto.getRsaPublicKey());
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		Map<String,String> contentMap = new HashMap<>();
		contentMap.put("out_biz_no",dto.getTradeNo());
		contentMap.put("payee_type",dto.getPayeeType());
		contentMap.put("payee_account",dto.getTradeNo());
		contentMap.put("amount",dto.getTradeNo());
		if(!StringUtils.isBlank(dto.getShowName()))
		contentMap.put("payer_show_name",dto.getShowName());
		if(!StringUtils.isBlank(dto.getRealName()))
			contentMap.put("payee_real_name",dto.getRealName());
		if(!StringUtils.isBlank(dto.getRemark()))
		contentMap.put("remark",dto.getRemark());
		request.setBizContent(JSONObject.toJSONString(contentMap));
//		request.setBizContent("{" +
//				"    \"out_biz_no\":\"3142321423432\"," +
//				"    \"payee_type\":\"ALIPAY_LOGONID\"," +
//				"    \"payee_account\":\"abc@sina.com\"," +
//				"    \"amount\":\"12.23\"," +
//				"    \"payer_show_name\":\"上海交通卡退款\"," +
//				"    \"payee_real_name\":\"张三\"," +
//				"    \"remark\":\"转账备注\"," +
//				"  }");
		AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
		if(response.isSuccess()){
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}

		return response.getBody();
	}
	public static String createAppPay(AlipayClient alipayClient,String body,String subject,String tradeNo,String timeout,String amount,String notifyUrl) throws AlipayApiException{
		return createAppPay(alipayClient, body, subject, tradeNo, timeout,amount, PRODUCT_CODE,notifyUrl); 
	}
	
	public static String createAppPay(AlipayClient alipayClient,
									  String body,String subject,String tradeNo,
									  String timeout,String amount,
									  String productCode,String notifyUrl) throws AlipayApiException{
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(body);
		model.setSubject(subject);
		model.setOutTradeNo(tradeNo);
		model.setTimeoutExpress(timeout);
		model.setTotalAmount(amount);
		model.setProductCode(PRODUCT_CODE);
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
