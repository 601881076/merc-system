package com.xhnj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.mapper.TBatchCheckMapper;
import com.xhnj.model.TBatchCheck;
import com.xhnj.service.TBatchCheckService;
import com.xhnj.util.UserUtil;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TBatchCheckServiceImpl extends ServiceImpl<TBatchCheckMapper, TBatchCheck> implements TBatchCheckService {
    @Resource
    private TBatchCheckMapper batchCheckMapper;
    @Override
    public int insert(List<String> batchIds){
        String username = UserUtil.getCurrentAdminUser().getUsername();
        Long id = UserUtil.getCurrentAdminUser().getId();
        List<TBatchCheck> list=new ArrayList<>();
        for (int i=0;i<batchIds.size();i++){
            Long batchid=Long.parseLong(batchIds.get(i));
            if(batchCheckMapper.secOne( batchid)){
                continue;
            }
            TBatchCheck batchCheck=new TBatchCheck();
            batchCheck.setBatchId(batchid);
            batchCheck.setUpUserId(id);
            batchCheck.setUpUserName(username);
            batchCheck.setType(0);
            list.add(batchCheck);
        }
        return batchCheckMapper.insert(list);
    }
    @Override
    public int insertCheck(List<String> batchIds){
        log.info("授权取消审核提交"+batchIds.toString());
        String username = UserUtil.getCurrentAdminUser().getUsername();
        Long id = UserUtil.getCurrentAdminUser().getId();
        List<TBatchCheck> list=new ArrayList<>();
        for (int i=0;i<batchIds.size();i++){
            Long batchid=Long.parseLong(batchIds.get(i));
            if(batchCheckMapper.secOne(batchid)){
                continue;
            }
            TBatchCheck batchCheck=new TBatchCheck();
            batchCheck.setBatchId(batchid);
            batchCheck.setUpUserId(id);
            batchCheck.setUpUserName(username);
            batchCheck.setType(1);
            list.add(batchCheck);
        }
        return batchCheckMapper.insertCheck(list);
    }
}
