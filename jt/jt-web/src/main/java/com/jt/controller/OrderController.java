package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//1.业务名称与Controller对应即可
@Controller
@RequestMapping("/order")
public class OrderController {

    //2.添加接口
    @Reference(check = false)
    private DubboCartService cartService;

    @Reference(check = false)
    private DubboOrderService orderService;

    /**
     * 跳转订单确认页面
     * 1.url:http://www.jt.com/order/create.html
     * 2.参数: 没有参数 null
     * 3.返回值: order-cart
     * 4.页面取值  {carts}  需要查询购物车信息.,给页面返回
     */
    @RequestMapping("/create")
    public String create(Model model){

        Long userId = UserThreadLocal.get().getId();
        List<Cart> cartList = cartService.findCartList(userId);
        model.addAttribute("carts",cartList);
        return "order-cart";
    }
    @RequestMapping("/submit")
    @ResponseBody
    public SysResult saveOrder(Order order, HttpServletRequest request){
        //利用拦截器的方式赋值.
        User user= (User) request.getAttribute("JT_USER");
        Long userId=user.getId();
        order.setUserId(userId);
        //1.完成订单入库,并且返回orderId
        String orderId=orderService.saveOrder(order);
        if (StringUtils.isEmpty(orderId)){
            return SysResult.fail();
        }
        return SysResult.success(orderId);
    }

    @RequestMapping("/success")
    public String findOrderById(String id,Model model){
        Order order=orderService.findOrderById(id);
        model.addAttribute("order",order);
        return "success";
    }

}
