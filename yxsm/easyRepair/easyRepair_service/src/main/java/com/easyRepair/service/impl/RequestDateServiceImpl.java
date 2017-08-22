package com.easyRepair.service.impl;

import com.easyRepair.dao.RequestDateDao;
import com.easyRepair.pojo.RequestMessageModel;
import com.easyRepair.service.RequestDateService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.RequestDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2017/1/16. */
@Service
@Transactional(readOnly = true)
public class RequestDateServiceImpl implements RequestDateService {
    @Autowired
    private RequestDateDao requestDateDao;
    
    
    public List<RequestDate> findAll() {
        return null;
    }
    
    public Page<RequestDate> find(int i, int i1) {
        return null;
    }
    
    public Page<RequestDate> find(int i) {
        return null;
    }
    
    public RequestDate getById(long l) {
        return requestDateDao.findOne(l);
    }
    
    public RequestDate deleteById(long l) {
        return null;
    }
    @Transactional
    public RequestDate create(RequestDate requestDate) {
        return requestDateDao.saveAndFlush(requestDate);
    }
    @Transactional
    public RequestDate update(RequestDate requestDate) {
        return requestDateDao.save(requestDate);
    }
    @Transactional
    public void deleteAll(long[] longs) {
        
    }
    
    public Map<String,Object> findByTypeAndUserId(Long userId,Long orderId, String type,Pageable pageable) {
        List<RequestMessageModel> requestMessageModels=new ArrayList<RequestMessageModel>();
        Page<RequestDate> page=type.equals("1")?requestDateDao.findByRepaiId(userId,pageable):requestDateDao.findByOrder_Id(orderId,pageable);
        for(RequestDate requestDate:page.getContent()){
            requestMessageModels.add(new RequestMessageModel(requestDate.getOrder().getOrderNum(),requestDate.getUser().getUserInfo().getNickName()
                    ,requestDate.getUser().getId(),requestDate.getOrder().getId(),requestDate.getCreateTime()));
        }
        return DataTableFactory.fittingPojo(page,requestMessageModels);
    }
    @Transactional
    public boolean deleteByOrderId(Long id) {
      return requestDateDao.deleteByOrderId(id) == 1;
    }
    /*查找工程师发送端请求*/
    public RequestDate requestOrder_IdAndUser_Id(Long orderId, Long id) {
        return requestDateDao.requestOrder_IdAndUser_Id(orderId,id);
    }
    /*查找用户发送的请求*/
    public RequestDate machingOrder_IdAndUser_Id(Long orderId,Long id){
        return requestDateDao.matchingOrder_IdAndUser_Id(orderId,id);
    }
}
