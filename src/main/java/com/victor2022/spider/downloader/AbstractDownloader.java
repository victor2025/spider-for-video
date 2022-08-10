package com.victor2022.spider.downloader;

import com.victor2022.spider.infos.DownloadInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午4:55
 * @description: 下载器模板，定义了下载器的公共行为
 */
@Slf4j
public abstract class AbstractDownloader implements Downloader {

    // 默认队列长度
    private static final int MAX_QUEUE_SIZE = 100;
    protected BlockingQueue<DownloadInfo> tasks;
    protected DownloadInfo lastSubmittedInfo;
    protected List<String> submittedTaskId = new ArrayList<>();
    private boolean isStarted = false;

    public AbstractDownloader() {
        tasks = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);
    }

    public AbstractDownloader(int queueSize) {
        if (queueSize <= 0) throw new RuntimeException("Queue size must higher than zero!");
        tasks = new LinkedBlockingQueue<>(queueSize);
    }

    /**
     * @return: void
     * @author: victor2022
     * @date: 2022/8/9 下午5:08
     * @description: 开启线程处理下载任务
     */
    public void start() {
        Runnable runnable = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                // 循环取数据
                while (!tasks.isEmpty()) {
                    // 取出一个下载任务
                    DownloadInfo info = tasks.take();
                    lastSubmittedInfo = info;
                    // 交给任务下载器处理
                    if(submitTask(info)){
                        saveState(info);
                    }
                }
                // 完成后将开关置为false
                isStarted = false;
            }

        };
        new Thread(runnable).start();
        this.isStarted = true;
    }

    /**
     * @param info:
     * @return: void
     * @author: victor2022
     * @date: 2022/8/9 下午5:09
     * @description: 添加下载任务
     */
    public boolean download(DownloadInfo info) {
        try {
            tasks.put(info);
        } catch (InterruptedException e) {
            log.error("Error occurred while queuing " + info.getFilename() + " !");
            return false;
        }
        log.info("Download Task: " + info.getFilename() + " has been queued...");
        // 开启线程
        if (!isStarted) {
            this.start();
        }
        return true;
    }

    protected abstract boolean submitTask(DownloadInfo info);
    protected abstract void saveState(DownloadInfo info);

}
