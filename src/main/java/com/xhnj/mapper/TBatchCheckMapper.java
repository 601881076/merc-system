package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TBatchCheck;
import com.xhnj.model.TBatchCheckSuccessExcel;
import com.xhnj.pojo.query.DisMissBatchQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 代扣批次审批表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-10-25
 */
public interface TBatchCheckMapper extends BaseMapper<TBatchCheck> {
    int insert(@Param("list") List<TBatchCheck> list);


    int insertCheck(@Param("list") List<TBatchCheck> batchId);

    List<TBatchCheckSuccessExcel> getListToBatchNo(@Param("dismissBatch") DisMissBatchQuery dismissBatch);

    int updateStatusByBatchNo(@Param("status") int status,
                              @Param("checkResult") int checkResult,
                              @Param("batchNo") List<String> batchNo,
                              @Param("admin")TAdmin admin);

    Long  secChenckOne(@Param("batchno")String batchno);

    Long  secBatchOne(@Param("batchno")String batchno);

    Long  secDisOne(@Param("batchno")String batchno);
}
