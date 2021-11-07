package com.xhnj.pojo.query;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @Description:    手续费导出查询对象
* @Author:         tan_yi
* @CreateDate:     2021/11/6 19:53
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/6 19:53
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Data
public class FeeMoneyQuery extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 日期*/
    private String time;

    /** 扣款成功交易笔数*/
    private int successTrans;

    /** 扣款银行*/
    private String bankName;

    /** 信息服务费(元)*/
    private BigDecimal feeMoney ;


}
