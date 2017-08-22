package com.server.dao;

import com.server.domain.entity.Order;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sean on 2017/6/6.
 */
@SuppressWarnings("JpaQlInspection")
@CacheConfig(cacheNames = "orderEhcache")
public interface OrderDao extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    //根据厂商标识查询所有数字量
    @Cacheable(value = "orderNum", key = "#p0")
    @Query(value = "SELECT group_concat(bin(o.bo +0) ORDER BY o.sort Separator '') as byt FROM orders o WHERE o.developers=?1 and o.sort<=64 GROUP BY o.num", nativeQuery = true)
    List<String> orderNum(String developers);

    //根据厂商标识查询所有模拟量
    @Cacheable(value = "orderReal", key = "#p0")
    @Query(value = "SELECT o.num FROM Order o WHERE o.developers=?1 and o.sort>64")
    List<Float> orderReal(String developers);

    /**
     * 修改对应厂商数字量指令
     *
     * @param msgContent 指令编号
     * @param developers 厂商标识
     */

    @Modifying
    @Query(value = "update orders o set o.bo=NOT o.bo where o.sort=?1 and o.developers=?2", nativeQuery = true)
    void updateNum(Short msgContent, String developers);

    /**
     * 修改对应厂商模拟量指令
     *
     * @param msgContent 指令编号
     * @param developers 厂商标识
     */
    @Modifying
    @Query(value = "update Order o set o.num=?3 where o.sort=?1 and o.developers=?2")
    void updateReal(short msgContent, String developers, float num);
}
