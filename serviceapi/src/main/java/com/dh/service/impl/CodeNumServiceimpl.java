package com.dh.service.impl;

import com.dh.entity.CodeNum;
import com.dh.repository.CodeNumDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Coolkid on 2016/9/12.
 */
@Service
@Transactional(readOnly = true)
public class CodeNumServiceimpl implements com.dh.service.CodeNumService {
    @Autowired
    private CodeNumDao codeNumDao;

    @Override
    public List<CodeNum> findAll() {
        return null;
    }

    @Override
    public Page<CodeNum> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<CodeNum> find(int pageNum) {
        return null;
    }

    @Override
    public CodeNum getById(int id) {
        return null;
    }

    @Override
    public CodeNum deleteById(int id) {
        return null;
    }

    @Override
    public CodeNum create(CodeNum codeNum) {
        return null;
    }

    @Override
    public CodeNum update(CodeNum codeNum) {
        return null;
    }

    @Override
    public void deleteAll(int[] ids) {

    }

    @Override
    public int createCodeNum(String codeParam) {
        // 获取该标示的code对象
        CodeNum code = codeNumDao.findByCodeParam(codeParam);
        String timeStr = new SimpleDateFormat("yyyyMM").format(new Date());
        // 若code不存在则根据标示 新建一个
        if (null == code) {
            CodeNum c = new CodeNum();
            c.setCodeParam(codeParam);
            c.setCodeNum(1);
            c.setCodeTime(timeStr);
            codeNumDao.save(c);
            return 1;
        } else {
            // code存在
            //则判断codeTime 是否是本月 若不是：则修改codeTime为本月时间 重置编码为1
            if (!timeStr.equals(code.getCodeTime())) {
                code.setCodeTime(timeStr);
                code.setCodeNum(1);
                codeNumDao.save(code);
                return 1;
            } else {
                //是本月 获取code编码 +1
                int num = code.getCodeNum() + 1;
                code.setCodeNum(num);
                codeNumDao.save(code);
                return num;
            }
        }
    }
}
