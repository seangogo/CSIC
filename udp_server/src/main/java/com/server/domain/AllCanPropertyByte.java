package com.server.domain;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/6/28.
 */
public class AllCanPropertyByte {
    private byte[] allCanPropertyByte;

    public byte[] getAllCanPropertyByte() {
        return allCanPropertyByte;
    }

    public void setAllCanPropertyByte(byte[] allCanPropertyByte) {
        this.allCanPropertyByte = allCanPropertyByte;
    }
    public ByteBuf getBuf(){
        return Unpooled.copiedBuffer(allCanPropertyByte);
    }

    @Override
    public String toString() {
        return "AllCanPropertyByte{" +
                "allCanPropertyByte=" + Arrays.toString(allCanPropertyByte) +
                '}';
    }
}
