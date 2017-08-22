package com.dh.repository;

import com.dh.entity.OrderCost;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@SuppressWarnings("JpaQlInspection")
public interface OrderCostDao extends PagingAndSortingRepository<OrderCost, Long>, JpaSpecificationExecutor<OrderCost> {

	List<OrderCost> findByOrderIdOrderByIdAsc(Long id);

	/*通过订单ID清除所有该订单下的费用数据*/
	@Modifying
	@Query("DELETE FROM OrderCost oc where oc.orderId=?1")
    int delOrderCostByOId(Long orderId);
}
