package com.xhnj.service;

import com.xhnj.pojo.query.PoundageParam;
import javax.servlet.http.HttpServletResponse;

/*
 @Description
 *@author kang.li
 *@date 2021/9/25 20:45   
 */
public interface PoundageService {
    void exportExcel(HttpServletResponse response,  PoundageParam poundageParam);

    void pdfExport(HttpServletResponse response,  PoundageParam poundageParam);
}
