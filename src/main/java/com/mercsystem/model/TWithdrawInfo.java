package com.mercsystem.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 提现记录表
 * </p>
 *
 * @author songcn
 * @since 2022-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TWithdrawInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmonut;

    /**
     * 提现申请人id
     */
    private Integer userId;

    /**
     * 提现发起时间
     */
    private LocalDateTime startTime;

    /**
     * 提现状态: 0 → 待审核; 1 → 成功; 2 → 失败；3 -> 审核完成
     */
    private Integer status;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 提现结果上传时间(完成时间)
     */
    private LocalDateTime finishTime;


}
