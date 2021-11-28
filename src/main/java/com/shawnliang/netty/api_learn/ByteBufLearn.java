package com.shawnliang.netty.api_learn;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/11/24
 */
public class ByteBufLearn {

    public static void main(String[] args) {

        // 池化的JVM内存
        PooledByteBufAllocator pooledByteBufAllocator = new PooledByteBufAllocator();
        ByteBuf poolJvmBf = pooledByteBufAllocator.heapBuffer();


        // 池化的直接内存
        PooledByteBufAllocator directPoolAllocator = new PooledByteBufAllocator(true);
        ByteBuf directByteBuf = directPoolAllocator.directBuffer();

        // 非池化的JVM内存
        UnpooledByteBufAllocator unpooledByteBufAllocator = new UnpooledByteBufAllocator(false);
        ByteBuf jvmUnpooledBuffer = unpooledByteBufAllocator.heapBuffer();

        // 非池化的直接内存
        UnpooledByteBufAllocator unpooledByteBufAllocator2 = new UnpooledByteBufAllocator(true);
        ByteBuf dircetUnpooledBuffer = unpooledByteBufAllocator2.directBuffer();



    }

}
