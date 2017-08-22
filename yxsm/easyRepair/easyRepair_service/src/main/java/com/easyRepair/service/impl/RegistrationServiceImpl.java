package com.easyRepair.service.impl;

import com.easyRepair.dao.RegistrationDao;
import com.easyRepair.pojo.RegistrationModule;
import com.easyRepair.service.IntegralsService;
import com.easyRepair.service.RegistrationService;
import com.easyRepair.tabEntity.Integrals;
import com.easyRepair.tabEntity.Registration;
import com.easyRepair.tabEntity.User;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 Created by sean on 2016/11/24. */
@Service
@Transactional(readOnly = true)
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private RegistrationDao registrationDao;
    @Autowired
    private IntegralsService integralsService;
    @PersistenceContext
    private EntityManager em;
    
    public List<Registration> findAll() {
        return null;
    }
    
    public Page<Registration> find(int i, int i1) {
        return null;
    }
    
    public Page<Registration> find(int i) {
        return null;
    }
    
    public Registration getById(long l) {
        return null;
    }
    
    public Registration deleteById(long l) {
        return null;
    }
    
    @Transactional
    public Registration create(Registration registration) {
        Integrals integrals=new Integrals(registration.getUser().getId(),30,"签到",new Date());
        integralsService.create(integrals);
        return registrationDao.save(registration);
    }
    
    public Registration update(Registration registration) {
        return null;
    }
    
    public void deleteAll(long[] longs) {
        
    }
    
    public List<Registration> getRegistrationByuserId(Long userId) {
        return registrationDao.getRegistrationByUserId(userId);
    }
    
    public List<RegistrationModule> getRegistrationByTime(String lastDate,Long userId) {
        DateTime dateTime = new DateTime(lastDate);
        /*最大天数*/
        int maximumValue = dateTime.dayOfMonth().getMaximumValue();
        /*当前月最后一天*/
        dateTime = dateTime.withDayOfMonth(maximumValue);
        lastDate = DateTimeFormat.forPattern("yyyy-MM-dd").print(dateTime);
        //调用返回部分列的存储过程
        Query query = em.createNativeQuery("{call qiandao(?,?,?)}");
        query.setParameter(1, lastDate);
        query.setParameter(2, maximumValue - 1);
        query.setParameter(3, userId);
        List<Object[]> result = query.getResultList();
        List<RegistrationModule> registrationModuleList = new ArrayList<RegistrationModule>();
        for(Object[] objects : result) {
            RegistrationModule registrationModule = new RegistrationModule();
            registrationModule.setRegistrationDate(objects[0].toString());
            registrationModule.setIsRegistration(objects[1].toString());
            registrationModuleList.add(registrationModule);
        }
        return registrationModuleList;
    }
    
    @Transactional
    public List<RegistrationModule> executeRegistration(Long id) {
        Registration registration = new Registration();
        User user = new User();
        user.setId(id);
        registration.setUser(user);
        create(registration);
        DateTime dateTime = new DateTime(new Date());
        String lastDate = DateTimeFormat.forPattern("yyyy-MM-dd").print(dateTime);
        return getRegistrationByTime(lastDate,id);
    }
    
    public boolean isExecuteRegistration(Long id) {
        List<Registration> registrationList=registrationDao.getRegistrationByUserId(id);
        return ! (registrationList == null || registrationList.size() == 0);
    }
}
