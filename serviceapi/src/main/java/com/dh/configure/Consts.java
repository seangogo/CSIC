package com.dh.configure;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class Consts implements EnvironmentAware {
    /**
     * 订单类型
     */
    public final static String ORDER_STATE = "ORDER_STATE";

    /**
     * 用户类型
     */
    public final static String USER_TYPE = "USER_TYPE";
    /**
     * 维修类型
     */
    public final static String REPAIR_TYPE = "REPAIR_TYPE";

    /**
     * 订单状态类型
     */
    public final static String MSG_TYPE = "MSG_TYPE";

    /**
     * 投诉内容类型
     */
    public final static String COMPLAIN_TYPE = "COMPLAIN_TYPE";

    /**
     * 取消订单内容类型
     */
    public final static String CANCEL_ORDER = "CANCEL_ORDER";

    /**
     * 再次上门事由类型
     */
    public final static String REASON_TYPE = "REASON_TYPE";


    public final static String SERVICE_ROLE = "service";

    public final static String MANAGER_ROLE = "manager";

    public final static String USER_ROLE = "user";


    /**
     * 请求成功
     */
    public static int RETURN_CODE_SUCC = 1;
    /**
     * 请求失败
     */
    public static int RETURN_CODE_FAILED = 0;

    /**
     * 请求本地app服务地址
     */
    public static String APP_BASE_URL = "";

    /*请求服务器文件下载地址*/
    public  static String FILE_URL="";
    /*请求服务器文件上传地址*/
    public  static String FILE_PATH="";
    /*请求服务器图片下载地址*/
    public  static String IMG_URL="";
    /*请求服务器图片下载地址*/
    public  static String IMG_PATH="";
    /**
     * 分页，每页数量
     */
    public static Integer PAGE_SIZE = 12;


    //服务启动时自动 读取配置文件 获取 baseurl 并赋值给 APP_BASE_URL
    @Override
    public void setEnvironment(Environment env) {
        APP_BASE_URL = env.getProperty("spring.datasource.baseurl");
        FILE_URL = env.getProperty("file.url");
        FILE_PATH = env.getProperty("file.path");
        IMG_URL = env.getProperty("img.url");
        IMG_PATH = env.getProperty("img.path");
    }

}
