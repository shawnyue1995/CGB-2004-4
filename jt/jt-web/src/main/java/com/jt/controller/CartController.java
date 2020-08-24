package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//由于需要返回页面的逻辑名称所以使用
@Controller
@RequestMapping("/cart")
public class CartController {
    private static final String JTUSER = "JT_USER";
    @Reference(check = false)
    private DubboCartService cartService;

    /**
     * 1.购物车列表数据展现
     * url: http://www.jt.com/cart/show.html
     * 参数: 无
     * 返回值: cart  页面逻辑名称
     * 页面取值数据: ${cartList}
     * 核心业务流程: 根据userId查询购物车记录.  userId = 7L;
     */
    @RequestMapping("show")
    public String show(Model model, HttpServletRequest request){
        //自定义userID 后期维护
      /*  User user = (User)request.getAttribute(JTUSER);
        Long userId = user.getId();*/
        Long userId = UserThreadLocal.get().getId();
        List<Cart> cartList = cartService.findCartList(userId);
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    /**
     * 业务需求: 修改购物车数量
     * 1.url地址:http://www.jt.com/cart/update/num/562379/8
     * 2.参数:  itemId/num
     * 3.返回值: void
     * 条件: userId = 7 暂时写死.
     */
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public void updateCartNum(Cart cart){ //restFul中的参数与对象的属性名称一致,可以简化
        //1.获取userId
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        cartService.updateCartNum(cart);
    }

    /**
     * 删除购物车
     * 1.url地址:http://www.jt.com/cart/delete/562379.html
     * 2.参数:itemId
     * 3.返回值: 重定向到购物车列表页面
     */
    @RequestMapping("/delete/{itemId}")
    public String deleteCart(Cart cart){

        //1.动态获取userId
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        //2.执行删除业务
        cartService.deleteCart(cart);
        return "redirect:/cart/show.html";
    }

    /**
     * 业务需求: 完成购物车新增
     * 1.url地址:http://www.jt.com/cart/add/562379.html
     * 2.参数:   整合cart的form表单
     * 3.返回值: 新增购物车完成之后,应该重定向到购物车展现页面
     * 注意事项:  如果用户重复添加购物车则只修改数量即可.
     * 10分钟  A  有思路没写 A- 良好
     */
    @RequestMapping("/add/{itemId}")
    public String saveCart(Cart cart){

        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        cartService.saveCart(cart);
        return "redirect:/cart/show.html";
    }







}
