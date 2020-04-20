package cn.zlfjw.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @description: PlainOioServer，阻塞式服务端的写法，可以正常工作
 * 但是在大连接数的情况下，如果有一个客户端连接超时，服务端就必须等待，响应就会严重延迟
 * @author zhaolinfeng3
 * @date 2020-04-16 10:18 2020-04-17 14:08
 */
public class PlainOioServer {
    /**
     * server方法
     * @param port port
     */
    public void server(int port){
        try {
            final ServerSocket serverSocket = new ServerSocket(port);
            for (;;){
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        OutputStream outputStream;
                        try {
                            outputStream = clientSocket.getOutputStream();
                            outputStream.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));                            //4
                            outputStream.flush();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
