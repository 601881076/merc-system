package com.xhnj.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单
 * </p>
 *
 * @author lk
 * @since 2021-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long menuId;


}
