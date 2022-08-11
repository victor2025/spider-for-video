package com.victor2022.spider.downloader.aria2;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午6:36
 * @description:
 */
@Data
@NoArgsConstructor
@ToString
public class Aria2Response {

    private String id;
    private String jsonrpc;
    private List<String> result = new ArrayList<>();
    private String error = "0";
    private List<Map<String,String>> resultMapList = new ArrayList<>();
}
