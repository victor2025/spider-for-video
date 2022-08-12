package com.victor2022.spider.downloader.aria2;

import com.victor2022.spider.downloader.AbstractDownloader;
import com.victor2022.spider.infos.DownloadInfo;
import com.victor2022.spider.props.RecordHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午5:11
 * @description: 基于aria2的下载器
 */
@Slf4j
public class Aria2Downloader extends AbstractDownloader {

    private static final String KEY_ACTIVE_NUM = "numActive";
    private static final String KEY_WAITING_NUM = "numWaiting";
    private static final String KEY_CNT = "cnt";
    private static final String KEY_MARK = "mark";
    private static final String KEY_RPC_ADDRESS = "rpc-address";
    private static final String KEY_MAX_ALIVE = "max-alive";


    // aria2远程调用地址
    private String rpcAddress = "http://localhost:6800/jsonrpc";
    private int maxNumAlive = 10;
    private Aria2Handler handler;
    // 提交总数
    private Properties rec;
    private int totalCnt;

    public Aria2Downloader() {
        this.handler = new Aria2Handler(rpcAddress);
        loadRec();
        loadProp();
    }

    public Aria2Downloader(String rpcAddress) {
        this.rpcAddress = rpcAddress;
        this.handler = new Aria2Handler(rpcAddress);
        loadRec();
        loadProp();
    }

    /**
     * @return: void
     * @author: victor2022
     * @date: 2022/8/10 上午10:34
     * @description: 读取记录
     */
    private void loadRec(){
        this.rec = RecordHandler.getRec();
        this.totalCnt = Integer.parseInt((String) rec.getOrDefault(KEY_CNT,"0"));
        if(rec.contains(KEY_RPC_ADDRESS))this.rpcAddress = (String) rec.get(KEY_RPC_ADDRESS);
    }

    /**
     * @return: void
     * @author: lihen
     * @date: 2022/8/12 12:36
     * @description: 读取配置
     */
    private void loadProp(){
        this.rpcAddress = (String) prop.getOrDefault(KEY_RPC_ADDRESS,this.rpcAddress);
        this.maxNumAlive = Integer.parseInt((String) prop.getOrDefault(KEY_MAX_ALIVE,String.valueOf(this.maxNumAlive)));
    }

    public void setMaxNumAlive(int num) {
        this.maxNumAlive = num;
    }

    @Override
    public boolean submitTask(DownloadInfo info) {
        int num = getSubmittedTaskNum(handler.fetchGlobalStat());
        // 若大于最大任务数目，则阻塞
        try {
            while (num >= this.maxNumAlive) {
                log.info("Waiting for downloading -- "+num);
                Thread.sleep(1000);
                num = getSubmittedTaskNum(handler.fetchGlobalStat());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 提交下载任务
        Aria2Response resp = doSubmitTask(info);
        if(!"0".equals(resp.getError())){
            return false;
        }
        // 记录已经提交的数据
        if(resp.getResult()!=null) this.submittedTaskId.addAll(resp.getResult());
        return true;
    }

    /**
     * @param info:
     * @return: void
     * @author: lihen
     * @date: 2022/8/10 21:08
     * @description: 实际进行提交的方法
     */
    private Aria2Response doSubmitTask(DownloadInfo info){
        // 处理主任务
        Aria2Response resp = handler.submitTask(info);
        // 处理附加任务，递归处理
        List<DownloadInfo> addOns = info.getAddons();
        for(DownloadInfo i : addOns){
            doSubmitTask(i);
        }
        return resp;
    }

    @Override
    protected void saveState(DownloadInfo info) {
        log.info("File: "+info.getFilename()+" has been submitted to aria2!");
        // 记录
        totalCnt++;
        this.rec.setProperty(KEY_CNT,String.valueOf(totalCnt));
        // 记录标记
        this.rec.setProperty(KEY_MARK,info.getMark());
        // 写入磁盘
        RecordHandler.saveRec();
    }

    /**
     * @param state:
     * @return: int
     * @author: victor2022
     * @date: 2022/8/9 下午8:36
     * @description: 获取当前正在进行和正在等待的任务总数
     */
    private int getSubmittedTaskNum(Aria2Response state){
        Map<String,String> map = state.getResultMapList().get(0);
        int numActive = Integer.parseInt(map.get(KEY_ACTIVE_NUM));
        int numWaiting = Integer.parseInt(map.get(KEY_WAITING_NUM));
        return numActive+numWaiting;
    }
}
