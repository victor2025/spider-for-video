package com.victor2022.spider.downloader.aria2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.victor2022.spider.infos.DownloadInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author: victor2022
 * @date: 2022/08/09  下午5:17
 * @description:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Slf4j
public class Aria2Handler {
    /**
     * 方法名常量
     */
    public final static String METHOD_TELL_ACTIVE = "aria2.tellActive";
    public final static String METHOD_ADD_URI = "aria2.addUri";
    public final static String METHOD_GET_GLOBAL_STAT = "aria2.fetchGlobalStat";
    public final static String METHOD_TELL_STOPPED = "aria2.tellStopped";
    public final static String METHOD_TELL_WAITING = "aria2.tellWaiting";
    public final static String METHOD_GLOBAL_STAT = "aria2.getGlobalStat";
    public final static String METHOD_REMOVE_DOWNLOAD_RESULT = "aria2.removeDownloadResult";
    public final static String DEF_ARIA2_ADDRESS = "http://localhost:6800/jsonrpc";
    private final static String[] PARAM_ARRAY_OF_FILED =
            new String[]{"totalLength", "completedLength", "files", "status", "errorCode", "gid"};
    /**
     * id随机生成，也可以手动设置
     */
    private String id = UUID.randomUUID().toString();
    private String jsonrpc = "2.0";
    private String aria2Address = DEF_ARIA2_ADDRESS;
    private String method = METHOD_TELL_ACTIVE;
    private String url;
    //暂存下载参数
    private List<Object> params = new ArrayList<>();

    public Aria2Handler(String aria2Address) {
        this.aria2Address = aria2Address;
    }

    /**
     * @return: void
     * @author: victor2022
     * @date: 2022/8/9 下午5:36
     * @description: 刷新数据
     */
    public void flush(){
        params.clear();
    }

    /**
     * 添加下载参数
     * @return
     */
    public Aria2Handler addParam(Object obj) {
        params.add(obj);
        return this;
    }

    /**
     * @return: java.lang.String
     * @author: victor2022
     * @date: 2022/8/9 下午6:33
     * @description: 注意避开get语句
     */
    public Aria2Response fetchGlobalStat(){
        this.flush();
        this.setMethod(METHOD_GLOBAL_STAT)
                .addParam(new String[0]);
        String res = this.send(aria2Address);
        return this.parseResult(res,true);
    }

    public Aria2Response submitTask(DownloadInfo info){
        this.flush();
        Aria2Option option = new Aria2Option().setOut(info.getFilename())
                .setDir(info.getPath())
                .setReferer("*");
        this.setMethod(Aria2Handler.METHOD_ADD_URI)
                .addParam(info.getUrlArray())
                .addParam(option);
        // 将任务传输给aria2
        String res = this.send(aria2Address);
        return this.parseResult(res,false);
    }

    public Aria2Response tellActive() {
        this.flush();
        this.setMethod(METHOD_TELL_ACTIVE)
                .addParam(PARAM_ARRAY_OF_FILED);
        String res = this.send(aria2Address);
        return this.parseResult(res,true);
    }

    public Aria2Response tellStopped() {
        this.flush();
        this.setMethod(METHOD_TELL_STOPPED)
                .addParam(-1)
                .addParam(1000)
                .addParam(PARAM_ARRAY_OF_FILED);
        String res = this.send(aria2Address);
        return this.parseResult(res,true);
    }

    public Aria2Response tellWaiting() {
        this.flush();
        this.setMethod(METHOD_TELL_WAITING)
                .addParam(0)
                .addParam(1000)
                .addParam(PARAM_ARRAY_OF_FILED);
        String res = this.send(aria2Address);
        return this.parseResult(res,false);
    }

    public Aria2Response removeDownloadResult(String gid) {
        this.flush();
        this.setMethod(METHOD_REMOVE_DOWNLOAD_RESULT)
                .addParam(gid);
        String result = this.send(aria2Address);
        return parseResult(result,true);
    }

    /**
     * @param str:
     * @param parseMap:
     * @return: com.victor2022.spider.downloader.aria2.Aria2Response
     * @author: victor2022
     * @date: 2022/8/9 下午6:50
     * @description: 解析结果
     */
    private Aria2Response parseResult(String str, boolean parseMap){
        Aria2Response response = JSON.parseObject(str, Aria2Response.class);
        if(parseMap&&!response.getResult().isEmpty()){
            for(String s:response.getResult()){
                response.getResultMapList().add(JSON.parseObject(s,Map.class));
            }
        }
        return response;
    }

    public String send(String jsonRpcUrl) {
        //rpcurl 默认为本地默认地址
        jsonRpcUrl = (jsonRpcUrl==null||jsonRpcUrl.isEmpty())? DEF_ARIA2_ADDRESS : jsonRpcUrl;
        //创建post请求对象
        HttpPost httpPost = new HttpPost(jsonRpcUrl);
        //设置content type（正文类型） 为json格式
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        //将 this 对象解析为 json字符串 并用UTF-8编码(重要)将其设置为 entity （正文）
        httpPost.setEntity(new StringEntity(JSONObject.toJSONString(this), StandardCharsets.UTF_8));
        //发送请求并获取返回对象
        CloseableHttpResponse response;
        try {
            response = HttpClients.createDefault().execute(httpPost);
        } catch (HttpHostConnectException e) {
            log.debug("Aria2 无法连接");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //返回的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        //请求结果字符串
        String result = null;
        try {
            //用UTF-8解码返回字符串
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            //如果状态码为200表示请求成功，返回结果
            if (statusCode == HttpStatus.SC_OK) {
                EntityUtils.consume(entity);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //请求失败 打印状态码和提示信息 返回null
        System.out.println("statusCode = " + statusCode);
        System.out.println("result = " + result);
        return null;
    }

}
