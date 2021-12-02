package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TDismissBatchCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.pojo.query.DisMissBatchQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 授权取消批次审批表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@Repository
public interface TDismissBatchCheckMapper extends BaseMapper<TDismissBatchCheck> {
    IPage listPage(IPage<DisMissBatchQuery> page, @Param("dismissBatch") DisMissBatchQuery dismissBatch, @Param("statusList")List<String> statusList);

    int addbatch(@Param("list") List<TDismissBatchCheck> list);

    Long secChenckOne(@Param("batchno")String batchno);

    Long secBatchOne(@Param("batchno")String batchno);

    Long secDisOne(@Param("batchno")String batchno);

    int updateStatusByBatchNo(@Param("status") int status,
                              @Param("checkResult") int checkResult,
                              @Param("batchNo") List<String> batchNo,
                              @Param("admin") TAdmin admin);

    /**
     * 根据batchId对授权取消表进行批量修改状态
     * @param status 审核状态(0->待提交;1->已提交;2->审核通过;3->审核拒绝)
     * @param batchIdList
     * @return
     */
    int updateByBatchIdList(@Param("status") int status, @Param("list") List<String> batchIdList);

    /**
     * 校验需要修改的批次中是否存在已提交的数据
     * @param batchNoList
     * @return
     */
    int selectIsCommitData(@Param("list") List<String> batchNoList);
}
