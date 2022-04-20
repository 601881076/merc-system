package com.mercsystem.service.impl;

import com.mercsystem.mapper.TLogMapper;
import com.mercsystem.model.TLog;
import com.mercsystem.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLogServiceImpl implements SysLogService {


    @Resource
    private TLogMapper logMapper;

    @Override
    public int insertAdmin(TLog tLog) {
        return logMapper.insert(tLog);
    }
}
