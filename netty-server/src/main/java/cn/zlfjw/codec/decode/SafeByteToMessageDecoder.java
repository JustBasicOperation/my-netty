package cn.zlfjw.codec.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

public class SafeByteToMessageDecoder extends ByteToMessageCodec {
    private static final int MAX_FRAME_SIZE = 1024;
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
        int i = in.readableBytes();
        //如果处理的帧长度太长，忽略所有的可读字节，并抛出异常
        if(i > MAX_FRAME_SIZE){
            in.skipBytes(i);
            throw new TooLongFrameException("frame too big!");
        }
    }
}
