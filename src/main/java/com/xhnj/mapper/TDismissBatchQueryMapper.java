package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TDismissBatch;
import com.xhnj.pojo.query.DisMissBatchQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 授权取消批次表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-09-22
 */
public interface TDismissBatchQueryMapper extends BaseMapper<DisMissBatchQuery> {
    IPage listPageDouble(IPage<DisMissBatchQuery> page, @Param("dismissBatch") DisMissBatchQuery dismissBatch);


}
