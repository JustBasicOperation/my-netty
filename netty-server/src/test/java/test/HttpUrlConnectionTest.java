package test;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @Author zhaolinfeng3
 * @Date 2020/5/8 17:34
 */
public class HttpUrlConnectionTest {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(){
                @Override
                public void run(){
                    createConnection();
                }
            }.start();
        }
    }

    public static void createConnection(){
        try {
            URL url = new URL("http://www.baidu.com");
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();
            TimeUnit.MINUTES.sleep(1);//休眠一分钟
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
