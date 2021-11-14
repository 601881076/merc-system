package com.xhnj.service;

import com.xhnj.model.TWithholdCancleExcel;

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


    List<TWithholdCancleExcel> getListToBatchCheck(List<String> systemBatchList);

}
