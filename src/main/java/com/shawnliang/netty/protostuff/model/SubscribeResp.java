package com.shawnliang.netty.protostuff.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/13
 */
@Data
@Builder
@ToString
public class SubscribeResp {

    private Integer subscribeId;

    private String desc;

    private Boolean result;
}
