package com.server;

/*import Automation.BDaq.DaqException;
import Automation.BDaq.DeviceInformation;
import Automation.BDaq.InstantDoCtrl;*/
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.listener.DataListener;
import com.server.config.UdpDateContext;
import com.server.domain.MessageInfo;
import com.server.domain.entity.CanProperty;
import com.server.netty.webSockt.exception.SocketExceptionListener;
import com.server.service.OrderService;
import com.server.utils.SpringContextUtils;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

//@EnableEurekaServer
@SpringBootApplication
@EnableAsync
@EnableCaching
public class ServerApplication {
    @Autowired
    protected SpringContextUtils springContextUtils;
    /*@Autowired
    protected InstantDoCtrl instantDoCtrl;*/
    private Log logger = LogFactory.getLog(this.getClass());
    @Value("${udp.server.host}")
    private String host;
    @Value("${udp.server.port}")
    private Integer port;
   /* @Value("${web.upload-path}")
    private String path;*/

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);

    }

    @Bean(value = "socketIOServer")
    public SocketIOServer socketIOServer() throws IOException {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setExceptionListener(new SocketExceptionListener());
        config.setWorkerThreads(100);//工作线程
        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData data) {
                return true;
            }
        });
        final SocketIOServer server = new SocketIOServer(config);
        for (UdpDateContext.NameSpaceEnum nameSpaceEnum : UdpDateContext.NameSpaceEnum.values()) {
            SocketIONamespace socketIONamespace=server.addNamespace(nameSpaceEnum.getNamespace());
            socketIONamespace.addEventListener("eventData", MessageInfo.class, new DataListener<MessageInfo>() {
                @Override
                public void onData(SocketIOClient socketIOClient, MessageInfo messageInfo, AckRequest ackRequest) {
                    logger.info("pc:" + socketIOClient.getHandshakeData().getAddress().getHostName());
                    logger.info("orderInfo:" + messageInfo.toString());
/*
                    if (messageInfo.getMsgContent()<=0) {
                        if (messageInfo.getMsgContent()==-1){
                            try {
                                instantDoCtrl.setSelectedDevice(new DeviceInformation("PCI-1761,BID#0"));
                            } catch (DaqException e) {
                                logger.error("Device error");
                                e.printStackTrace();
                            }
                            instantDoCtrl.LoadProfile("C:\\1.xml");
                          //  byte[] portMask = instantDoCtrl.getFeatures().getDoDataMask();
                            byte a=0x00;
                            System.out.println("报警");
                            instantDoCtrl.Write(0, a);
                            return;
                        }
                        //报警无实际就地操作，前端页面体现
                        if (messageInfo.getMsgContent()==-2){
                            try {
                                instantDoCtrl.setSelectedDevice(new DeviceInformation("PCI-1761,BID#0"));
                            } catch (DaqException e) {
                                logger.error("Device error");
                                e.printStackTrace();
                            }
                            instantDoCtrl.LoadProfile("C:\\1.xml");
                            //  byte[] portMask = instantDoCtrl.getFeatures().getDoDataMask();
                            byte a=0x01;
                            instantDoCtrl.Write(0, a);
                            System.out.println("关闭报警");
                            return;
                        }
                        System.out.println("不处理");
                        return;
                    }
*/
                    if (!socketIOClient.getNamespace().getName().equals(messageInfo.getNameSpace())) {
                        logger.warn("nameSpace is error");
                        return;
                    }
                    if (messageInfo.getMsgContent() < 1 || messageInfo.getMsgContent() > 67) {
                        logger.warn("content is error");
                        return;
                    }

                    OrderService orderService = (OrderService) SpringContextUtils.getBean("orderService");
                    if (messageInfo.getMsgContent() <= 64) {
                        orderService.updateOrdersNum(messageInfo);
                    } else {
                        orderService.updateOrdersReal(messageInfo);

                    }
                }
            });
            socketIONamespace.addEventListener("eventCan", CanProperty.class, new DataListener<CanProperty>() {
                @Override
                public void onData(SocketIOClient socketIOClient, CanProperty canProperty, AckRequest ackRequest) {
                    logger.info("canProperty:" + canProperty.toString());
                    OrderService orderService = (OrderService) SpringContextUtils.getBean("orderService");
                  //  logger.info(ByteBufUtil.prettyHexDump(allCanPropertyByte.getBuf()));
                }
            });
        }
        return server;
    }

    @Bean
    public NioEventLoopGroup nioEventLoopGroup() {
        return new NioEventLoopGroup();
    }
    /*@Bean
    public InstantDoCtrl instantDoCtrl() {
        return new InstantDoCtrl();
    }*/
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}
