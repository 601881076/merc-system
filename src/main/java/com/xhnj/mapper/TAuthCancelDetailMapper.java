package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TWithholdCancle;
import com.xhnj.pojo.query.AuthCancelDetailQuery;
import org.apache.ibatis.annotations.Param;

/**
 * @program:
 * @description: 授权取消明细Mapper
 * @author: xiel
 * @create: 2021-11-05 17:32
 **/
public interface TAuthCancelDetailMapper extends BaseMapper<TWithholdCancle> {

    IPage<TWithholdCancle> listPage(IPage<TWithholdCancle> page, @Param("tbatchDtl") AuthCancelDetailQuery tbatchDtl);

}
