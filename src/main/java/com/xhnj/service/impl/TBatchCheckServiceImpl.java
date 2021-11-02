package com.xhnj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.mapper.TBatchCheckMapper;
import com.xhnj.model.TBatchCheck;
import com.xhnj.model.TBatchNo;
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
        log.info("代扣审核提交"+batchIds.toString());
        String username = UserUtil.getCurrentAdminUser().getUsername();
        Long id = UserUtil.getCurrentAdminUser().getId();
        List<TBatchCheck> list=new ArrayList<>();
        for (int i=0;i<batchIds.size();i++){
            String batch_id=batchIds.get(i);
            log.info("batch_id:"+batch_id);
            Long batchid =batchCheckMapper.secBatchOne(batch_id);
            if (batchid == null) {
                continue;
            }
            log.info("batchid:"+batchid);
            if(batchCheckMapper.secChenckOne( batch_id)>0){
                continue;
            }
            TBatchCheck batchCheck=new TBatchCheck();
            batchCheck.setBatchId(batchid);
            batchCheck.setBatchNo(batch_id);
            batchCheck.setUpUserId(id);
            batchCheck.setUpUserName(username);
            batchCheck.setType(0);
            list.add(batchCheck);
        }
        log.info("list:"+list.toString());
        if(list.size() ==0){
            return 0;
        }else {
            return batchCheckMapper.insert(list);
        }
    }
    @Override
    public int insertCheck(List<String> batchIds){
        log.info("授权取消审核提交"+batchIds.toString());
        String username = UserUtil.getCurrentAdminUser().getUsername();
        Long id = UserUtil.getCurrentAdminUser().getId();
        List<TBatchCheck> list=new ArrayList<>();
        for (int i=0;i<batchIds.size();i++){
            String batch_id=batchIds.get(i);
            Long batchid =batchCheckMapper.secDisOne(batch_id);
            if (batchid == null) {
                continue;
            }
            if(batchCheckMapper.secChenckOne( batch_id)>0){
                continue;
            }
            TBatchCheck batchCheck=new TBatchCheck();
            batchCheck.setBatchId(batchid);
            batchCheck.setBatchNo(batch_id);
            batchCheck.setUpUserId(id);
            batchCheck.setUpUserName(username);
            batchCheck.setType(1);
            list.add(batchCheck);
        }
        log.info("list:"+list.toString());
        if(list.size() ==0){
            return 0;
        }else {
            return batchCheckMapper.insertCheck(list);
        }
    }
}
