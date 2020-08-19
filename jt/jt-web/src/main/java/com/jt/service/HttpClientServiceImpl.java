package com.jt.service;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HttpClientServiceImpl implements HttpClientService {

    //位置jt-web 不能直接链接数据库 需要将数据传递给sso.jt.com
    @Override
    public void saveUser(User user) {
        //1.将user对象转化为json   http://xxxxx?id=1&name=xxx
        String userJSON = ObjectMapperUtil.toJSON(user);
        String url="http://sso.jt.com/user/httpClient/saveUser?username="
                +user.getUsername()+"&password="+user.getPassword();

        //get请求
        HttpClient httpClient= HttpClients.createDefault();
        HttpGet get=new HttpGet(url);
        try {
            HttpResponse httpResponse=httpClient.execute(get);
            //获取返回值状态信息
            int status=httpResponse.getStatusLine().getStatusCode();
            if (httpResponse.getStatusLine().getStatusCode() != 200)
                throw new RuntimeException("请求错误");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("请求错误");
        }
    }
}
