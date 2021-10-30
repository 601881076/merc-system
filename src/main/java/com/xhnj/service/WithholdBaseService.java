package com.xhnj.service;

import com.xhnj.pojo.query.WithholdParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/9/20 18:51   
 */
public interface WithholdBaseService {
    /**
     * 导出
     * @param withholdParam
     * @return
     */
    void exportExcel(HttpServletResponse response, WithholdParam withholdParam);
    /**
     * 下载模板
     */
    void exportExcel(HttpServletResponse response);
    /**
     * 批量导出
     */
    void batchExport(HttpServletResponse response, List<String> list);
}
