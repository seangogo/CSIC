package com.dh.web.api.appApiController;

import com.dh.commont.CommonButil;
import com.dh.commont.Des3;
import com.dh.commont.HttpRequestButil;
import com.dh.entity.InviteCode;
import com.dh.entity.User;
import com.dh.service.UserService;
import com.dh.web.api.BaseApiController;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/personal")
public class PersonalApiController extends BaseApiController {
    private static String folder = "userIco/";
    @Autowired
    private UserService userService;

    /**
     * 获取用户个人信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getPersonal")
    public Map<String, Object> getPersonal(HttpServletRequest request) {
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        // 设置返回值 用户id、用户类型、用户名、账号、公司、部门
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userId", user.getId());
        map.put("userType", user.getUserType());
        map.put("userState", user.getUserState());
        map.put("userName", user.getUserName());
        map.put("loginName", user.getLoginName());
        map.put("userCompany", user.getUserCompany());
        map.put("userGroup", user.getGroup() == null ? null : user.getGroup().getGroupName());
        map.put("userIco", CommonButil.isEmpty(user.getUserIco()) ? null : IMG_URL + user.getUserIco());
        return setReturnValue("获取成功", RETURN_CODE_SUCC, map);
    }

    @RequestMapping(value = "getPersonalDetail")
    public Map<String, Object> getPersonalDetail(HttpServletRequest request) {
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        // 设置返回值 用户id、用户类型、用户名、账号、公司、部门
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userId", user.getId());
        map.put("userName", user.getUserName());
        map.put("userSex", user.getUserSex());
        map.put("userCompany", user.getUserCompany());
        map.put("userGroup", user.getGroup() == null ? null : user.getGroup().getGroupName());
        map.put("userMail", user.getUserMail());
        map.put("userAddress", user.getUserAddress());
        return setReturnValue("获取成功", RETURN_CODE_SUCC, map);
    }


    /**
     * 修改用户个人信息
     *
     * @param request
     * @param userName    用户名
     * @param userSex     性别 0男 1女
     * @param userCompany 公司名称
     * @param userMail    邮箱
     * @param userAddress 地址
     * @return
     */
    @RequestMapping(value = "updatePersonal")
    public Map<String, Object> updatePersonal(HttpServletRequest request,
                                              @RequestParam(value = "userName", defaultValue = "") String userName,
                                              @RequestParam(value = "userSex", defaultValue = "") String userSex,
                                              @RequestParam(value = "userCompany", defaultValue = "") String userCompany,
                                              @RequestParam(value = "userMail", defaultValue = "") String userMail,
                                              @RequestParam(value = "userAddress", defaultValue = "") String userAddress) {
        if (CommonButil.isEmpty(userName)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }

        // 修改用户个人信息
        user.setUserSex(userSex);
        user.setUserName(userName);
        user.setUserCompany(userCompany);
        user.setUserMail(userMail);
        user.setUserAddress(userAddress);
        User savedUser = userService.update(user);
        request.getSession().setAttribute("currentUser", savedUser);
        return setReturnValue("修改成功", RETURN_CODE_SUCC);
    }

