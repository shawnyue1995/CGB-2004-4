package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ImageVO implements Serializable {
    private static final long serialVersionUID = -5302337766946721739L;
    private Integer error;  //确认是否错误 0正常 1错误
    private String url;     //图片访问的虚拟地址
    private Integer width;  //宽度
    private Integer height; //高度

    //工具API
    public static ImageVO fail(){
        return new ImageVO(1,null,null,null);
    }

    public static ImageVO success(String url){
        return new ImageVO(0,url,null,null);
    }
    public static ImageVO success(String url,Integer width,Integer height){
        return new ImageVO(0,url,width,height);
    }
}
