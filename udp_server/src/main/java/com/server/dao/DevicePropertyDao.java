package com.server.dao;


import com.server.domain.entity.DeviceProperty;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"JpaQlInspection", "SpringCacheAnnotationsOnInterfaceInspection"})
@CacheConfig(cacheNames = "deviceProperty")
public interface DevicePropertyDao extends JpaRepository<DeviceProperty, Long>, JpaSpecificationExecutor<DeviceProperty> {
    //根据udp报文下标查询对应下标的所有属性元素,unless
    @Cacheable(value = "udpSort", key = "#p0", condition = " #p0.intValue()<25")
    @Query(value = "select new map(d.deviceKey as dk ,d.propertyKey as pk,d.readSort as sort,d.clientId as clientId ) from DeviceProperty as d " +
            "where d.udpServerIndex=?1 and d.clientId is not null order by d.readSort")
    List<Map<String, Object>> findByUdpServerIndex(int udpServerIndex);

    //根据报文下标查询所有对应报文的客户端下标
    @Cacheable(value = "clientIndexList", key = "#p0", condition = " #p0.intValue()<25")
    @Query("select distinct d.clientId from DeviceProperty as d " +
            "where d.udpServerIndex=?1 and d.clientId is not null")
    List<String> findClientsByUdpIndex(int index);

    //通过页面查询所需报文的下标
    @Query("select distinct d.udpServerIndex from DeviceProperty as d " +
            "where d.clientId LIKE ?1 and d.udpServerIndex is not null")
    List<Integer> selectUdpServerIndex(String nameSpace);

}
