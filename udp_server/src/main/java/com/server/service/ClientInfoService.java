package com.server.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.server.domain.entity.ClientInfo;

public interface ClientInfoService {
    boolean checkClientId(String clientId);

    void saveLoginInfo(SocketIOClient client, Long id);

    ClientInfo sendAllByClientId(String clientId);

    void onDisconnectUpdate(String clientId);
    //查询页面对应的数据缓存，并清除以便再次推送数据
    void clearCacheableMap(String nameSpace);
}
