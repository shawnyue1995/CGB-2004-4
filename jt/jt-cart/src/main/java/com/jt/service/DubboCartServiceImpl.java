package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class DubboCartServiceImpl implements  DubboCartService{

    @Autowired
    private CartMapper cartMapper;

    //根据userId 查询购物车记录
    @Override
    public List<Cart> findCartList(Long userId) {

        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return cartMapper.selectList(queryWrapper);
    }

    /**
     * 目的: 修改购物车数量
     * @param cart
     */
    @Override
    public void updateCartNum(Cart cart) {
        //1.晚上自己完成手写sql  注意时间的修改
        Cart cartTemp = new Cart();
        cartTemp.setNum(cart.getNum());
        UpdateWrapper updateWrapper = new UpdateWrapper();
        //根据itemId和userId可以唯一确定购物行为
        updateWrapper.eq("user_id", cart.getUserId());
        updateWrapper.eq("item_id", cart.getItemId());
        cartMapper.update(cartTemp, updateWrapper);
    }

    //cart对象 {itemId,userId  null....null}
    //@Override
    public void deleteCart(Cart cart) {
        //将对象中不为null的元素当做where条件
        cartMapper.delete(new QueryWrapper<>(cart));
    }

    //如果重复添加,则更新数量
    //如何判断是否为重复添加  item_id/user_id
    @Override
    public void saveCart(Cart cart) {

        //1.查询数据 检查是否已经加购
        QueryWrapper<Cart> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", cart.getUserId())
                    .eq("item_id", cart.getItemId());
        Cart cartDB = cartMapper.selectOne(queryWrapper);
        if(cartDB ==null){
            //表示用户第一次新增,直接入库即可
            cartMapper.insert(cart);
        }else{
            //表示用户重复加购 只修改数量
            int num = cartDB.getNum() + cart.getNum();
            //主键充当where条件,对象中其他不为null的属性当做set的值.
            Cart cartTemp = new Cart();
            cartTemp.setId(cartDB.getId())
                    .setNum(num);
            cartMapper.updateById(cartTemp);
            //sql简单  面向对象的方式实现
        }

    }
}
