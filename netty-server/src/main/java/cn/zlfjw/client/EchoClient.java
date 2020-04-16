package cn.zlfjw.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @description: echo客户端
 * @author zhaolinfeng3
 * @date 2020-04-15 19:54
 */
public class EchoClient {
    /**
     * 主机ip
     */
    private final String host;
    /**
     * 端口
     */
    private final int port;

    /**
     *
     * @param host host
     * @param port port
     */
    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * start方法
     */
    public void start() throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host,port))
                .handler(new ChannelInitializer<SocketChannel>(){

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                });

            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    /**
     * main方法
     * @param args args
     */
    public static void main(String[] args) {
        final String host = "127.0.0.1";
        final int port = 8081;
        try {
            new EchoClient(host,port).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
