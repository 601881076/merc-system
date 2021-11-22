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

    IPage<TWithholdAgree> conditionQuery(IPage<TWithholdAgree> page,@Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 授权报告查询 - 条件查询成功数据
     * @param page
     * @param withholdAgree
     * @return
     */
    IPage<WithholdAgreeQuery> selectSuccess(IPage<WithholdAgreeQuery> page,@Param("withholdAgree") TWithholdAgree withholdAgree);

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
     * @param page
     * @param withholdAgree
     * @return
     */
    IPage<WithholdAgreeQuery> notCompletedAuth(IPage<WithholdAgreeQuery> page,
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
     * 授权报告查询 -- 未完成授权状态查询
     * 查出短信表有数据，授权表无数据的卡号+协议号
     * @param withholdAgree
     * @return
     */
    List<WithholdAgreeQuery> selectSmsExistsAndAuthorizationNotExists(@Param("withholdAgree") TWithholdAgree withholdAgree);

    /**
     * 授权报告查询 -- 未完成授权状态查询
     * 根据卡号和协议号查询出 未完成授权状态的数据
     * @param smsList
     * @return
     */
    List<TWithholdAgreeSms> selectSmsExistsAndAuthorizationNotExistsList(@Param("smsList") List<TWithholdAgreeSms> smsList,
                                                                         @Param("withholdAgree") TWithholdAgree withholdAgree,
                                                                         @Param("minNumber") Integer minNumber,
                                                                         @Param("pageSize") Integer pageSize);
    /**
     * 授权报告查询 -- 未完成授权状态查询
     * 根据卡号和协议号查询出 未完成授权状态的数据 总数量
     * @param smsList
     * @return
     */
    int selectSmsExistsAndAuthorizationNotExistsCount(@Param("smsList") List<WithholdAgreeQuery> smsList,
                                                                         @Param("withholdAgree") TWithholdAgree withholdAgree);


    /**
     * 授权报告查询 -- 未完成授权状态查询
     * 短信表、授权表都存在数据，但是授权表对应状态是失败的 -- count查询
     * @param withholdAgree
     * @param minNumber 分页查询最小值
     * @param pageSize 分页查询当前页数量
     * @return
     */
    int selectSmsAuthorizationBothExistsCount(@Param("withholdAgree") TWithholdAgree withholdAgree,
                                         @Param("minNumber") Integer minNumber,
                                         @Param("pageSize") Integer pageSize);

    /**
     * 授权报告查询 -- 未完成授权状态查询
     * 短信表、授权表都存在数据，但是授权表对应状态是失败的 -- 数据查询
     * @param withholdAgree
     * @param minNumber 分页查询最小值
     * @param pageSize 分页查询当前页数量
     * @return
     */
    List<TWithholdAgreeSms> selectSmsAuthorizationBothExistsList(@Param("withholdAgree") TWithholdAgree withholdAgree,
                                         @Param("minNumber") Integer minNumber,
                                         @Param("pageSize") Integer pageSize);

    /**
     * @Auther: zhanglujun
     * @Date: 2021/11/18 下午5:09
     * @Description: TODO
     * @param:
     * 获取导出数据
     */
    List<TWithholdAgree> selectList(@Param("withholdAgree") TWithholdAgree withholdAgree);

}
