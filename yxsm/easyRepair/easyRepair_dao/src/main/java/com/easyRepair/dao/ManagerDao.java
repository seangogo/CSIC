package com.easyRepair.dao;

import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.Role;
import com.easyRepair.tabEntity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/11/14. */
@SuppressWarnings("JpaQlInspection")
public interface ManagerDao extends JpaRepository<Manager, Long>, JpaSpecificationExecutor<Manager> {

    @Query("SELECT manager FROM Manager as manager join fetch manager.role where manager.mobile=?1 and manager.isCut=0")
    Manager findByMobile(String mobile);

    List<Manager> findByRole_Id(Long roleId);

    @Query("SELECT m FROM Manager m where m.isCut=0 ")
    Page<Manager> findAll(Pageable pageable);

    @Modifying
    @Query("update  Manager m set m.isCut=1 where m.id in(?1)")
    void deleteByIds(List<Long> longs);


    @Query("select m from Manager m where m.id in (?1)")
    List<Manager> findByIds(List<Long> longs);

    /*@Query("SELECT manager FROM Manager as manager  where manager.nickName=?1")
    List<Manager> findByNickName(String nickName);*/

    @Query("select m.role from Manager m where m.id=?1")
    Role getRoleById(Long id);

    @Query("select m.role from Manager m where m.id in (?1)")
    List<Role> findRolesByIds(List<Long> longs);

    @Query("select count(m) from Manager m where m.nickName=?1 and m.isCut=0 ")
    Integer findCountByNickName(String nickName);

    //Page<Manager> findByIsCutTrue(PageRequest pageRequest);
}
