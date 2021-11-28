package com.shawnliang.netty.customize.message;

import lombok.Data;

/**
 * Description :  自定义协议的Netty消息 .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/17
 */
@Data
public class NettyMessage {

    private Header header;

    private Object body;
}
