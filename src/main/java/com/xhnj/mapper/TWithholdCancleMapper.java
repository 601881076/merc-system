package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.model.TWithholdCancel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 代扣协议取消表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@Repository
public interface TWithholdCancleMapper extends BaseMapper<TWithholdCancel> {

    List<TWithholdCancel> getByCardNoAndAgreeId(@Param("cancelList") List<TWithholdCancel> cancelList);

    List<TWithholdCancel> isRepeatData(@Param("withholdCancle") TWithholdCancel withholdCancle);

    int addBatch(@Param("list") List<TWithholdCancel> list);

    IPage<TWithholdCancel> listPageByBatchNo(IPage<TWithholdCancel> page, String batchNo);

    IPage<TWithholdAgree> conditionQuery(IPage<TWithholdAgree> page,@Param("withholdAgree") TWithholdAgree withholdAgree);
}
