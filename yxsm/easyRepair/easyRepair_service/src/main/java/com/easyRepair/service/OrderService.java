package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Order;
import com.easyRepair.tabEntity.PayLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/24. */
public interface OrderService extends ICommonService<Order> {
    Order findByIdAndUser_Id(Long id, Long userId);//订单详情(用户)
    Order findByIdAndRepair_Id(Long id, Long userId);//订单详情(工程师)
    Page<Order> findByRepairId(Long repairId, Pageable pageable);//工程师查找我的订单
    Page<Order> findByUserId(Long userId, Pageable pageable);//用户查找我的订单
    List<Order> findByIdIn(List<Long> ids);//通过ID集合批量查找订单集合
    void updateExpiredOrderState();//定时器
    String findOrderNum();//调用存储过程获得订单号
    int findAllCommentsCount(Long repairId);//todo
    boolean confirmRepair(Long orderId, Long repairId);//同意抢单

    Map<String,Object> mineOrderPage(Long id,String type,Integer status, PageRequest pageRequest);
    
    boolean userPay(Long orderId,Double price,String payModel);
    
    Order cancel(Order order,String cancelRemark);
    
    Order payPrice(Order order);
    
    Order findByIdAndUser_IdOrRepairId(Long orderId, Long userId);
    
    Order cutOrder(Order order);
    
    PayLog confirmPay(Order order, boolean b);
    
    PayLog refund(Order order);
    boolean confirmOrderRequest(Long orderId);//确认匹配请求
    boolean updateConfirmOrdersStutas(Long orderId,Long userId);//更改订单状态为已接单
    Order findByOrderNum(String out_trade_no);//通过订单号查找订单
    boolean aliPayNotify(HttpServletRequest request);//支付成功以后回调修改订单状态以及记录交易数据
    Map<String,Object> putOrder(HttpServletRequest request,Order order);
    Object indexOrder();//工程师首页推荐订单
    //判断工程师是否正在接单
    Boolean findIsWorkByUserId(Long id);
    //订单详情验证规则(1 所有人都能看,大于二只能接单人和发单人查看 )
    Boolean getByIdCheckUser(Long orderId,Long userId);
    //判断用户是否用户查看工程师定位权限
    boolean checkRepairLngAndLat(Long repairId,Long userId);
    //查询工程师接单总数(作为接单人参与的订单数)
    Long countByRepair_Id(Long repairId);

    List<Order> findByServiceTypeIds(long[] arrayId);
    //后台分页
    Page<Order> page(Map<String, Object> searchParams, PageRequest pageRequest);
}
