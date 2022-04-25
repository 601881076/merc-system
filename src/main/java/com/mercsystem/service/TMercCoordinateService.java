package com.mercsystem.service;

import com.mercsystem.model.TMercCoordinate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商户地址表 服务类
 * </p>
 *
 * @author tanyi
 * @since 2022-04-25
 */
public interface TMercCoordinateService extends IService<TMercCoordinate> {


    int addTMercCoordin(TMercCoordinate tMercCoordinate);

}
