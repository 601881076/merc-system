package com.xhnj.service;

import com.xhnj.pojo.query.WithholdParam;

import javax.servlet.http.HttpServletResponse;

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
}
