package com.dh.web.api.appApiController;

import com.dh.commont.CommonButil;
import com.dh.commont.JsonUtil;
import com.dh.commont.WebUtil;
import com.dh.commont.bean.Result;
import com.dh.entity.Feedback;
import com.dh.entity.User;
import com.dh.service.BannerService;
import com.dh.service.FeedBackService;
import com.dh.service.UserPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.dh.configure.Consts.RETURN_CODE_FAILED;
import static com.dh.configure.Consts.RETURN_CODE_SUCC;
import static com.dh.web.api.BaseApiController.setReturnValue;


@RestController
@RequestMapping(value = "/api/system")
public class SystemApiController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private FeedBackService feedBackService;

    @RequestMapping(value = "firstRequest")
    public Map<String, Object> firstRequest(HttpServletRequest request) {
        return setReturnValue("请求成功", RETURN_CODE_SUCC);
    }

    /**
     * 获取banner
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "getBanner")
    public void getBanner(HttpServletResponse response) {
        WebUtil.printApi(response, JsonUtil.obj2ApiJson(new Result(true).msg("获取成功").data(bannerService.getBanner()), "createTime", "updateTime"
                , "orderBy", "isShow", "news", "type", "detailContent", "id"));
    }

    /**
     * 更新经纬度
     *
     * @param request
     * @param location
     * @return
     */
    @RequestMapping(value = "updateLocation")
    public Map<String, Object> updateLocation(HttpServletRequest request,
                                              @RequestParam(value = "location", defaultValue = "") String location) {
        if (CommonButil.isEmpty(location)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        try {
            userPositionService.updateLocation(user, location);
            return setReturnValue("更新成功", RETURN_CODE_SUCC);
        } catch (Exception e) {
            return setReturnValue("上传定位失败", RETURN_CODE_FAILED);
        }
    }

    /**
     * 用户发布意见反馈
     *
     * @param request
     * @param deviceVersion
     * @param deviceSystem
     * @param deviceSize
     * @param appVersion
     * @param content
     * @return
     */
    @RequestMapping(value = "saveFeedback")
    public Map<String, Object> saveFeedback(HttpServletRequest request,
                                            @RequestParam(value = "deviceVersion", defaultValue = "") String deviceVersion,
                                            @RequestParam(value = "deviceSystem", defaultValue = "") String deviceSystem,
                                            @RequestParam(value = "deviceSize", defaultValue = "") String deviceSize,
                                            @RequestParam(value = "deviceNetwork", defaultValue = "") String deviceNetwork,
                                            @RequestParam(value = "appVersion", defaultValue = "") String appVersion,
                                            @RequestParam(value = "content", defaultValue = "") String content) {
        if (CommonButil.isEmpty(appVersion) || CommonButil.isEmpty(content)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setAppVersion(appVersion);
        feedback.setContent(content);
        feedback.setCreateTime(CommonButil.getNowTime());
        feedback.setDeviceNetwork(deviceNetwork);
        feedback.setDeviceSystem(deviceSystem);
        feedback.setDeviceVersion(deviceVersion);
        feedback.setDeviceSize(deviceSize);
        feedBackService.create(feedback);
        return setReturnValue("发布成功", RETURN_CODE_SUCC);
    }
}
