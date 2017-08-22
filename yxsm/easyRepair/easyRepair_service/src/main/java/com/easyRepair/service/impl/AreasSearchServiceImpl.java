package com.easyRepair.service.impl;

import com.easyRepair.dao.AreasSearchDao;
import com.easyRepair.entityUtils.ApiBeanUtils;
import com.easyRepair.pojo.OrderListMudule;
import com.easyRepair.pojo.RepairMatchingModule;
import com.easyRepair.service.AreasSearchService;
import com.easyRepair.service.OrderService;
import com.easyRepair.service.UserRepairInfoService;
import com.easyRepair.tabEntity.*;
import common.core.EASY_ERROR_CODE;
import common.core.bean.Result;
import common.geo.DistUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/25. */
@SuppressWarnings("JpaQueryApiInspection")
@Service
@Transactional(readOnly = true)
public class AreasSearchServiceImpl implements AreasSearchService {
    @Autowired
    private AreasSearchDao areasSearchDao;
    
    @Autowired
    private UserRepairInfoService userRepairInfoService;
    
    @Autowired
    private OrderService orderService;
    
    @PersistenceContext
    private EntityManager em;
    
    
    public List<AreasSearch> findAll() {
        return areasSearchDao.findAll();
    }
    
    public Page<AreasSearch> find(int i, int i1) {
        return null;
    }
    
    public Page<AreasSearch> find(int i) {
        return null;
    }
    
    public AreasSearch getById(long l) {
        return null;
    }
    
    public AreasSearch deleteById(long l) {
        return null;
    }
    
    
    @Transactional
    public AreasSearch create(AreasSearch areasSearch) {
        return areasSearchDao.saveAndFlush(areasSearch);
    }
    
    public AreasSearch update(AreasSearch areasSearch) {
        return null;
    }
    
    public void deleteAll(long[] longs) {
        
    }
    
    public List<RepairMatchingModule> findByLngAndLatAndServiceTypeId(Double lng, Double lat, Long serviceTypeId,
                                                                      Integer start, Integer length) {
        List<Object[]> objects = areasSearchDao.findByLngAndLatAndServiceTypeId(lng, lat, serviceTypeId, start, length);
        List<Long> repairInfoIdList = new ArrayList<Long>();
        List<RepairMatchingModule> repairMatchingModuleList = new ArrayList<RepairMatchingModule>();
        /*获取距离*/
        for(Object[] object : objects) {
            repairInfoIdList.add(Long.parseLong(object[1].toString()));
            Double lng1 = Double.parseDouble(object[3].toString());
            Double lat1 = Double.parseDouble(object[4].toString());
            String dist = DistUtils.DubToStr(lng, lat, lng1, lat1);
            RepairMatchingModule repairMatchingModule = new RepairMatchingModule();
            repairMatchingModule.setDist(dist);
            repairMatchingModuleList.add(repairMatchingModule);
            
        }
        List<UserRepairInfo> userRepairInfoList = userRepairInfoService.findByIdIn(repairInfoIdList);
        for(int i = 0; i < userRepairInfoList.size(); i++) {
            RepairMatchingModule repairMatchingModule = repairMatchingModuleList.get(i);
            UserRepairInfo userRepairInfo = userRepairInfoList.get(i);
            User user = userRepairInfo.getUser();
            repairMatchingModule.setAuthentication(user.isAuthentication());
            repairMatchingModule.setPhoto(user.getUserInfo().getPhoto());
            repairMatchingModule.setId(user.getId());
            repairMatchingModule.setRealName(userRepairInfo.getRealName());
            repairMatchingModule.setJobCode(userRepairInfo.getJobCode());
            repairMatchingModule.setJobTitle(userRepairInfo.getJobTitle());
        }
        return repairMatchingModuleList;
        
        
    }
    
    public Map<String,Object> findByLngAndLatAndServiceAreaId(Double lng, Double lat, Long userId, Integer start, Integer length) {
        UserRepairInfo userRepairInfo = userRepairInfoService.findByUser_Id(userId);
        StringBuilder ids = new StringBuilder();
        int sort = 0;
        for(ServiceArea serviceArea : userRepairInfo.getServiceArea()) {
            if(sort == 0) {
                ids.append(serviceArea.getId());
                sort++;
            } else {
                ids.append(",");
                ids.append(serviceArea.getId());
            }
        }
        List<Object[]> objects = areasSearchDao.findByLngAndLatAndServiceAreaId(lng, lat,ids.toString() , start, length);
        String sql="SELECT count(z1.oid) FROM t_areas_search a1 LEFT JOIN(SELECT DISTINCT a1.id AS oid FROM t_order a1 " +
                "WHERE a1.service_type_id IN ( SELECT t1.service_type_id FROM t_service_type_area t1 LEFT JOIN t_service_type t2 ON t1.service_type_id = t2.id " +
                "WHERE t1.service_area_id IN (?1) AND a1.`status` = 1 )) z1 ON a1.object_id = z1.oid " +
                "WHERE a1.order_or_repair = 0 ORDER BY IF (z1.oid = \"\", 0, 1)";
        Query query=em.createNativeQuery(sql);
        query.setParameter(1,ids.toString());
        BigInteger count = (BigInteger)query.getSingleResult();
        List<Long> orderIdList = new ArrayList<Long>();
        /*获取订单距离*/
        List<OrderListMudule> orderListMuduleList=new ArrayList<OrderListMudule>();
          for(Object[] object : objects) {
            if(object[1]!=null) {
                 orderIdList.add(Long.parseLong(object[1].toString()));
                Double lng1 = Double.parseDouble(object[3].toString());
                Double lat1 = Double.parseDouble(object[4].toString());
                String dist = DistUtils.DubToStr(lng, lat, lng1, lat1);
                  OrderListMudule orderListMudule = ApiBeanUtils.getOrderListModel(orderService.getById(Long.parseLong(object[1].toString())));
                if(orderListMudule!=null){
                    orderListMudule.setDist(dist);
                }
                orderListMuduleList.add(orderListMudule);
            }
        }
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("data",orderListMuduleList);
        map.put("isLast",((start+1))>count.intValue());
        return map;
    }

    public List<AreasSearch> indexTop5Order(Double lng, Double lat) {
        String s="select a from AreasSearch a  where  a.orderOrRepair=0 order by (POWER(MOD(ABS(?1 - a.lng),360),2) + POWER(ABS(?2 - a.lat),2))";
        Query query=em.createQuery(s);
        query.setParameter(1,lng);
        query.setParameter(2,lat);
        query.setMaxResults(5);
        return query.getResultList();
    }

    public AreasSearch findByObjectId(Long objectId, boolean orderOrRepair) {
        return areasSearchDao.findByObjectId(objectId, orderOrRepair);
    }
    @Transactional
    public boolean deleteByObjectIdAndOr(Long objectId, boolean orderOrRepair) {
        return areasSearchDao.deleteByObjectIdAndOr(objectId, orderOrRepair) > 0;
    }

    public Result findByOrderlngAndLat(Long orderId, Long id) {
        if (orderService.getByIdCheckUser(orderId,id)){
            return  new Result(true).msg("获取成功！").data(areasSearchDao.findByObjectId(orderId,false));
        }else {
            return  new Result(false).msg("数据异常").data(EASY_ERROR_CODE.ERROR_CODE_0002);
        }
    }
}
