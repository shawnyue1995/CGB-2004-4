package com.jt.controller;

import com.jt.pojo.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //保证返回的数据转化为JSON
@ConfigurationProperties(prefix = "jdbc")//定义属性的前缀

public class JDBCController2 {

    //利用配置文件为属性赋值
    //批量为属性赋值时，要求配置文件的属性和类中的属性名称必须一致，自动的赋值
    private String username;	//定义数据库用户名
    private String password;	//定义数据库密码

    //为属性赋值时，已定位调用对象的set方法


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @RequestMapping("/getMsgPrefix")
    public String getMsgValue() {

        User user=new User();
        user.setId(100).setName("aaa");//链式加载结构
        this.username = "root";
        this.password = "root";
        return username+"|"+password;
    }

}
