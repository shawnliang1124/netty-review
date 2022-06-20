package com.shawnliang.netty.protostuff.client;

import com.shawnliang.netty.protostuff.constant.Constant;
import com.shawnliang.netty.protostuff.handler.ClientMsgDecoder;
import com.shawnliang.netty.protostuff.handler.ClientMsgEncoder;
import com.shawnliang.netty.protostuff.handler.SubscribeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/13
 */
public class SubscribeClient {

    /**
     * 连接
     * @param port
     * @param host
     */
    public void connect(int port, String host) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        try {
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_SNDBUF, 65535)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            ByteBuf delimiter = Unpooled.copiedBuffer(Constant.SPLIT_SYMBOL.getBytes());

                            pipeline.addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                            pipeline.addLast(new ClientMsgDecoder());
                            pipeline.addLast(new ClientMsgEncoder());
                            pipeline.addLast(new SubscribeClientHandler());
                        }
                    });

            // 发起异步的连接操作
            ChannelFuture f = b.connect(host, port).sync();

            // 等待客户端链路的关闭
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SubscribeClient subscribeClient = new SubscribeClient();
        subscribeClient.connect(8082, "127.0.0.1");
    }

}
