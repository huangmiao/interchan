package com.petecat.interchan.pay.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: AliPayDTO
 * @Description:支付宝支付
 * @author: mhuang
 * @date: 2018年4月18日 下午3:14:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AliPayDTO extends BasePayDTO {

    /**
     * 请求方式
     */
    private String mode;

    /**
     * 公匙
     */
    private String rsaPublicKey;

    /**
     * 私匙
     */
    private String rsaPrivateKey;
}
