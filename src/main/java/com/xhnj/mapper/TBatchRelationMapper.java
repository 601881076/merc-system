package com.xhnj.mapper;

import com.xhnj.model.TBatchRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: lk
 * DateTime: 2021-12-22 13:15
 */
public interface TBatchRelationMapper {

    int addBatch(@Param("list")List<TBatchRelation> list);
}
