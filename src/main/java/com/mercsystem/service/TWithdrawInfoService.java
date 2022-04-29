package com.mercsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.model.TWithdrawInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提现记录表 服务类
 * </p>
 *
 * @author songcn
 * @since 2022-04-28
 */
public interface TWithdrawInfoService extends IService<TWithdrawInfo> {

    Page qryTWithdrawInfo(Page page, Map param);

    List<TWithdrawInfo> outInputWithdarwInfo();

    Integer chelkWithdatwInfo(Integer id,Integer status);

    TWithdrawInfo qryWithdatwById(Integer id);
}
