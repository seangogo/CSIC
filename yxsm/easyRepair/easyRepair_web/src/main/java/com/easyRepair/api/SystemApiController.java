package com.easyRepair.api;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.Cache.CacheServiceImpl;
import com.easyRepair.service.*;
import com.easyRepair.tabEntity.User;
import com.easyRepair.tabEntity.UserInfo;
import com.easyRepair.tabEntity.UserLoginInfo;
import com.easyRepair.tabEntity.UserRepairInfo;
import common.core.EASY_ERROR_CODE;
import common.core.bean.Result;
import common.utils.Iphone.IphoneUtils;
import common.utils.JsonUtil;
import common.utils.password.Des3;
import common.utils.password.LocalhostStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


@Api(value = "/system", description = "关于系统用户（登录注册）相关接口")
@Controller
@RequestMapping(value = "/api/system", produces = {"text/html;charset=UTF-8"})
public class SystemApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CacheServiceImpl cacheService;
    @Autowired
    private UserRepairInfoService userRepairInfoService;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private OrderService orderService;

    /**
     * 注册接口
     *
     * @param iphoneCode 账号（手机号）password 简单加密后的密码 type 用户类型 0 用户1 工程师
     * @param phoneCode  验证码
     * @param deviceOs   设备类型
     * @param deviceId   设备ID
     * @param response
     */
    @RequestMapping(value = "{iphoneCode}/register")
    @ResponseBody
    @ApiOperation(notes = "工程师用户注册接口，连带短信验证码,lastLoction 最后登录经纬度 注册不传", httpMethod = "POST", value = "注册账号")
    public void register(@ApiParam(required = true, name = "iphoneCode", value = "用户手机号")
                         @RequestParam(value = "iphoneCode") String iphoneCode,
                         @ApiParam(required = true, name = "password", value = "简单加密后的密码")
                         @RequestParam(value = "password") String password,
                         @ApiParam(required = true, name = "type", value = "用户类型 0 用户1 工程师")
                         @RequestParam(value = "type") String type,
                         @ApiParam(required = true, name = "lastLng", value = "注册精度")
                         @RequestParam(value = "lastLng") Double lng,
                         @ApiParam(required = true, name = "lastLat", value = "注册纬度")
                         @RequestParam(value = "lastLat") Double lat,
                         @ApiParam(name = "phoneCode", value = "六位数验证码")
                         @RequestParam(value = "phoneCode") String phoneCode,
                         @ApiParam(required = true, name = "deviceId", value = "手机唯一标识")
                         @RequestParam(value = "deviceId") String deviceId,
                         @ApiParam(required = true, name = "deviceOs", value = "手机类型：0:android  1:ios   空为其他")
                         @RequestParam(value = "deviceOs") String deviceOs, HttpServletResponse response) {
        // 校验参数非空
        if (StringUtils.isEmpty(iphoneCode) || StringUtils.isEmpty(password) || StringUtils.isEmpty(type)
                || StringUtils.isEmpty(deviceId)
                || StringUtils.isEmpty(deviceOs)) {
            WebUtil.printJson(response, new Result(false).msg("请求参数错误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        try {
            /*//判断手机号码和验证码
            @SuppressWarnings("unused")
            Checkcache c = new Checkcache("aaa");
            cacheService.checkCode(phoneCode, iphoneCode);
            if (Checkcache.returnval().equals(phoneCode + iphoneCode)) {
                cacheService.delete(phoneCode, iphoneCode);// 不存在 清除缓存
                WebUtil.printApi(response, new Result(false).msg("验证码错误").data(EASY_ERROR_CODE.ERROR_CODE_0010));
                return;
            }
            cacheService.delete(phoneCode, iphoneCode);// 存在，验证通过 清除缓存*/
            Result result = userService.register(new User(iphoneCode, type, password, new UserInfo(), new UserLoginInfo(lng, lat, new Date(), true, deviceId, deviceOs)));
            WebUtil.printApi(response, result);
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printApi(response, new Result(false).msg("服务器异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
        }
    }

    /**
     * 完善个人信息
     *
     * @param userInfo
     * @param response
     */
    @RequestMapping(value = "/perfect/user")
    @ResponseBody
    @ApiOperation(notes = "nickName:昵称,gender:性别(boolean),address:居住地,email:邮箱", httpMethod = "POST", value = "完善信息")
    public void perfect(@RequestBody @ApiParam(name = "userInfo", required = true, value = "用户/工程师对象")
                                UserInfo userInfo, HttpServletResponse response) {
        try {
            UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
            if (!LocalhostStringUtils.allIsNotEmpty(userInfo.getNickName(), userInfo.getAddress(), userInfo.getEmail())) {
                WebUtil.printApi(response, new Result(false).msg("参数有误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
                return;
            }
            WebUtil.printApi(response, userInfoService.perfect(userSessionModul.getId(),
                    new UserInfo(userInfo.getNickName(), userInfo.getAddress(), userInfo.isGender(), userInfo.getEmail())));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printApi(response, new Result(false).msg("服务器异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
        }
    }

    /**
     * 工程师认证
     *
     * @param request
     * @param userRepairInfoStr
     * @param response
     */
    @RequestMapping(value = "/authentication/user")
    @ResponseBody
    @ApiOperation(notes = "realName:真实姓名，identityCode:身份证号，jobTitle:工作年限" +
            "serviceId:服务类型ID，逗号分隔（必穿）qualification:资格证书图片集合（选填),profile:签名",
            httpMethod = "POST", value = "工程师认证")
    public void authentication(HttpServletRequest request,
                               @ApiParam(name = "userRepairInfoStr", value = "工程师对象")
                               @RequestParam(value = "userRepairInfoStr") String userRepairInfoStr,
                               HttpServletResponse response) {
        try {
            UserRepairInfo userRepairInfo = JsonUtil.json2Obj(userRepairInfoStr, UserRepairInfo.class);
            if (!LocalhostStringUtils.allIsNotEmpty(userRepairInfo.getIdentityCode(), userRepairInfo.getServiceId(),
                    userRepairInfo.getJobTitle(), userRepairInfo.getRealName())) {
                WebUtil.printApi(response, new Result(false).msg("身份信息不可为空").data(EASY_ERROR_CODE.ERROR_CODE_0002));
                return;
            }
            UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
            WebUtil.printApi(response, userRepairInfoService.authentication(request, userSessionModul.getId(), userRepairInfo));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printApi(response, new Result(false).msg("服务器异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
        }


    }

    /**
     * 修改密码
     *
     * @param iphoneCode
     * @param phoneCode
     * @param password
     * @param response
     */
    @RequestMapping(value = "{iphoneCode}/findPassword")
    @ResponseBody
    @ApiOperation(notes = "根据手机号修改密码", httpMethod = "POST", value = "找回密码")
    public void findPassword(@ApiParam(required = true, name = "iphoneCode", value = "用户手机号")
                             @RequestParam(value = "iphoneCode") String iphoneCode,
                             @ApiParam(required = true, name = "type", value = "用户类型")
                             @RequestParam(value = "type") String type,
                             @ApiParam(required = true, name = "phoneCode", value = "手机验证码")
                             @RequestParam(value = "phoneCode") String phoneCode,
                             @ApiParam(required = true, name = "password", value = "密码")
                             @RequestParam(value = "password") String password,
                             HttpServletResponse response) {
        // 参数校验非空
        if (!LocalhostStringUtils.allIsNotEmpty(phoneCode, iphoneCode, password, type)) {
            WebUtil.printApi(response, new Result(false).msg("请求参数错误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        /*@SuppressWarnings("unused")
        Checkcache c = new Checkcache("aaa"); // 判断手机号码和验证码  验证code
        cacheService.checkCode(phoneCode, iphoneCode);
        if (Checkcache.returnval().equals(phoneCode + iphoneCode)) {
            // 不存在
            cacheService.delete(phoneCode, iphoneCode);
            WebUtil.printApi(response, new Result(false).msg("验证码错误").data(EASY_ERROR_CODE.ERROR_CODE_0010));
            return;
        }
        cacheService.delete(phoneCode, iphoneCode); // 清除缓存   存在，验证通过*/
        try {
            WebUtil.printApi(response, userService.findPassword(new User(iphoneCode, type, Des3.decode(password))));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printApi(response, new Result(false).msg("修改密码失败"));
        }
    }

    /**
     * 前台登录
     *
     * @param iphoneCode
     * @param password
     * @param deviceId
     * @param response
     * @param type
     * @return
     */
    @RequestMapping(value = "{iphoneCode}/login")
    @ResponseBody
    @ApiOperation(notes = "登录", httpMethod = "GET", value = "登录账号")
    public void login(@ApiParam(required = true, name = "iphoneCode", value = "用户手机号")
                      @RequestParam(value = "iphoneCode") String iphoneCode,
                      @ApiParam(required = true, name = "type", value = "用户类型")
                      @RequestParam(value = "type") String type,
                      @ApiParam(required = true, name = "password", value = "密码")
                      @RequestParam(value = "password") String password,
                      @ApiParam(required = true, name = "lastLng", value = "注册精度")
                      @RequestParam(value = "lastLng") Double lng,
                      @ApiParam(required = true, name = "lastLat", value = "注册纬度")
                      @RequestParam(value = "lastLat") Double lat,
                      @ApiParam(required = true, name = "deviceId", value = "当前登录设备号")
                      @RequestParam(value = "deviceId") String deviceId,
                      @ApiParam(required = true, name = "deviceOs", value = "当前登录设备类型")
                      @RequestParam(value = "deviceOs") String deviceOs,
                      HttpServletResponse response) {
        if (!LocalhostStringUtils.allIsNotEmpty(iphoneCode, password, deviceId, type)) {
            WebUtil.printApi(response, new Result(false).msg("请求参数错误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
        }
        try {
            WebUtil.printApi(response, userService.apiLogin(new User(iphoneCode, type, Des3.decode(password)), new UserLoginInfo(lng, lat, new Date(), true, deviceId, deviceOs)));

        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printApi(response, new Result(false).msg("密码数据异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
        }
    }


    /**
     * 发送验证码
     *
     * @param iphoneCode
     * @param type
     * @param state
     * @param response
     */
    @RequestMapping(value = "{iphoneCode}/sendCode")
    @ResponseBody
    @ApiOperation(notes = "此接口用户注册和修改密码，为了提高复用性，因此加上状态，注意逻辑相反", httpMethod = "GET", value = "发送验证")
    public void sendCode(
            HttpServletRequest request,
            @ApiParam(required = true, name = "iphoneCode", value = "用户手机号")
            @RequestParam(value = "iphoneCode") String iphoneCode,
            @ApiParam(required = true, name = "type", value = "用户类型")
            @RequestParam(value = "type") String type,
            @ApiParam(required = true, name = "state", value = "操作类型：0 注册，1 修改")
            @RequestParam(value = "state") String state, HttpServletResponse response) {
        //验证参数
        if (StringUtils.isEmpty(iphoneCode) || StringUtils.isEmpty(state) || StringUtils.isEmpty(type)) {
            WebUtil.printApi(response, new Result(false).msg("请求参数错误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        List<User> userList = userService.findUserByLoginNameAndTypeAndIsLocked(iphoneCode, type, false);
        //注册
        if (state.equals("0")) {  //判断手机号存在性
            if (userList != null && userList.size() > 0) {
                if (userList.get(0).getType().equals(type) || userList.size() > 1) {
                    WebUtil.printApi(response, new Result(false).msg("手机号被占用").data(EASY_ERROR_CODE.ERROR_CODE_0007));
                    return;
                }
            }
        }
        //修改手机号
        if (state.equals("1")) {
            if (userList == null || userList.size() != 1) {
                WebUtil.printApi(response, new Result(false).msg("手机号未注册用户").data(EASY_ERROR_CODE.ERROR_CODE_0013));
                return;
            }
        }
        String code = IphoneUtils.getCode(6);
        try {
            cacheService.addCode(code, iphoneCode);
            String result = IphoneUtils.sendSMS(iphoneCode, code);
            if (result.equals("Success")) {
                WebUtil.printApi(response, new Result(true).msg("发送成功!"));
                return;
            }
            request.getSession().setAttribute("code", 111);
            WebUtil.printApi(response, new Result(true).msg("发送失败!").data(EASY_ERROR_CODE.ERROR_CODE_0009));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 退出登录
     *
     * @param status
     * @param response
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    @ApiOperation(notes = "用户退出登录,参数为SpringMVC自带", httpMethod = "POST", value = "退出登录")
    public void logout(SessionStatus status, HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        User user = userService.getById(userSessionModul.getId());
        user.getUserLoginInfo().setLogin(false);
        userService.update(user);
        status.setComplete();
        SessionUtils.clear();
        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 定时保存用户经纬度
     *
     * @param lng
     * @param lat
     * @param response
     */
    @RequestMapping(value = "upload/{lng}/{lat}/userPosition")
    @ResponseBody
    @ApiOperation(notes = "保存用户经纬度,如果是工程师角色登录并在正在处理订单返回数据data(true)", httpMethod = "POST", value = "保存位置")
    public void uploadUserPosition(@ApiParam(name = "lng", value = "经度")
                                   @PathVariable(value = "lng") Double lng,
                                   @ApiParam(name = "lat", value = "纬度")
                                   @PathVariable(value = "lat") Double lat,
                                   HttpServletResponse response) {
        try {
            UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
            userPositionService.saveRealTimePosition(userSessionModul.getId(), lng, lat);
            WebUtil.printApi(response, new Result(true).data(userSessionModul.getType().equals("1") ?
                    orderService.findIsWorkByUserId(userSessionModul.getId()) : false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测用户名是否存
     *
     * @param iphoneCode
     * @param type
     * @param response
     */
    @RequestMapping(value = "{iphoneCode}/check")
    @ResponseBody
    @ApiOperation(notes = "同一手机号可以同时注册工程师和用户", httpMethod = "GET", value = "检测账户")
    public void checkLoginName(@ApiParam(required = true, name = "iphoneCode", value = "用户手机号")
                               @RequestParam(value = "iphoneCode") String iphoneCode,
                               @ApiParam(required = true, name = "type", value = "用户类型")
                               @RequestParam(value = "type") String type, HttpServletResponse response) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        if (StringUtils.isEmpty(iphoneCode) || StringUtils.isEmpty(type) || !p.matcher(iphoneCode).matches()) {
            WebUtil.printApi(response, new Result(false).msg("请求参数错误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        List<User> userList = userService.findUserByLoginNameAndTypeAndIsLocked(iphoneCode, type, false);
        if (userList != null && userList.size() > 0) {
            WebUtil.printApi(response, new Result(false).msg("用户已存在").data(EASY_ERROR_CODE.ERROR_CODE_0007));
            return;
        }
        WebUtil.printApi(response, new Result(true).msg("检测可用"));
    }

}
