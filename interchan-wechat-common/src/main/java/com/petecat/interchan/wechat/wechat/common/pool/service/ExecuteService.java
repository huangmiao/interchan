package com.petecat.interchan.wechat.wechat.common.pool.service;

/**
 * 执行接口..可自行拓展
 *
 * @author mHuang
 */
public interface ExecuteService {

    /**
     * 微信分享
     *
     * @param usrId
     * @param status
     * @param type
     * @param shareName
     * @param uuid
     */
    void share(String usrId, String status, String type,
               String shareName, String uuid);

    void subscribe(String openId, String status);

    void scan(String openId, String eventKey);

    void subscribeOtherEvent(String openId, String status, String eventKey);

    void saveOpTextSend(String openId, String content);
}
