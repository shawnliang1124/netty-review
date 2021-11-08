package com.shawnliang.netty.delimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Description : Delimiter分隔符处理器的客户端开发  .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/8
 */
public class DelimiterBasedFrameDecoderClient {

    /**
     * 客户端连接事件
     * @param port
     * @throws InterruptedException
     */
    public void connet(String host, int port) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        try {
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            ch.pipeline().addLast(
                                    new DelimiterBasedFrameDecoder(1024, delimiter));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoderClientHandler());
                        }
                    });

            // 绑定端口
            ChannelFuture f = b.connect(host, port).sync();

            // 等待客户端关闭操作
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DelimiterBasedFrameDecoderClient delimiterBasedFrameDecoderClient = new DelimiterBasedFrameDecoderClient();
        delimiterBasedFrameDecoderClient.connet("127.0.0.1", 8080);
    }

}
