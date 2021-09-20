package com.xhnj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.mapper.TPlatformserialMapper;
import com.xhnj.model.WithholdFailExcel;
import com.xhnj.model.WithholdSuccessExcel;
import com.xhnj.model.TPlatformserial;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.service.TPlatformserialService;
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
public class TPlatformserialServiceImpl extends ServiceImpl<TPlatformserialMapper, TPlatformserial> implements TPlatformserialService {
    @Resource
    private TPlatformserialMapper platformserialMapper;

    @Override
    public List<WithholdSuccessExcel> getList(WithholdParam withholdParam) {
        return platformserialMapper.getList(withholdParam);
    }

    @Override
    public List<WithholdFailExcel> getFailList(WithholdParam withholdParam) {
        return platformserialMapper.getFailList(withholdParam);
    }
}
