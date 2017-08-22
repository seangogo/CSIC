package com.dh.repository;

import com.dh.entity.RepairOrder;
import com.dh.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Map;

@SuppressWarnings("JpaQlInspection")
public interface RepairOrderDao extends PagingAndSortingRepository<RepairOrder, Long>, JpaSpecificationExecutor<RepairOrder> {
	
	@Query("select o from RepairOrder o where (TO_DAYS(NOW()) - TO_DAYS(o.completionTime) >= 7 ) and o.orderState = 2 ")
	List<RepairOrder> getTimeoutOrder();

	@Query("select new User(u.id,u.userName) from User u  where u.id in(?1) ")
	List<User> getRepairByRepairIds(List<Long> list);


    @Query("select new User(u.id,u.loginName,u.userName,u.userIco,u.lastLocation,u.repairType, u.isLogin,u.lastLocationTime, "
            + " (select count(t1) from RepairOrder  t1 where t1.repair.id=u.id) as totalCount, "
            + " (select count(t2) from RepairOrder t2 where t2.repair.id=u.id and t2.orderState=1) as currentCount ) "
            + " from User u where (u.userType = 1 or u.userType =2) and u.id <> ?1  ")
	List<User> getRepairCCList(Long repairId);

	@Query("select new User(u.id,u.loginName,u.userName,u.userIco,u.lastLocation,u.repairType, u.isLogin,u.lastLocationTime, "
			+ " (select count(t1) from RepairOrder  t1 where t1.repair.id=u.id) as totalCount, "
			+ " (select count(t2) from RepairOrder t2 where t2.repair.id=u.id and t2.orderState=1) as currentCount ) "
			+ " from User u where (u.userType = 1 or u.userType =2)")
	List<User> getOrderAllCCList();

	@Query(value="SELECT p.* FROM t_repair_order p  LEFT JOIN t_word_book w ON w.word_index=p.order_state "
			+"LEFT JOIN t_user u1 on u1.id=p.user_id LEFT JOIN t_user u2 ON p.repair_id=u2.id "
			+"WHERE w.type=ORDER_STATE ?3  ORDER BY w.order_by DESC,p.order_num DESC limit ?1 ,?2",nativeQuery = true)
	List<RepairOrder> getOrderList(int start,int end,String select);

	@Query(value="SELECT p.* FROM t_repair_order p  LEFT JOIN t_word_book w ON w.word_index=p.order_state "
			+"LEFT JOIN t_user u1 on u1.id=p.user_id LEFT JOIN t_user u2 ON p.repair_id=u2.id "
			+"WHERE w.type=ORDER_STATE  ORDER BY w.order_by DESC,p.order_num DESC limit ?1 ,?2",nativeQuery = true)
	List<RepairOrder> getOrderListNo(int start,int end);

	@Query(value = "SELECT DISTINCT o.*  FROM t_repair_order o RIGHT JOIN t_order_cost co ON co.order_id = o.id RIGHT JOIN t_order_engineer e ON e.order_id =o.id WHERE o.order_state in (2,4) and o.repair_type=1 AND o.id not in("
			+"SELECT c.order_id FROM t_repair_order_cost c)" ,nativeQuery = true)
	List<RepairOrder> findAllByState();

	@Query(value = "SELECT e.user_id FROM t_order_engineer e LEFT JOIN  t_repair_order o  ON e.order_id = o.id  WHERE o.id=?1" ,nativeQuery = true)
	List<String> findIdsByRepair(Long orderId);

	@Modifying
	@Query("UPDATE RepairOrder o set o.repair=?2,o.orderState=1,o.isRead= 0 where o.id=?1")
	int findIdsByRepair(Long orderId,User repair);

	@Query("select distinct user.deviceId from User user where (user.userType = 1 or user.userType = 2 ) and user.deviceId <> '' and LENGTH(user.deviceId) > 10 ")
	List<String> getAllRepairDeviceId();

	int countByUserAndOrderStateLessThan(User user, int i);

	int countByRepairAndOrderStateLessThan(User user, int i);

	int countByOrderStateLessThan(int i);

	@Query(value = "SELECT u.user_name,\n" +
			"COUNT(CASE WHEN date_format(r.create_time, '%Y-%m') = date_format(DATE_SUB(DATE_ADD(NOW(), INTERVAL - 0 MONTH),\n" +
			"INTERVAL 1 MONTH),'%Y-%m')THEN 1 ELSE NULL END) AS orderCount,\n" +
			"COUNT(CASE WHEN date_format(r.create_time, '%Y-%m') = date_format(DATE_SUB(DATE_ADD(NOW(), INTERVAL - 1 MONTH),\n" +
			"INTERVAL 1 MONTH),'%Y-%m') THEN 1 ELSE NULL END) AS orderCount1,\n" +
			"COUNT(CASE WHEN date_format(r.create_time, '%Y-%m') = date_format(DATE_SUB(DATE_ADD(NOW(), INTERVAL - 2 MONTH),\n" +
			"INTERVAL 1 MONTH),'%Y-%m') THEN 1 ELSE NULL END ) AS orderCount2,\n" +
			"COUNT(CASE WHEN date_format(r.create_time, '%Y-%m') = date_format(DATE_SUB(DATE_ADD(NOW(), INTERVAL - 3 MONTH),\n" +
			"INTERVAL 1 MONTH),'%Y-%m') THEN 1 ELSE NULL END ) AS orderCount3\n" +
			"FROM t_repair_order r LEFT JOIN t_user u ON u.id = r.repair_id WHERE u.user_name IS NOT NULL\n" +
			"AND r.order_state > 3 GROUP BY r.repair_id ORDER BY u.id",nativeQuery = true)
	List<Object[]> findAllRepairCountBy4Month();

	@Query(value = "select DATE_FORMAT(create_time,'%Y%m') months,count(CASE WHEN r.repair_type=0 THEN 1 ELSE NULL END) count from t_repair_order r \n" +
			"group by months ORDER BY months DESC",nativeQuery = true)
	List<Object[]> findWeekByType();
}
