package com.easyRepair.service.impl;

import com.easyRepair.dao.UserDao;
import com.easyRepair.entityUtils.ApiBeanUtils;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.*;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.*;
import common.core.Constant;
import common.core.EASY_ERROR_CODE;
import common.core.bean.Result;
import common.utils.*;
import common.utils.Iphone.CodesUtils;
import common.utils.jpush.SendPush;
import common.utils.password.Des3;
import common.utils.password.PasswordUtil;
import io.rong.RongCloud;
import io.rong.models.TokenReslut;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by sean on 2016/11/8.
 */
@SuppressWarnings("JpaQlInspection")
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRepairInfoService userRepairInfoService;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private UserLoginInfoService userLoginInfoService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private OrderService orderService;

    //查找根据用户类型和手机号查找没有锁定的用户
    public List<User> findUserByLoginNameAndTypeAndIsLocked(String loginName, String type, boolean isLocked) {
        return userDao.findUserByLoginNameAndTypeAndIsLocked(loginName, type, isLocked);
    }


    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Transactional
    public Result register(User user) {
        // 简单解密后设置明文密码值password = Des3.encode(password);
        //密码加密
        try {
            String[] passwords = PasswordUtil.entryptSaltAndPassword(Des3.decode(user.getPassword()));
            user.setPassword(passwords[1]);
            user.setSalt(passwords[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false).msg("注册失败!").data(EASY_ERROR_CODE.ERROR_CODE_0030);
        }
        //用户登录表
        RongCloud rongCloud = RongCloud.getInstance(Constant.RONG_APPKEY, Constant.RONG_APPSECRET);
        // 获取 Token 方法
        TokenReslut userGetTokenResult = null;
        try {
            userGetTokenResult = rongCloud.user.getToken(user.getLoginName() + user.getType(), user.getLoginName(), "http://www.rongcloud.cn/images/logo.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> resultMap = JsonUtil.json2Obj(userGetTokenResult.toString(), HashMap.class);
        user.getUserLoginInfo().setToken(resultMap.get("token"));
        //用户详情表
        UserInfo userInfo = user.getUserInfo();
        userInfo.setScore(discountService.findNumByType(0).intValue());//积分
        userInfo.setMoney(0.00F);//账户金额
        userInfo.setCreateTime(new Date());//创建时间
        userInfo.setPhoto(ConfigUtil.getBaseUrl("default.boyPhoto"));//头像
        userInfo.setPhotoThu(ConfigUtil.getBaseUrl("default.boyPhoto"));//缩略图
        userInfo.setGender(true);//默认男
        userInfo.setHonor(0);//荣誉值
        userInfo.setInvitationCode(CodesUtils.jobCode(user.getLoginName()));//邀请码
        userInfo.setFansCount(0);//粉丝
        user.setUserInfo(userInfo);
        User userCheck = userDao.saveAndFlush(user);
        if (userCheck == null) {
            return new Result(false).msg("注册失败!").data(EASY_ERROR_CODE.ERROR_CODE_0030);
        }
        if (user.getType().equals("1")) {
            UserRepairInfo userRepairInfo = new UserRepairInfo(userCheck, 0, "这家伙很懒!什么都没有留下...",
                    CodesUtils.jobCode(user.getLoginName()), userCheck.getId().intValue(), new Date());
            userRepairInfoService.create(userRepairInfo);
        }
        SessionUtils.put("user", ApiBeanUtils.getUserModel(user, new UserSessionModul()));
        return new Result(true).msg("注册成功!");
    }

    public boolean checkUserPassword(User user, String password) {
        /**
         * 根据salt 获取加密后的密码
         *
         * @param salt
         * @param plainPassword
         * @return
         */
        String salt = user.getSalt();
        return Encodes.encodeHex(Digests.sha1(password.getBytes(),
                Encodes.decodeHex(salt), StringCodeEnum.HASH_INTERATIONS))
                .equals(user.getPassword());
    }

    public List<User> findAllUser() {
        return userDao.findAll();
    }

    public User findById(Long id) {
        return userDao.findOne(id);
    }

    /**
     * 用户分页
     *
     * @param searchParams 搜索参数
     * @param pageRequest
     * @return
     */
       /* EntityGraph graph = this.em.getEntityGraph("graph.Order.items");Map hints = new HashMap();hints.put("javax.persistence.fetchgraph", graph);return this.em.find(Order.class, orderId, hints);
        Query query = entityManager.createQuery("select u from User u LEFT join fetch u.userInfo LEFT join fetch u.userLoginInfo");
        Query countQuery = entityManager.createQuery("select count(u) from User u");
        Long count = (Long) countQuery.getSingleResult();
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        List<User> users = query.getResultList();
        return new PageImpl(users, pageRequest, count.intValue());*/
    public Page<User> page(Map<String,Object> searchParams,PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);
        Page<User> page= userDao.findAll(spec, pageRequest);
        return page;
    }

    /**
     * 新增修改用户信息
     *
     * @param user
     */
    @Transactional
    public Result createAndUpdate(User user) {
        //新增
        if (user.getId() == null) {
            //检验用户是否已经存在
            if (!checkUserLoginNameAndType(user)) {
                return new Result(false).msg("添加失败!用户已存在");
            }
            String[] passwords = PasswordUtil.entryptSaltAndPassword(user.getPassword());
            user.setPassword(passwords[1]);
            user.setSalt(passwords[0]);
            user.getUserInfo().setCreateTime(new Date());
            user.getUserInfo().setFansCount(0);
            user.getUserInfo().setHonor(0);
            user.getUserInfo().setScore(0);

            //用户登录表
            RongCloud rongCloud = RongCloud.getInstance(Constant.RONG_APPKEY, Constant.RONG_APPSECRET);
            // 获取 Token 方法
            TokenReslut userGetTokenResult = null;
            try {
                userGetTokenResult = rongCloud.user.getToken(user.getLoginName() + user.getType(), user.getLoginName(), "http://www.rongcloud.cn/images/logo.png");
            } catch (Exception e) {
                e.printStackTrace();
            }
            String result = userGetTokenResult.toString();
            Map<String, String> resultMap = JsonUtil.json2Obj(result, HashMap.class);
            String token = resultMap.get("token");
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setToken(token);
            user.setUserLoginInfo(userLoginInfo);
        }else {
            //修改
            //通过用户ID查找用户信息
            UserInfo Info=userDao.findUserInfoById(user.getId());
            if (!checkUserLoginNameAndType(user)) {
                return new Result(false).msg("添加失败!用户已存在");
            }
        }
        if (userDao.save(user) != null) {
            if (user.getType().equals("1")) {
                UserRepairInfo userRepairInfo = new UserRepairInfo();
                userRepairInfo.setUser(user);
                userRepairInfo.setProfile("这家伙很懒!什么都没有留下...");
                userRepairInfo.setJobCode(CodesUtils.jobCode(user.getLoginName()));
                userRepairInfo.setState(0);
                userRepairInfo.setSort(user.getId().intValue());
                userRepairInfoService.create(userRepairInfo);
            }
            return new Result(true).msg("操作成功");
        }
        return new Result(false).msg("操作失败");
    }

    /**
     * 根据用户名和类型检验用户是否已经存在
     *
     * @param user
     * @return
     */
    public boolean checkUserLoginNameAndType(User user) {
        List<User> userList = userDao.findByLoginNameAndType(user.getLoginName(), user.getType());
        return userList == null || userList.size() == 0;
    }


    /**
     * 接口修改密码
     *
     * @param user
     * @return
     */
    @Transactional
    public Result findPassword(User user) {
        String[] passwords = PasswordUtil.entryptSaltAndPassword(user.getPassword());
        user = userDao.findFirstByLoginNameAndTypeAndIsLocked(user.getLoginName(), user.getType(),false);
        user.setSalt(passwords[0]);
        user.setPassword(passwords[1]);
        userDao.saveAndFlush(user);
        return new Result(true).msg("修改密码成功");
    }

    /**
     * 接口前台登录
     * @param user
     * @param userLoginInfo
     * @return
     */
    @Transactional
    public Result apiLogin(User user, UserLoginInfo userLoginInfo) {
        String password = user.getPassword();
        user = userDao.findFirstByLoginNameAndTypeAndIsLocked(user.getLoginName(), user.getType(), false);
        if (user == null || !checkUserPassword(user, password)) {
            return new Result(false).msg("账户密码错误").data(EASY_ERROR_CODE.ERROR_CODE_0005);
        }
        UserSessionModul userSessionModul = ApiBeanUtils.getUserModel(user, new UserSessionModul());
        //判断若该用户之前登录的设备不是当前设备，则给之前设备推送一条信息
        if (!StringUtils.isEmpty(userSessionModul.getDeviceId())
                && !userSessionModul.getDeviceId().equals(userLoginInfo.getDeviceId()) && userSessionModul.getDeviceId().length() > 10) {
            String content = "你的帐号于" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + "在另一台设备登录。如非本人操作，则密码可能已泄露，建议及时修改密码。";
            SendPush.pushToUser(userSessionModul.getDeviceId(), content, "下线通知", "5", userSessionModul.getId().toString());
        }
        // 修改用户最后登录时间、位置、次数,并插入用户位置表
        userLoginInfo.setId(user.getUserLoginInfo().getId());//主键
        userLoginInfo.setToken(user.getUserLoginInfo().getToken());//融云
        userLoginInfoService.update(userLoginInfo);
        SessionUtils.put("user", userSessionModul);// 设置返回值 用户
        return new Result(true).msg("登录成功").data(userSessionModul);
    }

    public Result getIndex() {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        UserLoginInfo userLoginInfo = getById(userSessionModul.getId()).getUserLoginInfo();
        Map<String, Object> listMap = new HashMap<String,Object>();
        String bannerListString=JsonUtil.obj2ApiJson(bannerService.findIsShowBanner(true),"news");
        listMap.put("banner",JsonUtil.json2Obj(bannerListString,ArrayList.class));
        if (userSessionModul.getType().equals("0")){
            listMap.put("userRepair", userRepairInfoService.findByOrderBySort(userLoginInfo.getLastLng(), userLoginInfo.getLastLat()));
        }
        if (userSessionModul.getType().equals("1")){
            listMap.put("orderList", orderService.indexOrder());
        }

        return new Result(true).data(listMap);
    }


    /**
     * 锁定用户
     * @param isLocked
     * @param id
     */
    @Transactional
    public void lock(Boolean isLocked, Long id) {
        userDao.lockByIds(isLocked,id);
    }


    public List<User> findAll() {
        return userDao.findAll();
    }


    public User getById(long i) {
        return userDao.findOne(i);
    }

    @Transactional
    public User deleteById(long i) {
        User user = getById(i);
        userDao.delete(i);
        return user;
    }

    @Transactional
    public User create(User user) {
        return userDao.save(user);
    }

    @Transactional
    public User update(User user) {
        return userDao.save(user);
    }

    public void deleteAll(long[] ints) {

    }
}
