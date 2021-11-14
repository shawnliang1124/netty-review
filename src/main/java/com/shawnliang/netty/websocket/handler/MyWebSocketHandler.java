package com.shawnliang.netty.websocket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import java.util.Date;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/14
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 如果是传统的http接入
        if (msg instanceof FullHttpRequest) {
            dealWithHttp(ctx, (FullHttpRequest)msg);
        }
        // websocket的接入
        else if (msg instanceof WebSocketFrame) {
            dealWithWebSocket(ctx, (WebSocketFrame)msg);
        }

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端处理消息完毕");
        ctx.flush();
    }

    /**
     * 处理http的请求
     * @param ctx
     * @param req
     */
    private void dealWithHttp(ChannelHandlerContext ctx, FullHttpRequest req) {
        // 如果http解码失败，返回http异常
        if (req.decoderResult() == null
                || !req.decoderResult().isSuccess()
                || !"websocket".equals(req.headers().get("Upgrade"))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // 构造握手的返回
        WebSocketServerHandshakerFactory ws = new WebSocketServerHandshakerFactory(
                "ws://localhost:8080/websocket", null, false);
        handshaker = ws.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    /**
     * 处理webSocket的请求
     * @param ctx
     * @param frame
     */
    private void dealWithWebSocket(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
         if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
         if (!(frame instanceof TextWebSocketFrame)) {
             throw new UnsupportedOperationException(String.format("NOT SUPPORT %s", frame.getClass().getName()));
         }

         String text = ((TextWebSocketFrame) frame).text();
         ctx.channel().write(new TextWebSocketFrame(text + ", 欢迎使用Netty的WebSocket服务端，现在时刻 ： "
         + new Date(System.currentTimeMillis()).toString()));
    }

    /**
     * 返回http的响应体
     * @param ctx
     * @param req
     * @param resp
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse resp) {
        if(resp.status().code() != 200) {
            ByteBuf byteBuf = Unpooled.copiedBuffer(resp.status().toString(), CharsetUtil.UTF_8);
            resp.content().writeBytes(byteBuf);
            byteBuf.release();
        }

        // 非keepAlive连接，需要关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(resp);
        if (!req.headers().contains("keep-alive")) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
