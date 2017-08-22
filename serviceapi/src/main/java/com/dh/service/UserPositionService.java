package com.dh.service;

import com.dh.entity.User;
import com.dh.entity.UserPosition;
import com.dh.service.common.ICommonService;

/**
 * Created by Coolkid on 2016/9/14.
 */
public interface UserPositionService extends ICommonService<UserPosition> {

    void updateLocation(User user, String location);
}
