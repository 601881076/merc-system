package com.xhnj.service.impl;

import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.service.TDismissBatchService;
import com.xhnj.service.WithholdAgreeDismissBaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class WithholdAgreeDismissBaseServiceImpl implements WithholdAgreeDismissBaseService {
    @Resource
    TDismissBatchService dismissBatchService;
    @Resource
    TDismissBatchMapper dismissBatchMapper;
    @Override
    public void exportExcel(HttpServletResponse response){
        dismissBatchService.exportExcel(response);
    }
    @Override
    public int delete(Long id){
        return dismissBatchMapper.deleteById(id);
    }

}
