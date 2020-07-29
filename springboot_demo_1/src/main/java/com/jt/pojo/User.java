package com.jt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data   //动态生成get/set方法/tostring方法
@NoArgsConstructor//动态生成无参构造
@AllArgsConstructor//动态生成全参构造
@Accessors(chain = true)//生成链式加载结构
public class User {
    private Integer id;
    private String name;
}
