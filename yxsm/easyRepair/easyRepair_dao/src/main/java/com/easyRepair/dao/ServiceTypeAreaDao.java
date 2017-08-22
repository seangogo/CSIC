package com.easyRepair.dao;

import com.easyRepair.tabEntity.ServiceTypeArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/11/22. */
@SuppressWarnings("JpaQlInspection")
public interface ServiceTypeAreaDao extends JpaRepository<ServiceTypeArea, Long>, JpaSpecificationExecutor<ServiceTypeArea> {

    ServiceTypeArea findByServiceAreaIdAndServiceTypeId(Long serviceAreaId, Long serviceTypeId);

    @Query("select s from ServiceTypeArea s where s.serviceTypeId in (?1)")
    List<ServiceTypeArea> findByServiceTypeIds(List<Long> longs);

    @Modifying
    @Query("delete from ServiceTypeArea s where s.serviceTypeId in (?1)")
    void deleteByServiceTypeIds(List<Long> longs);


    @Modifying
    @Query("delete from ServiceTypeArea s where s.id in (?1)")
    void deleteByIds(List<Long> longs1);

    @Query("select count(ta) from ServiceTypeArea ta where ta.serviceTypeId=?1 and ta.serviceAreaId=?2")
    int findCountByServiceTypeIdAndServiceAreaId(Long serviceTypeId, long l);

    //select  * from  courseware c left join user_cour uc on  c.cid = uc.cid left join `user` u  on uc.uid = u.uid where u.uid=1;

    /*@Query("select t,a from ServiceType t,left outer join ServiceTypeArea ta on t.id=" +
            "(select ta.serviceTypeId from ServiceTypeArea ta where ta.serviceAreaId=(select a.id from a))")
    List<Object[]> findAllSreviceName();*/
}
