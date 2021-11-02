package com.shawnliang.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/2
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);

        String body = new String(req, StandardCharsets.UTF_8);
        System.out.println("the content is " + body);

        String time = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                new Date().toString() : "BAD ORDER";

        // 返回响应体, 并且将待发送的消息放到缓冲数组中
        ByteBuf resp = Unpooled.copiedBuffer(time.getBytes());
        ctx.write(resp);

        // 将缓冲数组中的数组发送到SocketChannel
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }




}
