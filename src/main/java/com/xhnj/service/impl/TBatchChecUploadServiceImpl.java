package com.xhnj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.mapper.TBatchCheckMapper;
import com.xhnj.mapper.TBatchDtlMapper;
import com.xhnj.model.TBatchDtl;
import com.xhnj.model.TWithholdCancelExcel;
import com.xhnj.service.TBatchChecUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 平台流水表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-08-11
 */
@Service
@Slf4j
public class TBatchChecUploadServiceImpl extends ServiceImpl<TBatchDtlMapper, TBatchDtl> implements TBatchChecUploadService {

    @Resource
    TBatchCheckMapper batchCheckMapper;

    @Override
    public List<TWithholdCancelExcel> getListToBatchCheck(List<String> list) {
        return batchCheckMapper.getListToBatchNo(list);
    }


}
