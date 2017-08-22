package com.dh.service.impl;

import com.dh.entity.WordBook;
import com.dh.repository.WordBookDao;
import com.dh.service.WordBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.dh.configure.Consts.*;

/**
 * Created by Coolkid on 2016/9/12.
 */
@Service
@Transactional(readOnly = true)
public class WordBookServiceImpl implements WordBookService {
    @Autowired
    private WordBookDao wordBookDao;

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Page find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page find(int pageNum) {
        return null;
    }

    @Override
    public Object getById(int id) {
        return null;
    }

    @Override
    public Object deleteById(int id) {
        return null;
    }

    @Override
    public Object create(Object o) {
        return null;
    }

    @Override
    public Object update(Object o) {
        return null;
    }

    @Override
    public void deleteAll(int[] ids) {

    }

    @Override
    public String findByIndex(Integer index, String repairType) {
        return wordBookDao.findByIndex(index, repairType);
    }

    @Override
    public List<WordBook> findByType(String type) {
        return wordBookDao.findByType(type);
    }

    @Override
    /**
     * 根据 类型判断 返回字典值
     *
     * @param type
     * @return
     */
    public List<WordBook> getContentType(Integer type) {
        List<WordBook> wbs = new ArrayList<WordBook>();
        switch (type) {
            case 0:// 发布订单的订单类型
                wbs = findByType(REPAIR_TYPE);
                break;
            case 1:// 取消订单
                wbs = findByType(CANCEL_ORDER);
                break;
            case 2:// 投诉
                wbs = findByType(COMPLAIN_TYPE);
                break;
            case 3:// 再次上门
                wbs = findByType(REASON_TYPE);
                break;
        }
        return wbs;
    }
}
