package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xhnj.model.TBatchDtl;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.pojo.query.DeductionDetailQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @program: ums-admin
 * @description: 代扣明细Mapper
 * @author: xiel
 * @create: 2021-11-05 17:32
 **/
public interface TDeductionDetailMapper extends BaseMapper<TBatchDtl> {

    IPage<TWithholdAgree> listPage(IPage<TBatchDtl> page, @Param("tbatchDtl") DeductionDetailQuery tbatchDtl);

    List<TBatchDtl> listPage1(@Param("tbatchDtl") DeductionDetailQuery tbatchDtl);

}
