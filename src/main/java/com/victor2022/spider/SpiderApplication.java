package com.victor2022.spider;

import com.victor2022.spider.downloader.aria2.Aria2Downloader;
import com.victor2022.spider.infos.DownloadInfo;
import com.victor2022.spider.producer.DownloadInfoProducer;
import com.victor2022.spider.producer.impl.YingAvDownloadInfoProducer;
import com.victor2022.spider.producer.impl.YingAvUrlProducer;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午4:12
 * @description: 爬虫主程序
 */
public class SpiderApplication {

    public static void main(String[] args) {
        // 创建下载信息生成器
        DownloadInfoProducer producer = new YingAvDownloadInfoProducer();
        // 创建下载器
        Aria2Downloader downloader = new Aria2Downloader();
        // 开始下载
        while(producer.hasNext()){
            DownloadInfo info = producer.getNext();
            downloader.download(info);
        }
        // 处理完成
        System.out.println("done");
    }

}