package com.shawnliang.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/28
 */
public class TimeServerRegisterHandler extends
        ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("触发time server handler register~");
    }
}
