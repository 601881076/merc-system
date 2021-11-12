package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TDismissBatchExcel;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.pojo.query.WithholdAgreeParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/9/19 18:00   
 */
public interface TWithholdAgreeService {

    /**
     * 授权条件查询
     * @param withholdAgree
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage conditionQuery(TWithholdAgree withholdAgree, Integer pageSize, Integer pageNum);

    /**
     * 分页查询授权取消批次
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage batchPage(TDismissBatch dismissBatch, Integer pageSize, Integer pageNum);

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


    /**
     * 授权报告查询导出
     * @param response
     * @param idList
     */
    void exportExcel(HttpServletResponse response, List<String> idList);


}
