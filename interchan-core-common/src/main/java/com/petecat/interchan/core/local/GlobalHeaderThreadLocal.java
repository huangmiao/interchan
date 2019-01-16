package com.petecat.interchan.core.local;

import com.petecat.interchan.core.exception.BusinessException;
import com.petecat.interchan.protocol.GlobalHeader;
import com.petecat.interchan.protocol.Result;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: GlobalThreadLocal
 * @Description:全局线程中得常量
 * @author: mhuang
 * @date: 2018年4月25日 上午10:14:05
 */
public class GlobalHeaderThreadLocal {

    private static ThreadLocal<GlobalHeader> globalUser = ThreadLocal.withInitial(() -> null);

    public static GlobalHeader get() {
        return globalUser.get();
    }

    public static GlobalHeader getOrException() {
        GlobalHeader globalHeader = get();
        if (globalHeader == null || StringUtils.isBlank(globalHeader.getUserId())) {
            throw new BusinessException(Result.TOKEN_IS_VALID, Result.TOKEN_IS_VALID_MSG);
        }
        return globalHeader;
    }

    public static void set(GlobalHeader value) {
        globalUser.set(value);
    }

    public static void remove() {
        globalUser.remove();
    }
}
