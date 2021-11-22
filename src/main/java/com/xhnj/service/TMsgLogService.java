package com.xhnj.service;

import com.xhnj.model.TMsgLog;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Date 2021/11/11 11:22
 * @Author lk
 **/
public interface TMsgLogService {

    List<TMsgLog> findDistinctBankByMap(Map<String, Object> map);

}
