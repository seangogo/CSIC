package com.dh.web.api.appApiController;

import com.dh.commont.CommonButil;
import com.dh.commont.Des3;
import com.dh.commont.jpush.SendPush;
import com.dh.entity.User;
import com.dh.service.UserService;
import com.dh.web.api.BaseApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Coolkid on 2016/9/13.
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserApiController extends BaseApiController {
    @Autowired
    private UserService userService;

    /**
     * 检测用户名是否存在
     *
     * @param request
     * @param loginName
     * @return
     */
    @RequestMapping(value = "checkLoginName")
    public Map<String, Object> checkLoginName(HttpServletRequest request,
                                              @RequestParam(value = "loginName", defaultValue = "") String loginName) {
        if (CommonButil.isEmpty(loginName)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED);
        }
        if (userService.findUserByLoginName(loginName) != null) {
            return setReturnValue("用户已存在", RETURN_CODE_FAILED);
        }
        return setReturnValue("检测可用", RETURN_CODE_SUCC);
    }

    /**
     * 获取验证码
     *
     * @param request
     * @param loginName 账号(手机号)
     * @param type      type为获取验证码类型 0为注册 1为找回密码
     * @return
     */
    @RequestMapping(value = "getPhoneCode")
    public Map<String, Object> getPhoneCode(HttpServletRequest request,
                                            @RequestParam(value = "loginName", defaultValue = "") String loginName,
                                            @RequestParam(value = "type", defaultValue = "") String type) {
        //验证必须是手机号
        Pattern p = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号
        if (CommonButil.isEmpty(loginName) || CommonButil.isEmpty(type) || !p.matcher(loginName).matches()) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED);
        }
        // 判断该账户是否存在
        User flagUser = userService.findUserByLoginName(loginName);
        Boolean flag = flagUser != null;

        // 注册获取验证码 若用户已存在则返回 false
        if ("0".equals(type) && flag) {
            return setReturnValue("该账户已存在", RETURN_CODE_FAILED);
        }
        // 找回密码获取验证码 若用户不存在则返回 false
        if ("1".equals(type) && !flag) {
            return setReturnValue("该账户不存在", RETURN_CODE_FAILED);
        }

        String phoneCode = CommonButil.getRandomNum(4);
        if (CommonButil.sendMessage(loginName, phoneCode)) {
            // TODO 调用短信接口
        }
        //设置session过期时间 5分钟
        request.getSession().setMaxInactiveInterval(300);
        request.getSession().setAttribute("loginName", loginName);
        request.getSession().setAttribute("phoneCode", phoneCode);
        return setReturnValue("获取验证码成功", RETURN_CODE_SUCC, phoneCode);
    }

    /**
     * 注册接口
     *
     * @param request
     * @param loginName     账号（手机号）
     * @param phoneCode     验证码
     * @param plainPassword 简单加密后的密码
     * @return
     */
    @RequestMapping(value = "register")
    public Map<String, Object> register(HttpServletRequest request,
                                        @RequestParam(value = "loginName", defaultValue = "") String loginName,
                                        @RequestParam(value = "phoneCode", defaultValue = "") String phoneCode,
                                        @RequestParam(value = "plainPassword", defaultValue = "") String plainPassword) {
        // 校验参数非空
        if (CommonButil.isEmpty(loginName) || CommonButil.isEmpty(phoneCode) || CommonButil.isEmpty(plainPassword)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED);
        }
        // 判断手机号码和验证码
        if (!loginName.equals(request.getSession().getAttribute("loginName"))
                || !phoneCode.equals(request.getSession().getAttribute("phoneCode"))) {
            return setReturnValue("验证码错误", RETURN_CODE_FAILED);
        }
        if (userService.findUserByLoginName(loginName) != null) {
            return setReturnValue("用户已存在", RETURN_CODE_FAILED);
        }
        User user = new User();
        user.setLoginName(loginName);
        // 简单解密后设置明文密码值
        try {
            user.setPlainPassword(Des3.decode(plainPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
        User u = userService.registerUser(user);
        if (null == u.getId()) {
            return setReturnValue("注册失败", RETURN_CODE_FAILED);
        }
        return setReturnValue("注册成功", RETURN_CODE_SUCC, loginName);
    }

    /**
     * 找回密码
     *
     * @param request
     * @param loginName        账号（手机号）
     * @param phoneCode        验证码
     * @param newPlainPassword 简单加密后的密码
     * @return
     */
    @RequestMapping(value = "findPassword")
    public Map<String, Object> findPassword(HttpServletRequest request,
                                            @RequestParam(value = "loginName", defaultValue = "") String loginName,
                                            @RequestParam(value = "phoneCode", defaultValue = "") String phoneCode,
                                            @RequestParam(value = "plainPassword", defaultValue = "") String newPlainPassword) {
        // 参数校验非空
        if (CommonButil.isEmpty(loginName) || CommonButil.isEmpty(phoneCode) || CommonButil.isEmpty(newPlainPassword)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED);
        }
        // 验证手机号和验证码
        if (!loginName.equals(request.getSession().getAttribute("loginName"))
                || !phoneCode.equals(request.getSession().getAttribute("phoneCode"))) {
            return setReturnValue("验证码错误", RETURN_CODE_FAILED);
        }
        User user = userService.findUserByLoginName(loginName);
        try {
            user.setPlainPassword(Des3.decode(newPlainPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userService.updatePassword(user);
        return setReturnValue("找回密码成功", RETURN_CODE_SUCC, loginName);
    }

    /**
     * 前台登录
     *
     * @param request
     * @param loginName     账号（手机号）
     * @param plainPassword 简单加密后的密码
     * @param deviceId      设备id
     * @param deviceOs      设备系统 "ios" 、"android"
     * @param lastLocation  经纬度
     * @return
     */
    @RequestMapping(value = "/login")
    public Map<String, Object> login(HttpServletRequest request,
                                     @RequestParam(value = "loginName", defaultValue = "") String loginName,
                                     @RequestParam(value = "plainPassword", defaultValue = "") String plainPassword,
                                     @RequestParam(value = "deviceId", defaultValue = "") String deviceId,
                                     @RequestParam(value = "deviceOs", defaultValue = "") String deviceOs,
                                     @RequestParam(value = "lastLocation", defaultValue = "") String lastLocation) {
        if (CommonButil.isEmpty(loginName) || CommonButil.isEmpty(plainPassword) || CommonButil.isEmpty(deviceId)
                || CommonButil.isEmpty(deviceOs)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED);
        }

        User user = userService.findUserByLoginName(loginName);
        try {
            if (null == user || !userService.checkUserPassword(user, Des3.decode(plainPassword))) {
                return setReturnValue("账户密码错误", RETURN_CODE_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //判断若该用户之前登录的设备不是当前设备，则给之前设备推送一条信息
        if (!CommonButil.isEmpty(user.getDeviceId())
                && !user.getDeviceId().equals(deviceId) && user.getDeviceId().length() > 10) {
            String content = "你的帐号于" + CommonButil.getNowTimeStr("HH:mm") + "在另一台设备登录。如非本人操作，则密码可能已泄露，建议及时修改密码。";
            SendPush.pushToUser(user.getDeviceId(), content, "下线通知", "5", user.getId().toString());
        }
        //判断账户是否锁定
        if (0 != user.getIsLocked()) {
            return setReturnValue("您的账户已锁定，请联系系统管理员", RETURN_CODE_FAILED, null, null, "4008");
        }

        // 修改用户最后登录时间、位置、次数,并插入用户位置表
        userService.updateUserLoginState(user, deviceOs, deviceId, lastLocation);
        /*秒为单位 3个小时 */
        request.getSession().setMaxInactiveInterval(10800);
        request.getSession().setAttribute("currentUser", user);

        // 设置返回值 用户id、用户类型、用户角色、用户名、账号、公司、部门
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userId", user.getId());
        map.put("userType", user.getUserType());
        map.put("roleId", user.getRole().getId());
        map.put("userState", user.getUserState());
        map.put("userName", user.getUserName());
        map.put("loginName", user.getLoginName());
        map.put("userCompany", user.getUserCompany());
        map.put("userGroup", user.getGroup() == null ? null : user.getGroup().getGroupName());
        map.put("userIco", CommonButil.isEmpty(user.getUserIco()) ? null : IMG_URL + user.getUserIco());
        return setReturnValue("登录成功", RETURN_CODE_SUCC, map);
    }

    /**
     * 注销账户
     *
     * @param request
     * @param userId
     * @return
     */
    @RequestMapping(value = "logout")
    public Map<String, Object> logout(HttpServletRequest request,
                                      @RequestParam(value = "userId", defaultValue = "") int userId) {
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            user = userService.getById(userId);
        }
        user.setDeviceOs("");
        user.setDeviceId("");
        user.setIsLogin(0);
        userService.update(user);
        request.getSession().invalidate();
        return setReturnValue("注销成功", RETURN_CODE_SUCC);
    }


}
