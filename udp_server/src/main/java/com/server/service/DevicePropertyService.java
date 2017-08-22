package com.server.service;

import com.server.config.UdpDateContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

import java.util.List;
import java.util.Map;

public interface DevicePropertyService {
    /**
     * 获取数值量集合
     *
     * @param byteBuf 報文主體buf
     * @return 數字量
     */
    byte[] getByte64(ByteBuf byteBuf);

    /**
     * 获取模拟量集合
     *
     * @param byteBuf 读取数字量以后的报文主题
     * @return 模拟量数组
     */
    float[] getSimulateFloat(ByteBuf byteBuf);

    /**
     * 需要整合的数据
     *
     * @param allNumByte       数字量
     * @param simulateFloat    模拟量
     * @param deviceProperties 设备属性集合
     * @param data             发送的数据集合
     * @return 要发送的数据
     */
    Map<String, Map<String, Object>> getData(byte[] allNumByte, float[] simulateFloat, List<Map<String, Object>> deviceProperties, Map<String, Map<String, Object>> data);

    /**
     * 通过报文下标查找所有相关属性
     *
     * @param udpServerIndex 报文下标
     * @return 所有对应下标的报文属性并按照sort 排序
     */
    List<Map<String, Object>> findPropertyByIndex(UdpDateContext.UdpPcEnum udpServerIndex);


    /**
     * 根据报文下标查询所有要推送的页面
     */
    Map<String, Map<String, Object>> findClientsByUdpIndex(UdpDateContext.UdpPcEnum udpPcEnum);

    void sendUdp(ChannelHandlerContext ctx, DatagramPacket datagramPacket, UdpDateContext.UdpPcEnum udpPcEnum);


    /**
     * 发送报文
     *
     * @param contentBuf 报文主体
     */
    void sendContent(ByteBuf contentBuf, UdpDateContext.UdpPcEnum udpPcEnum);

    /**
     * 查询页面对应的报文下标
     * @param nameSpace 页面的命名空间
     * @return
     */
    List<Integer> selectUdpServerIndex(String nameSpace);
}
