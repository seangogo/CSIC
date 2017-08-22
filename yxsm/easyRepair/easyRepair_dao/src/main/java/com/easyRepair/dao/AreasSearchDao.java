package com.easyRepair.dao;

import com.easyRepair.tabEntity.AreasSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/11/25. */
@SuppressWarnings("JpaQlInspection")
public interface AreasSearchDao extends JpaRepository<AreasSearch, Long>, JpaSpecificationExecutor<AreasSearch> {
    /*匹配工程师*/
    @Query(value = "SELECT z1.rid AS repairInfoId,a1.object_id," +
            "a1.id,a1.lng,a1.lat ," +
            "(POWER(MOD(ABS(?1 - a1.lng),360),2) + POWER(ABS(?2 - a1.lat),2)) AS distance FROM t_areas_search a1 " +
            "LEFT JOIN (SELECT DISTINCT a1.user_repair_info_id AS rid " +
            "FROM t_user_repair_service_area a1 WHERE a1.service_area_id in" +
            "(SELECT t1.service_area_id FROM t_service_type_area t1 LEFT JOIN " +
            "t_service_area t2 ON t1.service_area_id=t2.id WHERE t1.service_type_id=?3)" +
            ") z1 ON a1.object_id=z1.rid WHERE a1.order_or_repair=0 order by if(z1.rid=\"\",0,1)," +
            "repairInfoId DESC,distance LIMIT ?4,?5", nativeQuery = true)
    List<Object[]> findByLngAndLatAndServiceTypeId(Double lng, Double lat, Long serviceTypeId, Integer start, Integer length);
    
    /*推荐订单*/
    @Query(value = "SELECT z1.oid AS orderId,a1.object_id,a1.id,a1.lng,a1.lat," +
            " (POWER(MOD(ABS(?1  - a1.lng),360),2) + POWER(ABS(?2 - a1.lat),2)) AS distance FROM t_areas_search a1" +
            "  LEFT JOIN (SELECT DISTINCT a1.id AS oid " +
            "  FROM t_order a1 WHERE a1.service_type_id in" +
            "  (SELECT t1.service_type_id FROM t_service_type_area t1 LEFT JOIN" +
            "   t_service_type t2 ON t1.service_type_id=t2.id WHERE t1.service_area_id in (?3) AND a1.`status`=1" +
            "))z1 ON a1.object_id=z1.oid WHERE a1.order_or_repair=0 order by if(z1.oid=\"\",0,1)," +
            "orderId DESC,distance LIMIT ?4,?5", nativeQuery = true)
    List<Object[]> findByLngAndLatAndServiceAreaId(Double lng, Double lat, String serviceAreaId, Integer start, Integer length);
    
    /*通过对象ID,对象类型查询单挑记录*/
    @Query("SELECT a from AreasSearch a where a.objectId=?1 and a.orderOrRepair=?2")
    AreasSearch findByObjectId(Long objectId, boolean orderOrRepair);
    
    @Query(value = "SELECT count(z1.oid) FROM t_areas_search a1 LEFT JOIN(SELECT DISTINCT a1.id AS oid FROM t_order a1 " +
            "WHERE a1.service_type_id IN ( SELECT t1.service_type_id FROM t_service_type_area t1 LEFT JOIN t_service_type t2 ON t1.service_type_id = t2.id " +
            "WHERE t1.service_area_id IN (?1) AND a1.`status` = 1 )) z1 ON a1.object_id = z1.oid " +
            "WHERE a1.order_or_repair = 0 ORDER BY IF (z1.oid = \"\", 0, 1)",nativeQuery = true)
    String findPageCount(String serviceAreaIds);
    
    @Modifying
    @Query("delete from AreasSearch as a where a.objectId=?1 and a.orderOrRepair=?2")
    int deleteByObjectIdAndOr(Long objectId, boolean orderOrRepair);


}
