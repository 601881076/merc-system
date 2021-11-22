package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TBatchNo;
import com.xhnj.pojo.query.FeeMoneyQuery;
import com.xhnj.pojo.query.PoundageParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 批次表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-08-16
 */
@Repository
public interface TBatchNoMapper extends BaseMapper<TBatchNo> {
    IPage<TBatchNo> listPage(IPage<TBatchNo> page,@Param("batchNo") TBatchNo batchNo);
    int deleteById(@Param("id") Long id);

    /**
     * PDF导出，数据查询
     * @param poundageParam
     * @return
     */
    List<FeeMoneyQuery> getFeeMoneySum(@Param("param") PoundageParam poundageParam);
}
