package cn.zlfjw.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 阻塞式IO
 * @author zhaolinfeng3
 * @date 2020-04-14 18:12
 */
public class BlockingIO {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(80);
            Socket clientSocket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            String request,response;
            while ((request = bufferedReader.readLine()) != null){
                if("done".equals(request)){
                    break;
                }
            }
            response = request;
            printWriter.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
