package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Order;
import com.easyRepair.tabEntity.PayLog;
import com.easyRepair.tabEntity.UserInfo;
import com.easyRepair.tabEntity.UserLoginInfo;
import common.core.bean.Result;

/**
 * Created by sean on 2016/11/14.
 */
public interface UserInfoService extends ICommonService<UserInfo> {

    /*更改登录状态*/
    void updateUserLoginState(UserLoginInfo userLoginInfo, Double lng, Double lat, String deviceId, String deviceOs);

    PayLog changeMoney(UserInfo userInfo, boolean b, Double realPay, Integer action);

    PayLog changeMoney(UserInfo userInfo, boolean b, Order order, Integer action);

    /**
     * 完善个人信息
     * @param userId
     * @param userInfo
     * @return
     */
    Result perfect(Long userId, UserInfo userInfo);

    Result change(UserInfo userInfo);
}
    