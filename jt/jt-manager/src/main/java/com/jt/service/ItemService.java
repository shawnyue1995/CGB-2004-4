package com.jt.service;

import com.jt.vo.EasyUITable;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;


public interface ItemService {
     EasyUITable findItemByPage(Integer page, Integer rows);

    void saveItem(Item item, ItemDesc itemDesc);

    void updateItem(Item item,ItemDesc itemDesc);

    void deleteItem(Long[] ids);


    void updateItemStatus(Long[] ids, Integer status);

    ItemDesc findItemDescById(Long itemId);
}
