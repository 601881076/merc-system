package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TBatchDtl;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.pojo.query.AuthCancelDetailQuery;
import com.xhnj.pojo.query.DeductionDetailQuery;
import org.apache.ibatis.annotations.Param;

/**
 * @program:
 * @description: 授权取消明细Mapper
 * @author: xiel
 * @create: 2021-11-05 17:32
 **/
public interface TAuthCancelDetailMapper extends BaseMapper<TWithholdAgree> {

    IPage<TWithholdAgree> listPage(IPage<TWithholdAgree> page, @Param("tbatchDtl") AuthCancelDetailQuery tbatchDtl);

}
