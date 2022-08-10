package com.victor2022.spider.producer.impl;

import com.victor2022.spider.producer.IteratorUrlProducer;
import lombok.Getter;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午4:33
 * @description: YINGAV的url产生器
 * 示例:
 * https://www.yingav20.com/get_file/1/25d7ad001a65119048c6abac5e32a5dfc217d0f194/11000/11493/11493.mp4
 */
@Getter
public class YingAvUrlProducer extends IteratorUrlProducer {

    public YingAvUrlProducer(){
        super();
    }

    @Override
    protected String processPrefix() {
        String group;
        if(this.ptr>1000){
            // 以1000为单位计算分割位置
            group = String.valueOf(ptr/1000*1000);
        }else{
            group="0";
        }
        return this.prefix+group+"/";
    }

    @Override
    protected String processIterator() {
        return String.valueOf(this.ptr);
    }

    @Override
    protected String processSuffix() {
        return this.suffix;
    }

    @Override
    protected String combine(String p, String i, String s) {
        return p+i+"/"+i+s;
    }

    @Override
    protected String postProcessUrl(String url) {
        return url;
    }

    @Override
    public String getId() {
        return String.valueOf(this.ptr-1);
    }
}
