package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMapper extends BaseMapper<Item>{

    //利用注解执行sql.
    @Select("SELECT * FROM tb_item  ORDER BY updated DESC LIMIT #{startIndex},#{rows}")
    List<Item> findItemByPage(Integer startIndex, Integer rows);
}
