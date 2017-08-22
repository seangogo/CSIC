package com.easyRepair.service.Cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 Created by sean on 2016/11/16. */
public class CacheUtils {
    //将code加入缓存
    @Cacheable(value = "andCache", key = "#code + '<'+#mobile")
    public String addCode(String code, String mobile) {
        return code;
    }

    //验证code是否存在
    @Cacheable(value = "andCache", key = "#code + '<'+#mobile")
    public String checkCode(String code, String mobile) {
        @SuppressWarnings("unused")
        Checkcache c = new Checkcache(code + mobile);
        return code;
    }

    //清除掉指定key的缓存
    @CacheEvict(value = "andCache", key = "#code + '<'+#mobile")
    public void delete(String code, String mobile) {
    }
}
