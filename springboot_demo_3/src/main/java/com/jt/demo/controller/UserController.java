package com.jt.demo.controller;

import com.jt.demo.mapper.UserMapper;
import com.jt.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/findAll")
    public String  findAll(Model model){
        List< User > list = userMapper.findAll();
        model.addAttribute("list",list);
        return "userList";
    }
    @RequestMapping("/userAjax")
    public String userAjax(){
        return "userAjax";
    }
}
