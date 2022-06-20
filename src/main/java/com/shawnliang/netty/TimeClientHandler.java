package com.shawnliang.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/2
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    ByteBuf firstMsg = null;

    public TimeClientHandler() {
        byte[] req = "QUERY TIME ORDER".getBytes();
        firstMsg = Unpooled.buffer(req.length);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firstMsg);
        System.out.println(LocalDateTime.now() + "finished");
        ctx.writeAndFlush(firstMsg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf data = (ByteBuf) msg;
        byte[] req = new byte[data.readableBytes()];
        data.readBytes(req);

        String result = new String(req, StandardCharsets.UTF_8);
        System.out.println("now is " + result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 释放资源
        ctx.close();
    }
}
