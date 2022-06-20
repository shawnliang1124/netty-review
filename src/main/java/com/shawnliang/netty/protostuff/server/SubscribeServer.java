package com.shawnliang.netty.protostuff.server;

import com.shawnliang.netty.protostuff.constant.Constant;
import com.shawnliang.netty.protostuff.handler.ServerMsgDecoder;
import com.shawnliang.netty.protostuff.handler.ServerMsgEncoder;
import com.shawnliang.netty.protostuff.handler.SubscribeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Description : PubStuff的使用  .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/13
 */
public class SubscribeServer {

    private NioEventLoopGroup bossGroup;

    private NioEventLoopGroup workGroup;

    private ServerBootstrap b;

    public SubscribeServer() {
        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup();
        b = new ServerBootstrap();
    }

    public void bind(int port) throws InterruptedException {
        try {
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, Integer.MAX_VALUE)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            ByteBuf delimiter = Unpooled.copiedBuffer(Constant.SPLIT_SYMBOL.getBytes());

                            // 分隔符处理器，防止TCP粘包拆包
                            pipeline.addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, delimiter));

                            // 消息的序列化反序列化
                            pipeline.addLast(new ServerMsgDecoder());
                            pipeline.addLast(new ServerMsgEncoder());

                            pipeline.addLast(new SubscribeServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SubscribeServer subscribeServer = new SubscribeServer();
        subscribeServer.bind(8082);
    }

}
