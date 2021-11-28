package com.shawnliang.netty.customize.coder;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/17
 */
public class MarshallingEncoder {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;

    public MarshallingEncoder() {
        MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        try {
            factory.createMarshaller(new MarshallingConfiguration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void encode(Object msg, ByteBuf out) throws IOException {
        int length = out.writerIndex();

    }

}
