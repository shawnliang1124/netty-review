package com.shawnliang.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/2
 */
public class TimeServer {

    /**
     * 绑定端口
     */
    public void bind(int port) throws InterruptedException {
        // 配置nio的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());

            // 绑定端口
            ChannelFuture f = b.bind(port).sync();

            System.out.println("启动成功");
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
            System.out.println("服务端关闭");
        } finally {
            // 优雅退出
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * handler
     */
    private static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new TimeServerHandler());
        }
    }

    /**
     * main 函数启动
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        int port = 8080;

        new TimeServer().bind(port);
    }
}
