package com.dh.service;

import com.dh.entity.CodeNum;
import com.dh.service.common.ICommonService;

/**
 * Created by Coolkid on 2016/9/12.
 */
public interface CodeNumService extends ICommonService<CodeNum> {
    int createCodeNum(String codeParam);
}
