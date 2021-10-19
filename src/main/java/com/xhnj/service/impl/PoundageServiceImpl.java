package com.xhnj.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xhnj.mapper.TPlatformserialMapper;
import com.xhnj.model.TPlatformserial;
import com.xhnj.pojo.query.PoundageParam;
import com.xhnj.service.PoundageService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/*
 @Description
 *@author kang.li
 *@date 2021/9/25 20:46   
 */
@Service
public class PoundageServiceImpl implements PoundageService {
    @Resource
    private TPlatformserialMapper platformserialMapper;

    @Override
    public void exportExcel(HttpServletResponse response, PoundageParam poundageParam) {
        QueryWrapper<TPlatformserial> wrapper = new QueryWrapper<>();
        wrapper.and(StrUtil.isNotBlank(poundageParam.getStartDate()),wp -> wp.ge("trade_time",poundageParam.getStartDate()));
        wrapper.and(StrUtil.isNotBlank(poundageParam.getEndDate()),wp -> wp.ge("trade_time",poundageParam.getEndDate()));
        platformserialMapper.selectList(wrapper);


    }
}
