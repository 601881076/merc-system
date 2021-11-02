package com.xhnj.service;

import com.xhnj.model.TBatchCheckSuccessExcel;
import com.xhnj.model.WithholdFailExcel;
import com.xhnj.model.WithholdSuccessExcel;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.pojo.vo.WithholdDetailVO;

import java.util.List;

/**
* @Description:
* @Author:         tan_yi
* @CreateDate:     2021/11/2 15:56
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/2 15:56
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public interface TBatchChecUploadService {


    List<TBatchCheckSuccessExcel> getListToBatchCheck(DisMissBatchQuery dismissBatch);

}
