package com.easyRepair.service.impl;

import com.easyRepair.dao.ManagerDao;
import com.easyRepair.service.ManagerService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.Role;
import common.core.Constant;
import common.utils.JsonUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.InvalidSearchFilterException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/14. */
@Service
@Transactional(readOnly = true)
public class ManagerServiceImpl implements ManagerService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ManagerDao managerDao;
    
    public List<Manager> findAll() {
        return managerDao.findAll();
    }
    
    public Page<Manager> find(int pageNum, int pageSize) {
        return managerDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }
    
    
    public Page<Manager> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }
    
    //@SystemServiceLog(description = "查询用户详情")
   // @Cacheable(value = "theUserCache")
    public Manager getById(long id) {
        return managerDao.findOne(id);
    }
    
    @Transactional
    public Manager deleteById(long id) {
        Manager manager = getById(id);
        /*if(manager.getId()==1){
            logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
            throw new ServiceException("不能删除超级管理员用户");
        }*/
        managerDao.delete(manager);
        return manager;
    }
    
    @CacheEvict(value = "theUserCache", key = "#user.getId()")
    @Transactional
    public Manager create(Manager manager) {
        return save(manager);
    }
    
    @Transactional
    public Manager update(Manager manager) {
        return managerDao.saveAndFlush(manager);
    }
    
    @Transactional
    public void deleteAll(long[] ints) {
        List<Long> longs = new ArrayList<Long>();
        for (long l : ints) {
            longs.add(l);
        }
        managerDao.deleteByIds(longs);
    }
    
    @Transactional
    @CacheEvict(value = "theUserCache", key = "#user.getId()")
    public Manager save(Manager manager) {
        return managerDao.save(manager);
    }
    
    
    //@SystemServiceLog(description = "查询用户列表")
    public Page<Manager> page(int pageNumber, int pageSize) {
        Page<Manager> page = managerDao.findAll(new Specification<Manager>() {
            public Predicate toPredicate(Root<Manager> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;
                if(predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }
                if(result != null) {
                    query.where(result);
                }
                return query.getRestriction();
            }
    
        }, new PageRequest(pageNumber, pageSize, new Sort(Sort.Direction.ASC, "id")));
        return page;
    }

    public Manager findByLoginName(String mobile) {
        return managerDao.findByMobile(mobile);
    }

    public List<Manager> findByRole_Id(Long roleId) {
        return managerDao.findByRole_Id(roleId);
    }

    public Page<Map<String, Object>> page(PageRequest pageRequest) {
        String count = "select count(m) from Manager m where m.isCut=0";
        String jpql = "select m.id,m.createTime,m.isCut,m.mobile,m.password,m.nickName," +
                "m.salt,m.userIco,m.role.id,m.user.id from Manager m where m.isCut=0";
        Query query = entityManager.createQuery(jpql);
        Long total = (Long) entityManager.createQuery(count).getSingleResult();
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        List<Object[]> managers = query.getResultList();
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (Object[] o : managers) {
            Map<String, Object> map = new HashedMap();
            map.put("id", o[0]);
            map.put("createTime", o[1]);
            map.put("isCut", o[2]);
            map.put("mobile", o[3]);
            map.put("password", o[4]);
            //map.put("user", o[5]);
            map.put("nickName", o[5]);
            map.put("salt", o[6]);
            map.put("userIco", o[7]);
            map.put("roleId", o[8]);
            map.put("userId", o[9]);
            maps.add(map);
        }
        return new PageImpl(maps, pageRequest, total);

        //return managerDao.findAll(pageRequest);
    }

    public Map<String, Object> pageResult(Map<String, Object> searchParams, PageRequest pageRequest, Integer draw) {
        /*String jpql = "select m from Manager m where m.isCut=0";
        String count = "from Manager";
        Query query = entityManager.createQuery(jpql);
        Integer total = entityManager.createQuery(count).getResultList().size();
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        List<Manager> managers = query.getResultList();*/
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        filters.put("EQ_isCut", new SpecificationUtil("isCut", SpecificationUtil.Operator.EQ, false));
        Specification<Manager> spec = DynamicSpecifications.bySearchFilter(filters.values(), Manager.class);

        Page<Manager> page = managerDao.findAll(spec, pageRequest);

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        return result;
    }

    public List<Manager> findByIds(long[] arrayId) {
        List<Long> longs = new ArrayList<Long>();
        for (long l : arrayId) {
            longs.add(l);
        }
        return managerDao.findByIds(longs);
    }

    /*public List<Manager> findByNickName(String nickName) {
        return managerDao.findByNickName(nickName);
    }*/

    public Role getRoleById(Long id) {
        return managerDao.getRoleById(id);
    }

    public List<Role> findRolesByIds(long[] arrayId) {
        List<Long> longs = new ArrayList<Long>();
        for (long l : arrayId) {
            longs.add(l);
        }
        return managerDao.findRolesByIds(longs);
    }

    public Integer findCountByNickName(String nickName) {
        return managerDao.findCountByNickName(nickName);
    }
}
