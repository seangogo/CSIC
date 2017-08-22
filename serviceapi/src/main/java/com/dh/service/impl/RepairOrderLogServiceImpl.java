package com.dh.service.impl;

import com.dh.configure.Consts;
import com.dh.entity.RepairOrder;
import com.dh.entity.RepairOrderLog;
import com.dh.entity.User;
import com.dh.entity.WordBook;
import com.dh.repository.RepairOrderLogDao;
import com.dh.service.RepairOrderLogService;
import com.dh.service.WordBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Coolkid on 2016/9/19.
 */
@Service
@Transactional(readOnly = true)
public class RepairOrderLogServiceImpl implements RepairOrderLogService {
    @Autowired
    private RepairOrderLogDao repairOrderLogDao;

    @Autowired
    private WordBookService wordBookService;

    @Override
    public List<RepairOrderLog> findAll() {
        return null;
    }

    @Override
    public Page<RepairOrderLog> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<RepairOrderLog> find(int pageNum) {
        return null;
    }

    @Override
    public RepairOrderLog getById(int id) {
        return repairOrderLogDao.getOne(id);
    }

    @Override
    @Transactional
    public RepairOrderLog deleteById(int id) {
        return null;
    }

    @Override
    @Transactional
    public RepairOrderLog create(RepairOrderLog repairOrderLog) {
        return repairOrderLogDao.save(repairOrderLog);
    }

    @Override
    @Transactional
    public RepairOrderLog update(RepairOrderLog repairOrderLog) {
        return null;
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {

    }

    @Override
    public List<RepairOrderLog> getOrderStateByOrderId(Integer orderId) {
        List<RepairOrderLog> logList = repairOrderLogDao.getOrderStateByOrderId(orderId);
        List<RepairOrderLog> logListTo = new ArrayList<RepairOrderLog>();
        List<WordBook> wordBookList = wordBookService.findByType(Consts.MSG_TYPE);
        for (RepairOrderLog log : logList) {
            RepairOrderLog orderLog = new RepairOrderLog();
            orderLog.setId(log.getId());
            RepairOrder repairOrder=new RepairOrder();
            repairOrder.setId(log.getRepairOrder().getId());
            orderLog.setRepairOrder(repairOrder);
            User creater=new User();
            if (log.getCreater()!=null){
            creater.setId(log.getCreater().getId());
            creater.setUserName(log.getCreater().getUserName());
            orderLog.setCreater(creater);
            }
            User repair=new User();
            repair.setId(log.getRepair()==null?null:log.getRepair().getId());
            repair.setUserName(log.getRepair()==null?null:log.getRepair().getUserName());
            orderLog.setRepair(repair);
            orderLog.setMsgType(log.getMsgType());
            orderLog.setContentType(log.getContentType());
            orderLog.setCreateTime(log.getCreateTime());//创建时间
            orderLog.setMsgContent(log.getMsgContent());//日志内容
            orderLog.setCommentStar(log.getCommentStar());//
            wordBookFor:
            for (WordBook wordBook : wordBookList) {
                if (wordBook.getWordIndex() == log.getMsgType()) {
                    orderLog.setMsgTypeStr(wordBook.getWordValue());
                    break wordBookFor;
                }
            }
            logListTo.add(orderLog);
        }
        return logListTo;
    }
}
