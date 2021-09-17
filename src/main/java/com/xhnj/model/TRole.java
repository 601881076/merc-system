package com.xhnj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xhnj.annotation.FieldRepeatValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author lk
 * @since 2021-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FieldRepeatValidator(fields = {"name"}, message = "角色名称不能重复")
public class TRole extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 启用状态：0->启用；1->不启用
     */
    @NotNull(message = "状态不能为空")
    private Integer status;


}
