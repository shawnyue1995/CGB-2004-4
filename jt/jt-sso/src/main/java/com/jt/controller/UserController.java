package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	/*demo测试*/
	@RequestMapping("/getMsg")
	public String  getMsg(){

		return "sso单点登录系统正常";
	}

	/**
	 * url请求地址:http://sso.jt.com/user/check/{param}/{type}
	 * 2.请求参数：	{需要校验的数据}/{校验的类型是谁}
	 * 3.返回值救国：	sysResult返回  需要包含true/false
	 *
	 */

	@RequestMapping("/check/{param}/{type}")
	public JSONPObject CheckUser(@PathVariable("param") String param, @PathVariable("type") Integer type, String callback){
		//1.校验数据库是否存在该数据
		boolean flag= userService.checkUser(param,type);
		return new JSONPObject(callback,SysResult.success(flag));
	}
    /**
     * 完成httpClient测试
     * url:http://sso.jt.com/user/httpClient/saveUser?username=111&password="2222"
     */
	@RequestMapping("/httpClient/saveUser")
	public SysResult saveUser(User user){

		userService.saveHttpClient(user);
		return SysResult.success();
	}

}
