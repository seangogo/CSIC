package common.core;

/**
 * 字典数据
 * Created by sean on 2016/11/14.
 */
public interface Constant {

    String SESSION_ADMIN_USER = "user";

    String ENCODING = "UTF-8";

    int PAGE_DEF_SZIE = 20;

    String PAGE_DEF_SZIE_STRING = "20";

    // 用户性别
    // 男
    int USER_SEX_MALE = 0;
    // 女
    int USER_SEX_FEMALE = 1;
    // 保密
    int USER_SEX_UNKNOWN = 2;

    // 用户状态：启用
    Integer USER_ENABLE = 1;
    // 用户状态：禁用
    Integer USER_UNABLE = 0;

    // 用户信息可见状态
    String USER_INFO_IsSHOW_YES = "yes";
    String USER_INFO_IsSHOW_NO = "no";

    // 用户信息是否推荐
    String USER_INFO_IsRECOMMEND_YES = "yes";
    String USER_INFO_IsRECOMMEND_NO = "no";

    String CACHE_MOBILE_CODE = "_CACHE_CODE>"; //缓存短信接口

    String ROLE_CUSTOMER = "customer"; // 用户
    String ROLE_ADMIN = "admin"; // 管理员

    // 发送验证码类型
    String SEND_CODE_FINDPASSWORD = "findPassword";
    String SEND_CODE_REGISTER = "register";

    String USER_USERTYPE_THIRDPARTY = "thirdparty";
    String USER_USERTYPE_COMMON = "common";
}
