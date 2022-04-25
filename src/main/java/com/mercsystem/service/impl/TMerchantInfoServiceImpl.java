package com.mercsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.mapper.TMercCoordinateMapper;
import com.mercsystem.model.TMercCoordinate;
import com.mercsystem.model.TMerchantInfo;
import com.mercsystem.mapper.TMerchantInfoMapper;
import com.mercsystem.pojo.query.AddMerchant;
import com.mercsystem.pojo.query.ExlInputMerchant;
import com.mercsystem.pojo.query.QryMerchantParam;
import com.mercsystem.service.TMerchantInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spatial4j.core.io.GeohashUtils;
import io.swagger.models.auth.In;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商户表 服务实现类
 * </p>
 *
 * @author songcn
 * @since 2022-04-22
 */
@Service
public class TMerchantInfoServiceImpl extends ServiceImpl<TMerchantInfoMapper, TMerchantInfo> implements TMerchantInfoService {
    @Autowired
    private TMerchantInfoMapper tMerchantInfoMapper;

    @Autowired
    private TMercCoordinateMapper tMercCoordinateMapper;


    @Override
    public Page qryTMerchantInfo(Page page, Map<String,Object> param ) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(param);
        Page  merchantInfoList = tMerchantInfoMapper.selectPage(page,queryWrapper);
        return merchantInfoList;
    }

    @Override
    public Integer updateMerchantByMercId(Integer merc_id,Integer checkStatus) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("merc_id",merc_id);
        updateWrapper.set("check_status",checkStatus);
        Integer rat = tMerchantInfoMapper.update(null,updateWrapper);
        return rat;
    }

    @Override
    public Integer updateMerchant(TMerchantInfo tMerchantInfo) {
        UpdateWrapper<TMerchantInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("merc_id",tMerchantInfo.getMercId());
        Integer ret = tMerchantInfoMapper.update(tMerchantInfo,updateWrapper);
        return ret;
    }

    @Override
    public Integer delMerchantByMercId(Integer merc_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("merc_id",merc_id);
        Integer rate = tMerchantInfoMapper.delete(queryWrapper);
        return rate;
    }

    @Override
    public TMerchantInfo tmerchantInfo(Integer merc_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("merc_id",merc_id);
        TMerchantInfo tMerchantInfo = tMerchantInfoMapper.selectOne(queryWrapper);
        return tMerchantInfo;
    }

    @Override
    public List<TMerchantInfo> exlTMerchantInfo(Map<String,Object> param) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(param);
        List<TMerchantInfo> merchantInfos = tMerchantInfoMapper.selectList(queryWrapper);
        return merchantInfos;
    }

    @Override
    public Integer addTMerchant(AddMerchant tMerchantInfo) {
        TMerchantInfo addtMerchantInfo = new TMerchantInfo();
        BeanUtils.copyProperties(tMerchantInfo,addtMerchantInfo);
        LocalDateTime startDateTime =  LocalDateTime.parse(tMerchantInfo.getManageStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDateTime =  LocalDateTime.parse(tMerchantInfo.getManageEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        addtMerchantInfo.setManageStartTime(startDateTime);
        addtMerchantInfo.setManageEndTime(endDateTime);
        if (tMerchantInfo.getRacmerchantId()==null){
            return -1;
        }else{
           TMerchantInfo qryMerchant= tMerchantInfoMapper.selectById(tMerchantInfo.getRacmerchantId());
           if (qryMerchant==null){
               return -1;
           }
        }
        Integer rat = tMerchantInfoMapper.insert(addtMerchantInfo);
        if (rat>0){
            Integer merc_id = addtMerchantInfo.getMercId();
            TMercCoordinate tMercCoordinate = new TMercCoordinate();
            tMercCoordinate.setMercId(merc_id);
            tMercCoordinate.setLatitude(addtMerchantInfo.getLatitude());
            tMercCoordinate.setLongitude(addtMerchantInfo.getLongitude());
            int len = 12;
            String geoHashCode = GeohashUtils.encodeLatLon(tMercCoordinate.getLatitude(), tMercCoordinate.getLongitude(), len);
            tMercCoordinate.setGeoHashCode(geoHashCode);
            int ret = tMercCoordinateMapper.insert(tMercCoordinate);
            if (ret>0){
                return ret;
            }
        }
        return -1;
    }
}