    /**
     * 修改密码
     *
     * @param request
     * @param plainPassword    旧密码
     * @param newPlainPassword 新密码
     * @return
     */
    @RequestMapping(value = "updatePassword")
    public Map<String, Object> updatePassword(HttpServletRequest request,
                                              @RequestParam(value = "plainPassword", defaultValue = "") String plainPassword,
                                              @RequestParam(value = "newPlainPassword", defaultValue = "") String newPlainPassword) {
        if (CommonButil.isEmpty(plainPassword) || CommonButil.isEmpty(newPlainPassword)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        // 判断旧密码是否正确
        try {
            if (!userService.checkUserPassword(user, Des3.decode(plainPassword))) {
                return setReturnValue("旧密码输入错误", RETURN_CODE_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 修改密码
        try {
            user.setPlainPassword(Des3.decode(newPlainPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
        User savedUser = userService.updatePassword(user);
        request.getSession().setAttribute("currentUser", savedUser);
        return setReturnValue("修改成功", RETURN_CODE_SUCC);
    }

    /**
     * 用户填写邀请码认证
     *
     * @param request
     * @param inviteCode 邀请码
     * @return
     */
    @RequestMapping(value = "userAuthentication")
    public Map<String, Object> userAuthentication(HttpServletRequest request,
                                                  @RequestParam(value = "inviteCode", defaultValue = "") String inviteCode) {
        if (CommonButil.isEmpty(inviteCode)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }

        if (1 == user.getUserState()) {
            return setReturnValue("用户已认证", RETURN_CODE_FAILED);
        }

        // 获取邀请码信息
        InviteCode code = userService.getInviteCode(inviteCode);
        if (null == code) {
            return setReturnValue("邀请码不存在", RETURN_CODE_FAILED);
        }
        if (1 == code.getIsUse()) {
            return setReturnValue("邀请码已使用", RETURN_CODE_FAILED);
        }
        if (CommonButil.isEmpty(user.getUserName())) {
            return setReturnValue("请完善个人信息后，再进行认证操作", RETURN_CODE_FAILED);
        }

        User savedUser = userService.userAuthentication(user, code);
        request.getSession().setAttribute("currentUser", savedUser);
        return setReturnValue("认证成功", RETURN_CODE_SUCC);
    }


    /**
     * 校验验证码
     *
     * @param request
     * @param phoneCode
     * @return
     */
    @RequestMapping(value = "checkPhoneCode")
    public Map<String, Object> checkPhoneCode(HttpServletRequest request,
                                              @RequestParam(value = "phoneCode", defaultValue = "") String phoneCode) {
        if (CommonButil.isEmpty(phoneCode)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        // 校验验证码
        if (!user.getLoginName().equals(request.getSession().getAttribute("loginName"))
                || !phoneCode.equals(request.getSession().getAttribute("phoneCode"))) {
            return setReturnValue("验证码错误", RETURN_CODE_FAILED);
        }
        return setReturnValue("检测可用", RETURN_CODE_SUCC);
    }

    /**
     * 更新手机号码
     *
     * @param request
     * @param loginName 手机号码
     * @param phoneCode 验证码
     * @return
     */
    @RequestMapping(value = "updatePhoneNum")
    public Map<String, Object> updatePhoneNum(HttpServletRequest request,
                                              @RequestParam(value = "loginName", defaultValue = "") String loginName,
                                              @RequestParam(value = "phoneCode", defaultValue = "") String phoneCode) {
        if (CommonButil.isEmpty(loginName) || CommonButil.isEmpty(phoneCode)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 判断手机号码和验证码
        if (!loginName.equals(request.getSession().getAttribute("loginName"))
                || !phoneCode.equals(request.getSession().getAttribute("phoneCode"))) {
            return setReturnValue("验证码错误", RETURN_CODE_FAILED);
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        // 更换手机号码 保存用户
        user.setLoginName(loginName);
        User savedUser = userService.update(user);
        request.getSession().setAttribute("currentUser", savedUser);
        return setReturnValue("绑定成功", RETURN_CODE_SUCC);
    }

    /**
     * 上传用户头像
     *
     * @param request
     * @param imgStr  图片base64字符串
     * @return
     */
    @RequestMapping(value = "uploadUserIco")
    public Map<String, Object> uploadUserIco(HttpServletRequest request,
                                             @RequestParam(value = "imgStr", defaultValue = "") String imgStr) {


        if (CommonButil.isEmpty(imgStr)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }

        //设置上传头像参数
        Map<String, String> paramMap = new HashMap<String, String>();
        try {
            //url转码
            paramMap.put("imgStr", URLEncoder.encode(imgStr, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return setReturnValue("参数转码发生错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        //添加文件路径、设置图片压缩宽度、高度
        String userFolder = folder + user.getId() + "/";
        paramMap.put("folder", userFolder);

        //请求url
        String requestUrl = APP_BASE_URL + "/api/uploadImg";
        String resultJson = HttpRequestButil.sendPost(requestUrl, paramMap);
        if (CommonButil.isEmpty(resultJson)) {
            return setReturnValue("上传图片失败", RETURN_CODE_FAILED);
        }
        //获取resultJson 为实体对象
        JSONObject result = new JSONObject(resultJson);
        // 获取resultJson 为实体对象
        if (result.getInt("c") == 0) {
            return setReturnValue(result.getString("m"), RETURN_CODE_FAILED);
        }
        //获取 图片路径
        String imgUrl = result.getString("o");
        if (CommonButil.isEmpty(imgUrl)) {
            return setReturnValue("保存图片失败", RETURN_CODE_FAILED);
        }
        //保存图片路径到user
        user.setUserIco(imgUrl);
        User savedUser = userService.update(user);
        //更新当前session 中的currentUser
        request.getSession().setAttribute("currentUser", savedUser);
        return setReturnValue("上传成功", RETURN_CODE_SUCC, IMG_URL + savedUser.getUserIco());
    }

}
