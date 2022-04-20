package com.mercsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.mapper.TLogMapper;
import com.mercsystem.model.TLog;
import com.mercsystem.service.TLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TLogServiceImpl implements TLogService {

    @Resource
    TLogMapper logMapper;

    @Override
    public IPage listPage(TLog tlog, Integer pageSize, Integer pageNum) {
        IPage<TLog> page = new Page<>(pageNum, pageSize);
        return logMapper.listPage(page, tlog);
    }
}