package com.server.netty.webSockt.exception;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListenerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class SocketExceptionListener extends ExceptionListenerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SocketExceptionListener.class);

    /**
     * 记录事件错误信息
     *
     * @param e
     * @param args
     * @param client
     */
    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
        if (e instanceof IOException) {
            log.info(e.getMessage());
        } else {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 记录断开连接的错误信息
     *
     * @param e
     * @param client
     */
    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
        if (e instanceof IOException) {
            log.info(e.getMessage());
        } else {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 记录连接服务器的错误信息
     *
     * @param e
     * @param client
     */
    @Override
    public void onConnectException(Exception e, SocketIOClient client) {
        if (e instanceof IOException) {
            log.info(e.getMessage());
        } else {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 其他错误信息
     *
     * @param ctx
     * @param e
     * @return
     * @throws Exception
     */
    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        if (e instanceof IOException) {
            log.info(e.getMessage());
        } else {
            log.error(e.getMessage(), e);
        }
        return true;
    }
}
