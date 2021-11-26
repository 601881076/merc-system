package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TBatchCheck;
import com.xhnj.model.TWithholdCancleExcel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 代扣批次审批表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-10-25
 */
@Repository
public interface TBatchCheckMapper extends BaseMapper<TBatchCheck> {
    int insert(@Param("list") List<TBatchCheck> list);


    int insertCheck(@Param("list") List<TBatchCheck> batchId);

    List<TWithholdCancleExcel> getListToBatchNo(@Param("list") List<String> list);

    int updateStatusByBatchNo(@Param("status") int status,
                              @Param("checkResult") int checkResult,
                              @Param("batchNo") List<String> batchNo,
                              @Param("admin")TAdmin admin);

    Long  secChenckOne(@Param("batchno")String batchno);

    Long  secBatchOne(@Param("batchno")String batchno);

    Long  secDisOne(@Param("batchno")String batchno);

    /**
     * 根据批次查询审核已通过的
     * @param batchNoList
     * @return
     */
    List<TBatchCheck> selectCheckPassBatch(@Param("batchNoList")List<String> batchNoList);

    /**
     * 修改审批表状态
     * @param batchIdList 需要修改的batchId
     * @param status 审核状态(0->待提交;1->已提交;2->审核通过;3->审核拒绝)
     * @return
     */
    int updateStatus(@Param("status") int status,
                     @Param("list") List<String> batchIdList);
}
