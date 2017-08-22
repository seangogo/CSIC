package com.dh.service.impl;

import com.dh.commont.CommonButil;
import com.dh.entity.User;
import com.dh.entity.UserPosition;
import com.dh.repository.UserDao;
import com.dh.repository.UserPositionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/14.
 */
@Service
@Transactional(readOnly = true)
public class UserPositionServiceImpl implements com.dh.service.UserPositionService {
    @Autowired
    private UserPositionDao userPositionDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<UserPosition> findAll() {
        return null;
    }

    @Override
    public Page<UserPosition> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<UserPosition> find(int pageNum) {
        return null;
    }

    @Override
    public UserPosition getById(int id) {
        return null;
    }

    @Override
    public UserPosition deleteById(int id) {
        return null;
    }

    @Override
    public UserPosition create(UserPosition userPosition) {
        return null;
    }

    @Override
    public UserPosition update(UserPosition userPosition) {
        return null;
    }

    @Override
    public void deleteAll(int[] ids) {

    }

    @Override
    @Transactional
    public void updateLocation(User user, String location) {
        user.setLastLocation(location);
        user.setLastLocationTime(CommonButil.getNowTime());
        userDao.save(user);
        // 添加用户的位置
        UserPosition userPosition = new UserPosition();
        userPosition.setUser(user);
        userPosition.setLastLocation(location);
        userPosition.setCreateTime(CommonButil.getNowTime());
        userPositionDao.save(userPosition);
    }
}
