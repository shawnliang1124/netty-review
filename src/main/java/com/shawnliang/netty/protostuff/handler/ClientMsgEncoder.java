package com.shawnliang.netty.protostuff.handler;

import com.shawnliang.netty.protostuff.constant.Constant;
import com.shawnliang.netty.protostuff.model.SubscribeReq;
import com.shawnliang.netty.protostuff.util.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/13
 */
public class ClientMsgEncoder extends MessageToByteEncoder<SubscribeReq> {


    @Override
    protected void encode(ChannelHandlerContext ctx, SubscribeReq msg, ByteBuf out)
            throws Exception {
        byte[] bytes = ProtostuffUtils.serialize(msg);
        byte[] symbolBytes = Constant.SPLIT_SYMBOL.getBytes();

        byte[] total = new byte[bytes.length + symbolBytes.length];

        // 将目标数组内容 复制到total数组中
        System.arraycopy(bytes, 0, total, 0, bytes.length);
        System.arraycopy(symbolBytes, 0 , total, bytes.length, symbolBytes.length);

        // 序列化对象
        out.writeBytes(total);
    }
}
