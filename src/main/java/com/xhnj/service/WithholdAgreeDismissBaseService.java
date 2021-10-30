package com.xhnj.service;

import javax.servlet.http.HttpServletResponse;

public interface WithholdAgreeDismissBaseService {
    /**
     * 下载模板
     */
    void exportExcel(HttpServletResponse response);

    /**
     * 删除
     * @return
     */
    int delete(Long id);
}
