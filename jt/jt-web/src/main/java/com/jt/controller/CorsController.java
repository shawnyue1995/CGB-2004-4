package com.jt.controller;

import com.jt.pojo.ItemDesc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {

    @RequestMapping("web/testCors")
    public ItemDesc testItem(){
        System.out.println("我执行了业务操作");
        return new ItemDesc().setItemId(100L).setItemDesc("我是cors的返回值");
    }
}
