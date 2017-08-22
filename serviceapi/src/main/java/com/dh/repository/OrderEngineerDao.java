package com.dh.repository;

import com.dh.entity.OrderEngineer;
import com.dh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderEngineerDao extends JpaRepository<OrderEngineer, Integer>, JpaSpecificationExecutor<OrderEngineer> {

    @Query(value = "select u.user_name from t_order_engineer oe,t_user u where oe.user_id = u.id and oe.order_id = ?1 order by oe.id asc ", nativeQuery = true)
    List<String> getOrderEngineerByOId(int orderId);


    @Modifying
    @Query("delete from OrderEngineer oe where oe.repairOrder.id = ?1")
    int deleteByOrderId(int orderId);

    @Modifying
    @Query("delete from OrderEngineer oe where oe.repairOrder.id= ?1 and oe.user.id = ?2")
    int deleteByorderIdAnduserId(int orderId, int userId);

    @Query("select o from OrderEngineer o where o.repairOrder.id=?1")
    List<OrderEngineer> findByOrderId(Integer orderId);


  /*  select u.id,u.login_name,u.user_name,u.user_ico,u.last_location,u.last_location_time,"
            +"u.repair_type,u.is_login,(select count(t1.id) from t_repair_order t1 where t1.repair_id=u.id) as total_order_count,"
            +"(select count(t2.id) from t_repair_order t2 where t2.repair_id=u.id and t2.order_state=1) "
            +"as current_order_count from t_user u where ( u.user_type = 1 or u.user_type =2 )and u.id"
            +" in (SELECT e1.user_id FROM t_order_engineer e1 WHERE e1.order_id="+order.getId()+")*/
    @Query("SELECT oe.user,(select count (t1.id) from RepairOrder t1 where t1.repair.id=oe.user.id) as totalOrderCount ,(select count(t2.id) from RepairOrder t2 where t2.repair.id=oe.user.id and t2.orderState=1) as currentOrderCount from OrderEngineer oe where oe.repairOrder.id=?1")
    List<Object[]> findEngineer(Integer id);
}
