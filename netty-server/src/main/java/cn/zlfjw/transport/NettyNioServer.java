package cn.zlfjw.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.nio.charset.Charset;

/**
 * @description: 使用netty实现的NIO通信，从OIO转换为NIO只需要修改一行代码
 * @author zhaolinfeng3
 * @date 2020-04-16 10:18 2020-04-17 17:15
 */
public class NettyNioServer {
    /**
     * server方法
     * @param port port
     */
    public void server(int port) throws InterruptedException {
        final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi/r/n", Charset.forName("UTF-8")));
        //OioEventLoopGroup oioEventLoopGroup = new OutOfMemoryError();
        //从OIO转换为NIO只需要改动一行代码
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventExecutors)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            ChannelFuture res = serverBootstrap.bind().sync();
            res.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            eventExecutors.shutdownGracefully().sync();
        }
    }
}
