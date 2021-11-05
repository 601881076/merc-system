package com.xhnj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TDeductionDetailMapper;
import com.xhnj.model.TBatchDtl;
import com.xhnj.pojo.query.DeductionDetailQuery;
import com.xhnj.service.DeductionDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: ums-admin
 * @description: 代扣明细实现类
 * @author: xiel
 * @create: 2021-11-05 17:26
 **/
@Service
public class DeductionDetailServiceImpl implements DeductionDetailService {
    @Resource
    TDeductionDetailMapper TDeductionDetailMapper;
    @Override
    public IPage listPage(DeductionDetailQuery tbatchDtl, Integer pageSize, Integer pageNum) {
        IPage<TBatchDtl> page = new Page<>(pageNum, pageSize);
        return TDeductionDetailMapper.listPage(page,tbatchDtl);
    }
}
