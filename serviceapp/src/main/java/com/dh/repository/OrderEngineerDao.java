package com.dh.repository;

import com.dh.entity.OrderEngineer;
import com.dh.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderEngineerDao extends PagingAndSortingRepository<OrderEngineer, Long>, JpaSpecificationExecutor<OrderEngineer> {

    @Query(value = "select u.user_name from t_order_engineer oe,t_user u where oe.user_id = u.id and oe.order_id = ?1 order by oe.id asc ",nativeQuery = true)
    List<String> getOrderEngineerByOId(Long orderId);

    @Query(value = "SELECT u1.user_name,u1.id FROM t_user u1 WHERE"
            +" u1.id in (select u.id from t_order_engineer oe,t_user u where oe.user_id = u.id and oe.order_id = ?1)"
            +"OR u1.id in( SELECT ro.repair_id FROM t_repair_order ro WHERE ro.id=?1)",nativeQuery = true)
    List<User> findEngineerByOrderId(Long orderId);

    /*不包含工程师*/
    @Query("SELECT o.userId FROM OrderEngineer o WHERE o.orderId= ?1")
    List<Long> findUseIdByOrderId(Long orderId);

    @Modifying
    @Query("delete from OrderEngineer o where o.orderId=?1")
    void deleteAllCCByOderId(Long orderId);
}
