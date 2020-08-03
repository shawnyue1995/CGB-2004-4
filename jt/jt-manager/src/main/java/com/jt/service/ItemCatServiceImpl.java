package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public ItemCat findItemCatById(Long itemCatId) {
        return itemCatMapper.selectById(itemCatId);
    }

    /**
     * 据接口添加实现类的方法
     * 业务思路:
     * 	1.用户传递的数据parentId
     * 	2.可以查询List<ItemCat>数据库对象信息.
     * 	3.动态的将ItemCat对象转化为EasyUITree对象
     * 	3.但是返回值要求 返回List<EasyUITree>
     */

    public List<EasyUITree> findItemCatListByParentId(Long parentId){
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        List<ItemCat> itemCatList = itemCatMapper.selectList(queryWrapper);
        List<EasyUITree> treeList = new ArrayList<>();  //先准备一个空集合.
        //需要将数据一个一个的格式转化.
        for(ItemCat itemcat :itemCatList){
            Long id = itemcat.getId();	//获取ID
            String text = itemcat.getName();	//获取文本
            //如果是父级,则默认应该处于关闭状态 closed, 如果不是父级 则应该处于打开状态. open
            String state = itemcat.getIsParent()?"closed":"open";
            //利用构造方法 为VO对象赋值  至此已经实现了数据的转化
            EasyUITree tree = new EasyUITree(id,text,state);
            treeList.add(tree);
        }

        //用户需要返回List<EasyUITree>
        return treeList;
    }

}
