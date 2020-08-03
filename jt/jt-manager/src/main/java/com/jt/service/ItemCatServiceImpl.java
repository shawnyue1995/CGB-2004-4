package com.jt.service;

import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public ItemCat findItemCatById(Long itemCatId) {
        return itemCatMapper.selectById(itemCatId);
    }

    @Override
    public List< EasyUITree > findItemCatList(Long parentId) {

        return itemCatMapper.;
    }
}
