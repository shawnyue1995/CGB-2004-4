package com.jt.handler;

import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//拦截器的类(业务)      拦截器的配置文件(拦截什么请求)
@Component
public class UserInterceptor implements HandlerInterceptor {

    private static final String TICKET = "JT_TICKET";
    private static final String JTUSER = "JT_USER";
    @Autowired
    private JedisCluster jedisCluster;

    /**
     *  实现pre的方法
     *  返回值说明:
     *          return  false 表示拦截 需要配合重定向一齐使用
     *          return  ture  表示放行
     *  需求1: 如果用户没有登录,则重定向到系统登录页面
     *  判断条件: 如何判断用户是否登录.  1.检查Cookie中是否有记录   2.Redis中是否有记录.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie cookie = CookieUtil.getCookieByName(request, TICKET);
        if(cookie != null){ //不为null.则表示用户可能登录.
            String ticket = cookie.getValue(); //cookie中存储的是Redis的key ticket密钥
            if(jedisCluster.exists(ticket)){

                //如果redis中的数据存在,则说明用户已经登录.可以放行请求.
                //获取真实的用户信息
                String userJSON = jedisCluster.get(ticket);
                //将json转化为对象
                User user = ObjectMapperUtil.toObject(userJSON, User.class);
                request.setAttribute(JTUSER, user);
                //利用ThreadLocal方式存储数据
                UserThreadLocal.set(user);
                return true;
            }else{
                //Cookie中的记录与Redis中的记录不一致.应该删除Cookie中的数据.
                CookieUtil.deleteCookie(TICKET, "/", "jt.com",response);
            }
       }

        response.sendRedirect("/user/login.html");
        return false;   //表示拦截
    }

    //实现数据的移除
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        UserThreadLocal.remove();
    }
}



