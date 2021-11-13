package com.shawnliang.netty.protostuff.handler;

import com.shawnliang.netty.protostuff.model.SubscribeReq;
import com.shawnliang.netty.protostuff.model.SubscribeResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/13
 */
public class SubscribeClientHandler extends SimpleChannelInboundHandler<SubscribeResp> {



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        List<String> addressList = new ArrayList<>();
        addressList.add("中关村");
        addressList.add("天安门");

        SubscribeReq req = SubscribeReq.builder()
                .subReqId(1)
                .productName("语文书")
                .userName("张三")
                .addressList(addressList)
                .build();

        SubscribeReq req2 = SubscribeReq.builder()
                .subReqId(2)
                .productName("数学书")
                .userName("李四")
                .addressList(addressList)
                .build();

        ctx.write(req);
        ctx.write(req2);

        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SubscribeResp msg) throws Exception {
        System.out.println("客户端收到了响应体 " + msg.toString());
    }
}
