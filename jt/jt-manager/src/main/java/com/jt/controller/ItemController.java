package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.vo.EasyUIResult;
import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.RestController;


@RestController//返回值都是Json串
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	/**
	 * 业务：根据分页要求展现商品列表，要求将最新最热门的商品展现给用户展现
	 * url：url:'/item/query
	 * 参数：page=1&rows=20 page当前页 rows记录数
	 * 返回值： EasyUITable
	 * 编码习惯：mapper-service-controller-页面js 手敲 自下而上的开发
	 * 课堂代码格式：
	 * 				url-controller-service-mapper  结构代码
	 */
	@RequestMapping("/query")
	public EasyUITable findItemByPage(Integer page,Integer rows){
		//1.调用业务层，获取商品信息
		return itemService.findItemByPage(page,rows);

	}
	@RequestMapping("/save")
	public EasyUIResult saveItem(Item item){
		try {
			itemService.saveItem(item);
			//int a=1/0
			return EasyUIResult.success();
		} catch (Exception e) {
			e.printStackTrace();	//打印错误信息
			return EasyUIResult.fail();
		}
	}

}
