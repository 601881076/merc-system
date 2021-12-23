package com.xhnj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: lk
 * DateTime: 2021-12-22 13:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TBatchRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String batchNo;

    private String systemBatch;

    private Integer type;

    private String bankCode;

    private Integer status;

    private Integer successTrans;

    private Integer failTrans;

    private Integer processTrans;

    private Date processFinishTime;

    private Date createTime;

    private Date updateTime;

}
