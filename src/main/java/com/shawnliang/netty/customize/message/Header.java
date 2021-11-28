package com.shawnliang.netty.customize.message;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/17
 */
@Data
public final class Header {

    private int crcCode = 0xabef0101;

    private int length;

    private long sessionID;

    /**
     * 消息类型
     */
    private byte type;

    /**
     * 消息优先级
     */
    private byte priority;

    private Map<String, Object> attachment = new HashMap<>();



}
