package com.victor2022.spider.utils;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;


/**
 * @author: victor2022
 * @date: 2022/08/09  下午9:03
 * @description: HTTP工具类
 */
public class HttpUtils {

    public static String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36";

    public static HttpResponse httpGet(String url){
        HttpGet httpGet = new HttpGet(url);
        // 设置请求头
        httpGet.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.TEXT_HTML.toString());
        httpGet.setHeader(HttpHeaders.USER_AGENT, USER_AGENT);
        // 开始请求
        CloseableHttpResponse response;
        try {
             response = HttpClients.createDefault().execute(httpGet);
        } catch (IOException e) {
            return null;
        }
        return response;
    }
}
