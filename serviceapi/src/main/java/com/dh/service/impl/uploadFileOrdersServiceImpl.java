package com.dh.service.impl;

import com.dh.commont.CommonButil;
import com.dh.entity.RepairOrder;
import com.dh.entity.UploadFileOrders;
import com.dh.repository.UploadFileOrdersDao;
import com.dh.service.UploadFileOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dh.configure.Consts.FILE_URL;

/**
 * Created by Coolkid on 2016/10/18.
 */
@Service
@Transactional(readOnly = true)
public class uploadFileOrdersServiceImpl implements UploadFileOrdersService {
    @Autowired
    private UploadFileOrdersDao uploadFileOrdersDao;
    @Override
    public List<UploadFileOrders> findAll() {
        return null;
    }

    @Override
    public Page<UploadFileOrders> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<UploadFileOrders> find(int pageNum) {
        return null;
    }

    @Override
    public UploadFileOrders getById(int id) {
        return null;
    }

    @Override
    public UploadFileOrders deleteById(int id) {
        return null;
    }

    @Override
    public UploadFileOrders create(UploadFileOrders uploadFileOrders) {
        return null;
    }

    @Override
    public UploadFileOrders update(UploadFileOrders uploadFileOrders) {
        return null;
    }

    @Override
    public void deleteAll(int[] ids) {

    }


    @Override
    public List<UploadFileOrders> findByRepairOrder(RepairOrder order) {
        List<UploadFileOrders> uploadFileOrdersList=uploadFileOrdersDao.findByRepairOrder(order);
        for (UploadFileOrders uploadFileOrders:uploadFileOrdersList){
            String path=uploadFileOrders.getDocPath();
            if (!CommonButil.isEmpty(path)){
                uploadFileOrders.setDocPath(FILE_URL+path);
            }
        }
        return uploadFileOrdersList;
    }
}
