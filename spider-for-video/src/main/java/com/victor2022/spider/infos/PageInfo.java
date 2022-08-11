package com.victor2022.spider.infos;

import com.victor2022.spider.consts.Strings;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午4:15
 * @description:
 */
@Data
public class PageInfo {

    private String baseUrl;
    private Map<String,String> infos;

    public PageInfo(String baseUrl){
        this.baseUrl = baseUrl;
        infos = new HashMap<>();
    }

    /**
     * @param key:
     * @return: java.lang.String
     * @author: victor2022
     * @date: 2022/8/9 下午4:17
     * @description: 从map中获取数据
     */
    public String getParam(String key){
        return infos.getOrDefault(key, Strings.DEF_INFO);
    }

    /**
     * @param key:
     * @param val:
     * @return: void
     * @author: victor2022
     * @date: 2022/8/9 下午4:19
     * @description: 存入数据
     */
    public void setParam(String key, String val){
        infos.put(key,val);
    }
}
