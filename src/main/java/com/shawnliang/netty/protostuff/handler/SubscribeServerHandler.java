package com.shawnliang.netty.protostuff.handler;

import com.shawnliang.netty.protostuff.model.SubscribeReq;
import com.shawnliang.netty.protostuff.model.SubscribeResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/13
 */
public class SubscribeServerHandler extends SimpleChannelInboundHandler<SubscribeReq> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SubscribeReq msg) throws Exception {
        if (msg == null) {
            return;

        }
        // todo 处理业务
        SubscribeResp resp;
        String userName = msg.getUserName();
        if (Objects.equals(userName, "张三")) {
            System.out.println(LocalDateTime.now() + "收到订单" + msg.toString());
            resp = SubscribeResp.builder()
                    .subscribeId(msg.getSubReqId())
                    .desc("下单成功")
                    .result(true).build();
        } else {
            resp = SubscribeResp.builder()
                    .subscribeId(msg.getSubReqId())
                    .desc("下单失败，非法请求")
                    .result(false).build();
            System.out.println(LocalDateTime.now() + "收到订单" + msg.toString());
        }

        Thread.sleep(2000);

        ctx.writeAndFlush(resp);
    }
}
