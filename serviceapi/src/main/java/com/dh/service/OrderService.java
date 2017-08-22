package com.dh.service;

import com.dh.entity.RepairOrder;
import com.dh.entity.RepairOrderLog;
import com.dh.entity.User;
import com.dh.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/12.
 */
public interface OrderService extends ICommonService<RepairOrder> {
  /*   Page<RepairOrder> getPageByLike(Integer userId, String repairLike, int pageNum, int pageSize);*/

    Page<RepairOrder> orderPage(int pageNumber, int pageSize, String orderState, User user, Integer repairId, String repairLike);

    RepairOrder saveOrder(String orderCost, Integer repairType, String contactUser, String contactPhone, String contactAddress,
                          String orderDesc, String orderImgs, String orderImgsThumb, String contactLocation, Integer id, Integer qty);

    List<String> getAllRepairDeviceId();

    RepairOrder getShowOrderDetail(Integer orderId, Integer id);

    List<RepairOrderLog> getOrderStateByOrderId(Integer orderId);

    void updateAppointmentTime(RepairOrder order, String appointmentTimeStr, Integer id);

    List<User> findAllRepairList(Boolean isContentRepair,RepairOrder order);

    List<User> findRepairCCList(RepairOrder order);

    RepairOrder updateOrderState(Integer orderState, RepairOrder order, User repair, String orderExplain);

    RepairOrder assignRepairCC(RepairOrder order, String repairIds, Integer userId);

    RepairOrder assignRepair(RepairOrder order, User repair, Integer id);

    User findRepairDetail(Integer repairId);

    RepairOrderLog saveReason(Integer type, Integer contentType, String msgContent, RepairOrder order, User user);

    List<String> findManagerDeviceId();

    RepairOrderLog saveComment(RepairOrder order, String commentStar, String commentContent, Integer id);

    int updateIsRead(Integer orderId);

}
