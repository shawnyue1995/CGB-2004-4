package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//1.业务名称与Controller对应即可
@Controller
@RequestMapping("/order")
public class OrderController {

    //2.添加接口
    @Reference
    private DubboCartService cartService;

    /**
     * 跳转订单确认页面
     * 1.url:http://www.jt.com/order/create.html
     * 2.参数: 没有参数 null
     * 3.返回值: order-cart
     * 4.页面取值  {carts}  需要查询购物车信息.,给页面返回
     */
    @RequestMapping("create")
    public String create(Model model){

        Long userId = UserThreadLocal.get().getId();
        List<Cart> cartList = cartService.findCartList(userId);
        model.addAttribute("carts",cartList);
        return "order-cart";
    }


}
