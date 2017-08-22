package com.server.netty.udp;


import com.server.config.UdpDateContext;
import com.server.netty.can.CanUdpSend;
import com.server.netty.udp.pojo.UdpRequest;
import com.server.service.OriginalMessageService;
import com.server.utils.SpringContextUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Component("clientHandler")
public class ClientHandler extends SimpleChannelInboundHandler<UdpRequest> {
    public static final Map<String, ByteBuf> byteBufHashMap = new ConcurrentHashMap<>();
    private static Log logger = LogFactory.getLog(ClientHandler.class);
    @Autowired
    protected SpringContextUtils springContextUtils;
    private int index = 0;

    /**
     * 初始化连接通道调用
     *
     * @param ctx 通道
     * @throws Exception 异常
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        ExecutorService executorService=Executors.newCachedThreadPool();
        UdpDateContext.UdpPcEnum[] pcs={UdpDateContext.UdpPcEnum.valueOf("HW_01"),
                UdpDateContext.UdpPcEnum.valueOf("HW_02"),
                UdpDateContext.UdpPcEnum.valueOf("HW_03"),
                UdpDateContext.UdpPcEnum.valueOf("WS_01")
        };
        executorService.execute(new CanUdpSend(pcs,ctx));
    }

    /**
     * 接受报文以后的handler
     *
     * @param ctx            通道
     * @param udpRequest     解码对象
     * @throws Exception     异常
     */
    @Override
    @Async("mySimpleAsync")
    protected void channelRead0(ChannelHandlerContext ctx, UdpRequest udpRequest) {
       // logger.info("start Task:#" + index);
       // long start = System.currentTimeMillis();
        OriginalMessageService originalMessageService = (OriginalMessageService) SpringContextUtils.getBean("originalMessageService");
        //1.保存原始报文,获取客户机
        ByteBuf byteBuf = udpRequest.getMsg();
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
        UdpDateContext.UdpPcEnum udpPcEnum = originalMessageService.checkPc(udpRequest.getPack());
        if (udpPcEnum == null) {
            //非目标IP发送过来的报文
            logger.info("Is not a goal IP sends the packet, please check the IP port");
            return;
        }
        //2.报文内部效验
        if (originalMessageService.validation(udpPcEnum,byteBuf)){
            logger.info("validation not pass");
            return;
        }
        //3.解析并发送报文
        originalMessageService.dataSend(udpPcEnum,byteBuf);
        //long end = System.currentTimeMillis();
        //logger.info("end Task:#" + index + "，time：" + (end - start) + "ms");
        // index++;
    }


    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了
     * 。也就是客户端与服务端建立了通信通道并且可以传输数据
     * udp 并无实际用途
     *
     * @param ctx 管道
     * @throws Exception 异常
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().localAddress().toString() + " channelActive");
        String str = "channelId：" + ctx.channel().id() + " \n" + new Date() + " " + ctx.channel().localAddress();
        logger.info(str);
    }

    /**
     * 从channelGroup中移除，当有客户端退出后，移除channel
     * udp 并无实际用途
     *
     * @param ctx 管道
     * @throws Exception 异常
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().localAddress().toString() + " channelInactive");
    }

    /**
     * 异常处理
     *
     * @param ctx   管道
     * @param cause 异常
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Server side Unexpected exception from downstream.", cause.getCause());
        logger.info(cause.getCause());
        ctx.close();
        cause.printStackTrace();
        //异常重连
        DatagramChannel datagramChannel = (DatagramChannel) ctx.channel();
        Executors.newCachedThreadPool().execute(new Client(datagramChannel.localAddress().getPort()));
    }
}


