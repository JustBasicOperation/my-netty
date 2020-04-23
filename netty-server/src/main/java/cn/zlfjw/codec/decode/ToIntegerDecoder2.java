package cn.zlfjw.codec.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ToIntegerDecoder2 extends ReplayingDecoder {
    //1.实现继承自 ReplayingDecoder 用于将字节解码为消息
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //2.实现继承自 ReplayingDecoder 用于将字节解码为消息
        out.add(in.readInt());
    }
}
