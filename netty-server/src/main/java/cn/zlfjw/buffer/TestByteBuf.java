package cn.zlfjw.buffer;


import io.netty.buffer.*;
import io.netty.util.ByteProcessor;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * @description: 测试类
 * @author zhaolinfeng3
 * @date 2020-04-16 10:18 2020-04-17 19:03
 */
public class TestByteBuf {

    @Test
    public void test01() {
        ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hello word", Charset.forName("UTF-8")));
        ByteBufAllocator alloc = buf.alloc();
        int i = buf.readerIndex();
        System.out.println(i);
    }

    /**
     * ByteBuf引用计数器
     */
    @Test
    public void test02(){
        ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hello word", Charset.forName("UTF-8")));
        ByteBufAllocator alloc = buf.alloc();
        ByteBuf buf1 = alloc.directBuffer();
        assert buf1.refCnt() == 1;
        boolean release = buf.release();
        System.out.println(release);
        assert buf.refCnt() == 1;
    }

    /**
     * ByteBuf使用模式之堆缓冲区
     */
    @Test
    public void test03(){
        ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hello word", Charset.forName("UTF-8")));
        ByteBufAllocator alloc = buf.alloc();
        ByteBuf heapBuffer = alloc.heapBuffer();
        //1.检查 ByteBuf 是否有支持数组。
        boolean b = heapBuffer.hasArray();
        System.out.println(b);
        //2.如果有的话，得到引用数组。
        byte[] array = heapBuffer.array();
        //3.计算第一字节的偏移量。
        int offset = heapBuffer.arrayOffset() + heapBuffer.readerIndex();
        System.out.println(offset);
        //4.获取可读的字节数。
        int i = heapBuffer.readableBytes();
        System.out.println(i);
    }

    /**
     * ByteBuf使用模式之直接缓冲区
     */
    @Test
    public void test04(){
        ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hello word", Charset.forName("UTF-8")));
        ByteBufAllocator alloc = buf.alloc();
        ByteBuf directBuf = alloc.directBuffer();
        if (!directBuf.hasArray()) {            //1
            int length = directBuf.readableBytes();//2
            byte[] array = new byte[length];    //3
            directBuf.getBytes(directBuf.readerIndex(), array);        //4
        }
    }

    /**
     * ByteBuf使用模式之复合缓冲区(JDK实现)
     */
    @Test
    public void test05(){
        ByteBuffer header = ByteBuffer.allocate(16);
        ByteBuffer body = ByteBuffer.allocate(32);
        // 使用数组保存消息的各个部分
        ByteBuffer[] message = { header, body };

        // 使用副本来合并这两个部分
        ByteBuffer message2 = ByteBuffer.allocate(
                header.remaining() + body.remaining());
        message2.put(header);
        message2.put(body);
        message2.flip();
    }

    /**
     * ByteBuf使用模式之复合缓冲区(netty实现)
     */
    @Test
    public void test06(){
        ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hello word", Charset.forName("UTF-8")));
        ByteBufAllocator alloc = buf.alloc();
        CompositeByteBuf messageBuf = alloc.compositeBuffer();
        ByteBuf headerBuf = alloc.directBuffer(); // 可以支持或直接
        ByteBuf bodyBuf = alloc.heapBuffer(); // 可以支持或直接
        messageBuf.addComponents(headerBuf, bodyBuf);

        messageBuf.removeComponent(0); // 移除头    //2

        for (int i = 0; i < messageBuf.numComponents(); i++) {                        //3
            System.out.println(messageBuf.component(i).toString());
        }
    }

    /**
     * 遍历缓冲区的可读字节
     */
    @Test
    public void test07(){
        ByteBuf buffer = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hello word", Charset.forName("UTF-8")));
        while (buffer.isReadable()) {
            System.out.println(buffer.readByte());
        }
        System.out.println();
        System.out.println(buffer.readerIndex());
        System.out.println(buffer.writerIndex());
        int anInt = buffer.getInt(11);
        System.out.println(anInt);
    }

    /**
     * 填充随机数到缓冲区中
     */
    @Test
    public void test08(){
        ByteBuf buffer = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hello word", Charset.forName("UTF-8")));
        ByteBufAllocator alloc = buffer.alloc();
        ByteBuf directBuffer = alloc.directBuffer();
        //填充随机整数到缓冲区中
        while (directBuffer.writableBytes() >= 4) {
            directBuffer.writeDouble(Math.random());
        }
//        while (directBuffer.isReadable()) {
//            System.out.println(directBuffer.readByte());
//        }
        System.out.println(directBuffer.forEachByte(ByteProcessor.FIND_ASCII_SPACE));
    }

}
