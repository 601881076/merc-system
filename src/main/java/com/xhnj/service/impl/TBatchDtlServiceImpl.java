package com.xhnj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.mapper.TBatchDtlMapper;
import com.xhnj.model.WithholdFailExcel;
import com.xhnj.model.WithholdSuccessExcel;
import com.xhnj.model.TBatchDtl;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.pojo.vo.WithholdDetailVO;
import com.xhnj.service.TBatchDtlService;
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
public class TBatchDtlServiceImpl extends ServiceImpl<TBatchDtlMapper, TBatchDtl> implements TBatchDtlService {
    @Resource
    private TBatchDtlMapper platformserialMapper;

    @Override
    public List<WithholdSuccessExcel> getList(WithholdParam withholdParam) {
        return platformserialMapper.getList(withholdParam);
    }

    @Override
    public List<WithholdDetailVO> getByBatchNoList(List<String> list) {
        return platformserialMapper.getByBatchNoList(list);
    }

    @Override
    public List<WithholdFailExcel> getFailList(WithholdParam withholdParam) {
        return platformserialMapper.getFailList(withholdParam);
    }

}
