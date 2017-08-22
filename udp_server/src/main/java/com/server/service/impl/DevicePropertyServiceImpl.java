package com.server.service.impl;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.server.config.UdpDateContext;
import com.server.dao.DevicePropertyDao;
import com.server.service.DevicePropertyService;
import com.server.service.OrderService;
import com.server.service.OriginalMessageService;
import com.server.utils.SpringContextUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.server.netty.udp.ClientHandler.byteBufHashMap;

/**
 * 处理设备属性业务
 */
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "SpringJavaAutowiringInspection"})
@Service
@Transactional(readOnly = true)
public class DevicePropertyServiceImpl implements DevicePropertyService {
    private Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private DevicePropertyDao devicePropertyDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OriginalMessageService originalMessageService;


    @Override
    public byte[] getByte64(ByteBuf byteBuf) {
        //数字
        byte[] allNumByte = new byte[64];
        for (int j = 0; j < 8; j++) {
            byte b = byteBuf.readByte();
            allNumByte[j * 8] = (byte) ((b >> 7) & 0x1);
            allNumByte[j * 8 + 1] = (byte) ((b >> 6) & 0x1);
            allNumByte[j * 8 + 2] = (byte) ((b >> 5) & 0x1);
            allNumByte[j * 8 + 3] = (byte) ((b >> 4) & 0x1);
            allNumByte[j * 8 + 4] = (byte) ((b >> 3) & 0x1);
            allNumByte[j * 8 + 5] = (byte) ((b >> 2) & 0x1);
            allNumByte[j * 8 + 6] = (byte) ((b >> 1) & 0x1);
            allNumByte[j * 8 + 7] = (byte) ((b) & 0x1);
        }
        return allNumByte;
    }

    //// TODO: 2017/6/14  检测报文所有数字量，通过对应报文 找出报警信息

    /**
     * 获取报文模拟量
     *
     * @param byteBuf buf
     * @return 模拟量数组
     */
    @Override
    public float[] getSimulateFloat(ByteBuf byteBuf) {
        float[] simulateFloat = new float[byteBuf.readableBytes() >> 2];
        int num = 0;
        while (byteBuf.readableBytes() > 0) {
            simulateFloat[num] = byteBuf.readIntLE();
            num++;
        }
        return simulateFloat;
    }

    @Override
    public Map<String, Map<String, Object>> getData(byte[] allNumByte, float[] simulateFloat, List<Map<String, Object>> deviceProperties, Map<String, Map<String, Object>> data) {
        Map<String, Object> keyVal = new ConcurrentHashMap<>();
        int index = 0;
        while (index < deviceProperties.size()) {
            Map<String, Object> deviceProperty = deviceProperties.get(index);
            int sort = (int) deviceProperty.get("sort");
            if (sort <= 64) {
                //数字量
                keyVal.put(deviceProperty.get("pk").toString(), allNumByte[sort]);
            } else {
                //模拟量
                keyVal.put(deviceProperty.get("pk").toString(), simulateFloat[sort - 65]);
            }
            index++;
            if (index == deviceProperties.size() ||
                    (index < deviceProperties.size() && !deviceProperties.get(index).containsValue(deviceProperty.get("dk")))) {
                Map<String, Object> map = new ConcurrentHashMap();
                map.putAll(keyVal);
                String[] clientIds = deviceProperty.get("clientId").toString().split(",");
                for (String s : clientIds) {
                    if (data.containsKey(s)) {
                        data.get(s).put(deviceProperty.get("dk").toString(), map);
                    }
                }
                keyVal.clear();
            }
        }
        return data;
    }

    /**
     * 通过报文下标查找所有报文属性
     *
     * @param udpServerIndex 报文下标
     * @return 所有的属性
     */

    @Override
    public List<Map<String, Object>> findPropertyByIndex(UdpDateContext.UdpPcEnum udpServerIndex) {
        return devicePropertyDao.findByUdpServerIndex(udpServerIndex.ordinal());
    }


    /**
     * 返回对应报文所有的客户端页面
     *
     * @param udpPcEnum 报文服务器枚举对象
     * @return 客户端的集合
     */
    @Override
    public Map<String, Map<String, Object>> findClientsByUdpIndex(UdpDateContext.UdpPcEnum udpPcEnum) {
        List<String> clientList = devicePropertyDao.findClientsByUdpIndex(udpPcEnum.ordinal());
        Set<String> stringSet = new HashSet<>();
        Map<String, Map<String, Object>> clientMap = new ConcurrentHashMap<>();
        for (String s : clientList) {
            Collections.addAll(stringSet, s.split(","));
        }
        for (String s : stringSet) {
            clientMap.put(s, new ConcurrentHashMap<String, Object>());
        }
        return clientMap;
    }

    /**
     * 查找最新的所有指令
     *
     * @param ctx            管道
     * @param datagramPacket
     * @param udpPcEnum      客户端机
     */
    @Override
    public void sendUdp(ChannelHandlerContext ctx, DatagramPacket datagramPacket, UdpDateContext.UdpPcEnum udpPcEnum) {
        if (udpPcEnum.getReturn()) {
            //报文头
            ByteBuf byteBuf = originalMessageService.packagingHead((short) (udpPcEnum.getSendLength() - 18));
            //报文主体
            orderService.getOrderByteBuf(byteBuf, udpPcEnum.getDevelopers());
            //报文尾
            originalMessageService.validationEnCode(byteBuf);
            logger.info("sendUdp:" + ByteBufUtil.prettyHexDump(byteBuf));
            //发送
            ctx.writeAndFlush(new DatagramPacket(byteBuf, datagramPacket.sender()));
        }
    }


    /**
     * 推送报文数据到前端页面
     *
     * @param contentBuf 报文主体
     * @param udpPcEnum  服務器地址
     */
    @Override
    public void sendContent(ByteBuf contentBuf, UdpDateContext.UdpPcEnum udpPcEnum) {
        //获取数字量
        byte[] allNumByte = getByte64(contentBuf);
        //获取模拟量
        float[] simulateFloat = getSimulateFloat(contentBuf);
        //查找报文对应的设备属性
        List<Map<String, Object>> deviceProperties = findPropertyByIndex(udpPcEnum);
        //查找报文对应的页面
        Map<String, Map<String, Object>> data = findClientsByUdpIndex(udpPcEnum);
        //整合要发送的数据
        getData(allNumByte, simulateFloat, deviceProperties, data);

        SocketIOServer socketIOServer = (SocketIOServer) SpringContextUtils.getBean("socketIOServer");
        StringBuilder sb = new StringBuilder();
        for (SocketIONamespace socketIONamespace : socketIOServer.getAllNamespaces()) {
            if (!StringUtils.isEmpty(socketIONamespace.getName()) && data.containsKey(socketIONamespace.getName().substring(1))) {
                sb.append(socketIONamespace.getName()).append("|");
                socketIONamespace.getBroadcastOperations().sendEvent("messageevent", data.get(socketIONamespace.getName().substring(1)));
            }
        }
        byteBufHashMap.put(udpPcEnum.getIp(),contentBuf.resetReaderIndex());
        logger.info("sending...from udp of " + udpPcEnum.toString() + "  to " + sb);
    }

    /**
     * 查询所有跟页面有关的报文下标
     *
     * @param nameSpace 页面的命名空间
     * @return
     */
    @Override
    public List<Integer> selectUdpServerIndex(String nameSpace) {
        return devicePropertyDao.selectUdpServerIndex("%" + nameSpace + "%");
    }
}
