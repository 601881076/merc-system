package com.xhnj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
* @Description:    日志管理表
* @Author:         tan_yi
* @CreateDate:     2021/11/7 16:12
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/7 16:12
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Data
public class TLog extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /** 操作动作*/
    private String operate;

    /** 操作对象类型*/
    private String objectType;


    /** 操作对象名称*/
    private String objectName;

    /**
     * 操作描述信息
     */
    private String descript;


    /**
     * 该条记录创建时间
     */
    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;

}
