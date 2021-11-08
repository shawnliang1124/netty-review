package com.shawnliang.netty.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Description :  Delimiter 业务处理器 .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/8
 */
public class DelimiterBasedFrameDecoderServerHandler extends ChannelInboundHandlerAdapter {

    int count = 0;

    /**
     *  处理读事件
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String data = (String) msg;
        count++;
        System.out.println("this is " + count
                + "time receive from client : [" + data + "]");
        data += "$_";
        ByteBuf retMsg = Unpooled.copiedBuffer("this is the server response.$_".getBytes());
        ctx.writeAndFlush(retMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
