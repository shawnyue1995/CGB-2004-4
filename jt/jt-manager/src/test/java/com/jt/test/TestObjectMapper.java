package com.jt.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TestObjectMapper {

    //该对象的工具api 有且只有一份即可，并且不允许别人修改
    private static final ObjectMapper MAPPER=new ObjectMapper();
    /**
     * 目的: 实现对象与json串之间的转化
     * 步骤1:  将对象转化为json
     * 步骤2:  将json转化为对象
     * 利用ObjectMapper 工具API实现
     * @throws JsonProcessingException
     */
    @Test
    public void test01() throws JsonProcessingException {
        ItemDesc itemDesc=new ItemDesc();
        itemDesc.setItemId(101L).
                setItemDesc("json转化测试").
                setCreated(new Date()).
                setUpdated(itemDesc.getCreated());

        //1.将对象转化为Json 调用的是对象的get方法
        String json=MAPPER.writeValueAsString(itemDesc);
        System.out.println(json);

        //2.将json转化为对象   传递需要转化之后的class类型   调用的是对象的set方法
        ItemDesc itemDesc2=MAPPER.readValue(json,ItemDesc.class);
        System.out.println(itemDesc2.getItemDesc());
    }
}
