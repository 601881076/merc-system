package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TDismissBatchExcel;
import com.xhnj.pojo.query.DisMissBatchQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 授权取消批次表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-09-22
 */
@Repository
public interface TDismissBatchMapper extends BaseMapper<TDismissBatch> {
    IPage<TDismissBatch> listPage(IPage<TDismissBatch> page,@Param("dismissBatch") TDismissBatch dismissBatch);

    IPage listPageDouble(IPage<DisMissBatchQuery> page, @Param("dismissBatch") TDismissBatch dismissBatch);

    /**
     * 授权取消列表分页查询
     * @param page
     * @param dismissBatch
     * @return
     */
    IPage<TDismissBatch> cancelPage(IPage<TDismissBatch> page, @Param("dismissBatch") TDismissBatch dismissBatch);


    List<TDismissBatchExcel> cancelExport(@Param("dismissBatch") TDismissBatchExcel dismissBatch);



    int deleteById(@Param("id")Long id);

    int add(@Param("dismissBatch") TDismissBatch dismissBatch);
}
