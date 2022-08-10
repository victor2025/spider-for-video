package com.victor2022.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午9:00
 * @description:
 */
public class TestParseHtml {

    public static void main(String[] args) throws IOException {
        String url = "https://www.yingav20.com/videos/11013/0/";
        Document doc = Jsoup.parse(new URL(url), 10000);
        System.out.println("doen");
    }
}
