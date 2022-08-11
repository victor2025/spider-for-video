package com.victor2022.spider.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;

/**
 * @author: victor2022
 * @date: 2022/08/10  上午9:23
 * @description:
 */
@Slf4j
public class FileUtils {

    public static Properties loadProperties(String path) {
        File file = createIfNotExist(path);
        Properties properties = new Properties();
        try {
            // 读取配置文件
            properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    /**
     * @param prop:
     * @param path:
     * @return: boolean
     * @author: victor2022
     * @date: 2022/8/10 上午9:31
     * @description: 存储配置文件
     */
    public static boolean saveProperties(Properties prop, String path){
        File file = createIfNotExist(path);
        try {
            prop.store(new FileWriter(file),"Record file for spider");
        } catch (Exception e) {
            log.error("Properties save failed!");
            return false;
        }
        return true;
    }

    public static File createIfNotExist(String path){
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

}
