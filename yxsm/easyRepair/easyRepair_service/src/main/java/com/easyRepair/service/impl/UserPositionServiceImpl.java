package com.easyRepair.service.impl;

import com.easyRepair.dao.UserPositionDao;
import com.easyRepair.service.OrderService;
import com.easyRepair.service.UserLoginInfoService;
import com.easyRepair.service.UserPositionService;
import com.easyRepair.service.UserService;
import com.easyRepair.tabEntity.User;
import com.easyRepair.tabEntity.UserLoginInfo;
import com.easyRepair.tabEntity.UserPosition;
import common.core.EASY_ERROR_CODE;
import common.core.bean.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 Created by sean on 2016/12/5. */
@Service
@Transactional(readOnly = true)
public class UserPositionServiceImpl implements UserPositionService {
    @Autowired
    private UserPositionDao userPositionDao;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserLoginInfoService userLoginInfoService;
    @PersistenceContext
    private EntityManager em;
    
    
    public List<UserPosition> findAll() {
        return null;
    }
    
    public Page<UserPosition> find(int i, int i1) {
        return null;
    }
    
    public Page<UserPosition> find(int i) {
        return null;
    }
    
    public UserPosition getById(long l) {
        return null;
    }
    
    public UserPosition deleteById(long l) {
        return null;
    }
    
    @Transactional
    public UserPosition create(UserPosition userPosition) {
        return userPositionDao.saveAndFlush(userPosition);
    }
    
    public UserPosition update(UserPosition userPosition) {
        return userPositionDao.saveAndFlush(userPosition);
    }
    
    public void deleteAll(long[] longs) {
        
    }
    
    @Transactional
    public void saveRealTimePosition(Long id, Double lng, Double lat) {
        User user = userService.getById(id);
        UserLoginInfo userLoginInfo = user.getUserLoginInfo();
        userLoginInfo.setLastLng(lng);
        userLoginInfo.setLastLat(lat);
        userLoginInfoService.update(userLoginInfo);
        UserPosition userPosition = new UserPosition(user, lng, lat, new Date(), 0);
        create(userPosition);
    }
    
    public UserPosition findByTimeAndUserId(Long userId) {
        String sql = "SELECT o.* FROM t_user_position  o " +
                "WHERE o.create_time < date_add(now(), INTERVAL - 15 MINUTE) AND o.user_id=?1 ORDER BY o.create_time DESC LIMIT 1";
    
        Query query = em.createNativeQuery(sql, UserPosition.class);
        query.setParameter(1, userId);
        List<UserPosition> list = query.getResultList();
        if(list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    
    }
    
    public UserPosition findPositionByUserId(Long id) {
        String sql = "SELECT o.* FROM t_user_position o " +
                "WHERE o.user_id =?1 ORDER BY o.create_time DESC LIMIT 1";
        Query query = em.createNativeQuery(sql, UserPosition.class);
        query.setParameter(1, id);
        List<UserPosition> list = query.getResultList();
        if(list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    
    public List<UserPosition> orderByDiskInUserId(List<Long> userIds) {
        String sql = "SELECT a.* FROM t_user_position a ,(SELECT user_id, max(create_time) max_day FROM t_user_position  GROUP BY user_id) b  " +
                "WHERE a.user_id = b.user_id AND a.create_time = b.max_day AND a.user_id in (?1)";
        Query query = em.createNativeQuery(sql, UserPosition.class);
        query.setParameter(1, userIds);
        return query.getResultList();
    }
    //获得最新的经纬度
    public Result findByRepairLngAndLat(Long repairId, Long id) {
        //验证用户是否拥有查看权限
        if (!orderService.checkRepairLngAndLat(repairId,id)){
            return new Result(false).msg("无法查看").data(EASY_ERROR_CODE.ERROR_CODE_0002);
        }
        //查询最新的经纬度
        Query query=em.createNativeQuery("SELECT * FROM t_user_position u WHERE u.user_id=?1 ORDER BY u.create_time DESC LIMIT 1",UserPosition.class);
        query.setParameter(1,repairId);
        UserPosition userPosition=(UserPosition) query.getSingleResult();
        return new Result(true).msg("获取成功").data(userPosition);
    }
}
