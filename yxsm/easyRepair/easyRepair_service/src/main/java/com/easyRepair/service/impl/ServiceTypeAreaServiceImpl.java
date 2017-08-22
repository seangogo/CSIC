package com.easyRepair.service.impl;

import com.easyRepair.dao.ServiceAreaDao;
import com.easyRepair.dao.ServiceTypeAreaDao;
import com.easyRepair.service.ServiceTypeAreaService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.OperationLog;
import com.easyRepair.tabEntity.ServiceType;
import com.easyRepair.tabEntity.ServiceTypeArea;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by WJY on 2017/2/21.
 */
@Service
@Transactional(readOnly = true)
public class ServiceTypeAreaServiceImpl implements ServiceTypeAreaService {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private ServiceTypeAreaDao serviceTypeAreaDao;

    public ServiceTypeArea findByIds(Long serviceAreaId, Long serviceTypeId) {
        return serviceTypeAreaDao.findByServiceAreaIdAndServiceTypeId(serviceAreaId, serviceTypeId);
    }

    public List<ServiceTypeArea> findByServiceTypeIds(long[] arrayId) {
        List<Long> longs = new ArrayList<Long>();
        for (long l : arrayId) {
            longs.add(l);
        }
        return serviceTypeAreaDao.findByServiceTypeIds(longs);
    }

    @Transactional
    public void deleteByServiceTypeIds(long[] arrayId) {
        List<Long> longs = new ArrayList<Long>();
        for (long l : arrayId) {
            longs.add(l);
        }
        serviceTypeAreaDao.deleteByServiceTypeIds(longs);
    }

    public Page<ServiceTypeArea> page(Map<String, Object> searchParams,PageRequest pageRequest) {
        /*Page<ServiceTypeArea> page = serviceTypeAreaDao.findAll(new Specification<ServiceTypeArea>() {
            public Predicate toPredicate(Root<ServiceTypeArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }
                if (result != null) {
                    query.where(result);
                }
                return query.getRestriction();
            }
        }, pageRequest);*/
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        Specification<ServiceTypeArea> spec = DynamicSpecifications.bySearchFilter(filters.values(), ServiceTypeArea.class);


       /* Specification<ServiceTypeArea> spec1 = new Specification<ServiceTypeArea>() {
            public Predicate toPredicate(Root<ServiceTypeArea> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                root.join(root.get("serviceTypeId").toString(), JoinType.LEFT);
                return query.getRestriction();
            }
        };
        Page<ServiceTypeArea> page= serviceTypeAreaDao.findAll(Specifications.where(spec).and(spec1),pageRequest);
        return page;*/

        String sql = "select t.service_name as serviceName,a.name as name,ta.id as id from t_service_area a, t_service_type t," +
                " t_service_type_area ta where a.id=ta.service_area_id and t.id=ta.service_type_id ";
        Set set = filters.entrySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object val = entry.getValue();
            System.out.println(val);
        }

        Query query = entityManager.createNativeQuery(sql);
        int page = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        Integer total = query.getResultList().size();
        query.setFirstResult(pageRequest.getOffset());
        query.setMaxResults(size);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        List<Object[]> objects = query.getResultList();
        for (Object[] o : objects) {
            Map<String, Object> map = new HashedMap();
            map.put("serviceTypeName", o[0]);
            map.put("serviceAreaName", o[1]);
            map.put("id", o[2]);
            list.add(map);
        }
        //List<ServiceTypeArea> serviceTypeAreas=query.getResultList();
        return new PageImpl(list, pageRequest, total);
    }

    @Transactional
    public void deleteByIds(long[] arrayId) {
        List<Long> longs1 = new ArrayList<Long>();
        for (long l : arrayId) {
            longs1.add(l);
        }
        serviceTypeAreaDao.deleteByIds(longs1);
    }

    public int findCountByServiceTypeIdAndServiceAreaId(Long serviceTypeId, long l) {
        return serviceTypeAreaDao.findCountByServiceTypeIdAndServiceAreaId(serviceTypeId, l);
    }

    public List<ServiceTypeArea> findAll() {
        return serviceTypeAreaDao.findAll();
    }

    public Page<ServiceTypeArea> find(int i, int i1) {
        return null;
    }

    public Page<ServiceTypeArea> find(int i) {
        return null;
    }

    public ServiceTypeArea getById(long l) {
        return serviceTypeAreaDao.findOne(l);
    }

    public ServiceTypeArea deleteById(long l) {
        return null;
    }

    public ServiceTypeArea create(ServiceTypeArea serviceTypeArea) {
        return null;
    }

    @Transactional
    public ServiceTypeArea update(ServiceTypeArea serviceTypeArea) {
        return serviceTypeAreaDao.saveAndFlush(serviceTypeArea);
    }

    public void deleteAll(long[] longs) {

    }
}
