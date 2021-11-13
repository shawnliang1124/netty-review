package com.shawnliang.netty.protostuff.handler;

import com.shawnliang.netty.protostuff.model.SubscribeReq;
import com.shawnliang.netty.protostuff.util.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * Description :  消息解码器 .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/13
 */
public class ServerMsgDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {

        byte[] body = new byte[in.readableBytes()];
        in.readBytes(body);

        out.add(ProtostuffUtils.deserialize(body, SubscribeReq.class));
    }
}
