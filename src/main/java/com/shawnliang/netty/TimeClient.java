package com.shawnliang.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/2
 */
public class TimeClient {

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
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeClientHandler());
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
        int port = 9000;

        new TimeClient().connect(port, "127.0.0.1");
    }

}
