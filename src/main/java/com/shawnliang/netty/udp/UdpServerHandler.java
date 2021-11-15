package com.shawnliang.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import java.util.Date;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/15
 */
public class UdpServerHandler extends
        SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        ByteBuf content = packet.content();
        String data = content.toString(CharsetUtil.UTF_8);
        System.out.println("udp server receive data " + data);

        String resp = new Date(System.currentTimeMillis()).toString();
        ByteBuf byteBuf = Unpooled.copiedBuffer(("server response is " + resp), CharsetUtil.UTF_8);

        ctx.writeAndFlush(new DatagramPacket(byteBuf, packet.sender()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
