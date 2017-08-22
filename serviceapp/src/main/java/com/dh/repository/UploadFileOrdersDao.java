package com.dh.repository;

import com.dh.entity.UploadFileOrders;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Coolkid on 2016/10/8.
 */
public interface UploadFileOrdersDao extends PagingAndSortingRepository<UploadFileOrders, Long>, JpaSpecificationExecutor<UploadFileOrders> {
    @Query(value = "SELECT doc_path FROM t_uploadfile_order WHERE id=?1",nativeQuery = true)
    String findByUpId(Long id);

    @Modifying
    @Query(value = "delete from t_uploadfile_order where id=?1",nativeQuery = true)
    Integer deleteById(Long id);

    @Modifying
    @Query(value = "UPDATE t_uploadfile_order SET repair_order_id = ?1 WHERE id = ?2",nativeQuery = true)
    Integer updateByOrderd(Long orderId,Long ids);

    @Query(value = "SELECT * from t_uploadfile_order  where repair_order_id=?1",nativeQuery = true)
    List<UploadFileOrders> findFilesByOrderId(Long orderId);
}
