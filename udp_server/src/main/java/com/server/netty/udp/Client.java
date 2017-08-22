package com.server.netty.udp;

import com.server.netty.udp.handler.DecoderUdp;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 接受udp报文的客户端
 */
class Client implements Runnable {
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private int port;

    Client(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_RCVBUF, 1024 * 2048)
                .option(ChannelOption.SO_BACKLOG, 100)//最大工作线程
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        super.channelActive(ctx);
                    }
                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        final ChannelPipeline pipeline = ch.pipeline();
                        // pipeline.addLast("encoder", new MessageEncoder());//编码器，目前暂时用不到
                        // pipeline.addLast("stdecode", new DelimiterBasedFrameDecoder(1024,
                        // Unpooled.copiedBuffer("#".getBytes())));// 当数据量过大可以配置此项进行拆包粘包
                        pipeline.addLast("decoder", new DecoderUdp());//解码器
                        pipeline.addLast("handler", new ClientHandler());//业务处理拦截器
                        pipeline.addLast("logging", new LoggingHandler());//todo netty日誌配置，生產環境刪除
                    }
                });
        try {
            // 监听端口
            b.bind(port).sync().channel();
        } catch (InterruptedException e) {
            group.shutdownGracefully();
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                group.shutdownGracefully();
            }
        }));
    }
}
