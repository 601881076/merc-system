package com.xhnj.service;

import org.springframework.web.multipart.MultipartFile;

/*
 @Description
 *@author kang.li
 *@date 2021/9/19 18:00   
 */
public interface TWithholdAgreeService {

    /**
     * 上传授权取消excel
     * @param file
     * @return
     */
    int uploadExcel(MultipartFile file);
}
