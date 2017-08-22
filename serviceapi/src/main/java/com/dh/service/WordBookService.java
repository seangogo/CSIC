package com.dh.service;

import com.dh.entity.WordBook;
import com.dh.service.common.ICommonService;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/12.
 */
public interface WordBookService extends ICommonService {
    String findByIndex(Integer repairType, String repairType1);

    /*根据类型查找所有字典数据*/
    List<WordBook> findByType(String type);

    List<WordBook> getContentType(Integer type);
}
