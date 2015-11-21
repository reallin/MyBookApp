package com.example.linxj.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;

/**
 * Created by linxj on 2015/9/25.
 */
public class NetAssistant {
    String url;
    StringBuilder sb;
    public NetAssistant(String url) {
        this.url = url;
    }

    public String getBookInfoFromNet() {

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(
                    this.url)
                    .openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() != 200) {

                return " ";
            } else {
                GZIPInputStream gzis = (GZIPInputStream) conn.getContent();
                InputStreamReader reader = new InputStreamReader(gzis);
                BufferedReader in = new BufferedReader(reader);
               sb = new StringBuilder();
                String readed;
                while ((readed = in.readLine()) != null) {
                    sb.append(readed);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }
}
