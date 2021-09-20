package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.pojo.query.WithholdParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/*
 @Description
 *@author kang.li
 *@date 2021/9/18 16:59   
 */
public interface TWithholdService {

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
     * 导出失败
     * @param withholdParam
     * @return
     */
    void exportExcelFail(HttpServletResponse response, WithholdParam withholdParam);
}
