package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.CouponComService;
import com.easyRepair.service.CouponQualifiedService;
import com.easyRepair.service.UserService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.CouponCom;
import com.easyRepair.tabEntity.CouponQualified;
import com.easyRepair.tabEntity.User;
import common.annotation.SystemControllerLog;
import common.core.bean.PageParam;
import common.core.bean.Result;
import common.utils.JsonUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20.
 */
@Controller
@RequestMapping("/couponQualified")
public class CouponQualifiedController {

    @Autowired
    private CouponQualifiedService couponQualifiedService;

    @Autowired
    private  UserService userService;

    @Autowired
    private CouponComService couponComService;

    @SystemControllerLog(description = "进入领券资格界面")
    @RequiresPermissions("couponQualified:view")
    @RequestMapping("/list")
    public String toCouponQualified( ){
        return "/couponQualified/list";
    }

    @RequiresPermissions("couponQualified:view")
    @RequestMapping(value = "/page")
    public void couponQualifiedPage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<CouponQualified> page = couponQualifiedService.page(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SystemControllerLog(description = "新增领券资格")
    @RequiresPermissions("couponQualified:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void createCouponQualified(CouponQualified couponQualified, Long userId, Long couponComId, HttpServletResponse response) {
        try {
            couponQualified.setUser(userService.getById(userId));
            couponQualified.setCouponCom(couponComService.getById(couponComId));
            couponQualified.setCreateTime(new Date());
            couponQualifiedService.update(couponQualified);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "修改领券资格")
    @RequiresPermissions("couponQualified:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void updateCouponQualified(CouponQualified couponQualified, Long userId, Long couponComId, HttpServletResponse response) {
        try {
            CouponQualified couponQualified2=couponQualifiedService.getById(couponQualified.getId());
            couponQualified2.setUser(userService.getById(userId));
            couponQualified2.setCount(couponQualified.getCount());
            couponQualified2.setCouponCom(couponComService.getById(couponComId));

            couponQualifiedService.update(couponQualified2);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "删除领券资格")
    @RequiresPermissions("couponQualified:delete")
    @RequestMapping("/delete")
    public void deleteCouponQualified(HttpServletResponse response,String ids){
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            couponQualifiedService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!请先删除其他关联数据"));
        }
    }

    @RequiresPermissions("couponQualified:view")
    @RequestMapping("/findUserAndCouponCom")
    @ResponseBody
    public Map<String, Object> findUser(Long id) {
        Map<String, Object> map = new HashedMap();
        CouponQualified couponQualified=couponQualifiedService.getById(id);
        User user= userService.getById(couponQualified.getUser().getId());
        CouponCom couponCom = couponComService.getById(couponQualified.getCouponCom().getId());
        map.put("user", user);
        map.put("couponCom", couponCom);
        return map;
    }

    @RequiresPermissions("couponQualified:view")
    @RequestMapping("/allUser")
    @ResponseBody
    public List<User> allUser() {
        List<User> users = userService.findAll();
        return users;
    }

    @RequiresPermissions("couponQualified:view")
    @RequestMapping("/allCouponCom")
    @ResponseBody
    public List<CouponCom> allCouponCom() {
        List<CouponCom> couponComs = couponComService.findAll();
        return couponComs;
    }


}
