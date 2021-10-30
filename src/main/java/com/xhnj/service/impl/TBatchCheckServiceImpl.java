package com.xhnj.service.impl;

import com.xhnj.mapper.TBatchDtlMapper;
import com.xhnj.model.TBatchCheck;
import com.xhnj.mapper.TBatchCheckMapper;
import com.xhnj.service.TBatchCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.util.UserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 批次审批表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-10-25
 */
@Service
public class TBatchCheckServiceImpl extends ServiceImpl<TBatchCheckMapper, TBatchCheck> implements TBatchCheckService {
    @Resource
    private TBatchCheckMapper batchCheckMapper;
    public int insert(List<String> batchIds){
        String username = UserUtil.getCurrentAdminUser().getUsername();
        Long id = UserUtil.getCurrentAdminUser().getId();
        List<TBatchCheck> list=new ArrayList<>();
        for (int i=0;i<batchIds.size();i++){
            Long batchid=Long.parseLong(batchIds.get(i));
            TBatchCheck batchCheck=new TBatchCheck();
            batchCheck.setBatchId(batchid);
            batchCheck.setUpUserId(id);
            batchCheck.setUpUserName(username);
            list.add(batchCheck);
        }
        return batchCheckMapper.insert(list);
    }
}
