package com.jt.service;

import com.jt.vo.ImageVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private String localDir="D:/images";

    @Override
    public ImageVO uploadFile(MultipartFile uploadFile) {

        Set<String> typeSet=new HashSet<>();
        typeSet.add(".jpg");
        typeSet.add(".png");
        typeSet.add(".gif");

        String fileName=uploadFile.getOriginalFilename();
        fileName=fileName.toLowerCase();
        int index=fileName.lastIndexOf(".");
        String fileType=fileName.substring(index);
        if (!typeSet.contains(fileType)){
            return ImageVO.fail();
        }

        String dateDir = new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
        String dirPath=localDir+dateDir;
        File dirFile=new File(dirPath);
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }

        String uuid= UUID.randomUUID().toString();
        String realFileName=uuid+fileType;

        File imageFile =new File(dirPath+realFileName);
        try {
            uploadFile.transferTo(imageFile);
            String url = "https://img14.360buyimg.com/n0/jfs/t1/71310/32/5640/402976/5d3a654eE0489baf9/fd8eafe74ef8779c.jpg";
            return ImageVO.success(url);
        } catch (IllegalStateException|IOException e) {
            e.printStackTrace();
        }
        return ImageVO.fail();
    }
}
