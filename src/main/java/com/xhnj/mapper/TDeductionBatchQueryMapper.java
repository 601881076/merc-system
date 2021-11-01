package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.pojo.query.DeductionBatchQuery;
import com.xhnj.pojo.query.DisMissBatchQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 扣款批次审批 Mapper 查询接口
 * </p>
 *
 * @author lk
 * @since 2021-09-22
 */
public interface TDeductionBatchQueryMapper extends BaseMapper<DeductionBatchQuery> {
    IPage listPage(IPage<DeductionBatchQuery> page, @Param("dismissBatch") DeductionBatchQuery deductionBatchQuery);


}
