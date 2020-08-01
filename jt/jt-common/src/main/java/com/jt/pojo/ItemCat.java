package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_item_cat")
@Data
@Accessors(chain = true)
public class ItemCat {

  private long id;
  private long parentId;  //父级id
  private String name;    //分类名称
  private long status;    //状态信息
  private long sortOrder; //排序号
  private boolean isParent;  //是否为父级
  private java.sql.Timestamp created;
  private java.sql.Timestamp updated;


}
