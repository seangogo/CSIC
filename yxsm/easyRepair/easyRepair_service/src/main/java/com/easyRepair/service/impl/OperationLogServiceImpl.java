package com.easyRepair.service.impl;

import com.easyRepair.dao.OperationLogDao;
import com.easyRepair.service.OperationLogService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.OperationLog;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * Created by sean on 2017/2/15.
 */
@Component
@Transactional(readOnly = true)
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    OperationLogDao operationLogDao;
    @Transactional
    public void save(OperationLog log) {
        operationLogDao.save(log);
    }

    public Page<OperationLog> page(Map<String, Object> searchParams, Pageable pageable) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
      //  filters.put("userName", new SpecificationUtil("creater.nickName", SpecificationUtil.Operator.LIKE,""));
        Specification<OperationLog> spec1 = new Specification<OperationLog>() {
            public Predicate toPredicate(Root<OperationLog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Join<OperationLog, Manager> join = root.join(root.getModel().getSingularAttribute("creater", Manager.class), JoinType.LEFT);
                root.get("creater");
                return query.getRestriction();
            }
        };
        /*query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());*//**/
        Specification<OperationLog> spec = DynamicSpecifications.bySearchFilter(filters.values(), OperationLog.class);
        Page<OperationLog> page= operationLogDao.findAll(Specifications.where(spec).and(spec1),pageable);
        return page;
    }

}
