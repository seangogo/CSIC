package com.dh.configure;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Consts {

	/**
	 * 分页，每页数量
	 */
	public static Integer PAGE_SIZE = 20;

	/**
	 * 订单类型
	 */
	public static String ORDER_STATE = "ORDER_STATE";

	/**
	 * 用户类型
	 */
	protected final static String USER_TYPE = "USER_TYPE";
	/**
	 * 维修类型
	 */
	public static String REPAIR_TYPE = "REPAIR_TYPE";

	/**
	 * 订单状态类型
	 */
	public static String MSG_TYPE = "MSG_TYPE";

	/**
	 * 投诉内容类型
	 */
	protected final static String COMPLAIN_TYPE = "COMPLAIN_TYPE";

	/**
	 * 取消订单内容类型
	 */
	protected final static String CANCEL_ORDER = "CANCEL_ORDER";

	/**
	 * 再次上门事由类型
	 */
	protected final static String REASON_TYPE = "REASON_TYPE";

	/**
	 * 初始密码
	 */
	public static String DEFAULT_PASSWORD = "123456";

	public static String SERVICE_ROLE = "service";

	public static String MANAGER_ROLE = "manager";

	public static String USER_ROLE = "user";

	/**
	 * banner显示最多的数量
	 */
	public static Integer BANNER_MAX_COUNT = 5;
	
	private static Properties p;

	/**
	 * 访问新闻内容url
	 */
	public static String NEWS_CONTENT_URL = "";

	{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		 p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	 	NEWS_CONTENT_URL = p.getProperty("news_content_url");
	}
	
	/**
	 * 订单过期时间
	 */
	protected final static Integer DAYS = 7;
	
	/**
	 * 默认评星
	 */
	public static String DEFAULT_STAR = "4";
	
	/**
	 * 默认评价内容
	 */
	public static String DEFAULT_COMMENT = "评价方未及时做出评价，系统默认4分！";
	
	/**
	 * 邀请码默认长度
	 */
	public static Integer INVITE_CODE_LENGTH = 6;

	/**
	 * 请求本地app服务地址
	 */
	public static String  APP_BASE_URL =  "http://192.168.10.87:8080/serviceapp/";

}
