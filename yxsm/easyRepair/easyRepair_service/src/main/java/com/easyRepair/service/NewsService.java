package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2017/1/24. */
public interface NewsService extends ICommonService<News> {
    Page<News> findPageByNewsType(Long newsTypeId, Pageable pageable);

    Page<News> page(Map<String, Object> searchParams, PageRequest pageRequest);

    List<News> findByIsShow();
}
