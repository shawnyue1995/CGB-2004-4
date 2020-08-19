package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

	private static Map<Integer,String> typeMap=new HashMap<>();
	static { //类加载时就要执行  只执行一次
		typeMap.put(1,"username");
		typeMap.put(2,"phone");
		typeMap.put(3,"email");

	}

	@Autowired
	private UserMapper userMapper;


	@Override
	public boolean checkUser(String param, Integer type) {
		//根据参数的类型获取校验的类型 column
		String column=typeMap.get(type);
		QueryWrapper queryWrapper=new QueryWrapper();
		queryWrapper.eq(column,param);
		int count = userMapper.selectCount(queryWrapper);

		return count==0 ? false : true;
	}

	@Override
	public void saveHttpClient(User userPOJO) {
		userPOJO.setEmail("111111111@qq.com")//暂时写死
				.setPhone("111111111");
		userMapper.insert(userPOJO);
	}
}
