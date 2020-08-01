package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.pojo.Item;
import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	//利用MP方式进行分页查询
	//执行步骤：1手动编辑sql 2.利用MP机制 动态生成
	//1.page参数问题：参数1：第几页 参数2：size 每页有多少条
	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		QueryWrapper<Item> queryWrapper=new QueryWrapper<>();
		queryWrapper.orderByDesc("updated");
		//根据分页模型执行分页操作，并将结果返回给分页对象
		IPage<Item> iPage= new Page<>(page,rows);
		iPage = itemMapper.selectPage(iPage,queryWrapper);
		
		Long total=iPage.getTotal();//由分页工具动态获取
		List<Item> itemList=iPage.getRecords();//获取当前分页的信息
		return new EasyUITable(total,itemList);
	}

	/**
	 * 执行步骤:1.手动编辑sql    2.利用MP机制 动态生成
	 * SELECT * FROM tb_item LIMIT 起始位置,查询记录数
	 /*第一页 java中数组运算 一般都是含头不含尾
	 SELECT * FROM tb_item LIMIT 0,20;
	 /*第二页
	 SELECT * FROM tb_item LIMIT 20,20;
	 /*第三页
	 SELECT * FROM tb_item LIMIT 40,20;
	 *第N页
	 SELECT * FROM tb_item LIMIT (n-1)ROWS,ROWS;
	 */

	/**@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {

	//参数1.记录总数   参数2: rows 当前页的记录数
	long total = itemMapper.selectCount(null);
	int startIndex = (page - 1) * rows;
	List<Item> itemList =
	itemMapper.findItemByPage(startIndex,rows);
	return new EasyUITable(total,itemList);
	}
	 **/

}
