package com.xhnj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 授权取消批次表
 * </p>
 *
 * @author lk
 * @since 2021-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TDismissBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 来源(0->DD、1->SDSP、2->MDD)
     */
    private Integer fromType;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 系统批次号
     */
    private String systemBatch;

    /**
     * 批次说明
     */
    private String batchDesc;

    /**
     * 成功笔数
     */
    private Integer successTrans;

    /**
     * 失败笔数
     */
    private Integer failTrans;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
