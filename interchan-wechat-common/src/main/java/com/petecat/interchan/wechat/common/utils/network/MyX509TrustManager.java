package com.petecat.interchan.wechat.common.utils.network;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author huang.miao
 * @Package: com.petecat.interchan.wechat.common.utils.network
 * @Description 信任管理器
 * @date 2016年12月22日 下午2:14:20
 * @group skiper-opensource
 * @since 1.0.0
 */
public class MyX509TrustManager implements X509TrustManager {

    // 检查客户端证书
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    // 检查服务器端证书
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    // 返回受信任的X509证书数组
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
