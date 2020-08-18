package com.jt.demo.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jt.demo.mapper.UserMapper;
import com.jt.demo.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

@RunWith(SpringRunner.class)	//注意测试文件的位置 必须在主文件加载包路径下
@SpringBootTest
public class TestMybatis {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void testFindUser() {
		
		List< User > userList = userMapper.findAll();
		System.out.println(userList);
	}

	@Test
	public void testList(){
		List< User > users = userMapper.selectList(null);
		System.out.println(users);
	}
	
	//新增用户信息
	//注意事项：MP操作时，将对象中不为空的数据当做执行要素
	@Test
	public void testSaveUser(){
		User user = new User();
		/*user.setName("诺克赛斯之手");
		user.setAge(100);
		user.setSex("男");*/
		user.setName("诺克赛斯之手").setAge(100).setSex("男");
		userMapper.insert(user);
		System.out.println("入库成功");
	}
	
	//删除用户信息
	@Test
	public void testDeleteUser(){
		//userMapper.deleteById(67);  删除主键信息
		
		//删除为null的数据
		//先查询再删除
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.isNull("name");	//删除指定数据
		userMapper.delete(queryWrapper);
		System.out.println("删除成功!!!");
	}
	
	//修改用户信息
	@Test
	public void testUpdateUser() {
		//entity用户set数据.updateWrapper用户设定where条件
		User user = new User();
		user.setName("复仇炎魂");
		user.setAge(2000);
		user.setSex("男");
		
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", 68);
		userMapper.update(user,updateWrapper);
		System.out.println("修改数据完成");
		
	}
	
	//查询用户信息
	//逻辑运算符 = eq > gt < lt >= ge <= le
	//SELECT id,name,age,sex FROM user WHERE (age = ?)
	@Test
	public void testQueryUser() {
		//定义条件构造器  动态拼接where条件之后的数据
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("age", 2000);  	//查询年龄为200岁的
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}

	//查询名字中包含“精”的用户
	//SELECT id,name,age,sex FROM user WHERE (name LIKE ?)
	@Test
	public void select03(){
		QueryWrapper<User> queryWrapper=new QueryWrapper< User >();
		//queryWrapper.like("name","精");
		queryWrapper.likeLeft("name","精");
		List< User > users = userMapper.selectList(queryWrapper);
		System.out.println(users);
	}

	//查询age 位于18-35之间，并且性别为“男”
	//再多条件的测试下，默认采用and连接
	//Preparing: SELECT id,name,age,sex FROM user WHERE (age BETWEEN ? AND ? AND sex = ?)
	@Test
	public void select04(){
		QueryWrapper<User> queryWrapper=new QueryWrapper< User >();
		queryWrapper.between("age",18,35)
							.eq("sex","男");
		List< User > users = userMapper.selectList(queryWrapper);
		System.out.println(users);
	}
	//查询name不为null，并且按age降序排序，age相同就按sex降序排序
	//SELECT id,name,age,sex FROM user WHERE (name IS NOT NULL) ORDER BY age DESC,sex DESC
	@Test
	public void select05(){
		QueryWrapper<User> queryWrapper=new QueryWrapper< User >();
		queryWrapper.isNotNull("name")
				.orderByDesc("age","sex");
		List< User > users = userMapper.selectList(queryWrapper);
		System.out.println(users);
	}

	//查询单个用户数据
	@Test
	public void select06() {
		//1.根据主键进行查询    返回值结果单个对象
		User user = userMapper.selectById(1);
		System.out.println(user);

		//2.根据非主键的字段查询单个数据
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq("name", "特朗普");
		User user2 = userMapper.selectOne(queryWrapper);
		System.out.println(user2);

	}
	//批量查询数据 要求查询id 1,3,5,8,10
	//SELECT id,name,age,sex FROM user WHERE id IN ( ? , ? , ? , ? , ? )
	//多表查询不建议使用
	@Test
	public void select07(){
		List< Integer > idList = new ArrayList<>();
		idList.add(1);
		idList.add(3);
		idList.add(5);
		idList.add(8);
		idList.add(10);

		//id信息一般都是有前端进行传递，所以一般都是数组格式
		//一般在定义数组格式时，最好采用对象类型
		Integer[] ids={1,3,5,8};
		//需要将数组的类型转化为集合
		List<Integer> list2= Arrays.asList(ids);
		List< User > users = userMapper.selectBatchIds(list2);
		System.out.println(users);
	}

	//查询记录总数 name不为null的
	@Test
	public void select08(){
		QueryWrapper<User> queryWrapper=new QueryWrapper< User >();
		queryWrapper.isNotNull("name");
		int count = userMapper.selectCount(queryWrapper);
		System.out.println(count);
	}

	
}
