package com.server.service.impl;

import com.server.dao.OrderDao;
import com.server.domain.MessageInfo;
import com.server.service.OrderService;
import io.netty.buffer.ByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 处理指令
 */
@Service(value = "orderService")
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    /**
     * 获得指定厂商的所有指令状态
     *
     * @param byteBuf    原始报文
     * @param developers 厂商标识
     * @return 封装过后的报文
     */
    @Override
    public ByteBuf getOrderByteBuf(ByteBuf byteBuf, String developers) {
        //1.获得数字量
        List<String> orderNum = orderDao.orderNum(developers);
        //2.获得模拟量
        List<Float> orderReal = orderDao.orderReal(developers);
        for (String s : orderNum) {
            byteBuf.writeByte(Integer.parseInt(s, 2));
        }
        for (Float f : orderReal) {
            byteBuf.writeFloat(f);
        }
        return byteBuf;
    }

    /**
     * 修改操作指令（前台操作）
     *
     * @param messageInfo (sort 指令下标,developers 对应厂商的标识)
     */
    @Override
    @Transactional
    //allEntries = true
    @CacheEvict(value = "orderNum",key ="#messageInfo.getDevelopers()" )
    public void updateOrdersNum(MessageInfo messageInfo) {
        orderDao.updateNum(messageInfo.getMsgContent(), messageInfo.getDevelopers());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "orderReal", key ="#messageInfo.getDevelopers()")
    public void updateOrdersReal(MessageInfo messageInfo) {
        orderDao.updateReal(messageInfo.getMsgContent(), messageInfo.getDevelopers(), messageInfo.getReal());
    }

}
