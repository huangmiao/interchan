/**
 * All rights Reserved, Designed By http://www.pete-cat.com/
 *
 * @Title: AuthConstant.java
 * @Package com.petecat.interchan.auth.common
 * @Description:TODO(用一句话描述该文件做什么)
 * @author: 成都皮特猫科技
 * @date:2017年8月21日 下午4:21:22
 * @version V1.0
 * @Copyright: 2017 www.pete-cat.com Inc. All rights reserved.
 * 注意：本内容仅限于成都皮特猫信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.petecat.interchan.auth.common.constant;

/**
 * @ClassName: AuthConstant
 * @Description:权限常量
 * @author: admin
 * @date: 2017年8月21日 下午4:21:22
 */
public class AuthConstant {

    /**
     * @Fields NOT_LOGIN_VIST_URLS_CACHEKEY : 不需要登录访问的地址
     */
    public static final String NOT_LOGIN_VIST_URLS_CACHEKEY = "not_login_vist_url";

    /**
     * @Fields USER_VIST_URL_CACHEKEY :用户可访问地址
     */
    public static final String USER_VIST_URL_CACHEKEY = "user_vist_url";


    /**
     * @Fields USER_VIST_ONLY_URL_CACHEKEY :用户可访问地址
     */
    public static final String USER_VIST_ONLY_URL_CACHEKEY = "user_vist_only_url";

    /**
     * @Fields EXCLUDE_VIST_URLS_CACHEKEY :排除访问的地址
     */
    public static final String EXCLUDE_VIST_URLS_CACHEKEY = "exclude_vist_url";


    /**
     * @Fields AUTH_EXCLUDE_DICT_KEY :排除访问的地址
     */
    public static final String AUTH_DICT_KEY = "auth_exclude";
}
