package com.xhnj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TAdminRole;
import com.xhnj.model.TConfig;
import com.xhnj.pojo.query.UmsAdminUpdatePassParam;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 * 配置表 服务类
 * </p>
 *
 * @author xiel
 * @since 2021-11-06
 */
public interface TConfigService extends IService<TConfig> {

    List<TConfig> selctOne(TConfig configs);

    TConfig selectByFieldName(TConfig configs);
    int updOne(TConfig configs);



}
