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
}
