package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TDismissBatchCheck;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhnj.pojo.query.DisMissBatchQuery;

import java.util.List;

/**
 * <p>
 * 授权取消批次审批表 服务类
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
public interface TDismissBatchCheckService extends IService<TDismissBatchCheck> {

    /**
     * 查询
     * @param dismissBatch 批次表
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage batchPage(DisMissBatchQuery dismissBatch, Integer pageSize, Integer pageNum);

    /**
     * 审核员授权取消审批操作
     * @param option 操作类型：1-批准，2-拒绝
     * @param batchNoList
     * @return
     */
    int update(int option, List<String> batchNoList, TAdmin admin);

}
