package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.WithholdFailExcel;
import com.xhnj.model.WithholdSuccessExcel;
import com.xhnj.model.TPlatformserial;
import com.xhnj.pojo.query.WithholdParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 平台流水表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-08-11
 */
public interface TPlatformserialMapper extends BaseMapper<TPlatformserial> {
    IPage<TPlatformserial> listPageByBatchNo(IPage<TPlatformserial> page, @Param("batchNo") String batchNo);

    List<TPlatformserial> getByOrderNoList(@Param("list") List<String> list);

    List<WithholdSuccessExcel> getList(@Param("withholdParam")WithholdParam withholdParam);

    List<WithholdFailExcel> getFailList(@Param("withholdParam")WithholdParam withholdParam);

    int addBatch(@Param("list") List<TPlatformserial> list);

}
