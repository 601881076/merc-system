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


    @Override
    public int update(int option, List<String> batchNoList) {
        // option 操作类型：1-批准，2-拒绝

        // 审核结果(0->待审核；1->通过;2->拒绝)
        int status = option == 1 ? 1 : 2;
        // 审核状态(0->待提交;1->已提交;2->审核通过;3->审核拒绝)
        int checkResult = option == 1 ? 2 : 3;

        return dismissBatchMapper.updateStatusByBatchNo(status, checkResult, batchNoList);
    }
}
