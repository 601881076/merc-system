package com.xhnj.service;

import com.xhnj.model.WithholdFailExcel;
import com.xhnj.model.WithholdSuccessExcel;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.pojo.vo.WithholdDetailVO;

import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/9/20 16:25   
 */
public interface TBatchDtlService {

    List<WithholdSuccessExcel> getList(WithholdParam withholdParam);

    List<WithholdFailExcel> getFailList(WithholdParam withholdParam);

    List<WithholdDetailVO> getByBatchNoList(List<String> list);
}
