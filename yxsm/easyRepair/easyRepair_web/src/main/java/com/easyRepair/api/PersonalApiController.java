package com.easyRepair.api;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.entityUtils.ApiBeanUtils;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.FansMudule;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.*;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import common.core.EASY_ERROR_CODE;
import common.core.bean.PageParam;
import common.core.bean.Result;
import common.utils.DateUtils;
import common.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by sean on 2016/9/14.
 */
@Api(value = "/personal", description = "用户的相关操作")
@RestController
@RequestMapping(value = "/api/personal", produces = {"*/*;charset=UTF-8"})
public class PersonalApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserRepairInfoService userRepairInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private FansService fansService;

    @Autowired
    private TradeInfoService tradeInfoService;



    @Autowired
    private CommentsService commentsService;

    @Autowired
    private UserPositionService userPositionService;

    /**
     * 用户首页
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/index")
    @ApiOperation(notes = "首页", httpMethod = "GET", value = "首页")
    public void userIndex(HttpServletResponse response){
        try {
            WebUtil.printApi(response,userService.getIndex());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 工程端首页
     * @return
     * @throws JsonProcessingException
     */
    /*@RequestMapping(value = "/index")
    @ApiOperation(notes = "首页", httpMethod = "GET", value = "工程首页")
    public Result repairIndex() throws JsonProcessingException {
        Map<String, Object> listMap = new HashMap<>();
        //轮播图
        List<Banner> banners = bannerService.findIsShowBanner(true);
        listMap.put("banner", banners);
        //推荐订单
        listMap.put("orderList", orderService.indexOrder());
        return new Result(true).data(listMap);
    }*/

    @RequestMapping(value = "/personal/user")
    @ApiOperation(notes = "用户个人中心", httpMethod = "GET", value = "个人中心")
    public void personalUser(HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        WebUtil.printApi(response, new Result(true).data(userSessionModul));
    }


    @RequestMapping(value = "/personal/repair")
    @ApiOperation(notes = "工程师个人中心", httpMethod = "GET", value = "个人中心")
    public void personalRepair(HttpServletResponse response) {
        try {
            UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
            Map<String, String> sessionMap = new HashMap<>();
            sessionMap.put("honor", userSessionModul.getHonor().toString());
            sessionMap.put("nickName", userSessionModul.getNickName());
            sessionMap.put("photo", userSessionModul.getPhoto());
            sessionMap.put("isBusy", userRepairInfoService.findByUser_Id(userSessionModul.getId()).is_busy() ? "1" : "0");
            WebUtil.printApi(response, new Result(true).data(sessionMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/registration")
    @ApiOperation(notes = "获取当月的签到记录", httpMethod = "POST", value = "签到记录")
    public void registration(@ApiParam(name = "lastDate", value = "查询签到记录月份的最后一天")
                             @RequestParam(value = "lastDate") String lastDate,
                             HttpServletResponse response) {
        if (!DateUtils.isValidDate(lastDate)) {
            WebUtil.printApi(response, new Result(false).msg("参数错误").data(EASY_ERROR_CODE.ERROR_CODE_0001));
            return;
        }
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        WebUtil.printApi(response, new Result(true).data(registrationService.getRegistrationByTime(lastDate, userSessionModul.getId())));
    }

    //执行签到
    @RequestMapping(value = "execute/registration")
    @ApiOperation(notes = "签到", httpMethod = "POST", value = "签到")
    public void executeRegistration(HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        if (registrationService.isExecuteRegistration(userSessionModul.getId())) {
            WebUtil.printApi(response, new Result(false).msg("今日已签到"));
        } else {
            WebUtil.printApi(response, new Result(true).data(registrationService.executeRegistration(userSessionModul.getId())));
        }
    }


    @RequestMapping(value = "/change/userPhoto")
    @ApiOperation(notes = "传递一个本地图片的List,长度为1.如果传入多张将执行不了", httpMethod = "POST", value = "修改头像")
    public void changePhoto(HttpServletRequest request,
                            @RequestPart MultipartFile photo,
                            HttpServletResponse response) {
        if (!SessionUtils.contains("user")) {
            WebUtil.printApi(response, new Result(false).msg("服务器异常").data(EASY_ERROR_CODE.ERROR_CODE_0001));
            return;
        }
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        User user = userService.getById(userSessionModul.getId());
        Map<String, Object> image = ImageUtilsService.getImages(request, photo, true);
        user.getUserInfo().setPhoto(image.get("imgURL").toString());
        user.getUserInfo().setPhotoThu(image.get("imgThu").toString());
        userService.update(user);
        SessionUtils.put("user", ApiBeanUtils.getUserModel(user, userSessionModul));
        WebUtil.printApi(response, new Result(true).data(userSessionModul));
    }

    /**
     * 修改用户信息
     * @param userInfo
     * @param response
     */
    @RequestMapping(value = "/change/userInfo")
    @ApiOperation(notes = "传递userInfo对象,userInfo.nickName=昵称 userInfo.gender=性别(boolean,真=男，假=女)" +
            "userInfo.address=居住地  userInfo.email邮箱", httpMethod = "POST", value = "修改资料")
    public void changeUserInfo(@RequestBody @ApiParam(name = "userInfo", required = true, value = "用户详情对象")
                              UserInfo userInfo, HttpServletResponse response) {
        try {
            WebUtil.printApi(response,userInfoService.change(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printApi(response, new Result(false).msg("服务器异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
        }
    }

    /**
     * 意见反馈
     * @param content
     * @param response
     */
    @RequestMapping(value = "/{content}/feedBack")
    @ApiOperation(notes = "用户意见反馈,参数content=反馈内容", httpMethod = "POST", value = "意见反馈")
    public void feedBack(@ApiParam(name = "content", value = "反馈内容")
                         @PathVariable(value = "content") String content, HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        User user = new User();
        user.setId(userSessionModul.getId());
        Feedback feedback = new Feedback(user, content, false, new Date());
        feedbackService.create(feedback);
        WebUtil.printApi(response, new Result(true).msg("反馈提交成功"));
    }


    /**
     * 工程师开启/关闭忙碌状态
     * @param state
     * @param response
     */
    @RequestMapping(value = "/{state}/repair")
    @ApiOperation(notes = "工程师开启/关闭忙碌状态", httpMethod = "POST", value = "忙碌状态")
    public void repairIsBusy(@ApiParam(name = "state", value = "true=开启,false=关闭")
                             @PathVariable(value = "state") Boolean state, HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        UserRepairInfo userRepairInfo = userRepairInfoService.findByUser_Id(userSessionModul.getId());
        if (userRepairInfo == null) {
            WebUtil.printApi(response, new Result(false).msg("该工程师不存在").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        userRepairInfo.setIs_busy(state);
        userRepairInfoService.update(userRepairInfo);
        WebUtil.printApi(response, new Result(true).msg(userRepairInfo.is_busy() ? "开启" + "忙碌模式成功" : "关闭" + "忙碌模式成功"));
    }

    /**
     * 查询用户关注的所有粉丝（分页）
     * @param pageNum
     * @param response
     */
    @RequestMapping("mine/follow/fans")
    @ApiOperation(notes = "我的关注分页查询,查询所有关注的工程师", httpMethod = "GET", value = "我的关注")
    public void mineFollowUser(@ApiParam(name = "pageNum", value = "页码")
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Page<User> page = fansService.findPageByFansUser(userSessionModul.getId(), new PageRequest(new PageParam(pageNum).getStart(), 10));
        List<FansMudule> fansMuduleArrayList = new ArrayList<>();
        for (User user : page.getContent()) {
            UserInfo userInfo = user.getUserInfo();
            fansMuduleArrayList.add(new FansMudule(user.getId(), userInfo.getNickName(), userInfo.getHonor(), userInfo.getPhoto(), userInfo.getPhotoThu()));
        }
        WebUtil.printApi(response, new Result(true).data(DataTableFactory.fittingPojo(page, fansMuduleArrayList)));//userId ,userInfo.nickName,userInfo.honor
    }

    /**
     * 取消关注
     * @param cancel
     * @param followId
     * @param response
     */
    @RequestMapping("/{cancel}/{followId}/fans")
    @ApiOperation(notes = "取消关注/关注用户", httpMethod = "POST", value = "关注/取消")
    public void followUser(@ApiParam(name = "cancel", value = "true=取消,false=关注")
                           @PathVariable(value = "cancel") Boolean cancel,
                           @ApiParam(name = "followId", value = "被关注者ID")
                           @PathVariable(value = "followId") Long followId,
                           HttpServletResponse response) {
        try {
            UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
            fansService.cancelFollowOrFollow(cancel, userSessionModul.getId(), followId);
            WebUtil.printApi(response, new Result(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 我的交易记录
     * @param pageNum
     * @param type
     * @param response
     */
    @RequestMapping("mine/{type}/user")
    @ApiOperation(notes = "登录用户的交易记录", httpMethod = "POST", value = "交易记录")
    public void mineTrades(@ApiParam(name = "pageNum", value = "页码")
                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @PathVariable(value = "type") String type,
                           HttpServletResponse response) {
        try {
            UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
            Page<TradeInfo> tradeInfoList = tradeInfoService.findMinePage(userSessionModul.getId(), type, new PageRequest(pageNum - 1, 10, Sort.Direction.DESC, "createTime"));
            Map<String, Object> dataMap = DataTableFactory.fitting(tradeInfoList);
            WebUtil.printJson(response, JsonUtil.obj2ApiJson(new Result(true).data(dataMap), "user"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查看工程师详情
    @RequestMapping("detail/{id}/repair")
    @ApiOperation(notes = "查看工程师详情", httpMethod = "GET", value = "工程师详情")
    public void repairDetail(@ApiParam(name = "id", required = true, value = "工程师ID")
                             @PathVariable(value = "id") Long id, HttpServletResponse response) {
        try {
            WebUtil.printApi(response, userRepairInfoService.detail(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看工程师所有订单的评论分页
     * @param id
     * @param pageNum
     * @param response
     */
    @RequestMapping("comments/{id}/repair")
    @ApiOperation(notes = "查看工程师订单的评论", httpMethod = "GET", value = "评论分页")
    public void commentsPage(@ApiParam(name = "id", required = true, value = "RepairId")
                             @PathVariable(value = "id") Long id,
                             @ApiParam(name = "pageNum", value = "页码")
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             HttpServletResponse response) {
        Page<Comments> commentsPage = commentsService.findPageByRepairId(id, new PageRequest(pageNum - 1, 10, Sort.Direction.DESC, "id"));
        Map<String, Object> objectMap = DataTableFactory.fitting(commentsPage);
        WebUtil.printJson(response, JsonUtil.obj2ApiJson(new Result(true).data(objectMap), "comments", "order"));
    }

    /**
     * 工程师搜索列表页
     * @param serviceId
     * @param jobTitle
     * @param star
     * @param pageNum
     * @param response
     */
    @RequestMapping("select/page/repair")
    @ApiOperation(notes = "工程师搜索列表页", httpMethod = "POST", value = "工程师搜索页")
    public void repairPage(@ApiParam(name = "serviceId", value = "服务类型")
                           @RequestParam(value = "serviceId", defaultValue = "") Long serviceId,
                           @ApiParam(name = "jobTitle", value = "工作年限")
                           @RequestParam(value = "jobTitle", defaultValue = "") String jobTitle,
                           @ApiParam(name = "star", value = "星级")
                           @RequestParam(value = "star", defaultValue = "") Integer star,
                           @ApiParam(name = "pageNum", value = "页码")
                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           HttpServletResponse response) {
        try {
            UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
            UserPosition userPosition = userPositionService.findPositionByUserId(userSessionModul.getId());
            Map<String, Object> pageMap = userRepairInfoService.findRepairPage(userPosition.getLng(), userPosition.getLat(), serviceId, jobTitle, star, new PageParam(pageNum));
            WebUtil.printApi(response, new Result(true).data(pageMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 工程师获取认证信息
     * @return
     */
    @RequestMapping(value = "mine/authentication/repair", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(notes = "获取工程师认证信息", httpMethod = "GET", value = "认证信息")
    public void mineAuthentication(HttpServletResponse response) {
        try {
           WebUtil.printJson(response,userRepairInfoService.mineAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

