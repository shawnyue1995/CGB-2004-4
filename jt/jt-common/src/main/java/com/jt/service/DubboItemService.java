package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

//定义item的中立接口
public interface DubboItemService {
    Item findItemById(Long itemId);

    ItemDesc findItemDescById(Long itemId);
}
