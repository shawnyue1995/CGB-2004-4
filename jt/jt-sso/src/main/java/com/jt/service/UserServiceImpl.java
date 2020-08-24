package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import jdk.nashorn.internal.codegen.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements  UserService{

    //采用工具API形式动态获取
    private static Map<Integer,String> typeMap = new HashMap<>();
    static {  //类加载时就要执行  只执行一次
        typeMap.put(1,"username");
        typeMap.put(2,"phone");
        typeMap.put(3,"email");
    }


    @Autowired
    private UserMapper userMapper;

    /**
     * 查询数据库,检查是否有数据.
     * @param param
     * @param type
     * @return  true 表示数据已存在   false 数据可以使用
     */
    @Override
    public boolean checkUser(String param, Integer type){
        //1.根据参数类型获取校验的类型 column
        String column = typeMap.get(type);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column, param);
        int count = userMapper.selectCount(queryWrapper);

        //获取支付宝的业务参数??????

        return count==0?false:true;
    }

    @Override
    public void saveHttpCleint(User userPOJO) {
        userPOJO.setEmail(System.currentTimeMillis()+"@qq.com") //数据库中要求email和phone是非空的 暂时写死
                .setPhone(System.currentTimeMillis()+"");
        userMapper.insert(userPOJO);
    }


}
