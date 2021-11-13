package com.shawnliang.netty.protostuff.model;

import java.util.List;
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
public class SubscribeReq {

    /**
     * 订阅的ID
     */
    private Integer subReqId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 收货地址
     */
    private List<String> addressList;

}
