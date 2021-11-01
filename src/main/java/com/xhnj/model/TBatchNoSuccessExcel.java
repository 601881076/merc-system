package com.xhnj.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* @Description:    取消授权审批导出实体类
* @Author:         tan_yi
* @CreateDate:     2021/11/1 18:47
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/1 18:47
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Data
public class TBatchNoSuccessExcel extends BaseRowModel {

    @ExcelProperty(value = "是否定时", index = 0)
    private String systemTypes;
    @ExcelProperty(value = "数据来源", index = 0)
    private String systemType;
    @ExcelProperty(value = "银行编码", index = 1)
    private Date bankCode;
    @ExcelProperty(value = "批次号", index = 3)
    private String batchNo;
    @ExcelProperty(value = "批次说明", index = 4)
    private String batchDesc;
    @ExcelProperty(value = "总比数", index = 6)
    private String totalTrans;
    @ExcelProperty(value = "总金额(元)", index = 6)
    private String totalAmt;
    @ExcelProperty(value = "提交状态", index = 6)
    private String statuss;
    @ExcelProperty(value = "成功笔数", index = 6)
    private String successTrans;
    @ExcelProperty(value = "失败笔数", index = 6)
    private String failTrans;
    @ExcelProperty(value = "处理中笔数", index = 6)
    private String processTrans;

}
