package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.model.TWithholdAgreeExcel;
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

    IPage<TWithholdAgree> conditionQuery(IPage<TWithholdAgree> page,@Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 授权报告查询 - 条件查询成功数据
     * @param pageNum 当前页
     * @param pageSize 当前页显示数量
     * @param withholdAgree
     * @return
     */
    List<WithholdAgreeQuery> selectSuccess(Integer pageSize, Integer pageNum,@Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 授权报告查询 - 查询授权取消
     * @param page
     * @param withholdAgree
     * @return
     */
    IPage<WithholdAgreeQuery> selectAuthorizationCancel(IPage<WithholdAgreeQuery> page, @Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 授权报告查询 - 精准查询第三步, 查询授权取消数据
     * @param withholdAgree
     * @return
     */
    List<WithholdAgreeQuery> selectAuthorizationCancel(@Param("withholdAgree") TWithholdAgree withholdAgree);


    /**
     * 授权报告查询 - 查询授权取消
     * @param withholdAgree
     * @return
     */
    List<TWithholdAgreeExcel> selectAuthorizationCancelExport(@Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 条件查询导出
     * @param withholdAgree
     * @return
     */
    List<TWithholdAgreeExcel> conditionQueryList(@Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 授权查询报告页面 -- 精准查询第一步, 查询已授权数据
     * @param withholdAgree
     * @return
     */
    List<WithholdAgreeQuery> selectSuccessList(@Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 授权查询报告页面 -- 精准查询第二步, 查询未完成授权数据
     * @param withholdAgree
     * @return
     */
    List<WithholdAgreeQuery> notCompletedAuth(@Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 授权报告查询页面 -- 未完成授权状态查询
     * @param withholdAgree
     * @return
     */
    List<WithholdAgreeQuery> notCompletedAuth(Integer pageSize, Integer pageNum,
                                                  @Param("withholdAgree") TWithholdAgree withholdAgree);


    /**
     * 授权报告查询页面 -- 未完成授权状态汇总查询
     * @param withholdAgree
     * @return
     */
    int notCompletedAuthCount(@Param("withholdAgree") TWithholdAgree withholdAgree);


    /**
     * 授权报告查询页面 -- 未完成授权状态查询导出
     * @param withholdAgree
     * @return
     */
    List<TWithholdAgreeExcel> notCompletedAuthExport( @Param("withholdAgree") TWithholdAgree withholdAgree);


    int addBatch(@Param("list") List<TWithholdAgree> list);

    List<TWithholdAgree> selectBankcode(@Param("bankCode") String bankCode);

    /**
     * 授权成功count查询
     * @param withholdAgree
     * @return
     */
    int selectSuccessCount(@Param("withholdAgree") TWithholdAgree withholdAgree);

}
