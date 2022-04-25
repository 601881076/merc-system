package com.mercsystem.model;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商户地址表
 * </p>
 *
 * @author tanyi
 * @since 2022-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TMercCoordinate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer mercId;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * Geohash编码
     */
    private String geoHashCode;


}
