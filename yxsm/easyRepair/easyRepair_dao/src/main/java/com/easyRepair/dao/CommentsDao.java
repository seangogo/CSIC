package com.easyRepair.dao;

import com.easyRepair.tabEntity.Comments;
import com.easyRepair.tabEntity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/12/26. */
@SuppressWarnings("JpaQlInspection")
public interface CommentsDao extends JpaRepository<Comments, Long>, JpaSpecificationExecutor<Comments> {
  //  @Query("select a from Comments a where a.order.repair=?1 order by a.createTime desc")
    List<Comments> findFirst1ByOrder_RepairOrderByCreateTimeDesc(User user);

    Comments findByOrder_Id(Long id);
    
    @Query("select a from Comments a where a.order.repair.id=?1 order by a.createTime desc")
    Page<Comments> findPageByRepairId(Long id, Pageable pageable);

    int countByOrder_Repair(User user);

    @Modifying
    @Query("delete from Comments c where c.id in (?1)")
    void deleteAllByIds(List<Long> longs1);
}
