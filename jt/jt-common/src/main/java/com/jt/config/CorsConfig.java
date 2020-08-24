package com.jt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  //标识我是一个配置类
public class CorsConfig implements WebMvcConfigurer {

    //扩展跨域请求的方法
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //1.允许什么样的请求进行跨域
        // /* 只允许一级目录请求    /** 表示多级目录请求.
        registry.addMapping("/**")
        //2.允许哪些服务进行跨域
                .allowedOrigins("*")
                //3.是否允许携带cookie信息
                .allowCredentials(true)
                //4.定义探针检测时间 在规定的时间内不再询问是否允许跨域
                .maxAge(1800);
    }
}
