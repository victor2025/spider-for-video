package com.victor2022.spider.utils;

import org.jsoup.internal.StringUtil;

import java.util.*;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午6:59
 * @description:
 */
public class StringUtils {

    private static Map<Character,Character> specialChars;

    public static boolean isEmpty(String str){
        return str==null||StringUtil.isBlank(str)||"[]".equals(str);
    }

    public static String removeSpecialChar(String str){
        if(str==null) return str;
        if(specialChars==null)initSpecialChars();
        StringBuilder sb = new StringBuilder();
        for(char c : str.toCharArray()){
            if (specialChars.containsKey(c)){
                sb.append(specialChars.get(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static void initSpecialChars(){
        specialChars = new HashMap<>();
        specialChars.put('\\','-');
        specialChars.put('/','-');
        specialChars.put(':','：');
        specialChars.put('*','`');
        specialChars.put('?','？');
        specialChars.put('"','\'');
        specialChars.put('<','[');
        specialChars.put('>',']');
        specialChars.put('|','-');
    }

}
