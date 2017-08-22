package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.RegistrationService;
import com.easyRepair.service.UserService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.User;
import common.annotation.SystemControllerLog;
import common.core.EASY_ERROR_CODE;
import common.core.bean.PageParam;
import common.core.bean.Result;
import common.utils.DateUtils;
import common.utils.JsonUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/11/8.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping("list")
    public String list() {
        return "user/list";
    }

    /**
     * 前台用户分页
     * @param response
     * @param request
     * @param start
     * @param length
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    public void getUser(HttpServletResponse response, HttpServletRequest request, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<User> page = userService.page(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            WebUtil.printJson(response, DataTableFactory.dataTableFitting(page));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户新增修改
     *
     * @param user     前端模型
     * @param response
     */
    @RequiresPermissions("user:create")
    @SystemControllerLog(description = "新增用户")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public void save(User user, HttpServletResponse response) {
        try {
            WebUtil.print(response, userService.createAndUpdate(user));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败"));
            e.printStackTrace();
        }
    }

    /**
     * 后台锁定用户
     *
     * @param id
     * @param isLocked
     */
    @RequiresPermissions("user:lock")
    @SystemControllerLog(description = "锁定用户")
    @RequestMapping(value = "lock" /*method = RequestMethod.POST*/)
    @ResponseBody
    public void lock(Long id,Boolean isLocked,HttpServletResponse response) {
        try {
            userService.lock(isLocked, id);
             WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }


    /**
     * 检验登录名是否已存在
     *
     * @param user     前端模型
     * @param response
     */
    @RequestMapping(value = "check", method = RequestMethod.POST)
    @ResponseBody
    public void check(User user, HttpServletResponse response) {
        try {
            Map<String, Object> map = new HashMap();
            if (userService.checkUserLoginNameAndType(user)) map.put("ok", "");
            else map.put("error", "该手机号已经注册!");
            WebUtil.print(response, new Result(true).data(map));
        } catch (Exception e) {
            WebUtil.print(response, false);
            e.printStackTrace();
        }
    }

    /**
     * 获取用户每月的签到记录
     * @param lastDate
     * @param id
     * @param response
     */
    @RequestMapping(value = "/registration")
    public void registration(@RequestParam(value = "lastDate") String lastDate,
                             @RequestParam(value = "id") Long id,
                             HttpServletResponse response) {
        if (!DateUtils.isValidDate(lastDate)) {
            WebUtil.printApi(response, new Result(false).msg("参数错误").data(EASY_ERROR_CODE.ERROR_CODE_0001));
            return;
        }
        WebUtil.printApi(response, new Result(true).data(registrationService.getRegistrationByTime(lastDate,id)));
    }
}
