package com.victor2022.spider.downloader;

import com.victor2022.spider.infos.DownloadInfo;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午4:49
 * @description: 下载器
 */
public interface Downloader {

    /**
     * @param info: 下载目标信息
     * @return: boolean
     * @author: victor2022
     * @date: 2022/8/9 下午4:51
     * @description: 发送目标地址
     */
    boolean download(DownloadInfo info);

}
