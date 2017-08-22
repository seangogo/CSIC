package com.dh.service.system;

import com.dh.common.CommonButil;
import com.dh.configure.annotation.SystemServiceLog;
import com.dh.entity.OperationLog;
import com.dh.repository.OperationLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class OperationLogService {

    @Autowired
    private OperationLogDao LogDao;

    public void save(OperationLog log) {
        LogDao.save(log);
    }

    /**
     * 分页查询列表
     *
     * @param pageNumber
     * @param pageSize
     * @param descriptionType
     * @param description
     * @param userName
     * @param logCreateTime
     * @return
     */
    @SystemServiceLog(description = "查询操作日志列表")
    public Page<OperationLog> findLogList(Integer pageNumber, Integer pageSize,
                                          List<String> logCreateTime, String userName,
                                          String description, String descriptionType, Integer type) {
        PageRequest pageRequest = buildLogListPageRequest(pageNumber, pageSize);
        Specification<OperationLog> spe = buildLogListSpecification(logCreateTime, userName, description, descriptionType, type);
        return LogDao.findAll(spe, pageRequest);
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildLogListPageRequest(int pageNumber, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    /**
     * 设置查询新闻列表的条件
     *
     * @param createTime
     * @param descriptionType
     * @param description
     * @param userName
     * @param type
     * @return
     */
    private Specification<OperationLog> buildLogListSpecification(List<String> logTime, String userName, String description, String descriptionType, Integer type) {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (logTime.size() == 2) {
            try {
                if (!CommonButil.isEmpty(logTime.get(0))) {
                    filters.put("startTime", new SearchFilter("createTime", Operator.GTE, sdf.parse(logTime.get(0))));
                }
                if (!CommonButil.isEmpty(logTime.get(1))) {
                    filters.put("startTime", new SearchFilter("createTime", Operator.LTE, sdf.parse(logTime.get(1))));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (!CommonButil.isEmpty(userName)) {
            filters.put("userName", new SearchFilter("creater.userName", Operator.LIKE, userName));
        }
        if (!CommonButil.isEmpty(descriptionType)) {
            filters.put("description", new SearchFilter("description", Operator.LIKE, descriptionType));
        } else if (!CommonButil.isEmpty(description)) {
            filters.put("description", new SearchFilter("description", Operator.LIKE, description));
        }

        if (!CommonButil.isEmpty(type)) {
            filters.put("type", new SearchFilter("type", Operator.EQ, type));
        }
        Specification<OperationLog> spec = DynamicSpecifications.bySearchFilter(filters.values(), OperationLog.class);
        return spec;
    }

    /**
     * 查找现有的操作描述
     *
     * @return
     */
    @SystemServiceLog(description = "查询操作描述列表")
    public List<String> findOperationDesc() {
        return LogDao.findOperationDesc();
    }

}
