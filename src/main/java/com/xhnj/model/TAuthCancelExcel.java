package com.xhnj.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.Date;

/**
 * @author : zhanglujun
 * @version : v1.0
 * @description TODO
 * @date : 2021/11/18 下午3:53
 */
@Data
public class TAuthCancelExcel extends BaseRowModel {
    @ExcelProperty(value = "协议编号", index = 0)
    private String agreementId;
    @ExcelProperty(value = "手机号", index = 1)
    private String mobileNo;
    @ExcelProperty(value = "银行卡号", index = 2)
    private String cardNo;
    @ExcelProperty(value = "客户名称", index = 3)
    private String customerName;
    @ExcelProperty(value = "证件类型", index = 4)
    private String certType;
    @ExcelProperty(value = "证件号码", index = 5)
    private String certNo;
    @ExcelProperty(value = "银行返回代码", index = 6)
    private String bankRetCode;
    @ExcelProperty(value = "授权处理状态", index = 7)
    private String status;
    @ExcelProperty(value = "是否送达银行", index = 8)
    private String isSend;
    @ExcelProperty(value = "失败原因", index = 9)
    private String reason;
    @ExcelProperty(value = "合同结束日期", index = 10)
    private String contractDate;
    @ExcelProperty(value = "协议失效日", index = 11)
    private String endDate;


}
