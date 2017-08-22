package com.server.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.server.config.UdpDateContext;
import com.server.dao.ClientInfoDao;
import com.server.domain.entity.ClientInfo;
import com.server.service.ClientInfoService;
import com.server.service.DevicePropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.server.netty.udp.ClientHandler.byteBufHashMap;

/**
 * 客户端事务
 */
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "client")
public class ClientInfoServiceImpl implements ClientInfoService {
    @Autowired
    private ClientInfoDao clientInfoDao;
    @Autowired
    private DevicePropertyService devicePropertyService;

    /**
     * 判断客户端ID是否存在
     */
    @Override
    @Cacheable(value = "checkClient", key = "#p0")
    public boolean checkClientId(String clientId) {
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setClientId(clientId);
        Example<ClientInfo> example = Example.of(clientInfo);
        return clientInfoDao.exists(example);
    }


    @Override
    @Transactional
    public void saveLoginInfo(SocketIOClient client, Long id) {
        clientInfoDao.onConnectUpdate(client.getSessionId().getMostSignificantBits(),
                client.getSessionId().getLeastSignificantBits(), new Date(System.currentTimeMillis()), id);
    }

    /**
     * 推送最新数据到指定的客户端页面
     *
     * @param clientId 客户端ID
     * @return 前端客户端信息
     */
    @Override
    public ClientInfo sendAllByClientId(String clientId) {
        return clientInfoDao.findByClientId(clientId);
    }


    /**
     * 断开连接时调用
     */
    @Override
    @Transactional
    public void onDisconnectUpdate(String clientId) {
        clientInfoDao.onDisconnectUpdate(clientId);
    }
    //清除全局缓存数据，以便再次推送
    @Override
    public void clearCacheableMap(String nameSpace) {
        List<Integer> udpIndexs=devicePropertyService.selectUdpServerIndex(nameSpace);
        UdpDateContext.UdpPcEnum[] udpPcEna= UdpDateContext.UdpPcEnum.values();
        for (int i: udpIndexs){
            if (byteBufHashMap.containsKey(udpPcEna[i].getIp())){
                byteBufHashMap.remove(udpPcEna[i].getIp());
            }
        }
    }
}
