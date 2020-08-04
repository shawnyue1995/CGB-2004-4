package com.jt.controller;

import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //该controller的返回值类型是Json
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    /**
     * 1.url:/item/cat/queryItemName
     * 2:请求参数： data:{itemCatId:val}
     * 3:返回值结果：返回商品分类的名称
     */
    @RequestMapping("/queryItemName")
    public String findItemCatNameById(Long itemCatId){

        ItemCat itemCat = itemCatService.findItemCatById(itemCatId);
        return itemCat.getName();
    }
    /**
     * 业务需求： 查询一级商品分类信息
     * Sql语句：select * from tb_item_cat where parent_id=0
     * url地址： /item/cat/list
     * 返回值： List<EasyUITree>
     *
     * 实现异步树的加载： id：xxxx
     *
     */
    @RequestMapping("/list")
    public List< EasyUITree > findItemCatList(Long id){
        Long parentId=(id == null ? 0L : id); //表示根据parentId=0  查询一级商品分类信息
        //作业   难点  itemCat~~~~~EasyUITree的转化
        return itemCatService.findItemCatListByParentId(parentId);
    }

}
