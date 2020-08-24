package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.CookieUtil;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller     // 如果返回的是json 注意方法中添加该注解@ResponseBody
@RequestMapping("/user")
public class UserController {

    private static final String TICKET = "JT_TICKET";
    //消费者在启动时检查是否有服务提供者  false 不检查,程序调用的时候才校验.
    @Reference(check = false)
    private DubboUserService userService;
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 业务说明:要求用一个方法实现页面的通用跳转.
     * http://www.jt.com/user/login.html   跳转页面 login.jsp
     * http://www.jt.com/user/register.html  跳转页面register.jsp
     * 自己完成该功能!!!!
     */
    @RequestMapping("/{moduleName}")
    public String moduleName(@PathVariable  String moduleName){

        return moduleName;
    }

    /**
     * 业务说明:完成用户数据入库操作
     * 1.url地址: http://www.jt.com/user/doRegister
     * 2.参数问题: {password:_password,username:_username,phone:_phone},
     * 3.返回值结果: SysResult对象
     */

    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult doRegister(User user){
        //service 第三方接口. 直接rpc调用访问jt-sso中的实现类
        userService.saveUser(user);
        return SysResult.success();
    }

    /**
     * 1.url地址:http://www.jt.com/user/doLogin?r=0.04360522021726099
     * 2.参数:  {username:_username,password:_password},
     * 3.返回值结果:  SysResult
     *
     * 需求1:  将cookie名称为 "JT_TICKET"数据输出到浏览器中,要求7天超时.
     * 并且实现"jt.com"数据共享
     *
     * Cookie特点:
     *  1.浏览器中只能查看当前网址下的Cookie信息
     *  2.doMain 表示cookie共享的策略
     *    doMain:www.jd.com   当前的Cookie数据只能在当前域名中使用
     *    doMain:.jd.com      当前Cookie是共享的可以在域名为jd.com结尾的域名中共享.
     *
     *  * * */
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult doLogin(User user, HttpServletResponse response){

        //完成用户登录操作 之后获取ticket密钥信息
        String ticket = userService.doLogin(user);
        if(StringUtils.isEmpty(ticket)){
            //如果为null,则说明用户名或密码有问题
            return SysResult.fail();
        }

        //1.创建Cookie对象
        Cookie cookie = new Cookie("JT_TICKET",ticket);
        //2.设定cookie存活的时间   value=-1  当用户关闭会话时,cookie删除
        //2.设定cookie存活的时间   value= 0  立即删除cookie
        //2.设定cookie存活的时间   value= >0 设定cookie超时时间
        cookie.setMaxAge(7*24*60*60);
        //3.在jt.com的域名中实现数据共享.
        cookie.setDomain("jt.com");
        cookie.setPath("/"); //一般情况下都是/
        //4.将数据保存到浏览器中
        response.addCookie(cookie);
        return SysResult.success();
    }

    /**
     * 实现用户退出操作
     * url:http://www.jt.com/user/logout.html
     * 返回值: 重定向到系统首页.
     * 目的:  删除redis. 删除Cookie
     * 前提:  需要获取cookie的key和value
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie = CookieUtil.getCookieByName(request,TICKET);
        //1.校验cookie中是否有记录
        if(cookie != null){
            String jtTicket = cookie.getValue();
            if(!StringUtils.isEmpty(jtTicket)){
                //删除Redis数据
                jedisCluster.del(jtTicket);
                //删除cookie
                CookieUtil.deleteCookie(TICKET, "/", "jt.com", response);
            }
        }

        return "redirect:/";
    }











}
