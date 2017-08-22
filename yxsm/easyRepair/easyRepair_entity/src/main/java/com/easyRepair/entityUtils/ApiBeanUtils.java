package com.easyRepair.entityUtils;

import com.easyRepair.pojo.*;
import com.easyRepair.tabEntity.*;
import common.utils.ConfigUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sean on 2017/3/2.
 */
public class ApiBeanUtils {

    /**
     * session中存入的用户
     * @param user
     * @param userSessionModul flushUser
     * @return
     */
    public static UserSessionModul getUserModel(User user, UserSessionModul userSessionModul) {
        BeanUtils.copyProperties(user.getUserLoginInfo(), userSessionModul);
        BeanUtils.copyProperties(user.getUserInfo(), userSessionModul); //将属性复制到userSessionModul
        BeanUtils.copyProperties(user, userSessionModul);
        return userSessionModul;
    }


    /**
     * 认证pojo
     * @param userRepairInfo
     * @return getAuthenModel
     */
    public static AuthenticationModule getAuthenModel(UserRepairInfo userRepairInfo) {
        AuthenticationModule authenticationModule = new AuthenticationModule();
        BeanUtils.copyProperties(userRepairInfo, authenticationModule);
        authenticationModule.setServiceAreaSet(userRepairInfo.getServiceArea());
        List<String> urlList = new ArrayList<String>();
        for(QualificationImage qualificationImage : userRepairInfo.getQualificationImages()) {
            urlList.add(ConfigUtil.getBaseUrl(qualificationImage.getUrl()));
        }
        authenticationModule.setUrls(urlList);
        return authenticationModule;
    }
    /**
     * 评论实体转化为pojo
     * @param comments
     * @return
     */
    public static CommentsMudule getCommentsModel(Comments comments){
        CommentsMudule commentsMudule=new CommentsMudule();
        BeanUtils.copyProperties(comments,commentsMudule);
        if(comments.getCommentsImages()!=null){
            for(CommentsImage commentsImage:comments.getCommentsImages()){
                commentsMudule.getCommentsImages().add(ConfigUtil.getBaseUrl(commentsImage.getUrl()));
            }
        }
        return commentsMudule;
    }

    /**
     * 订单对象转化为订单列表的pojo
     * @param order
     * @return getMuduleByOrder
     */
    public static OrderListMudule getOrderListModel(Order order) {
        String orderImgas="";
        if(order.getOrderImages()!=null&&order.getOrderImages().size()>0){
            ok:for(OrderImage orderImage:order.getOrderImages()){
                orderImgas= ConfigUtil.getBaseUrl(orderImage.getUrl());
                break ok;
            }
        }else {
            orderImgas=ConfigUtil.getBaseUrl(ConfigUtil.getString("defaul.computer"));
        }
        String name="";
        if(order.getRepair()!=null){
            name=order.getRepair().getUserInfo().getNickName();
        }
        return new OrderListMudule(order.getId(),name,order.getStatus(),
                order.getOrderNum(), order.getServiceType().getServiceName(), order.getServiceType().getPrice(),
                order.getPrice(), order.getRemark(),orderImgas,order.getOrderInfo().getCreateTime(),order.getAppointmentTime());
    }

    /**
     * 订单转为订单详情pojo
     * @param order
     * @param user getOrderModule
     * @return
     */
    public static OrderModule getOrderdetailModel(Order order, User user) {
        OrderModule orderModule = new OrderModule();
        BeanUtils.copyProperties(order.getOrderInfo(), orderModule);
        BeanUtils.copyProperties(order, orderModule);
        orderModule.setNickName(user!=null?user.getUserInfo().getNickName():"");
        orderModule.setPhotoThu(user!=null?user.getUserInfo().getPhotoThu():"");
        orderModule.setServiceName(order.getServiceType().getServiceName());
        orderModule.setServicePrice(order.getServiceType().getPrice());
        orderModule.setExpenses(order.getServiceType().getExpenses());
        orderModule.setCouponName(order.getCoupon() == null ? "" : order.getCoupon().getName());
        if(order.getRepair()!=null){
            orderModule.setRepairPhone(order.getRepair().getLoginName());
            orderModule.setRepairId(order.getRepair().getId());
        }
        if(order.getStatus()>2){
            orderModule.setPayPrice(order.getRealpay());
        }
        List<String> imagesUrlList = new ArrayList<String>();

        for(OrderImage orderImage : order.getOrderImages()) {
            imagesUrlList.add(ConfigUtil.getBaseUrl(orderImage.getUrl()));
        }
        orderModule.setImagesUrl(imagesUrlList);
        return orderModule;
    }
}
