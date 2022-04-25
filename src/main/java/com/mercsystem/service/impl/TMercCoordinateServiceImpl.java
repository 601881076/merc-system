package com.mercsystem.service.impl;

import com.mercsystem.model.TMercCoordinate;
import com.mercsystem.mapper.TMercCoordinateMapper;
import com.mercsystem.service.TMercCoordinateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户地址表 服务实现类
 * </p>
 *
 * @author tanyi
 * @since 2022-04-25
 */
@Service
public class TMercCoordinateServiceImpl extends ServiceImpl<TMercCoordinateMapper, TMercCoordinate> implements TMercCoordinateService {
    @Autowired
    private TMercCoordinateMapper tMercCoordinateMapper;


    @Override
    public int addTMercCoordin(TMercCoordinate tMercCoordinate) {
        return tMercCoordinateMapper.insert(tMercCoordinate);
    }
}
