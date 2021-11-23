package com.xhnj.pojo.query;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
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
public class FeeMoneyQuery extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 日期*/
    @ExcelProperty(value = "日期/日期范围", index = 0)
    private String time;

    /** 扣款成功交易笔数*/
    @ExcelProperty(value = "扣款成功交易笔数", index = 1)
    private int successTrans;

    /** 扣款银行*/
    @ExcelProperty(value = "扣款银行", index = 2)
    private String bankName;

    /** 信息服务费(元)*/
    @ExcelProperty(value = "信息服务费(元)", index = 3)
    private BigDecimal feeMoney ;


}
