package com.victor2022.spider.producer.impl;

import com.victor2022.spider.parser.impl.YingAvParser;
import com.victor2022.spider.producer.DownloadInfoProducer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: victor2022
 * @date: 2022/08/10  上午10:17
 * @description: 针对YingAv的DownloadInfo产生器
 */
@Slf4j
public class YingAvDownloadInfoProducer extends DownloadInfoProducer {

    public YingAvDownloadInfoProducer(){
        super();
        this.urlProducer = new YingAvUrlProducer();
        this.parser = new YingAvParser();
    }

    @Override
    protected String getUrl() {
        return this.urlProducer.getNextUrl();
    }

    @Override
    protected String getId() {
        return this.urlProducer.getId();
    }

    @Override
    protected String getSuffix() {
        return "."+this.type;
    }

    @Override
    protected String getBasePath() {
        return this.basePath;
    }

    @Override
    protected String getMark() {
        return this.urlProducer.getId();
    }
}
