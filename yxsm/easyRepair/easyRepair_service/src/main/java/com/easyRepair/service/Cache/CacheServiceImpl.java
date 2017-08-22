package com.easyRepair.service.Cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 Created by sean on 2016/11/16. */
@Service
public class CacheServiceImpl {
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
    
    public boolean checkLoginNameCode(String code, String mobile) {
        try {
            // 验证code
            @SuppressWarnings("unused")
            Checkcache c = new Checkcache("aaa");
            checkCode(code, mobile);
            if(Checkcache.returnval().equals(code + mobile)) {
                // 不存在
                delete(code, mobile);
                return false;
            }
            // 存在，验证通过
            // 清除缓存
            delete(code, mobile);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
}
