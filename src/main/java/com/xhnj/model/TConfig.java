package com.xhnj.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 配置表
 * </p>
 *
 * @author xiel
 * @since 2021-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TConfig extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段名
     */
    @NotBlank(message = "字段名不能为空")
    private String fieldName ;

    /**
     * 名称
     */
    private String name ;

    /**
     * 配置值
     */
    private String value ;



}
