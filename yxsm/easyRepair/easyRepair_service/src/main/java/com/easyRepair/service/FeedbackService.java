package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 Created by sean on 2016/12/1. */
public interface FeedbackService extends ICommonService<Feedback> {

    Page<Feedback> page(Map<String, Object> searchParams, PageRequest pageRequest);
}
