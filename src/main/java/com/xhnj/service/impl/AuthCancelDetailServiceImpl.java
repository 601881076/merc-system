package com.xhnj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TAuthCancelDetailMapper;
import com.xhnj.mapper.TDeductionDetailMapper;
import com.xhnj.model.TBatchDtl;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.pojo.query.AuthCancelDetailQuery;
import com.xhnj.pojo.query.DeductionDetailQuery;
import com.xhnj.service.AuthCancelDetailService;
import com.xhnj.service.DeductionDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: ums-admin
 * @description: 授权取消明细实现类
 * @author: xiel
 * @create: 2021-11-05 17:26
 **/
@Service
public class AuthCancelDetailServiceImpl implements AuthCancelDetailService {
    @Resource
    TAuthCancelDetailMapper authCancelDetailMapper;
    @Override
    public IPage listPage(AuthCancelDetailQuery detailQuery, Integer pageSize, Integer pageNum) {
        IPage<TWithholdAgree> page = new Page<>(pageNum, pageSize);
        return authCancelDetailMapper.listPage(page,detailQuery);
    }


}
