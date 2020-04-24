package cn.zlfjw.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class Server {

    //引导服务端
    public void start(){
        NioEventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap(); //1
        bootstrap.group(group) //2
                .channel(NioServerSocketChannel.class) //3
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() { //4
                                  @Override
                                  protected void channelRead0(ChannelHandlerContext ctx,
                                                              ByteBuf byteBuf) throws Exception {
                                      System.out.println("Reveived data");
                                      byteBuf.clear();
                                  }
                              }
                );
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080)); //5
        future.addListener(new ChannelFutureListener() {
                               @Override
                               public void operationComplete(ChannelFuture channelFuture)
                                       throws Exception {
                                   if (channelFuture.isSuccess()) {
                                       System.out.println("Server bound");
                                   } else {
                                       System.err.println("Bound attempt failed");
                                       channelFuture.cause().printStackTrace();
                                   }
                               }
                           }
        );
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
