package com.xhnj.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.common.exception.BusinessException;
import com.xhnj.mapper.TConfigMapper;
import com.xhnj.mapper.TMenuMapper;
import com.xhnj.mapper.TRoleMapper;
import com.xhnj.mapper.TRoleMenuMapper;
import com.xhnj.model.TConfig;
import com.xhnj.model.TMenu;
import com.xhnj.model.TRole;
import com.xhnj.model.TRoleMenu;
import com.xhnj.service.TConfigService;
import com.xhnj.service.TRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 配置表表 服务实现类
 * </p>
 *
 * @author xiel
 * @since 2021-11-06
 */
@Service
public class TConfigServiceImpl extends ServiceImpl<TConfigMapper, TConfig> implements TConfigService {

    @Resource
    private TConfigMapper configMapper;


    @Override
    public List<TConfig> selctOne(TConfig configs) {
        return configMapper.selctOne(configs);
    }

    @Override
    public TConfig selectByFieldName(TConfig configs) {

        return configMapper.selectByFieldName(configs);
    }


    @Override
    public int updOne(TConfig configs) {
        return configMapper.updOne(configs);
    }




}
