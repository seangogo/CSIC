package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.NewsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 Created by sean on 2017/1/24. */
public interface NewsTypeService extends ICommonService<NewsType> {

    Page<NewsType> page(Map<String, Object> searchParams, PageRequest pageRequest);

}
