package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 构建商品详情的pojo对象
 */
@Data
@TableName("tb_item_desc")
@Accessors(chain = true)
public class ItemDesc extends BasePojo {
    @TableId    //只标识主键即可1
    private Long itemId;
    private String itemDesc;
}
