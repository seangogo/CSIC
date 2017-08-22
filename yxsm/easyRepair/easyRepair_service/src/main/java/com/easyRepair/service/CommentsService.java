package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Banner;
import com.easyRepair.tabEntity.Comments;
import com.easyRepair.tabEntity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 Created by sean on 2016/12/26. */
public interface CommentsService extends ICommonService<Comments> {
    Comments findByRepairId(User user);
    
    Comments findByOrder_Id(Long id);
    
    Page<Comments> findPageByRepairId(Long id, Pageable pageable);

    int findCountByRepair(User user);

    Page<Comments> page(Map<String, Object> searchParams, PageRequest pageRequest);
}
