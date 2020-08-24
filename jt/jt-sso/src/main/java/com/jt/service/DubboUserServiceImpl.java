package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;

@Service    //注意使用Dubbo的注解
public class DubboUserServiceImpl implements DubboUserService{  //alt+shift+p

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 1.密码加密
     * 2.邮箱使用手机代替
     * @param user
     */
    @Override
    public void saveUser(User user) {

        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password).setEmail(user.getPhone());
        userMapper.insert(user);
    }

    /**
     * 目的: 校验用户信息是否有效并且实现单点登录操作.
     * 步骤:
     *      1.校验用户名和密码是否正确(密码明文转化为密文)
     *      2.查询数据库检查是否有结果
     *      3.如果有结果,则动态生成TICKET信息(uuid),将user对象转化为JSON
     *      4.将数据保存到redis中,并且设定超时时间.
     *      5.返回当前用户登录的密钥.
     *
     * @param user
     * @return
     */
    @Override
    public String doLogin(User user) {
        String passwork = user.getPassword();
        String md5Pass = DigestUtils.md5DigestAsHex(passwork.getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername())
                    .eq("password", md5Pass);
        //获取的是用户的全部记录 (包含涉密信息)
        User userDB = userMapper.selectOne(queryWrapper);
        //校验数据是否有效
        if( userDB == null){
            //用户名和密码错误
            return null;
        }

        //如果程序执行到这里,说明用户名和密码正确的. 开启单点登录流程
        String ticket = UUID.randomUUID()
                            .toString().replace("-", "");
        //脱敏处理 余额/密码/手机号/家庭地址
        userDB.setPassword("123456你信不???");
        String userJSON = ObjectMapperUtil.toJSON(userDB);
        //操作时保证原子性
        jedisCluster.setex(ticket, 7*24*60*60, userJSON);
        return ticket;
    }





}
