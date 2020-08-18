package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 利用springmvc中提供的工具api实现文件上传的简化
     * 记住类型：MultipartFile
     * 实现步骤：
     *      1.接收资源文件
     *      2.准备文件上传的目录
     *      3.准备文件上传的全路径    目录/文件名称
     */
    @RequestMapping("/file")
    public String file(MultipartFile fileImage) throws IllegalStateException, IOException{
        //2.准备文件上传的目录
        String dirPath="D:/images";
        File fileDir=new File(dirPath);
        //判断文件目录是否存在
        if (!fileDir.exists()){
            //如果文件的目录没有，则应该新建目录
            fileDir.mkdirs();//创建多级目录
        }
        //3.准备文件上传的全路径 路径+文件名称
        String fileName = fileImage.getOriginalFilename();//文件名称.后缀
        File file=new File(dirPath+"/"+fileName);
        //将字节信息输出到文件中，实现文件上传
        fileImage.transferTo(file);
        return "文件上传成功";
    }

    /**
     * 实现图片上传操作
     * @param uploadFile
     * @return ImageVO对象
     */
    @RequestMapping("/pic/upload")
    public ImageVO uploadFile(MultipartFile uploadFile) throws IOException {
        return fileService.upload(uploadFile);
    }
}
