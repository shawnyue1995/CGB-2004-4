package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //保证返回的数据转化为JSON
//properties与spring容器建立关系,指定pro文件后进行加载
//如果其中包含中文，则应该采用utf-8的形式
@PropertySource(value = "classpath:/properties/jdbc.properties",encoding = "utf-8")
public class JDBCController {

    //1.需求:利用YML配置文件的信息为属性赋值
    //@Value作用: 从spring容器中找到具体的key,为属性赋值.

    @Value("${jdbc2.username}")//spel表达式 spring提供
    private String username;	//定义数据库用户名

    @Value("${jdbc2.password}")
    private String password;	//定义数据库密码

    @RequestMapping("/getMsg")
    public String getMsg() {
        return username+"|"+password;
    }

    @RequestMapping("/getMsgValue")
    public String getMsgValue() {

        return username+"|"+password;
    }

}
