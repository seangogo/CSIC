package com.easyRepair.api;

import com.easyRepair.service.NewsService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.News;
import common.core.bean.PageParam;
import common.core.bean.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 Created by sean on 2017/1/24. */
@Api(value = "/news", description = "关于新闻资讯相关接口")
@RestController
@RequestMapping(value = "/api/news")
public class NewsApiController {
    @Autowired
    private NewsService newsService;
    //新闻分页
    @RequestMapping(value = "page/news")
    @ApiOperation(notes = "根据条件查询新闻分页", httpMethod = "GET", value = "新闻分页")
    public Result newsPage(@ApiParam(name = "newsTypeId", value = "用于查询服务类型的设备ID")
                           @RequestParam(value ="newsTypeId",defaultValue = "1") Long newsTypeId,
                           @RequestParam(value = "pageNum") Integer pageNum) {
        Page<News> page=newsService.findPageByNewsType(newsTypeId,new PageRequest(new PageParam(pageNum).getStart(),2));
        return new Result(true).data(DataTableFactory.fitting(page));
    }
    //新闻详情
    @RequestMapping(value = "details/{newsId}/news")
    @ApiOperation(notes = "根据ID查询新闻详情", httpMethod = "GET", value = "新闻详情")
    public Result newsPage(@ApiParam(name = "newsId", value = "新闻ID")
                           @PathVariable(value ="newsId") Long newsId) {
        return new Result(true).data(newsService.getById(newsId));
    }
    
    
}
