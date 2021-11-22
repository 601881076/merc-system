package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.*;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.pojo.vo.WithholdDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 平台流水表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-08-11
 */
@Repository
public interface TBatchDtlMapper extends BaseMapper<TBatchDtl> {
    IPage<TBatchDtl> listPageByBatchNo(IPage<TBatchDtl> page, @Param("batchNo") String batchNo);

    List<WithholdDetailVO> getByBatchNoList(@Param("list") List<String> list);

    List<WithholdSuccessExcel> getList(@Param("withholdParam")WithholdParam withholdParam);

    List<TBatchCheckSuccessExcel> getListToBatchNo(@Param("dismissBatch") DisMissBatchQuery dismissBatch);

    List<WithholdFailExcel> getFailList(@Param("withholdParam")WithholdParam withholdParam);

    int addBatch(@Param("list") List<TBatchDtl> list);

}
