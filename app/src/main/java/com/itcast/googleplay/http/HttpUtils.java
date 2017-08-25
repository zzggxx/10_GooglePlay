package com.itcast.googleplay.http;

import com.itcast.googleplay.utils.LogUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * 使用不同的工具请求网络,尽量不抛,会给上部造成一定的影响.可以底层抛出来或者是toast出问题来.让别人知道是谁的问题
 * Created by Lenovo on 2016/7/28.
 */
public class HttpUtils {
    public static String get(String url) {
        //补充?????
/*        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        ResponseBody body = response.body();
        System.out.println(body);*/

        URL murl = null;
        try {
            murl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {

                InputStream inputStream = conn.getInputStream();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                inputStream.close();
//            String tempString = byteArrayOutputStream.toString();
//            if (tempString.contains("charset=utf-8")) {
                return byteArrayOutputStream.toString("utf-8");   //json数据不判断,直接的将其编码成utf-8
//            } else if (tempString.contains("charset=gb2312")) {
//                return byteArrayOutputStream.toString("gb2312");
//            }
            } else {
                LogUtil.i("请求数据", "失败!");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 下载文件，返回流对象,和上边是一模一样的,只是最后的转换方式不同罢了
     *
     * @param url
     * @return
     */
    public static HttpResult download(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        boolean retry = true;//重试
        while (retry) {
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                if (httpResponse != null) {
                    return new HttpResult(httpClient, httpGet, httpResponse);
                }
            } catch (Exception e) {
                retry = false;
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Http返回结果的进一步封装
     *
     * @author Administrator
     */
    public static class HttpResult {
        private HttpClient httpClient;
        private HttpGet httpGet;
        private HttpResponse httpResponse;
        private InputStream inputStream;

        public HttpResult(HttpClient httpClient, HttpGet httpGet,
                          HttpResponse httpResponse) {
            super();
            this.httpClient = httpClient;
            this.httpGet = httpGet;
            this.httpResponse = httpResponse;
        }

        /**
         * 获取状态码
         *
         * @return
         */
        public int getStatusCode() {
            org.apache.http.StatusLine status = httpResponse.getStatusLine();
            return status.getStatusCode();
        }

        /**
         * 获取输入流
         *
         * @return
         */
        public InputStream getInputStream() {
            if (inputStream == null && getStatusCode() < 300) {
                HttpEntity entity = httpResponse.getEntity();
                try {
                    inputStream = entity.getContent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return inputStream;
        }

        /**
         * 关闭链接和流对象
         */
        public void close() {
            if (httpGet != null) {
                httpGet.abort();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭链接
            if (httpClient != null) {
                httpClient.getConnectionManager().closeExpiredConnections();
            }
        }
    }

}
