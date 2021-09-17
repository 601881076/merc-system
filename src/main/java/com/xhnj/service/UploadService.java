package com.xhnj.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/*
 @Description
 *@author kang.li
 *@date 2021/2/19 10:06   
 */
public interface UploadService {
    public String uploadPic(MultipartFile file, HttpServletRequest request);
}
