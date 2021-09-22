package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

/*
 @Description
 *@author kang.li
 *@date 2021/9/19 18:00   
 */
public interface TWithholdAgreeService {

    /**
     * 分页查询授权解除明细
     * @param batchNo
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage listPage(String batchNo, Integer pageSize, Integer pageNum);

    /**
     * 上传授权取消excel
     * @param file
     * @return
     */
    int uploadExcel(MultipartFile file);
}
