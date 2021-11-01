package com.xhnj.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * 审核员授权取消审批操作
     * @param option 操作类型：1-批准，2-拒绝
     * @param batchNoList
     * @return
     */
    int update(int option, List<String> batchNoList);

}
