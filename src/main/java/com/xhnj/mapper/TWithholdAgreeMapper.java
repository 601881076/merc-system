package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.model.TWithholdAgreeExcel;
import com.xhnj.model.TWithholdAgreeSms;
import com.xhnj.pojo.query.WithholdAgreeQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 代扣协议表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-08-10
 */
@Repository
public interface TWithholdAgreeMapper extends BaseMapper<TWithholdAgree> {
    IPage<TWithholdAgree> listPageByBatchNo(IPage<TWithholdAgree> page, @Param("batchNo") String batchNo);

    List<TWithholdAgree> getByCardNo(@Param("cardNoList") List<String> cardNoList, @Param("dealFlag") Integer dealFlag);

    /**
     * 授权报告历史查询
     * @param page
     * @param withholdAgree
     * @return
     */
    IPage<WithholdAgreeQuery> selectAgreeHistory(IPage<WithholdAgreeQuery> page, @Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 授权报告历史导出
     * @param withholdAgree
     * @return
     */
    List<TWithholdAgreeExcel> selectAgreeHistoryExport(@Param("withholdAgree") TWithholdAgree withholdAgree);


    /**
     * 授权查询报告页面 --  查询未完成授权数据汇总查询
     * @param withholdAgree
     * @return
     */
    int notCompletedAuthCount(@Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 授权报告查询页面 -- 未完成授权状态查询
     * @param withholdAgree
     * @return
     */
    List<WithholdAgreeQuery> notCompletedAuth( Integer pageSize, Integer pageNum,
                                                  @Param("withholdAgree") TWithholdAgree withholdAgree);


    /**
     * 授权报告查询页面 -- 未完成授权状态查询导出
     * @param withholdAgree
     * @return
     */
    List<TWithholdAgreeExcel> notCompletedAuthExport( @Param("withholdAgree") TWithholdAgree withholdAgree);


    int addBatch(@Param("list") List<TWithholdAgree> list);

    List<TWithholdAgree> selectBankcode(@Param("bankCode") String bankCode);


    /**
     * @Auther: zhanglujun
     * @Date: 2021/11/18 下午5:09
     * @Description: TODO
     * @param:
     * 获取导出数据
     */
    List<TWithholdAgree> selectList(@Param("withholdAgree") TWithholdAgree withholdAgree);
}
