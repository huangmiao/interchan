package com.petecat.interchan.core.controller;

import com.alibaba.fastjson.JSON;
import com.petecat.interchan.core.exception.BusinessException;
import com.petecat.interchan.protocol.GlobalHeader;
import com.petecat.interchan.protocol.Result;
import org.apache.commons.lang3.StringUtils;


/**
 * @ClassName: BaseController
 * @Description:通用Controller
 * @author: mhuang
 * @date: 2017年7月13日 下午3:36:23
 */
public abstract class BaseController {

    /**
     * @param jsonHeadUser
     * @param nullThrowException 为null时要不要抛出异常
     * @return GlobalHeader
     * @Title: getUserInfo
     * @Description: 获取用户信息
     */
    @Deprecated
    public GlobalHeader getUserInfo(String jsonHeadUser, boolean nullThrowException) {
        GlobalHeader header = null;
        if (StringUtils.isNotBlank(jsonHeadUser)) {
            try {
                header = JSON.parseObject(jsonHeadUser, GlobalHeader.class);
            } catch (Exception e) {
            }
        }
        if ((header == null || StringUtils.isBlank(header.getUserId())) && nullThrowException) {
            throw new BusinessException(Result.tokenValid().getCode(),
                    Result.tokenValid().getMessage());
        }
        return header;
    }
}
