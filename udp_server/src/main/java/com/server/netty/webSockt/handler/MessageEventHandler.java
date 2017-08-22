package com.server.netty.webSockt.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.server.domain.entity.ClientInfo;
import com.server.service.ClientInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息处理类
 */
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Component
public class MessageEventHandler {
    private static Log logger = LogFactory.getLog(MessageEventHandler.class);
    private final SocketIOServer server;
    @Autowired
    private ClientInfoService clientInfoService;


    @Autowired
    public MessageEventHandler(SocketIOServer server) {
        this.server = server;
    }

    /**
     * 添加connect事件，当客户端发起连接时调用
     * 本文中将clientid与sessionid存入数据库方
     * 便后面发送消息时查找到对应的目标client
     *
     * @param client 前端socket客户端
     * @throws InterruptedException 中断异常
     */
    @OnConnect
    public void onConnect(SocketIOClient client) throws InterruptedException {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        //如果客户端存在
        if (clientInfoService.checkClientId(clientId)) {
            //发送数据
            ClientInfo clientInfo = clientInfoService.sendAllByClientId(clientId);
            //保存客户端登录信息
            clientInfoService.saveLoginInfo(client, clientInfo.getId());
        }
    }

    /**
     * 添加@OnDisconnect事件，客户端断开连接时
     * 调用，刷新客户端信息
     *
     * @param client 客户端
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        //修改客户端状态
        clientInfoService.onDisconnectUpdate(
                client.getHandshakeData().getSingleUrlParam("clientid"));
        //清空数据缓存以便再次进入页面时重新推送数据到页面
        clientInfoService.clearCacheableMap(client.getHandshakeData().getSingleUrlParam("clientId"));
    }

    /**
     * 无命名空间发送指令
     * @param client 客户端
     * @param request 请求数据
     * @param floats 三个前端模拟量数据
     */
    @OnEvent(value = "eventCan")
    public void onEvent(SocketIOClient client, AckRequest request,float[] floats){

    }
}