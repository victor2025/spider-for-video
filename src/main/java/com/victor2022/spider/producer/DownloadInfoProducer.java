package com.victor2022.spider.producer;

import com.victor2022.spider.infos.DownloadInfo;
import com.victor2022.spider.parser.DownloadInfoParser;
import com.victor2022.spider.props.PropertiesHandler;
import lombok.Data;

import java.util.Properties;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午7:49
 * @description:
 */
public abstract class DownloadInfoProducer {

    private static final String KEY_PATH = "path";
    private static final String KEY_TYPE = "type";

    protected String basePath = "{HOME}/下载/aria2";
    protected String type = ".mp4";
    protected UrlProducer urlProducer;
    protected DownloadInfoParser parser;
    protected Properties prop;

    protected abstract String getUrl();
    protected abstract String getId();
    protected abstract String getSuffix();
    protected abstract String getPath();
    protected abstract String getMark();

    public DownloadInfoProducer(){
        this.prop = PropertiesHandler.getProperties();
        if(prop.containsKey(KEY_PATH))this.basePath = prop.getProperty(KEY_PATH);
        if(prop.containsKey(KEY_TYPE))this.type = prop.getProperty(KEY_TYPE);
    }

    public DownloadInfo getNext(){
        DownloadInfo info = new DownloadInfo()
                .setPath(getPath())
                .setUrl(getUrl())
                .setId(getId())
                .setSuffix(getSuffix())
                .setMark(getMark());
        // 解析当前配置数据
        return parser.parse(info);
    }

    public boolean hasNext(){
        return urlProducer.hasNextUrl();
    }

}
