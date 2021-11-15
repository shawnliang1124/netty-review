package com.shawnliang.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;

/**
 * Description : UDP CLIENT端，会向本网段所有的主机广播请求消息  .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/15
 */
public class MyUdpClient {

    public void run(int port) throws InterruptedException {
        NioEventLoopGroup work = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(work)
                    .channel(NioDatagramChannel.class)
                    // 设置广播模式
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpClientHandler());

            Channel channel = b.bind(0).sync().channel();
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("client 查询", CharsetUtil.UTF_8)
                            , new InetSocketAddress("255.255.255.255", port))).sync();

            if (!channel.closeFuture().await(10 * 1000)) {
                System.out.println("查询超时");
            }
        } finally {
            work.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyUdpClient myUdpClient = new MyUdpClient();
        myUdpClient.run(8085);
    }

}
