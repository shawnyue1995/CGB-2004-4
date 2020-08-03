package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EasyUIResult implements Serializable {
    private static final long serialVersionUID = -8036587971437632797L;
    /**
     * 策略说明：
     *      属性1：status==200 调用正确 status==201 调用失败
     *      属性2：msg提交服务器相关说明信息
     *      属性3：data服务器返回页面的业务数据 一般都是对象的Json
     */
    private Integer status;
    private String msg;
    private Object data;

    //准备工具API 方便用户使用
    public static EasyUIResult fail(){
        return new EasyUIResult(201,"业务调用失败",null);
    }
    //成功方式1 ：只返回状态码信息
    public static EasyUIResult success(){
        return new EasyUIResult(200,"业务调用成功",null);
    }
    //成功方式2：需要返回服务器数据
    public static EasyUIResult success(Object data){
        return new EasyUIResult(200,"业务调用成功",data);
    }
    //成功方式3:可能告知服务器信息及 服务器数据
    public static EasyUIResult success(String msg,Object data){
        return new EasyUIResult(200,msg,data);
    }
}
