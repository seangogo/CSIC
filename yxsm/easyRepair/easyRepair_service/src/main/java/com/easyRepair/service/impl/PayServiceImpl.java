package com.easyRepair.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 Created by sean on 2017/1/10. */
@Service
@Transactional(readOnly = true)
public class PayServiceImpl {
    /*预支付生成订单*/
    public void payDemo(){
       /* String md5 = Md5Util.md5(source).toLowerCase();
        Logger.info("--------------------------------------");
        Logger.info("原串:" + source);
        Logger.info("加密是否匹配:" + md5.equals(payCallbackVo.getMd5()));
        Logger.info("加密后的串:" + md5);
        Logger.info("--------------------------------------");*/
    }
    //存储日志
}
