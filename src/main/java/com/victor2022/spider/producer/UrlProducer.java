package com.victor2022.spider.producer;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午4:22
 * @description: 目标地址产生器顶层接口
 */
public interface UrlProducer {

    /**
     * @return: java.lang.String
     * @author: victor2022
     * @date: 2022/8/9 下午4:24
     * @description: 获取下一个url
     */
    public String getNextUrl();

    /**
     * @return: boolean
     * @author: victor2022
     * @date: 2022/8/9 下午4:24
     * @description: 判断是否有下一个url
     */
    public boolean hasNextUrl();

    /**
     * @return: boolean
     * @author: victor2022
     * @date: 2022/8/9 下午8:54
     * @description: 获取当前url的id
     */
    public String getId();

}
