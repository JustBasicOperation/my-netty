package cn.zlfjw.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @description: EchoServer
 * @author zhaolinfeng3
 * @date 2020-04-15 17:56
 */
public class EchoServer {
    /**
     * 端口号
     */
    private final int port;

    /**
     * 构造器
     * @param port port
     */
    public EchoServer(int port) {
        this.port = port;
    }

    /**
     * start方法
     */
    public void start() throws InterruptedException {
        //1.创建EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            //2.创建ServerBootstrap
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(group)
                .channel(NioServerSocketChannel.class)//3.指定channel
                .localAddress(new InetSocketAddress(port))//4.指定socket地址选用端口
                .childHandler(new ChannelInitializer<SocketChannel>() {//5.添加 EchoServerHandler 到 Channel 的 ChannelPipeline
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new EchoServerHandler());
                    }
                });

            ChannelFuture sync = serverBootstrap.bind().sync();//6.服务器绑定端口，sync等待关闭
            System.out.println(EchoServer.class.getName() + " started and listen on " + sync.channel().localAddress());
            sync.channel().closeFuture().sync();//7.关闭channel通道，syc等待关闭
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();//8.关闭EventLoopGroup，释放所有资源
        }

    }

    /**
     * main方法
     * @param args args
     */
    public static void main(String[] args) {
        int port = 8081;
        try {
            new EchoServer(port).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
