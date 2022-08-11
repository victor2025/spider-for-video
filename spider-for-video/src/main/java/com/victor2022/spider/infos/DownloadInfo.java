package com.victor2022.spider.infos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午4:52
 * @description:
 */
@Data
@Accessors(chain = true)
public class DownloadInfo {

    private String id;
    private String url;
    private String filename;
    private String path;
    private String suffix;
    private String mark;
    private List<DownloadInfo> addons = new ArrayList<>();

    public String[] getUrlArray(){
        return new String[]{url};
    }

}
