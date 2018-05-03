package com.petecat.interchan.core.constans;

/**
 * 
 * @ClassName:  Global   
 * @Description:全局常量 
 * @author: mhuang
 * @date:   2017年7月12日 上午10:25:28
 */
public class Global {
	public static final String APP_USER = "appUser";
	public static final String WEB_USER = "webUser";
	public static final String TOKEN = "token";
	public static final String USERID = "userId";
	public static final String COMPANY_ID = "companyId";
	public static final String USER = "user";
	public static final String GLOBAL_HEADER = "global_header";
	public static final String GLOBAL_AUTHORIZATION = "Authorization";
	public static final int COMMON_DB_INDEX = 0;
	public static final int COMMON_MEMBER_DB_INDEX = 12;
	public static final int COMMON_KAFKA_DB_INDEX = 12;
	/**
	 * 30分钟过期
	 */
	public static final Long EXPIRE_THIRTY_MINUTES = 60 * 30L;

	/**
	 * 2小时过期
	 */
	public static final Long EXPIRE_TWO_HOURS = 60 * 60 * 2L;

	/**
	 * 1天过期
	 */
	public static final Long EXPIRE_ONE_DAY = 60 * 60 * 24L;

	/**
	 * 1周过期
	 */
	public static final Long EXPIRE_ONE_WEEKS = 60 * 60 * 24 * 7L;

	/**
	 * 30天过期
	 */
	public static final Long EXPIRE_THIRTY_DAYS = 60 * 60 * 24 * 30L;

}
