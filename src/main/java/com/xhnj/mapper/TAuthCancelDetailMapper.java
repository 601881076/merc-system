package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TWithholdCancel;
import com.xhnj.pojo.query.AuthCancelDetailQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program:
 * @description: 授权取消明细Mapper
 * @author: xiel
 * @create: 2021-11-05 17:32
 **/
public interface TAuthCancelDetailMapper extends BaseMapper<TWithholdCancel> {

    IPage<TWithholdCancel> listPage(IPage<TWithholdCancel> page, @Param("tbatchDtl") AuthCancelDetailQuery tbatchDtl);

    List<TWithholdCancel> listPage(@Param("tbatchDtl") AuthCancelDetailQuery tbatchDtl);

}
