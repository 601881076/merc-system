package com.xhnj.service.impl;

import cn.hutool.core.util.IdUtil;
import com.xhnj.common.exception.BusinessException;
import com.xhnj.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/2/19 10:08   
 */
@Slf4j
@Service
public class UploadServiceImpl implements UploadService {
    @Value("${pic.suffix}")
    private List<String> suffixList;

    @Value("${uploadFile.resourceHandler}")
    private String resourceHandler;

    @Value("${uploadFile.location}")
    private String uploadFileLocation;

    @Override
    public String uploadPic(MultipartFile file, HttpServletRequest request) {
        String originalFilename = file.getOriginalFilename();
        // 校验文件的类型
        String name = file.getOriginalFilename();
        String suffix = getFileSuffix(name);
        if (!suffixList.contains(suffix)){
            log.info("图片类型不合法：{}", originalFilename);
            throw new BusinessException("图片类型不合法");
        }
        try {
            // 校验文件的内容
           /* BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null){
                log.info("文件内容不合法：{}", originalFilename);
                throw new BusinessException("文件内容不合法");
            }*/
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            String fileName = file.getOriginalFilename();
            //重命名
            String chgFileName = IdUtil.objectId() + fileName;
            String fileServerPath = basePath + resourceHandler.substring(0, resourceHandler.lastIndexOf("/") + 1) + chgFileName;
            log.info("文件访问路径 {}" , fileServerPath);
            // 保存到服务器
            File saveFile = new File(uploadFileLocation, chgFileName);
            file.transferTo(saveFile);
            return fileServerPath;
        } catch (IOException e) {
            log.info("服务器内部错误：{}", originalFilename);
            e.printStackTrace();
        }
        return null;
    }

    private String getFileSuffix(String filename){
        int pos = filename.lastIndexOf(".");
        if(pos == -1){
            return null;
        }
        return filename.substring(pos+1).toLowerCase();
    }
}
