package cn.zlfjw.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @description:
 * @Author zhaolinfeng3
 * @Date 2020/4/30 17:14
 */
public class TimeClient {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = null;
        PrintWriter writer = null;
        Socket client = null;
        try {
            client = new Socket("127.0.0.1",8080);
            writer = new PrintWriter(client.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while (true){
                writer.println("GET CURRENT TIME");
                writer.flush();
                String response = reader.readLine();
                System.out.println("Current Time:"+response);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            writer.close();
            reader.close();
            client.close();
        }
    }
}
