package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TDismissBatchCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.pojo.query.DisMissBatchQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 授权取消批次审批表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
public interface TDismissBatchCheckMapper extends BaseMapper<TDismissBatchCheck> {
    IPage listPage(IPage<DisMissBatchQuery> page, @Param("dismissBatch") DisMissBatchQuery dismissBatch);

    int addbatch(@Param("list") List<TDismissBatchCheck> list);

    Long secChenckOne(@Param("batchno")String batchno);

    Long secBatchOne(@Param("batchno")String batchno);

    Long secDisOne(@Param("batchno")String batchno);

    int updateStatusByBatchNo(@Param("status") int status,
                              @Param("checkResult") int checkResult,
                              @Param("batchNo") List<String> batchNo,
                              @Param("admin") TAdmin admin);
}
