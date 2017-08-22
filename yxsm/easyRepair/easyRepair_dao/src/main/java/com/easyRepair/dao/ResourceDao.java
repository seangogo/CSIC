package com.easyRepair.dao;

import com.easyRepair.tabEntity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/12/22. */
@SuppressWarnings("JpaQlInspection")
public interface ResourceDao extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {
    @Query("select resource from Resource resource order by resource.level, resource.sort desc")
    List<Resource> findAllResources();

    @Query("select resource from Resource resource where resource.resourceType=0 order by resource.level, resource.sort desc")
    List<Resource> findAllMenu();

    @Query("select resource from Resource resource where resource.type = ?1 order by resource.level, resource.sort desc , resource.resourceType ")
    Page<Resource> findResourcesByType(String type,Pageable pageable);

    @Query("select r.type from Resource r where r.resUrl like %?1% ")
    Integer findTabeType(String url);

    List<Resource>  findByIdIn(List<Long> idList);
    //批量删除
    @Modifying
    @Query("delete from Resource resource where resource.id in (?1)")
    int deleteBatch(List<Long> idList);

    int countByParentId(Long parentId);

    int countByType(String type);
}
