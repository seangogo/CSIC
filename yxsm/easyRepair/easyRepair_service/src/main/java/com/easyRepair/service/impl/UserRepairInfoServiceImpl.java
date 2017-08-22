package com.easyRepair.service.impl;

import com.easyRepair.dao.UserDao;
import com.easyRepair.dao.UserRepairInfoDao;
import com.easyRepair.entityUtils.ApiBeanUtils;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.RepairListModule;
import com.easyRepair.pojo.RepairModule;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.*;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.*;
import common.core.EASY_ERROR_CODE;
import common.core.bean.PageParam;
import common.core.bean.Result;
import common.geo.DistUtils;
import common.utils.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by sean on 2016/11/22.
 */
@Service
@Transactional(readOnly = true)
public class UserRepairInfoServiceImpl implements UserRepairInfoService {
    @Autowired
    private UserRepairInfoDao userRepairInfoDao;

    @Autowired
    private AreasSearchService areasSearchService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private FansService fansService;
    @Autowired
    private ServiceAreaService serviceAreaService;
    @Autowired
    private QualificationImageService qualificationImageService;
    @Autowired
    private UserDao userDao;
    @PersistenceContext
    private EntityManager em;


    public List<UserRepairInfo> findAll() {


        return null;
    }

    public Page<UserRepairInfo> find(int i, int i1) {
        return null;
    }

    public Page<UserRepairInfo> find(int i) {
        return null;
    }

    public UserRepairInfo getById(long l) {
        return userRepairInfoDao.findOne(l);
    }

    public UserRepairInfo deleteById(long l) {
        return null;
    }

    @Transactional
    public UserRepairInfo create(UserRepairInfo userRepairInfo) {
        return userRepairInfoDao.saveAndFlush(userRepairInfo);
    }

    @Transactional
    public UserRepairInfo update(UserRepairInfo userRepairInfo) {
        return userRepairInfoDao.save(userRepairInfo);
    }

    public void deleteAll(long[] longs) {

    }

    public List<UserRepairInfo> findByIdIn(List<Long> ids) {
        return userRepairInfoDao.findByIdIn(ids);
    }

    public UserRepairInfo findByUser_Id(Long userId) {
        return userRepairInfoDao.findByUser_Id(userId);
    }

