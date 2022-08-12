package com.victor2022.spider;

import com.victor2022.spider.downloader.Downloader;
import com.victor2022.spider.downloader.aria2.Aria2Downloader;
import com.victor2022.spider.infos.DownloadInfo;
import com.victor2022.spider.producer.DownloadInfoProducer;
import com.victor2022.spider.producer.impl.YingAvDownloadInfoProducer;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午4:12
 * @description: 爬虫主程序
 */
@Slf4j
public class SpiderApplication {

    public static void main(String[] args) {
        log.info("Application is starting...");
        // 创建下载信息生成器
        DownloadInfoProducer producer = new YingAvDownloadInfoProducer();
        // 创建下载器
        Downloader downloader = new Aria2Downloader();
        // 开始下载
        while(producer.hasNext()){
            DownloadInfo info = producer.getNext();
            downloader.download(info);
        }
        // 处理完成
        System.out.println("Press enter to exit...");
        new Scanner(System.in).nextLine();
    }

}