package com.shawnliang.netty.delimiter;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/8
 */
public class DelimiterTest {

    public static void main(String[] args) throws Exception{


        Thread.sleep(2000);

        DelimiterBasedFrameDecoderClient delimiterBasedFrameDecoderClient = new DelimiterBasedFrameDecoderClient();
        delimiterBasedFrameDecoderClient.connet("127.0.0.1", 8080);
        System.out.println("客户端连接成功");
    }

}
