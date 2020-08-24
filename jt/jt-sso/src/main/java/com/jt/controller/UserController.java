package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

//服务器端程序要求返回的都是JSON 所以使用
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JedisCluster jedisCluster;


    /*demo测试*/
    @RequestMapping("/getMsg")
    public String  getMsg(){

        return "sso单点登录系统正常";
    }
    /**
     * 1.url请求地址: http://sso.jt.com/user/check/{param}/{type}
     * 2.请求参数:    {需要校验的数据}/{校验的类型是谁}
     * 3.返回值结果:  SysResult返回  需要包含true/false
     * 4.JSONP请求方式:  返回值必须经过特殊的格式封装 callback(json)
     */
    @RequestMapping("/check/{param}/{type}")
    public JSONPObject checkUser(@PathVariable String param,
                                 @PathVariable Integer type,
                                 String callback){
        //1.校验数据库中是否存在该数据
        boolean flag = userService.checkUser(param,type);  //存在true  不存在false
        //int a = 1/0;
        return new JSONPObject(callback, SysResult.success(flag));
    }

    /**
     * 完成httpClient测试
     * url:http://sso.jt.com/user/httpClient/saveUser?username=111&password="2222"
     */
    @RequestMapping("/httpClient/saveUser")
    public SysResult saveUser(User user){

        userService.saveHttpCleint(user);
        return SysResult.success();
    }

    /**
     * 完成回显用户名称
     * url地址: http://sso.jt.com/user/query/494fcc9ac98e49bc98baffd6d8fc6802?callback=jsonp1597999688824&_=1597999688872
     * 参数:  ticket信息
     * 返回值: SysResult对象(userJSON)
     * */
    @RequestMapping("/query/{ticket}")
    public JSONPObject  findUserByTicket(@PathVariable String ticket,String callback){

        String userJSON = jedisCluster.get(ticket);
        if(StringUtils.isEmpty(userJSON)){
            //ticket有误.返回错误信息即可
            return new JSONPObject(callback, SysResult.fail());
        }else{
            return new JSONPObject(callback, SysResult.success(userJSON));
        }
    }








}
