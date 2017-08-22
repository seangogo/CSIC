package com.easyRepair.dao;

import com.easyRepair.tabEntity.TradeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 Created by sean on 2016/12/23. */
@SuppressWarnings("JpaQlInspection")
public interface TradeInfoDao extends JpaRepository<TradeInfo, Long>, JpaSpecificationExecutor<TradeInfo> {

    @Query("select tradeInfo from TradeInfo tradeInfo where tradeInfo.user.id=?1 and  tradeInfo.type=1")
    Page<TradeInfo> findTrades(Long id, Pageable pageable);

    @Query("select tradeInfo from TradeInfo tradeInfo where tradeInfo.user.id=?1")
    Page<TradeInfo> findMineMoneyPage(Long id, Pageable pageable);


}
