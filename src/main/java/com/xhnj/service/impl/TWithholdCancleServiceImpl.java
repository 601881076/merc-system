package com.xhnj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.model.TWithholdCancel;
import com.xhnj.mapper.TWithholdCancleMapper;
import com.xhnj.service.TWithholdCancelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.util.BusinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 代扣协议取消表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@Service
public class TWithholdCancleServiceImpl extends ServiceImpl<TWithholdCancleMapper, TWithholdCancel> implements TWithholdCancelService {
    @Autowired
    private BusinUtil businUtil;

    @Resource
    private TWithholdCancleMapper withholdCancleMapper;


    @Override
    public IPage listPage(String batchNo, Integer pageSize, Integer pageNum) {
        IPage<TWithholdCancel> page = new Page<>(pageNum, pageSize);
        List<TWithholdCancel> records = page.getRecords();
        records.stream().forEach(e ->e.setCardNo(businUtil.maskBankCard(e.getCardNo())));
        records.stream().forEach(e ->e.setCertNo(businUtil.maskBankCard(e.getCertNo())));
        records.stream().forEach(e ->e.setMobileNo(businUtil.maskPhone(e.getMobileNo())));
        page.setRecords(records);
        return withholdCancleMapper.listPageByBatchNo(page,batchNo);
    }

    @Override
    public IPage<TWithholdAgree> conditionQuery(TWithholdAgree withholdAgree, Integer pageSize, Integer pageNum) {
        IPage<TWithholdAgree> page = new Page<>(pageNum, pageSize);
        return withholdCancleMapper.conditionQuery(page,withholdAgree);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int partialInsert(List<TWithholdCancel> list, int count){
        int insertLength = list.size();
        int i = 0;
        List<TWithholdCancel> partialList = null;
        while (insertLength > count){
            partialList = list.subList(i, i+count);
            withholdCancleMapper.addBatch(partialList);
            i = i + count;
            insertLength = insertLength - count;
        }
        if(insertLength > 0){
            partialList = list.subList(i, i+insertLength);
            withholdCancleMapper.addBatch(partialList);
        }
        return 1;
    }
}
