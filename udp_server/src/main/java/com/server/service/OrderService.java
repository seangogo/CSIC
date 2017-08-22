package com.server.service;

import com.server.domain.MessageInfo;
import io.netty.buffer.ByteBuf;

/**
 * Created by sean on 2017/6/7.
 */
public interface OrderService {
    /**
     * 获得指定厂商的所有指令状态
     *
     * @param byteBuf    原始报文
     * @param developers 厂商标识
     * @return 封装过后的报文
     */
    ByteBuf getOrderByteBuf(ByteBuf byteBuf, String developers);

    /**
     * 修改数字指令（前台操作）
     *
     * @param messageInfo (sort 指令下标,developers 对应厂商的标识)
     */
    void updateOrdersNum(MessageInfo messageInfo);

    /**
     * 修改模拟量指令(前端操作)
     *
     * @param messageInfo
     */
    void updateOrdersReal(MessageInfo messageInfo);
}
