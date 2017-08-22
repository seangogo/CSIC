package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/12/20. */
public interface BannerService extends ICommonService<Banner> {
    List<Banner> findIsShowBanner(boolean shows);

    Page<Banner> page(Map<String, Object> searchParams, PageRequest pageRequest);

    Integer findCountByNewsIds(long[] arrayId);
}
