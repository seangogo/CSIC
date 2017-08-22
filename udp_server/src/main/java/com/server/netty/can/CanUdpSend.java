package com.server.netty.can;

import com.server.config.UdpDateContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;

import static com.server.config.UdpDateContext.canHashMap;

/**
 * Created by Administrator on 2017/7/10.
 */
public class CanUdpSend implements Runnable {
    private UdpDateContext.UdpPcEnum[] udpPcEna;
    private ChannelHandlerContext context;

    public CanUdpSend() {
    }

    public CanUdpSend(UdpDateContext.UdpPcEnum[] udpPcEna, ChannelHandlerContext context) {
        this.udpPcEna = udpPcEna;
        this.context = context;
    }

    @Override
    public void run() {
        for (; ; ) {
            for (int sum = 0; sum < udpPcEna.length; sum++) {
                ByteBuf byteBuf = Unpooled.copyShort(0x55aa);
                for (int i=0;i<canHashMap.values().size();i++) {
                byteBuf.writeBytes((byte[]) canHashMap.values().toArray()[i]);
                }
                context.writeAndFlush(new DatagramPacket(byteBuf, new InetSocketAddress(udpPcEna[sum].getIp(), udpPcEna[sum].getPort())));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
