package com.easyRepair.service.impl;

import com.easyRepair.dao.DiscountDao;
import com.easyRepair.service.DiscountService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.Discount;
import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/3.
 */
@Service
@Transactional(readOnly = true)
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private DiscountDao discountDao;

    public List<Discount> findAll() {
        return null;
    }

    public Page<Discount> find(int i, int i1) {
        return null;
    }

    public Page<Discount> find(int i) {
        return null;
    }

    public Discount getById(long id) {
        return discountDao.findOne(id);
    }

    public Discount deleteById(long l) {
        return null;
    }

    public Discount create(Discount discount) {
        return null;
    }

    @Transactional
    public Discount update(Discount discount) {
        return discountDao.saveAndFlush(discount);
    }

    public void deleteAll(long[] longs) {

    }

    public Page<Discount> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        Specification<Discount> spec = DynamicSpecifications.bySearchFilter(filters.values(), Discount.class);
        Page<Discount> page = discountDao.findAll(spec, pageRequest);
        return page;
    }

    public Double findNumByType(Integer type) {
        return  discountDao.findFirstByType(type).getNumOne();
    }
}
