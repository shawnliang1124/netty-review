package com.shawnliang.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/15
 */
public class MyUdpServer {

    public void bind(int port) throws InterruptedException {
        NioEventLoopGroup work = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(work)
                    .channel(NioDatagramChannel.class)
                    // 设置广播模式
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpServerHandler());

            b.bind(port).sync().channel().closeFuture().await();
        } finally {
            work.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyUdpServer myUdpServer = new MyUdpServer();
        myUdpServer.bind(8085);
    }

}
