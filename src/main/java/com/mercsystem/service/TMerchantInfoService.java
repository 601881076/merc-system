package com.mercsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.model.TMerchantInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mercsystem.pojo.query.AddMerchant;
import com.mercsystem.pojo.query.ExlInputMerchant;
import com.mercsystem.pojo.query.QryMerchantParam;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商户表 服务类
 * </p>
 *
 * @author songcn
 * @since 2022-04-22
 */
public interface TMerchantInfoService extends IService<TMerchantInfo> {


    Page qryTMerchantInfo(Page page, Map<String,Object> param);
    List<TMerchantInfo> exlTMerchantInfo(Map<String,Object> param);

    TMerchantInfo tmerchantInfo(Integer merc_id);

    Integer addTMerchant(AddMerchant tMerchantInfo);

    Integer delMerchantByMercId(Integer merc_id);

    Integer updateMerchantByMercId(Integer merc_id,Integer checkStatus);

    Integer updateMerchant(TMerchantInfo tMerchantInfo);

}
