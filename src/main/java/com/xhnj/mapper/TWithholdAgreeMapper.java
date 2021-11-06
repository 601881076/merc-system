package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.pojo.query.WithholdAgreeParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 代扣协议表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-08-10
 */
public interface TWithholdAgreeMapper extends BaseMapper<TWithholdAgree> {
    IPage<TWithholdAgree> listPageByBatchNo(IPage<TWithholdAgree> page, @Param("batchNo") String batchNo);

    List<TWithholdAgree> getByCardNo(@Param("cardNoList") List<String> cardNoList, @Param("dealFlag") Integer dealFlag);

    IPage<TWithholdAgree> conditionQuery(IPage<TWithholdAgree> page,@Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 查询短信发送未完成授权
     * @param page
     * @param withholdAgree
     * @return
     */
    IPage<TWithholdAgree> selectSmsIsNotCompleted(IPage<TWithholdAgree> page,
                                                  @Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 查询最新一条数据
     * @param page
     * @param withholdAgree
     * @return
     */
    IPage<TWithholdAgree> selectLatestDate(IPage<TWithholdAgree> page,
                                           @Param("withholdAgree") TWithholdAgree withholdAgree);

    int addBatch(@Param("list") List<TWithholdAgree> list);

}
