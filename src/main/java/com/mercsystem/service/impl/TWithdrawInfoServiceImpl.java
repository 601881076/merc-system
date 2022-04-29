package com.mercsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.model.TWithdrawInfo;
import com.mercsystem.mapper.TWithdrawInfoMapper;
import com.mercsystem.service.TWithdrawInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提现记录表 服务实现类
 * </p>
 *
 * @author songcn
 * @since 2022-04-28
 */
@Service
public class TWithdrawInfoServiceImpl extends ServiceImpl<TWithdrawInfoMapper, TWithdrawInfo> implements TWithdrawInfoService {

    @Resource
    private TWithdrawInfoMapper tWithdrawInfoMapper;

    @Override
    public Page qryTWithdrawInfo(Page page, Map param) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(param);
        Page  selectPage = tWithdrawInfoMapper.selectPage(page,queryWrapper);
        return selectPage;
    }

    @Override
    public List<TWithdrawInfo> outInputWithdarwInfo() {
        return null;
    }

    @Override
    public Integer chelkWithdatwInfo(Integer id,Integer status) {
        //先查看是否满足审核
        TWithdrawInfo tWithdrawInfo = qryWithdatwById(id);
        if (tWithdrawInfo.getStatus()!=1 && tWithdrawInfo.getStatus()!=2){
            //满足审核条件
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.set("status",status);
           int ret = tWithdrawInfoMapper.update(null,updateWrapper);
           return ret;
        }
        return -1;
    }

    @Override
    public TWithdrawInfo qryWithdatwById(Integer id) {
        return tWithdrawInfoMapper.selectById(id);
    }
}
