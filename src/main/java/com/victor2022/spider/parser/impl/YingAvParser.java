package com.victor2022.spider.parser.impl;

import com.alibaba.fastjson.JSONObject;
import com.victor2022.spider.infos.DownloadInfo;
import com.victor2022.spider.parser.DownloadInfoParser;
import com.victor2022.spider.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午8:49
 * @description:
 */
@Slf4j
public class YingAvParser implements DownloadInfoParser {

    private String prefix = "https://www.yingav20.com/videos/";
    private String suffix = "/0/";
    private static String INFO_FILE_NAME = "detail.txt";

    @Override
    public DownloadInfo parse(DownloadInfo info) {
        String url = getUrl(info.getId());
        Map<String, String> paraMap = new HashMap<>();
        // 记录id
        paraMap.put("id",info.getId());
        Document doc = null;
        try {
            // 解析数据
            doc = Jsoup.parse(new URL(url), 60000);
        } catch (IOException e) {
            //重试一遍
            try {
                doc = Jsoup.parse(new URL(url), 60000);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(doc!=null){
            // 获取文件名
            getTitle(doc, paraMap);
            // 获取分类
            getVideoInfo(doc, paraMap);
        }
        // 添加到下载信息中
        String path = info.getPath()+"/"+paraMap.getOrDefault("分类","default")+"/"+info.getId()+"-"+paraMap.getOrDefault("title",info.getId());
        info.setPath(path);
        info.setFilename(paraMap.getOrDefault("title",info.getId())+info.getSuffix());
        // 写入细节
        writeDetail(path,paraMap);
        return info;
    }

    /**
     * @param path:
     * @param paraMap:
     * @return: void
     * @author: victor2022
     * @date: 2022/8/9 下午10:23
     * @description: 将detail存入本地
     */
    private void writeDetail(String path, Map<String, String> paraMap) {
        String fullPath = path+"/"+INFO_FILE_NAME;
        FileOutputStream os = null;
        try  {
            // 创建文件夹
            File dir = new File(path);
            dir.mkdirs();
            // 创建文件
            File file = new File(fullPath);
            file.createNewFile();
            // 写入数据
            String json = JSONObject.toJSONString(paraMap, true);
            os = new FileOutputStream(file);
            os.write(json.getBytes(StandardCharsets.UTF_8));
            log.info("Detail has been wrote in: "+fullPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void getVideoInfo(Document doc, Map<String,String> map) {
        String duration = doc.getElementsByClass("details_list").get(0).getElementsByClass("item").get(0).child(0).text();
        // 获取分类信息
        Elements detailItems = doc.getElementsByClass("details_items").get(0).getElementsByClass("col");
        for(Element ele : detailItems){
            String key = ele.getElementsByTag("span").text();
            String value = ele.getElementsByClass("wrap").text();
            key = key.replaceAll(":","");
            map.put(key,value);
        }
        if(map.get("分类")==null){
            map.put("分类","default");
        }
    }

    /**
     * @param doc:
     * @return: java.lang.String
     * @author: victor2022
     * @date: 2022/8/9 下午9:31
     * @description: 获取文件名
     */
    private void getTitle(Document doc, Map<String,String> map){
        // 开始解析
        String title = doc.getElementsByTag("title").get(0).text();
        title = StringUtils.removeSpecialChar(title);
        if(title!=null){
            map.put("title",title);
        }
    }

    private String getUrl(String id){
        return prefix+id+suffix;
    }
}
