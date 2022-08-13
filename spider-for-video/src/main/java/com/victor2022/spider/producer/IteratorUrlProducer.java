package com.victor2022.spider.producer;

import com.victor2022.spider.props.PropertiesHandler;
import com.victor2022.spider.props.RecordHandler;
import lombok.Data;

import java.util.Properties;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午4:25
 * @description: 基于迭代方式的url产生器
 */
@Data
public abstract class IteratorUrlProducer implements UrlProducer {


    protected static final String KEY_PRE = "prefix";
    protected static final String KEY_SUF = "suffix";
    protected static final String KEY_START = "start";
    protected static final String KEY_END = "end";
    protected static final String KEY_PTR = "mark";
    // url前缀
    protected String prefix;
    // url后缀
    protected String suffix;
    // 迭代起始位置
    protected int start = 1000;
    // 迭代指针
    protected int ptr;
    // 迭代终止位置
    protected int end = 1100;

    protected Properties prop;
    protected Properties rec;

    protected IteratorUrlProducer() {
        // 首先获取配置文件
        this.prop = PropertiesHandler.getProperties();
        this.rec = RecordHandler.getRec();
        // 设置属性
        if (prop.containsKey(KEY_PRE)) this.prefix = (String) prop.get(KEY_PRE);
        if (prop.containsKey(KEY_SUF)) this.suffix = (String) prop.get(KEY_SUF);
        if (prop.containsKey(KEY_START)) this.start = Integer.parseInt((String) prop.get(KEY_START));
        if (prop.containsKey(KEY_END)) this.end = Integer.parseInt((String) prop.get(KEY_END));
        // 设置指针
        if(rec.containsKey(KEY_PTR)){
            // 若有指针，就从下一个开始
            this.ptr = Integer.parseInt((String) rec.get(KEY_PTR))+1;
        }else{
            // 否则从start开始
            this.ptr = this.start;
        }
    }

    protected abstract String processPrefix();

    protected abstract String processIterator();

    protected abstract String processSuffix();

    protected abstract String combine(String p, String i, String s);

    protected abstract String postProcessUrl(String url);

    @Override
    public String getNextUrl() {
        // 前缀+迭代部分+后缀
        String url = combine(processPrefix(), processIterator(), processSuffix());
        // 后置处理
        url = postProcessUrl(url);
        ptr++;
        return url;
    }

    @Override
    public boolean hasNextUrl() {
        return ptr <= end;
    }
}
