package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/3.
 */
public interface DiscountService extends ICommonService<Discount> {

    Page<Discount> page(Map<String, Object> searchParams, PageRequest pageRequest);
    //根据类型查询系数
    Double findNumByType(Integer type);
}
