package com.dh.service;

import com.dh.entity.InviteCode;
import com.dh.entity.User;
import com.dh.service.common.ICommonService;

/**
 * Created by Coolkid on 2016/9/12.
 */
public interface UserService extends ICommonService<User> {
    //通过用户ID查找了用户设备号是否存在
    String findUserDeviceId(Integer userId);

    //根据登录名查找用户
    User findUserByLoginName(String loginName);

    //根据查询的用户信息验证密码
    Boolean checkUserPassword(User user, String plainPassword);

    //得到加盐都带密码
    String getPassword(String salt, String plainPassword);

    // 修改用户最后登录时间、位置、次数,并插入用户位置表
    void updateUserLoginState(User user, String deviceOs, String deviceId, String lastLocation);

    //修改用户密码
    User updatePassword(User user);

    User registerUser(User user);

    InviteCode getInviteCode(String inviteCode);

    User userAuthentication(User user, InviteCode code);
}
