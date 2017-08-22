package com.dh.service.impl;

import com.dh.commont.CommonButil;
import com.dh.commont.PasswordUtil;
import com.dh.configure.Consts;
import com.dh.entity.InviteCode;
import com.dh.entity.User;
import com.dh.entity.UserPosition;
import com.dh.repository.InviteCodeDao;
import com.dh.repository.RoleDao;
import com.dh.repository.UserDao;
import com.dh.repository.UserPositionDao;
import com.dh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/12.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends Consts implements UserService {
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserPositionDao userPositionDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private InviteCodeDao inviteCodeDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Page<User> find(int pageNum, int pageSize) {
        return userDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<User> find(int pageNum) {
        return find(pageNum, Consts.PAGE_SIZE);
    }

    @Override
    public User getById(int id) {
        return userDao.findOne(id);
    }

    @Override
    @Transactional
    public User deleteById(int id) {
        User user = getById(id);
        userDao.delete(user);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        userDao.save(user);
        return user;
    }

    @Override
    @Transactional
    public User update(User user) {
        return userDao.save(user);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public String findUserDeviceId(Integer userId) {
        return userDao.findUserDeviceId(userId);
    }

    /**
     * 检查密码是否正确
     *
     * @param user
     * @param plainPassword
     * @return
     */
    @Override
    public Boolean checkUserPassword(User user, String plainPassword) {
        String salt = user.getSalt();
        return getPassword(salt, plainPassword).equals(user.getPassword());
    }

    /**
     * 根据salt 获取加密后的密码
     *
     * @param salt
     * @param plainPassword
     * @return
     */
    public String getPassword(final String salt, final String plainPassword) {
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_INTERATIONS);
        return Encodes.encodeHex(hashPassword);
    }

    /**
     * 修改用户的登录状态、登录次数、设备id、登录时间等
     *
     * @param user
     * @param deviceOs
     * @param deviceId
     * @param lastLocation
     */
    @Override
    @Transactional
    public void updateUserLoginState(User user, String deviceOs, String deviceId, String lastLocation) {
        //清空当前设备id
        //修改用户最后登录时间、位置、次数、登录设备id
        user.setDeviceOs(deviceOs);
        user.setDeviceId(deviceId);
        if (!CommonButil.isEmpty(lastLocation)) {
            user.setLastLocation(lastLocation);
            user.setLastLocationTime(CommonButil.getNowTime());
        }
        //添加用户的位置
        UserPosition userPosition = new UserPosition();
        userPosition.setCreateTime(CommonButil.getNowTime());
        userPosition.setLastLocation(lastLocation);
        userPosition.setUser(user);
        userPositionDao.save(userPosition);
        //更新用户其他信息
        user.setLoginCount(user.getLoginCount() == null ? 1 : user.getLoginCount() + 1);
        user.setLastLoginTime(CommonButil.getNowTime());
        user.setIsLogin(1);
        userDao.save(user);
    }

    @Override
    @Transactional
    public User updatePassword(User user) {
        PasswordUtil.entryptPassword(user);
        return userDao.save(user);
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        PasswordUtil.entryptPassword(user);
        //手机注册用户默认为普通用户
        user.setRole(roleDao.findRoleByName(Consts.USER_ROLE));
        user.setIsLogin(0);
        user.setUserState(0);
        user.setUserType(0);
        user.setLoginCount((long) 0);
        user.setIsLocked(0);
        user.setRegisterDate(CommonButil.getNowTime());
        return userDao.save(user);
    }

    /**
     * 获取邀请码
     *
     * @param inviteCode
     * @return
     */
    @Override
    public InviteCode getInviteCode(String inviteCode) {
        return inviteCodeDao.getInviteCode(inviteCode);
    }

    /**
     * 用户认证
     *
     * @param user
     * @param code
     */
    @Override
    @Transactional
    public User userAuthentication(User user, InviteCode code) {
        //修改邀请码的使用状态、使用时间、使用人
        code.setToUser(user);
        code.setIsUse(1);
        code.setUseTime(CommonButil.getNowTime());
        inviteCodeDao.save(code);
        //修改用户的认证状态
        user.setUserState(1);
        return userDao.saveAndFlush(user);
    }

    @Override
    public User findUserByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }
}
