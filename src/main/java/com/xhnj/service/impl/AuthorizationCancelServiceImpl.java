package com.xhnj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.model.TDismissBatch;
import com.xhnj.service.AuthorizationCancelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @Description:    授权取消列表 服务类
* @Author:         tan_yi
* @CreateDate:     2021/11/6 15:44
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/6 15:44
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Service
public class AuthorizationCancelServiceImpl implements AuthorizationCancelService {

    @Resource
    private TDismissBatchMapper dismissBatchMapper;

    @Override
    public IPage listPage(TDismissBatch dismissBatch, Integer pageSize, Integer pageNum) {
        IPage<TDismissBatch> page = new Page<>(pageNum, pageSize);
        return dismissBatchMapper.cancelPage(page,dismissBatch);
    }
}
