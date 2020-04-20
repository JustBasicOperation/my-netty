package cn.zlfjw.transport;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @description: PlainNioServer，非阻塞式服务端写法
 * @author zhaolinfeng3
 * @date 2020-04-16 10:18 2020-04-17 14:20
 */
public class PlainNioServer {
    /**
     * server方法
     * @param port port
     */
    public void server(int port){
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            ServerSocket socket = serverChannel.socket();
            socket.bind(new InetSocketAddress(port));
            Selector selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
            for(;;){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if(key.isAcceptable()){
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();

                            SocketChannel client = channel.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_WRITE |
                                    SelectionKey.OP_READ, msg.duplicate());    //7
                            System.out.println("Accepted connection from " + client);
                    }
                    if(key.isWritable()){
                        SocketChannel client =
                                (SocketChannel)key.channel();
                        ByteBuffer buffer =
                                (ByteBuffer)key.attachment();
                        while (buffer.hasRemaining()) {
                            if (client.write(buffer) == 0) {        //9
                                break;
                            }
                        }
                        client.close();
                        }
                    key.cancel();
                    key.channel().close();
                    }
                }
            } catch (ClosedChannelException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
