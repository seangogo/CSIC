package com.easyRepair.service.impl;

import com.easyRepair.dao.UserInfoDao;
import com.easyRepair.dao.UserLoginInfoDao;
import com.easyRepair.dao.UserPositionDao;
import com.easyRepair.entityUtils.ApiBeanUtils;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.*;
import com.easyRepair.tabEntity.*;
import common.core.Constant;
import common.core.bean.Result;
import common.utils.JsonUtil;
import io.rong.RongCloud;
import io.rong.models.CodeSuccessReslut;
import io.rong.models.TokenReslut;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/14. */
@Service
@Transactional(readOnly = true)
public class UserInfoServiceImpl implements UserInfoService {
    protected final static Log logger = LogFactory.getLog(UserInfoServiceImpl.class);
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserLoginInfoDao userLoginInfoDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PayLogService payLogService;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private UserLoginInfoService userLoginInfoService;

    /**
     * 完善个人信息
     * @param userId
     * @param userInfo
     * @return
     */
    @Transactional
    public Result perfect(Long userId, UserInfo userInfo) {
        User user = userService.getById(userId);
        UserInfo userInfoSave=user.getUserInfo();
        userInfoSave.setAddress(userInfo.getAddress());//地址
        userInfoSave.setNickName(userInfo.getNickName());//昵称
        userInfoSave.setGender(userInfo.isGender());//性别
        userInfoSave.setEmail(userInfo.getEmail());//邮箱
        userInfoSave.setScore(discountService.findNumByType(1).intValue()+userInfoSave.getScore());//添加积分
        user.setAuthentication(true);//认证状态
        // 刷新用户信息
        RongCloud rongCloud = RongCloud.getInstance(Constant.RONG_APPKEY, Constant.RONG_APPSECRET);
        String code;
        if (user.getUserLoginInfo().getToken() == null) {
            // 获取 Token 方法
            TokenReslut userGetTokenResult = null;
            try {
                userGetTokenResult = rongCloud.user.getToken(user.getLoginName() + user.getType(), userInfo.getNickName(), "http://www.rongcloud.cn/images/logo.png");
            } catch (Exception e) {
                e.printStackTrace();
            }
            String result1 = userGetTokenResult.toString();
            Map<String, String> resultMap = JsonUtil.json2Obj(result1, HashMap.class);
            String token = resultMap.get("token");
            code = resultMap.get("code");
            user.getUserLoginInfo().setToken(token);
            userLoginInfoService.update(user.getUserLoginInfo());
        } else {
            CodeSuccessReslut userRefreshResult = null;
            try {
                userRefreshResult = rongCloud.user.refresh(user.getLoginName() + user.getType(), userInfo.getNickName(), "http://www.rongcloud.cn/images/logo.png");
            } catch (Exception e) {
                e.printStackTrace();
            }
            String result = userRefreshResult.toString();
            Map<String, Object> resultMap = JsonUtil.json2Obj(result, HashMap.class);
            code = resultMap.get("code").toString();
        }
        if (code.equals("200.0")) {
            userInfoDao.saveAndFlush(userInfoSave);
            return   new Result(true);
        } else {
            return new Result(false);
        }
    }

    public Result change(UserInfo userInfo) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        User user = userService.getById(userSessionModul.getId());
        if(userInfoDao.countByNickNameOrEmail(userInfo.getNickName(),userInfo.getEmail())>0){
            return new Result(false).msg("邮箱或昵称已存在");
        }
        UserInfo userInfoSave = user.getUserInfo();
        if (userInfoSave.isGender() != userInfo.isGender()) userInfoSave.setGender(userInfo.isGender());
        if (!StringUtils.isEmpty(userInfo.getAddress())) userInfoSave.setAddress(userInfo.getAddress());
        if (!StringUtils.isEmpty(userInfo.getEmail())) userInfoSave.setEmail(userInfo.getEmail());
        if (!StringUtils.isEmpty(userInfo.getNickName())) userInfoSave.setNickName(userInfo.getNickName());
        update(userInfo);
        SessionUtils.put("user", ApiBeanUtils.getUserModel(user, userSessionModul));
        return new Result(true).msg("修改成功");
    }

    @Transactional
    public void updateUserLoginState(UserLoginInfo userLoginInfo, Double lng, Double lat, String deviceId, String deviceOs) {
        userLoginInfo.setDeviceOs(deviceOs);
        userLoginInfo.setDeviceId(deviceId);
        if(lng!=null&&lat!=null){
            userLoginInfo.setLastLat(lat);
            userLoginInfo.setLastLng(lng);
        }
        userLoginInfoDao.saveAndFlush(userLoginInfo);
    }
    /*账户变动充值提现(true 增加 false 减少)*/
    public PayLog changeMoney(UserInfo userInfo, boolean b, Double realPay,Integer action) {
        float price=realPay.floatValue();
        if(b)userInfo.setMoney(userInfo.getMoney() + price);
        else userInfo.setMoney(userInfo.getMoney() - price);
        return null;
    }
    
    /*用户账户变动(true 增加 false 减少)*/
    @Transactional
    public PayLog changeMoney(UserInfo userInfo, boolean b,Order order,Integer action) {
        float price=order.getRealpay().floatValue();
        if(b)userInfo.setMoney(userInfo.getMoney() + price);
        else userInfo.setMoney(userInfo.getMoney() - price);
        userInfo=update(userInfo);
        return  payLogService.create(new PayLog(order,order.getOrderNum(),action,price,userInfo!=null,1,"",new Date()));
    }
    
    
    public List<UserInfo> findAll() {
        return null;
    }
    
    public Page<UserInfo> find(int i, int i1) {
        return null;
    }
    
    public Page<UserInfo> find(int i) {
        return null;
    }
    
    public UserInfo getById(long l) {
        return null;
    }
    
    public UserInfo deleteById(long l) {
        return null;
    }
    
    @Transactional
    public UserInfo create(UserInfo userInfo) {
        return userInfoDao.save(userInfo);
    }
    
    @Transactional
    public UserInfo update(UserInfo userInfo) {
        return userInfoDao.saveAndFlush(userInfo);
    }
    
    public void deleteAll(long[] longs) {
        
    }
}
