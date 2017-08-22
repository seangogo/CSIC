package com.dh.service.common;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/12.
 */
public interface ICommonService<T> {
    List<T> findAll();

    Page<T> find(int pageNum, int pageSize);

    Page<T> find(int pageNum);

    T getById(int id);

    T deleteById(int id);

    T create(T t);

    T update(T t);

    void deleteAll(int[] ids);

}