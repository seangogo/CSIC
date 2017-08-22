package com.server.netty.webSockt;


import com.corundumstudio.socketio.SocketIOServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 配置Netty Server相关代码
 */
@Component
public class ServerRunner implements CommandLineRunner {
    private static Log logger = LogFactory.getLog(ServerRunner.class);
    private final SocketIOServer server;

    @Autowired
    public ServerRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(">>>>>>>>>>>>>>>web socket start<<<<<<<<<<<<<");
        server.start();
    }
}