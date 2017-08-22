package com.server.netty.udp.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;

/**
 * 解码器对象
 */
public class UdpRequest {
    private ByteBuf msg;
    private DatagramPacket pack;
    public UdpRequest(ByteBuf msg, DatagramPacket pack) {
        super();
        this.msg = msg;
        this.pack = pack;
    }

    public ByteBuf getMsg() {
        return msg;
    }

    public void setMsg(ByteBuf msg) {
        this.msg = msg;
    }

    public DatagramPacket getPack() {
        return pack;
    }

    public void setPack(DatagramPacket pack) {
        this.pack = pack;
    }
}