package com.victor2022.spider.downloader.aria2;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午5:58
 * @description: aria2下载参数
 */
@Data
@Accessors(chain = true)
public class Aria2Option {
    String dir;
    String out;
    String referer;
}
