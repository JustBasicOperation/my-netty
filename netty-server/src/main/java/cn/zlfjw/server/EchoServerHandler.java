package cn.zlfjw.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @description: EchoServerHandler
 * @author zhaolinfeng3
 * @date 2020-04-15 16:34
 */
@ChannelHandler.Sharable //1.标识这类的实例之间可以在channel里面共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        ByteBuf in = (ByteBuf) msg;
        //2.日志消息输出到控制台
        System.out.println("server received:" + in.toString(CharsetUtil.UTF_8));
        //3.将接收到的数据返回给发送者，此时还没有冲刷数据
        ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        //4.冲刷所有待审消息到远程节点
      ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
              .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        //5.打印异常堆栈跟踪
        cause.printStackTrace();
        //6.关闭通道
        ctx.close();
    }
}
