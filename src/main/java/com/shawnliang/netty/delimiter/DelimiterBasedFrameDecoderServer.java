package com.shawnliang.netty.delimiter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Description : 消息换行符的服务端开发 .
 *               以 “$s”为标记位置进行换行的开发
 *
 * @author : Phoebe
 * @date : Created in 2021/11/8
 */
public class DelimiterBasedFrameDecoderServer {

    public void bind(int port) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            ch.pipeline().addLast(
                                    new DelimiterBasedFrameDecoder(1024, delimiter));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoderServerHandler());
                        }
                    });

            // 绑定端口
            ChannelFuture f = b.bind(port).sync();

            // 等待服务端的监听端口关闭
            f.channel().closeFuture().sync();

        } finally {
            // 优雅退出
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DelimiterBasedFrameDecoderServer server = new DelimiterBasedFrameDecoderServer();
        server.bind(8080);
        System.out.println("服务端启动完毕");
    }

}
