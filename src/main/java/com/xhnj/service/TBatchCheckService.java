package com.xhnj.service;

import com.xhnj.model.TBatchCheck;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 批次审批表 服务类
 * </p>
 *
 * @author lk
 * @since 2021-10-25
 */
public interface TBatchCheckService extends IService<TBatchCheck> {
    int insert(List<String> list);
    int insertCheck(List<String> batchId);

    /**
     * 修改审批表状态
     * @param batchIdList 需要修改的batchId
     * @param status 审核状态(0->待提交;1->已提交;2->审核通过;3->审核拒绝)
     * @return
     */
    int updateStatus(int status, List<String> batchIdList);
}
