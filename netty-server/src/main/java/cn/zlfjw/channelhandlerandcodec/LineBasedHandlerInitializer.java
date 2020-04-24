package cn.zlfjw.channelhandlerandcodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                //1.添加一个 LineBasedFrameDecoder 用于提取帧并把数据包转发到下一个管道中的处理程序,在这种情况下就是 FrameHandler
                new LineBasedFrameDecoder(65*1024),
                //2.添加 FrameHandler 用于接收帧
                new FrameHandler()
        );
    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
        //3.每次调用都需要传递一个单帧的内容
        @Override
        public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {  //3
            // Do something with the frame
        }
    }
}
