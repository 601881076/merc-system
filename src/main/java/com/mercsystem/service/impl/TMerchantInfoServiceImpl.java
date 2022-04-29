package com.mercsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.mapper.TMercCoordinateMapper;
import com.mercsystem.model.TAdmin;
import com.mercsystem.model.TMercCoordinate;
import com.mercsystem.model.TMerchantInfo;
import com.mercsystem.mapper.TMerchantInfoMapper;
import com.mercsystem.pojo.query.AddMerchant;
import com.mercsystem.pojo.query.ExlInputMerchant;
import com.mercsystem.pojo.query.QryMerchantParam;
import com.mercsystem.service.TMerchantInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercsystem.util.UserUtil;
import com.spatial4j.core.io.GeohashUtils;
import io.swagger.models.auth.In;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    @Resource
    private TMerchantInfoMapper tMerchantInfoMapper;

    @Resource
    private TMercCoordinateMapper tMercCoordinateMapper;


    @Override
    public Page qryTMerchantInfo(Page page, Map<String,Object> param ) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("merc_name",param.get("merc_name").toString());
        param.remove("merc_name");
        queryWrapper.allEq(param);
        Page  merchantInfoList = tMerchantInfoMapper.selectPage(page,queryWrapper);
        return merchantInfoList;
    }

    @Override
    public Integer updateMerchantByMercId(Integer merc_id,Integer checkStatus) {
        TAdmin currentAdminUser = UserUtil.getCurrentAdminUser();
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("merc_id",merc_id);
        updateWrapper.set("check_status",checkStatus);
        updateWrapper.set("check_person",currentAdminUser.getUsername());
        Date date = new Date();
        //返回当前系统默认的时区
        ZoneId zoneId = ZoneId.systemDefault();

        //atZone()方法返回在指定时区,从该Instant生成的ZonedDateTime
        ZonedDateTime zonedDateTime = date.toInstant().atZone(zoneId);
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        updateWrapper.set("check_time",localDateTime);
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
        //先查看是否存在经纬度信息存在先删除经纬度
        TMercCoordinate tMercCoordinate= tMercCoordinateMapper.selectOne(queryWrapper);
        if (tMercCoordinate!=null){
            tMercCoordinateMapper.delete(queryWrapper);
        }
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
        TAdmin currentAdminUser = UserUtil.getCurrentAdminUser();
        addtMerchantInfo.setContactName(currentAdminUser.getUsername());
        addtMerchantInfo.setManageStartTime(startDateTime);
        addtMerchantInfo.setManageEndTime(endDateTime);
        addtMerchantInfo.setManageStatus(0);
        addtMerchantInfo.setCheckStatus(0);
        addtMerchantInfo.setStatus(0);
        if (tMerchantInfo.getRacmerchantId()!=null){
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

    @Override
    public Integer freeZeMerchant(Integer merc_id) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("merc_id",merc_id);
        updateWrapper.set("status",1);
        int ret = tMerchantInfoMapper.update(null,updateWrapper);
        return ret;
    }
}
