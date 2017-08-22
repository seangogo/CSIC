package com.easyRepair.dao;

import com.easyRepair.tabEntity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/11/24. */
@SuppressWarnings("JpaQlInspection")
public interface OrderDao extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    /*获取订单详情(用户)*/
    Order findByIdAndUser_Id(Long id, Long userId);
    
    /*获取订单详情(工程师)*/
    Order findByIdAndRepair_Id(Long id, Long userId);
    
    /*我的订单(用户)*/
    @Query("select a from Order a where a.user.id=?1")
    Page<Order> findByUser_Id(Long userId, Pageable pageable);
    
    /*我的订单(工程师)*/
    @Query("select a from Order a where a.repair.id=?1")
    Page<Order> findByRepair_Id(Long repairId, Pageable pageable);
    
    List<Order> findByIdIn(List<Long> ids);
    
    //通过接单人ID 查询最近一条记录的订单
    @Query("select a from Order a where a.repair.id=?1")
    Order findByRepair_IdOrderByCreateTimeDesc();
    
    //查询该工程师所有已经评论的数量
    @Query("select count (a) from Order a where a.repair.id=?1 and a.orderInfo.isComment=1")
    int findAllCommentsCount(Long repairId);
    //验证订单是否能够被删除
    @Query("select a from Order as a where a.id=?1 and (a.user.id=?2 or a.repair.id=?2)")
    Order findByIdAndUser_IdOrRepairId(Long orderId, Long userId);
    //通过订单号查找
    Order findByOrderNum(String out_trade_no);
    //判断工程师是否正在接单
    @Query("select order from Order as order where order.status=3 and order.repair.id=?1")
    List<Order> findIsWorkByUserId(Long id);

    //from Goods where id = ? and (name = ? or name is null) and (value = ? or value is null) and (mark = ? or mark is null)
    //通过当前登录用户和订单ID 获取订单
    @Query("select order from Order as order where id=?1 and (user.id=?2 or repair.id=?2)")
    Order findOneChechUser(Long orderId,Long userId);

    //验证用户是否用户查看工程师定位的权限
    @Query("select order from Order as order where status=3 and repair.id=?1 and user.id=?2 ")
    List<Order> checkRepairLngAndLat(Long repairId,Long userId);

    //查询工程师所有订单
   // @Query("select count(order) from Order as order where order.repair.id=?1")
      Long countByRepair_Id(Long repairId);

    @Query("from Order o where o.serviceType.id in (?1)")
    List<Order> findByServicetypeIds(List<Long> longs);
}
