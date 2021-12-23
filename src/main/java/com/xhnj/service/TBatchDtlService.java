package com.xhnj.service;

import com.xhnj.model.*;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.pojo.vo.WithholdDetailVO;

import java.util.List;
import java.util.Set;

/*
 @Description
 *@author kang.li
 *@date 2021/9/20 16:25   
 */
public interface TBatchDtlService {

    List<WithholdSuccessExcel> getList(WithholdParam withholdParam);

    List<TBatchCheckSuccessExcel> getListToBatchCheck(DisMissBatchQuery dismissBatch);

    List<WithholdFailExcel> getFailList(WithholdParam withholdParam);

    List<WithholdDetailVO> getByBatchNoList(List<String> list);

    /**
     * 分批插入
     * @param list
     * @param count 分批数量
     * @return
     */
    int partialInsert(List<TBatchDtl> list, int count);

    /**
     * 处理代扣批次
     * @param batchNo
     * @param originalList
     * @param bankCodeSet
     * @return
     */
    int handleBatchDtl(TBatchNo batchNo, List<TBatchDtl> originalList, Set<String> bankCodeSet);
}
