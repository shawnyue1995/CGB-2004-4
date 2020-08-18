package com.jt;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class testHttpClient {
    /**
     * 业务需求：在java代码中访问百度的页面
     * url：http:www.baidu.com  html代码片段
     * 实现步骤：
     *      1、获取httpClient对象
     *      2.自定义url地址
     *      3.定义请求类型
     *      4.发起请求  获取响应结果
     *      5.检验返回值状态，是否正确
     *      6.获取返回值信息，之后完成后续业务处理
     */
    @Test
    public void test01(){
        HttpClient httpClient=HttpClients.createDefault();
        String url="http://www.bilibili.com";
        HttpGet get=new HttpGet(url);
        try {
            HttpResponse httpResponse=httpClient.execute(get);
            //获取返回值状态信息
            int status=httpResponse.getStatusLine().getStatusCode();
            if (status == 200)
                //请求正确的  获取响应结果
            {
                HttpEntity entity = httpResponse.getEntity();//获取响应对象的实体
                //将实体对象转化为用户能够识别的字符串
                String result = EntityUtils.toString(entity,"UTF-8");
            } else System.out.println("httpClient调用异常");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
