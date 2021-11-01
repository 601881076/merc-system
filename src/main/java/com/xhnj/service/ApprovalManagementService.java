package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TBatchCheck;
import com.xhnj.model.TBatchNo;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.WithholdSuccessExcel;
import com.xhnj.pojo.query.WithholdParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @Description:    授权取消审批接口
 * 包含页面查询、批准、拒绝、批量导出报告
* @Author:         tan_yi
* @CreateDate:     2021/11/1 17:24
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/1 17:24
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public interface ApprovalManagementService {

    /**
     * 查询
     * @param dismissBatch 批次表
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage batchPage(TDismissBatch dismissBatch, Integer pageSize, Integer pageNum);

    /**
     * 审核员授权取消审批操作
     * @param option 操作类型：1-批准，2-拒绝
     * @param batchNoList
     * @return
     */
    int update(int option, List<String> batchNoList);


    /**
     * 导出报告
     * @param batchNo
     * @return
     */
    void exportExcel(HttpServletResponse response, TBatchNo batchNo);


}
