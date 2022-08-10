package com.victor2022.spider.parser;

import com.victor2022.spider.infos.DownloadInfo;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午8:48
 * @description:
 */
public interface DownloadInfoParser {

    public DownloadInfo parse(DownloadInfo info);
}
