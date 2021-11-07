package com.xhnj.controller;

import cn.hutool.core.util.StrUtil;
import com.xhnj.annotation.MyLog;
import com.xhnj.common.CommonResult;
import com.xhnj.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/*
 @Description 上传文件
 *@author kang.li
 *@date 2021/2/19 9:19   
 */
@Api(value = "文件上传接口", tags = "文件上传接口")
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @ApiOperation("图片上传")
    @PostMapping("/image")
    @MyLog(operate = "添加", objectType = "文件上传", objectName = "文件上传", descript = "图片上传")
    public CommonResult uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        String url = uploadService.uploadPic(file, request);
        if(StrUtil.hasBlank(url)) {
            return CommonResult.failed();
        }
        return CommonResult.success(url);
    }

}
