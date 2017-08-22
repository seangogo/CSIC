package com.dh.service.system;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.dh.service.system.socket.apiTest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service("socketIoService")
public class SocketIoService {
    static SocketIOServer server;
    static Map<String, SocketIOClient> clientsMap = new HashMap<String, SocketIOClient>();

    public void startServer() throws InterruptedException {
        Configuration config = new Configuration();
        //服务器主机ip
        config.setHostname("127.0.0.1");
        //端口
        config.setPort(8000);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        server = new SocketIOServer(config);
        //监听广告推送事件，advert_info为事件名称，自定义
        server.addEventListener("advert_info", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws ClassNotFoundException {
                //客户端推送advert_info事件时，onData接受数据，这里是string类型的json数据，还可以为Byte[],object其他类型

                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));//获取客户端连接的ip
                Map params = client.getHandshakeData().getUrlParams();//获取客户端url参数

                System.out.println(clientIp + "：客户端：************" + data);
            }
        });
        //监听通知事件
        server.addEventListener("notice_info", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                //同上
            }
        });

        /**
         * 监听其他事件
         */

        //添加客户端连接事件
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                // TODO Auto-generated method stub
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));//获取设备ip
                System.out.println(clientIp + "-------------------------" + "客户端已连接");
                Map params = client.getHandshakeData().getUrlParams();
                //如果没有人开启页面则启用推送
               // if (server.getAllClients().size() == 0) {

           //     }
                //获取客户端连接的uuid参数
                Object object = params.get("uuid");
                String uuid = "";
                if (object != null) {
                    uuid = ((List<String>) object).get(0);
                    //将uuid和连接客户端对象进行绑定
                    clientsMap.put(uuid, client);
                }
                ExecutorService executorService = Executors.newCachedThreadPool();
                executorService.execute(new apiTest(client));
                //给客户端发送消息
                client.sendEvent("advert_info", clientIp + "客户端你好，我是服务端，有什么能帮助你的？");
            }
        });
        //添加客户端断开连接事件
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                // TODO Auto-generated method stub
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));//获取设备ip
                System.out.println(clientIp + "-------------------------" + "客户端已断开连接");

                //给客户端发送消息
                client.sendEvent("advert_info", clientIp + "客户端你好，我是服务端，期待下次和你见面");
                //如果当前用户数等于0 则关闭线程
            }
        });
        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

    public void stopServer() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }

    /**
     * 给所有连接客户端推送消息
     *
     * @param eventType 推送的事件类型
     * @param message   推送的内容
     */
    public void sendMessageToAllClient(String eventType, String message) {
        Collection<SocketIOClient> clients = server.getAllClients();
        for (SocketIOClient client : clients) {
            client.sendEvent(eventType, message);
        }
    }

    /**
     * 给具体的客户端推送消息
     *
     * @param uuid        设备类型
     * @param eventType 推送事件类型
     * @param message         推送的消息内容
     */
    public void sendMessageToOneClient(String uuid, String eventType, String message) {
        try {
            if (uuid != null && !"".equals(uuid)) {
                SocketIOClient client = (SocketIOClient) clientsMap.get(uuid);
                if (client != null) {
                    client.sendEvent(eventType, message);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static SocketIOServer getServer() {
        return server;
    }
}