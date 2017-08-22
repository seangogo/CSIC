package com.dh.service.impl;

import com.dh.entity.Role;
import com.dh.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/14.
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Page<Role> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<Role> find(int pageNum) {
        return null;
    }

    @Override
    public Role getById(int id) {
        return null;
    }

    @Override
    public Role deleteById(int id) {
        return null;
    }

    @Override
    public Role create(Role role) {
        return null;
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    @Override
    public void deleteAll(int[] ids) {

    }
}
