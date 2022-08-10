package com.victor2022.spider.props;

import com.victor2022.spider.utils.FileUtils;

import java.util.Properties;

/**
 * @author: victor2022
 * @date: 2022/08/10  上午9:50
 * @description: 记录处理
 */
public class RecordHandler {

    private static final String REC_FILENAME = "spider-rec";

    private static volatile Properties rec;

    public static Properties getRec(){
        if(rec==null){
            synchronized (RecordHandler.class){
                if(rec==null){
                    rec = FileUtils.loadProperties(REC_FILENAME);
                }
            }
        }
        return rec;
    }

    public static boolean saveRec(){
        if(rec==null)return false;
        return FileUtils.saveProperties(rec,REC_FILENAME);
    }

}
