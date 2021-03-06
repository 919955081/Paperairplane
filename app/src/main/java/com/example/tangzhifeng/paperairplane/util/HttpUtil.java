package com.example.tangzhifeng.paperairplane.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * 作者: tangzhifeng on 2017/2/16.
 * 邮箱: tzfjobmail@gmail.com
 */

public class HttpUtil {


    public interface IHttpCallbackListenet {
        void onFinish(String response);
        void onError(Exception e);
    }

    public static final String API_KEY = "0d2f5fb2161c371b6c78f46caa60604c";

    public static void sendHttpRequest(final String address, final IHttpCallbackListenet httpCallbackListenet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(address);
                    Log.i(TAG, "run: "+url);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    httpCallbackListenet.onFinish(response.toString());
                } catch (Exception e) {
                    Log.i(TAG, "run: " + address);
                    httpCallbackListenet.onError(e);
                } finally {
                    urlConnection.disconnect();
                }
            }
        }).start();
    }

    public static void sendArgRequest(String httpUrl, final String httpArg, final IHttpCallbackListenet callbackListenet) {


        // TODO: 2017/1/4 county code have a bug
        httpUrl = httpUrl + "?" +httpArg;
        final String finalHttpUrl = httpUrl;

//        Log.e(TAG, "sendArgRequest: finalHttpUrl="+finalHttpUrl+"\t CountyCode ==>>\t"+httpArg,new Exception());
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader = null;
                String result = null;
                HttpURLConnection connection = null;
                StringBuffer sbf = new StringBuffer();


                try {
                    URL url = new URL(finalHttpUrl);
                    connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setRequestMethod("GET");
                    // 填入apikey到HTTP header
                    connection.setRequestProperty("apikey", API_KEY);
                    connection.connect();
                    InputStream is = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String strRead = null;
                    while ((strRead = reader.readLine()) != null) {
                        sbf.append(strRead);
                        sbf.append("\r\n");
                    }
                    reader.close();
                    result = sbf.toString();
                } catch (Exception e) {
                    callbackListenet.onError(e);
                } finally {
                    connection.disconnect();
                }
                if(!TextUtils.isEmpty(result))
                    callbackListenet.onFinish(result);
            }
        }).start();

    }

}
