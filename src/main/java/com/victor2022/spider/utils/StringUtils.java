package com.victor2022.spider.utils;

import org.jsoup.internal.StringUtil;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午6:59
 * @description:
 */
public class StringUtils {

    public static boolean isEmpty(String str){
        return str==null||StringUtil.isBlank(str)||"[]".equals(str);
    }

}