    public List<Map<String, String>> findByOrderBySort(Double lng, Double lat) {
        List<Map<String,Object>> userList=userDao.findTest(lng,lat);
        List<User> users=new ArrayList<User>();
        for (Map map:userList){
           users.add((User) map.get("0"));
        }
        List<UserRepairInfo> userRepairInfoList=userRepairInfoDao.findByUserIn(users);
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (UserRepairInfo userRepairInfo : userRepairInfoList) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", userRepairInfo.getId().toString());
            //服务名称
            int sum = 0;
            StringBuffer serviceAreaName = new StringBuffer();
            for (ServiceArea serviceArea : userRepairInfo.getServiceArea()) {
                if (sum == 0) serviceAreaName.append(serviceArea.getName());
                else {
                    serviceAreaName.append(serviceArea.getName()).append(",");
                }
                sum++;
            }
            UserInfo userInfo=userRepairInfo.getUser().getUserInfo();
            UserLoginInfo userLoginInfo=userRepairInfo.getUser().getUserLoginInfo();
            //距离
            String dist = DistUtils.DubToStr(lng, lat,userLoginInfo.getLastLng(), userLoginInfo.getLastLat());
            map.put("dist", dist);
            map.put("nickName",userInfo.getNickName());
            map.put("star", userInfo.getHonor().toString());
            map.put("photo",userInfo.getPhotoThu());
            map.put("jobTitle", userRepairInfo.getJobTitle());
            map.put("serviceName", serviceAreaName.toString());
            map.put("profile", userRepairInfo.getProfile());
            mapList.add(map);
        }
        return mapList;
    }

    public Map<String, Object> findRepairPage(Double lng, Double lat, Long serviceId, String jobTitle, Integer star, PageParam pageParam) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DISTINCT i.*  FROM  t_user_repair_info i LEFT JOIN t_user u ON i.user_id=u.id "
                + "LEFT JOIN t_user_info f ON u.user_info_id= f.id LEFT JOIN t_user_repair_service_area s  ON i.id=s.user_repair_info_id WHERE 1=1 ");
        if (serviceId != null) sql.append("s.service_area_id = " + serviceId.toString());
        if (!StringUtils.isEmpty(jobTitle)) sql.append("and i.job_title LIKE '%" + jobTitle + "%'");
        if (star != null) sql.append("AND f.honor=" + star);
        Query query = em.createNativeQuery(sql.toString(), UserRepairInfo.class);
        int total = query.getResultList().size();
        query.setFirstResult(pageParam.getStart());
        query.setMaxResults(pageParam.getLength());
        Page<UserRepairInfo> page = new PageImpl<UserRepairInfo>(query.getResultList(), new PageRequest(pageParam.getPageNum() - 1, pageParam.getLength()), total);
        List<RepairListModule> repairListModuleList = new ArrayList<RepairListModule>();
        List<Long> ids = new ArrayList<Long>();
        for (UserRepairInfo userRepairInfo : page.getContent()) {
            User user = userRepairInfo.getUser();
            UserLoginInfo userLoginInfo = userRepairInfo.getUser().getUserLoginInfo();
            ids.add(user.getId());
            RepairListModule repairListModule = new RepairListModule(user.getId(), user.getUserInfo().getHonor()
                    , userRepairInfo.is_busy(), userRepairInfo.getJobTitle(), userRepairInfo.getProfile(),
                    user.getUserInfo().getNickName(), user.getUserInfo().getPhoto(), userRepairInfo.getServiceName(), DistUtils.DubToStr(lng, lat, userLoginInfo.getLastLng(), userLoginInfo.getLastLat()));
            repairListModuleList.add(repairListModule);
        }
        return DataTableFactory.fittingPojo(page, repairListModuleList);
    }

    /**
     * 工程师详情
     *
     * @param repairId 工程师ID
     * @return 结果对象
     */
    public Result detail(Long repairId) {
        UserRepairInfo userRepairInfo = userRepairInfoDao.findOne(repairId);
        if (StringUtils.isEmpty(userRepairInfo)){
            return new Result(false).msg("工程师不存在");
        }
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        User user = userRepairInfo.getUser();
        RepairModule repairModule = new RepairModule();
        BeanUtils.copyProperties(userRepairInfo, repairModule);//工程师详情数据
        BeanUtils.copyProperties(userRepairInfo.getUser().getUserInfo(), repairModule);//用户详情数据
        repairModule.setFans(fansService.isFans(userSessionModul.getId(), user.getId()));//是否关注
        Comments comments = commentsService.findByRepairId(user);//找到最新评论
        UserInfo userInfo = user.getUserInfo();
        repairModule.setId(user.getId());
        if (comments != null) {
            comments.setPhotoThu(userInfo.getPhotoThu());
            comments.setNickName(userInfo.getNickName());
            repairModule.setComments((Comments) JsonUtil.json2Obj(JsonUtil.obj2Json(comments, "order"), Comments.class));//显示的评论
            repairModule.setCommentsNum(commentsService.findCountByRepair(user));//评论总数
        }
        repairModule.setOrderSum(orderService.countByRepair_Id(userRepairInfo.getUser().getId()));
        repairModule.setPhoto(userRepairInfo.getUser().getUserInfo().getPhoto());//头像
        repairModule.setLoginName(userRepairInfo.getUser().getLoginName());//手机号
        //总接单数
        String status;//状态 1 离线 2在线 3忙碌
        if (userRepairInfo.getUser().getUserLoginInfo().isLogin()) {
            if (userRepairInfo.is_busy()) {
                status = "3";
            } else {
                status = "2";
            }
        } else {
            status = "1";
        }
        repairModule.setStatus(status);
        List<String> urlList = new ArrayList<String>();
        for (QualificationImage qualificationImage : userRepairInfo.getQualificationImages()) {
            urlList.add(qualificationImage.getUrl());
        }
        repairModule.setQualificationImageList(urlList);
        return new Result(true).data(repairModule);
    }

    /**
     * 工程师认证
     * @param request
     * @param userId
     * @param userRepairInfo
     * @return
     */
    @Transactional
    public Result authentication(HttpServletRequest request, Long userId, UserRepairInfo userRepairInfo) {
        UserRepairInfo userRepairInfoUpdate = findByUser_Id(userId);
        //服务领域
        String[] ids = userRepairInfo.getServiceId().split(",");
        List<ServiceArea> serviceAreaList=serviceAreaService.findByIdIn(ids);
        userRepairInfoUpdate.setServiceArea(new HashSet(serviceAreaList));
        //审核状态：审核中
        userRepairInfoUpdate.setRealName(userRepairInfo.getRealName());//真实姓名
        userRepairInfoUpdate.setJobTitle(userRepairInfo.getJobTitle());//工年限
        userRepairInfoUpdate.setProfile(userRepairInfo.getProfile());//签名
        userRepairInfoUpdate.setIdentityCode(userRepairInfo.getIdentityCode());//身份证号码
        userRepairInfoUpdate.setCreateTime(new Date());//认证时间
        //工程师认证详情表
        update(userRepairInfoUpdate);
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> photos = multipartRequest.getFiles("photos");
            //上传图片
            List<Map<String, Object>> imagesList = ImageUtilsService.getImages(request, photos, false);
            for (Map<String, Object> map : imagesList) {
                QualificationImage qualificationImage = new QualificationImage(map.get("imgURL").toString(), new Date(), userRepairInfoUpdate);
                qualificationImageService.create(qualificationImage);
            }
        }
        return new Result(true).msg("提交成功，等待审核");
    }

    public String mineAuthentication() {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        if (userSessionModul.getType().equals("0")) {
            return JsonUtil.obj2Json(new Result(false).msg("数据有误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
        }
        UserRepairInfo userRepairInfo = userRepairInfoDao.findByUser_Id(userSessionModul.getId());

        return  JsonUtil.obj2Json(new Result(true).data(ApiBeanUtils.getAuthenModel(userRepairInfo)));
    }


}
