package com.jt.demo.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)	//链式设置属性
@TableName(value="user") //将对象与表名关联，如果对象名称与表名一致，则可以省略不写
public class User {
	
	@TableId(type=IdType.AUTO)		//表示主键自增
	private Integer id;
	//@TableField(value = "name")   //表示字段与属性的关系
	private String name;
	private Integer age;
	private String sex;

}
