/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.service.system;

import com.dh.common.CommonButil;
import com.dh.configure.annotation.SystemServiceLog;
import com.dh.entity.News;
import com.dh.repository.NewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class
NewsService {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private BannerService bannerService;

    /**
     * 获取新闻列表
     *
     * @param newsTitle
     * @param pageNumber
     * @return
     */
    @SystemServiceLog(description = "查询新闻列表")
    public Page<News> findNewsList(Integer pageNumber, String newsTitle, int pageSize) {
        PageRequest pageRequest = buildNewsListPageRequest(pageNumber, pageSize);
        Specification<News> spe = buildNewsListSpecification(newsTitle);
        //TODO 此处查询新闻列表，把富文本也一并查出，会影响效率 建议查询时不查询该字段
        return newsDao.findAll(spe, pageRequest);
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildNewsListPageRequest(int pageNumber, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "orderBy").and(new Sort(Sort.Direction.DESC, "createTime"));
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    /**
     * 设置查询新闻列表的条件
     *
     * @param newsTitle
     * @return
     */
    private Specification<News> buildNewsListSpecification(String newsTitle) {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        if (!CommonButil.isEmpty(newsTitle)) {
            filters.put("newsTitle", new SearchFilter("newsTitle", Operator.LIKE, newsTitle));
        }
        Specification<News> spec = DynamicSpecifications.bySearchFilter(filters.values(), News.class);
        return spec;
    }

    /**
     * 保存新闻
     *
     * @param news
     * @return
     */
    public void save(News news) {
        news.setUpdateTime(CommonButil.getNowTime());
        newsDao.save(news);
    }

    /**
     * 查看新闻详情
     *
     * @param id
     * @return
     */
    @SystemServiceLog(description = "查询新闻")
    public News findNewsById(Long id) {
        return newsDao.findOne(id);
    }

    /**
     * 删除新闻
     *
     * @param id
     */
    @SystemServiceLog(description = "删除新闻")
    public void delete(Long id) {
        newsDao.delete(id);
    }

    /**
     * 查找新闻标题list
     *
     * @return
     */
    @SystemServiceLog(description = "查询新闻标题列表")
    public List<News> findNewsTitleList() {
        return newsDao.findNewsTitleList();
    }

    /**
     * 检查banner中是否有关联该新闻
     *
     * @param id
     * @return
     */
    @SystemServiceLog(description = "检查是否有幻灯片关联新闻")
    public Boolean checkBannerHasNews(Long id) {
        return bannerService.findBannerCountByNewsId(id) > 0;
    }

    /**
     * 根据id查找新闻标题
     *
     * @param newsId
     * @return
     */
    @SystemServiceLog(description = "查询新闻标题")
    public String findNewsTitleById(Long newsId) {
        return newsDao.findNewsTitleById(newsId);
    }
}
