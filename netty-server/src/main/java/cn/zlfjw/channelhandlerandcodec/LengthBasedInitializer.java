package cn.zlfjw.channelhandlerandcodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 解码基于长度的协议
 */
public class LengthBasedInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
                //1.添加一个 LengthFieldBasedFrameDecoder ,用于提取基于帧编码长度8个字节的帧。
                new LengthFieldBasedFrameDecoder(65 * 1024, 0, 8)); //1
        //2.添加一个 FrameHandler 用来处理每帧
        pipeline.addLast(new FrameHandler()); //2
    }

    public static final class FrameHandler
            extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx,
                                 ByteBuf msg) throws Exception {
            //3.处理帧数据
            // Do something with the frame //3
        }
    }
}
