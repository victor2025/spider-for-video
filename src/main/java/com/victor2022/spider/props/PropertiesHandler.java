package com.victor2022.spider.props;

import com.victor2022.spider.utils.FileUtils;

import java.util.Properties;

/**
 * @author: victor2022
 * @date: 2022/08/10  上午9:48
 * @description: 配置处理
 */
public class PropertiesHandler {

    private static final String PROP_FILENAME = "spider-prop";

    private static volatile Properties prop;

    public static Properties getProperties(){
        if (prop==null){
            synchronized (PropertiesHandler.class){
                if(prop==null){
                    prop = FileUtils.loadProperties(PROP_FILENAME);
                }
            }
        }
        return prop;
    }

    public static boolean saveProperties(){
        if(prop==null)return false;
        return FileUtils.saveProperties(prop,PROP_FILENAME);
    }

}
