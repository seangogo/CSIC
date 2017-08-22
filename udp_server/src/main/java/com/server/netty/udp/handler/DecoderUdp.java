package com.server.netty.udp.handler;

import com.server.netty.udp.pojo.UdpRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 解码器将原始网络序变为小端机本地序
 */
public class DecoderUdp extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
        ByteBuf in = datagramPacket.content();
        ByteBuf bf = Unpooled.copyShort(in.readShortLE(), in.readShortLE());//起始符 报文属性 4
        bf.writeInt(in.readIntLE());//报文类型 4
        bf.writeShort(in.readShortLE());//长度 2
        bf.writeInt(in.readIntLE());//时戳 4
        final byte[] sums = new byte[8];//数字量
        in.readBytes(sums);
        bf.writeBytes(sums);
        while (in.readableBytes() > 4) {//模拟量
            bf.writeFloat(in.readIntLE());
        }
        bf.writeShort(in.readShortLE());//效验
        bf.writeShort(in.readShortLE());//结束符
        out.add(new UdpRequest(bf, datagramPacket));
    }
}