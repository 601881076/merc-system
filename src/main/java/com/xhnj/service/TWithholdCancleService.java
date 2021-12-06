package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TBatchDtl;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.model.TWithholdCancle;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 代扣协议取消表 服务类
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
public interface TWithholdCancleService extends IService<TWithholdCancle> {

    /**
     * 分页查询授权解除明细
     * @param batchNo
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage listPage(String batchNo, Integer pageSize, Integer pageNum);


    IPage<TWithholdAgree> conditionQuery(TWithholdAgree withholdAgree, Integer pageSize, Integer pageNum);

    /**
     * 分批插入
     * @param list
     * @param count 分批数量
     * @return
     */
    int partialInsert(List<TWithholdCancle> list, int count);
}
