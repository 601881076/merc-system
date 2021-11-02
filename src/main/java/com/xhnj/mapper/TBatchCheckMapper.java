package com.xhnj.mapper;

import com.xhnj.model.TBatchCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.model.TBatchCheckSuccessExcel;
import com.xhnj.pojo.query.DisMissBatchQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 批次审批表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-10-25
 */
public interface TBatchCheckMapper extends BaseMapper<TBatchCheck> {
    int insert(@Param("list") List<TBatchCheck> list);

    List<TBatchCheckSuccessExcel> getListToBatchNo(@Param("dismissBatch") DisMissBatchQuery dismissBatch);

    int updateStatusByBatchNo(@Param("status") int status, @Param("checkResult") int checkResult, @Param("batchNo") List<String> batchNo);

}
