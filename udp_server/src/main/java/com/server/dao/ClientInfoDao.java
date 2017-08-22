package com.server.dao;

import com.server.domain.entity.ClientInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

@SuppressWarnings({"JpaQlInspection", "SpringCacheAnnotationsOnInterfaceInspection"})
public interface ClientInfoDao extends JpaRepository<ClientInfo, Long>, JpaSpecificationExecutor<ClientInfo> {
    @Cacheable(value = "findClient", key = "#p0")
    ClientInfo findByClientId(String clientId);

    /**
     * 连接保存
     */
    @Modifying
    @Cacheable(value = "originalMessage", key = "#p0")
    @Query("update ClientInfo as c set c.mostsignbits=?1,c.connected=1" +
            ",c.leastsignbits=?2,c.lastconnecteddate=?3 where c.id=?4")
    void onConnectUpdate(Long mostsignbits, Long leastsignbits, Date lastconnecteddate, Long id);

    /**
     * 断开连接
     *
     * @param clientId 客戶端命名空間
     */
    @Modifying
    @Query("update ClientInfo as c set c.mostsignbits=null,c.connected=0" +
            ",c.leastsignbits=null where c.clientId=?1")
    void onDisconnectUpdate(String clientId);
}
