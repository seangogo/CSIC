package com.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.server.config.UdpDateContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

/**
 * 原始报文
 */
public interface OriginalMessageService {
    /**
     * 验证pc
     * @param datagramPacket 报文客户端
     * @return
     */
    UdpDateContext.UdpPcEnum checkPc(DatagramPacket datagramPacket);

    /**
     * 报文内部校验
     * @param udpPcEnum pc
     * @param byteBuf   报文
     * @return
     */
    boolean validation(UdpDateContext.UdpPcEnum udpPcEnum, ByteBuf byteBuf);


    /**
     * 验证效验和和尾部字
     */
    boolean validationDeCode(ByteBuf byteBuf);

    /**
     * 加密效验和
     */
    void validationEnCode(ByteBuf byteBuf);

    /**
     * 返回当前时间的byte[]
     */
    byte[] getDateBytes();

    /**
     * 返回发送报文的具体时间
     */
    void getSendDate(ByteBuf byteBuf);

    /**
     * 封装发送报文的头部
     *
     * @param sendLength 整体报文长度
     * @return 报文头
     */
    ByteBuf packagingHead(short sendLength);

    /**
     *  发送报文
     * @param udpPcEnum 计算机套接字信息
     * @param byteBuf   整条报文
     */
    void dataSend(UdpDateContext.UdpPcEnum udpPcEnum, ByteBuf byteBuf);
}
