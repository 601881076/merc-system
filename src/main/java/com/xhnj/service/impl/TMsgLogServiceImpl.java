package com.xhnj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.mapper.TMsgLogMapper;
import com.xhnj.model.TMsgLog;
import com.xhnj.service.TMsgLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Date 2021/11/11 11:23
 * @Author lk
 **/

@Service
public class TMsgLogServiceImpl extends ServiceImpl<TMsgLogMapper, TMsgLog> implements TMsgLogService {
    @Resource
    private TMsgLogMapper msgLogMapper;

    @Override
    public List<TMsgLog> findDistinctBankByMap(Map<String, Object> map) {
        return msgLogMapper.findDistinctBankByMap(map);
    }
}
