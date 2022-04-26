package com.mercsystem.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 商户表
 * </p>
 *
 * @author songcn
 * @since 2022-04-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TMerchantInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户主键
     */
    @TableId(type = IdType.AUTO)
    private Integer mercId;

    /**
     * 商户名称
     */
    private String mercName;

    /**
     * 商户地址
     */
    private String address;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 商家logo URL
     */
    private String logo;

    /**
     * 经营开始时间
     */
    private LocalDateTime manageStartTime;

    /**
     * 经营关闭时间
     */
    private LocalDateTime manageEndTime;

    /**
     * 经营状态: 0 → 正常营业; 1 → 打烊; 2 → 休息; 3 → 平台关闭
     */
    private Integer manageStatus;

    /**
     * 平台类型: 0 → 美团; 1 → 饿了么。 存在多个以 , 分割
     */
    private String  platformType;

    /**
     * 省编码
     */
    private String province;

    /**
     * 市编码
     */
    private String city;

    /**
     * 区编码
     */
    private String area;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 得分，好店推荐接口按照此分数进行排名
     */
    private Integer score;

    /**
     * 审核状态: 0 → 待审核; 1 → 审核通过; 2 → 审核失败;
     */
    private Integer checkStatus;

    /**
     * 商户创建时间
     */
    private LocalDateTime createTime;

    /**
     * 商户最新审核时间
     */
    private LocalDateTime checkTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */

    private String createPerson;

    /**
     * 审核人
     */
    private String checkPerson;

    /**
     * 修改人
     */
    private String updatePerson;

    /**
     * 商户状态。 0 → 正常; 1 → 禁用
     */
    private Integer status;
    /**
     * 上册商户id
     */
    private Integer racmerchantId;



}
