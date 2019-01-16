package com.petecat.interchan.logger.common;

/**
 * @ClassName: Global
 * @Description:全局可修改
 * @author: mhuang
 * @date: 2017年8月3日 下午3:22:07
 */
public class LoggerGlobal {

    protected static String jwtHeaderName = "global_header";
    protected static boolean jwtHeader = true;
    protected static String systemType = "";

    public static void setJwtHeader(boolean header) {
        jwtHeader = header;
    }

    public static boolean getJwtHeader() {
        return jwtHeader;
    }

    public static void setJwtHeaderName(String headerName) {
        jwtHeaderName = headerName;
    }

    public static String getJwtHeaderName() {
        return jwtHeaderName;
    }

    public static String getSystemType() {
        return systemType;
    }

    public static void setSystemType(String systemType) {
        LoggerGlobal.systemType = systemType;
    }
}
