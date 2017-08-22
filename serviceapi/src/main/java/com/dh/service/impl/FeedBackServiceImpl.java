package com.dh.service.impl;

import com.dh.configure.Consts;
import com.dh.entity.Feedback;
import com.dh.repository.FeedbackDao;
import com.dh.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/14.
 */
@Service
@Transactional(readOnly = true)
public class FeedBackServiceImpl extends Consts implements FeedBackService {
    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public List<Feedback> findAll() {
        return null;
    }

    @Override
    public Page<Feedback> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<Feedback> find(int pageNum) {
        return null;
    }

    @Override
    public Feedback getById(int id) {
        return null;
    }

    @Override
    public Feedback deleteById(int id) {
        return null;
    }

    @Override
    @Transactional
    public Feedback create(Feedback feedback) {
        return feedbackDao.save(feedback);
    }

    @Override
    public Feedback update(Feedback feedback) {
        return null;
    }

    @Override
    public void deleteAll(int[] ids) {

    }

}
