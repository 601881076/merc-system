package com.xhnj.model;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 后台用户角色表
 * </p>
 *
 * @author lk
 * @since 2021-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TAdminRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;


}
