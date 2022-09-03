package com.victor2022.spider.producer;

import com.victor2022.spider.infos.DownloadInfo;
import com.victor2022.spider.parser.DownloadInfoParser;
import com.victor2022.spider.props.PropertiesHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Properties;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午7:49
 * @description:
 */
@Slf4j
public abstract class DownloadInfoProducer {

    private static final String KEY_PATH = "path";
    private static final String KEY_TYPE = "type";

    protected String basePath = ".downloads";
    protected String type = ".sfv";
    protected UrlProducer urlProducer;
    protected DownloadInfoParser parser;
    protected Properties prop;

    protected abstract String getUrl();
    protected abstract String getId();
    protected abstract String getSuffix();
    protected abstract String getBasePath();
    protected abstract String getMark();

    public DownloadInfoProducer(){
        this.prop = PropertiesHandler.getProperties();
        if(prop.containsKey(KEY_PATH))this.basePath = prop.getProperty(KEY_PATH);
        if(prop.containsKey(KEY_TYPE))this.type = prop.getProperty(KEY_TYPE);
        log.info("DownloadInfoProducer has been created...");
    }

    public DownloadInfo getNext(){
        DownloadInfo info = new DownloadInfo()
                .setPath(getAbsoluteBasePath())
                .setUrl(getUrl())
                .setId(getId())
                .setSuffix(getSuffix())
                .setMark(getMark());
        // 解析当前配置数据
        return parser.parse(info);
    }

    /**
     * @return: java.lang.String
     * @author: lihen
     * @date: 2022/9/3 12:22
     * @description: 获取绝对路径
     */
    private String getAbsoluteBasePath(){
        String basePath = getBasePath();
        return new File(basePath).getAbsolutePath();
    }

    public boolean hasNext(){
        return urlProducer.hasNextUrl();
    }

}
