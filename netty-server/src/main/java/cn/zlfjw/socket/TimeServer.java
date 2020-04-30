package cn.zlfjw.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

/**
 * @description:
 * @Author zhaolinfeng3
 * @Date 2020/4/30 17:06
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("TimeServer Started on 8080...");
            while (true){
                Socket client = serverSocket.accept();
                new Thread(new TimeServerHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            serverSocket.close();
        }
    }
    public static class TimeServerHandler implements Runnable {
        private Socket clientProxy;

        public TimeServerHandler(Socket clientProxy) {
            this.clientProxy = clientProxy;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(clientProxy.getInputStream()));
                writer = new PrintWriter(clientProxy.getOutputStream());
                while (true) {//因为一个client可以发送多次请求，这里的每一次循环，相当于接收处理一次请求
                    String request = reader.readLine();
                    if (!"GET CURRENT TIME".equals(request)) {
                        writer.println("BAD_REQUEST");
                    } else {
                        writer.println(Calendar.getInstance().getTime().toLocaleString());
                    }
                    writer.flush();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    writer.close();
                    reader.close();
                    clientProxy.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
