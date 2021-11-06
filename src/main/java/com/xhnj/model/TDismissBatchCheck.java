package com.xhnj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 授权取消批次审批表
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TDismissBatchCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 授权取消批次表id
     */
    private Long batchId;

    private String systemBatch;

    /**
     * 审核结果(0->待审核；1->通过;2->拒绝)
     */
    private Integer checkResult;

    /**
     * 审核状态(0->待提交;1->已提交;2->审核通过;3->审核拒绝)
     */
    private Integer status;

    /**
     * 上传人id
     */
    private Long upUserId;

    /**
     * 上传人名称
     */
    private String upUserName;

    /**
     * 审核员id
     */
    private Long checkUserId;

    /**
     * 审核员名称
     */
    private String checkUserName;

    /**
     * 备注
     */
    private String remark;

    private Date createTime;

    /**
     * 审核时间
     */
    private Date checkTime;

    private Date updateTime;


}
