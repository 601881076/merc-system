package com.mercsystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description mq消息记录
 * @Date 2021/11/11 11:17
 * @Author tanyi
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class TMsgLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String batchNo;

    private String systemBatch;

    private String bankCode;

    private String msg;

    private String exchange;

    private String routingKey;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
