package com.dh.service;

import com.dh.entity.Banner;
import com.dh.service.common.ICommonService;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/12.
 */
public interface BannerService extends ICommonService<Banner> {
    List<Banner> getBanner();
}
