package com.xhnj.service;

import com.xhnj.pojo.query.DeductionBatchQuery;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @Description:    授权取消审批批量导出
* @Author:         tan_yi
* @CreateDate:     2021/11/2 0:17
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/2 0:17
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public interface BatchCheckService {

    /**
     * 导出成功
     * @param disMissBatchQuery
     * @return
     */
    void exportExcelSuccess(HttpServletResponse response, DisMissBatchQuery disMissBatchQuery);
    
}
