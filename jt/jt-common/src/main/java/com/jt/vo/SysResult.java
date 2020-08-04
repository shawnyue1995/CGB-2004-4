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
public class SysResult implements Serializable {
    private static final long serialVersionUID = -8036587971437632797L;
    /**
     * 策略说明：
     *      1、定义状态码：status==200 调用正确 status==201 调用失败 人为定义的(和浏览器无关)业务定义
     *      2、定义返回值信息：msg提交服务器相关说明信息 例如：执行成功，用户名或密码错误等
     *      3、定义返回值对象：data服务器返回页面的业务数据 一般都是对象的Json
     */
    private Integer status;
    private String msg;
    private Object data;
    //注意事项：写工具API时，切记方法重载千万不要耦合
    //准备工具API 方便用户使用
    public static SysResult fail(){
        return new SysResult(201,"业务调用失败",null);
    }
    //成功方式1 ：只返回状态码信息
    public static SysResult success(){
        return new SysResult(200,"业务调用成功",null);
    }
    //成功方式2：需要返回服务器数据
    public static SysResult success(Object data){
        return new SysResult(200,"业务调用成功",data);
    }
    //成功方式3:可能告知服务器信息及 服务器数据
    public static SysResult success(String msg,Object data){
        return new SysResult(200,msg,data);
    }
}
