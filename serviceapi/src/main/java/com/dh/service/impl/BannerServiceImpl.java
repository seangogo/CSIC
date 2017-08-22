package com.dh.service.impl;

import com.dh.commont.CommonButil;
import com.dh.configure.Consts;
import com.dh.entity.Banner;
import com.dh.repository.BannerDao;
import com.dh.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/12.
 */
@Service
@Transactional(readOnly = true)
public class BannerServiceImpl extends Consts implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    public List<Banner> findAll() {
        return null;
    }

    @Override
    public Page<Banner> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<Banner> find(int pageNum) {
        return null;
    }

    @Override
    public Banner getById(int id) {
        return null;
    }

    @Override
    public Banner deleteById(int id) {
        return null;
    }

    @Override
    public Banner create(Banner banner) {
        return null;
    }

    @Override
    public Banner update(Banner banner) {
        return null;
    }

    @Override
    public void deleteAll(int[] ids) {

    }

    @Override
    public List<Banner> getBanner() {
        List<Banner> list = bannerDao.getBanner(1);
        for (Banner b : list) {
            String img = CommonButil.isEmpty(b.getBannerImg()) ? null : IMG_URL + b.getBannerImg();
            b.setBannerImg(img);
        }
        return list;
    }
}
