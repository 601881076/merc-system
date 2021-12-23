package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TBatchDtl;
import com.xhnj.model.TBatchNo;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/*
 @Description
 *@author kang.li
 *@date 2021/9/18 16:59   
 */
public interface TWithholdService {

    /**
     * 分页查询扣款批次
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage batchPage(TBatchNo batchNo,Integer pageSize, Integer pageNum);

    /**
     * 分页查询代扣明细
     * @param batchNo
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage listPage(String batchNo, Integer pageSize, Integer pageNum);

    /**
     * 上传代扣excel
     * @param file
     * @return
     */
    int uploadExcel(MultipartFile file);

    /**
     * 导出成功
     * @param withholdParam
     * @return
     */
    void exportExcelSuccess(HttpServletResponse response, WithholdParam withholdParam);
    /**
     * 下载模板
     * @return
     */
    void exportExcelSuccess(HttpServletResponse response);
    /**
     * 批量导出
     */
    void batchExportExcelSuccess(HttpServletResponse response, List<String> list);

    /**
     * 导出失败
     * @param withholdParam
     * @return
     */
    void exportExcelFail(HttpServletResponse response, WithholdParam withholdParam);

    /**
     * 删除
     * @return
     */
    int delete(Long id);

    /**
     * 导出成功
     * @param dismissBatch
     * @return
     */
    void exportExcelSuccess(HttpServletResponse response, DisMissBatchQuery dismissBatch);

}
