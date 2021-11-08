package com.shawnliang.netty.delimiter;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/8
 */
public class DelimiterBasedFrameDecoderClientHandler extends ChannelInboundHandlerAdapter {

    private int count;

    private static final String DATA = "welcome, this is netty hello world.$_";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(DATA.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        count ++;
        System.out.println("this is " + count
                + "this receive from server:[" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
