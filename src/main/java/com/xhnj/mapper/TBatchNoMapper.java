package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TBatchNo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 批次表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-08-16
 */
public interface TBatchNoMapper extends BaseMapper<TBatchNo> {
    IPage<TBatchNo> listPage(IPage<TBatchNo> page,@Param("batchNo") TBatchNo batchNo);
    int deleteById(@Param("id") Long id);
}
