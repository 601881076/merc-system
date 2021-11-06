package com.xhnj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.common.exception.BusinessException;
import com.xhnj.mapper.TBatchCheckMapper;
import com.xhnj.mapper.TDismissBatchCheckMapper;
import com.xhnj.model.TBatchCheck;
import com.xhnj.model.TBatchNo;
import com.xhnj.model.TDismissBatchCheck;
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

    @Resource
    private TDismissBatchCheckMapper dismissBatchCheckMapper;

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
            batchCheck.setStatus(1);
            list.add(batchCheck);
        }
        log.info("list:"+list.toString());
        if(list.size() ==0){
            throw new BusinessException("批次已提交，请勿重复提交");
        }else {
            return batchCheckMapper.insert(list);
        }
    }

    @Override
    public int insertCheck(List<String> batchIds){
        log.info("授权取消审核提交"+batchIds.toString());
        String username = UserUtil.getCurrentAdminUser().getUsername();
        Long id = UserUtil.getCurrentAdminUser().getId();
        List<TDismissBatchCheck> list=new ArrayList<>();
        TDismissBatchCheck dismissBatchCheck = null;
        for (int i=0;i<batchIds.size();i++){
            String batch_id=batchIds.get(i);
            Long batchid =dismissBatchCheckMapper.secDisOne(batch_id);
            if (batchid == null) {
                continue;
            }
            if(dismissBatchCheckMapper.secChenckOne( batch_id)>0){
                continue;
            }
            dismissBatchCheck = new TDismissBatchCheck();
            dismissBatchCheck.setBatchId(batchid);
            dismissBatchCheck.setSystemBatch(batch_id);
            dismissBatchCheck.setUpUserId(id);
            dismissBatchCheck.setUpUserName(username);
            dismissBatchCheck.setStatus(1);
            list.add(dismissBatchCheck);
        }
        log.info("list:"+list.toString());
        if(list.size() ==0){
            throw new BusinessException("批次已提交，请勿重复提交");
        }else {
            return dismissBatchCheckMapper.addbatch(list);
        }
    }
}
