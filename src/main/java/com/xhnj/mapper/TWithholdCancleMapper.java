package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.model.TWithholdCancle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 代扣协议取消表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
public interface TWithholdCancleMapper extends BaseMapper<TWithholdCancle> {

    List<TWithholdCancle> getByCardNo(@Param("cardNoList") List<String> cardNoList);

    int addBatch(@Param("list") List<TWithholdCancle> list);

    IPage<TWithholdCancle> listPageByBatchNo(IPage<TWithholdCancle> page, String batchNo);

    IPage<TWithholdAgree> conditionQuery(IPage<TWithholdAgree> page,@Param("withholdAgree") TWithholdAgree withholdAgree);
}
