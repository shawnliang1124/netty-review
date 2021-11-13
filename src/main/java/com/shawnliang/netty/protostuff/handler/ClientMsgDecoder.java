package com.shawnliang.netty.protostuff.handler;

import com.shawnliang.netty.protostuff.model.SubscribeResp;
import com.shawnliang.netty.protostuff.util.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/13
 */
public class ClientMsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        byte[] body = new byte[in.readableBytes()];
        in.readBytes(body);

        out.add(ProtostuffUtils.deserialize(body, SubscribeResp.class));
    }
}
