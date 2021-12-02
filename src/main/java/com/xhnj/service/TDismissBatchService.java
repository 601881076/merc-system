package com.xhnj.service;

import com.xhnj.model.TDismissBatch;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 授权取消批次表 服务类
 * </p>
 *
 * @author lk
 * @since 2021-09-22
 */
public interface TDismissBatchService extends IService<TDismissBatch> {
    /**
     * 下载模板
     * @return
     */
    void exportExcel(HttpServletResponse response);


    /**
     * 根据batchNo修改 授权取消审核表状态
     * @param status 审核状态 0->待提交;1->已提交;2->审核通过;3->审核拒绝
     * @param batchNoList
     * @return
     */
    int updateStatusByBatchIdList(int status, List<String> batchNoList);

}
