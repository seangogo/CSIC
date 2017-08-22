package com.server.netty.udp;

import com.server.netty.can.CanDevice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class UdpRunner implements CommandLineRunner {
    private Log logger = LogFactory.getLog(this.getClass());
    //本地端口
    @Value("${localhost.port}")
    private int port;

    @Override
    public void run(String... args) throws Exception {
        logger.info(">>>>>>>>>>>>>>> udp server start in <<<<<<<<<<<<<" + new Date());
        ExecutorService executorService=Executors.newCachedThreadPool();
        //第一个CAN口工作线程
        executorService.execute(new CanDevice(16,0,0,(byte) 0x01,(byte) 0x01,(byte) 0x1c));
        //第二个CAN口工作线程
        //executorService.execute(new CanDevice(16,0,1,(byte) 0x01,(byte) 0x01,(byte) 0x1c));
        executorService.execute(new Client(2000));
    }

}