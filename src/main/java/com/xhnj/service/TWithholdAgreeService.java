package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.pojo.query.WithholdAgreeParam;
import org.springframework.web.multipart.MultipartFile;

/*
 @Description
 *@author kang.li
 *@date 2021/9/19 18:00   
 */
public interface TWithholdAgreeService {

    /**
     * 授权条件查询
     * @param withholdAgreeParam
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage conditionQuery(WithholdAgreeParam withholdAgreeParam, Integer pageSize, Integer pageNum);

    /**
     * 分页查询授权取消批次
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage batchPage(Integer pageSize, Integer pageNum);

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
