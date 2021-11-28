package com.shawnliang.netty.customize.coder;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;
import io.netty.buffer.ByteBuf;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/22
 */
public class JsonEncoder {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    JSONWriter jsonWriter;

    public JsonEncoder() throws UnsupportedEncodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        jsonWriter = new JSONWriter(new OutputStreamWriter(out, "UTF-8"));
    }


    protected void encoder(Object msg, ByteBuf out) {
        // 获得buffer的写入位置
        int index = out.writerIndex();
        // 将固定的字节数组写入bytebuf中， 并且writeIndex索引前进4位
        out.writeBytes(LENGTH_PLACEHOLDER);

        jsonWriter.writeObject(JSONObject.toJSONString(msg));

        // 此时的writeIndex已经是从index + 4的位置了
        out.setInt(index, 1000 - index -4);

    }

}
