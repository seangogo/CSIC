package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.service.ResourceService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Resource;
import common.annotation.SystemControllerLog;
import common.core.bean.PageParam;
import common.core.bean.Result;
import common.utils.JsonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/24.
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/list")
    public String list() {
        return "/resource/list";
    }

    @RequestMapping("/findMenu")
    @ResponseBody
    public List<Resource> findMenu() {
        List<Resource> resource = (List<Resource>) SessionUtils.get("userMenu");
        return resource;
    }

    @RequiresPermissions("resource:view")
    @RequestMapping(value = "page", method = RequestMethod.GET)
    @SystemControllerLog(description = "查看菜单列表")
    @ResponseBody
    public Map<String, Object> page(String type, Integer draw, Integer start, Integer length) {
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Page<Resource> page = resourceService.page(new PageRequest(pageNum - 1, length), type);
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresPermissions("resource:create")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增修改菜单")
    public void create(Resource resource, HttpServletResponse response) {
        try {
            Long id=resource.getId();
            resourceService.saveResource(resource);
            WebUtil.print(response, new Result(true).msg(id==null?"添加成功":"修改成功"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("添加失败"));
        }
    }

    @RequiresPermissions("resource:delete")
    @SystemControllerLog(description = "删除菜单")
    @RequestMapping("/batchDelete")
    public void batchDelete(HttpServletResponse response, String ids) {
        try {
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            WebUtil.print(response, resourceService.batchDelete(arrayId));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("批量删除资讯失败!"));
        }
    }

}
