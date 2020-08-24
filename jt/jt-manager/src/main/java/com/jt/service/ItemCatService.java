package com.jt.service;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

import java.util.List;

public interface ItemCatService {

	ItemCat findItemCatById(Long itemCatId);

    List<EasyUITree> findItemCatListByParentId(Long parentId);
    //利用redis缓存查询数据
    List<EasyUITree> findItemCatCache(Long parentId);
}
