package com.xhnj.service.impl;

import com.xhnj.mapper.TLogMapper;
import com.xhnj.model.TLog;
import com.xhnj.service.SysLogService;
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
