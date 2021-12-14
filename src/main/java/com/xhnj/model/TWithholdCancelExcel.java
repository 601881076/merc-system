package com.xhnj.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 代扣协议取消导出类
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TWithholdCancelExcel extends BaseRowModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 系统来源类型(1->IDS; 2->COF; 3->PAS; 4->CEFT; 5->AMO)
     */
    private Integer systemType;

    /**
     * 数据来源类型(0->上游系统、1->界面操作)
     */
    private Integer sourceType;

    /**
     * 银行编码
     */
    @ExcelProperty(value = "银行编码", index = 0)
    private String bankCode;

    /**
     * 银行名称
     */
    @ExcelProperty(value = "银行名称",index = 1)
    private String bankName;

    /**
     * 系统批次号
     */
    @ExcelProperty(value = "系统批次号",index = 2)
    private String systemBatch;

    /**
     * 品牌(2000->Ford; 2001->Lincoln)
     */
    private String finLabelCd;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 协议编号
     */
    private String agreementId;

    /**
     * 请求的唯一序列号
     */
    private String bizserialNo;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号",index = 3)
    private String mobileNo;

    /**
     * 银行卡号
     */
    @ExcelProperty(value = "银行卡号",index = 4)
    private String cardNo;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名",index = 5)
    private String customerName;

    /**
     * 证件类型
     */
    @ExcelProperty(value = "证件类型",index = 6)
    private String certType;

    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码",index = 7)
    private String certNo;

    @ExcelProperty(value = "短信验证码", index = 8)
    private String checkNo;

    @ExcelProperty(value = "短信发送编号", index = 9)
    private String sendNo;

    /**
     * 动态属键值(json字符串)
     */
    private String ownSpec;

    /**
     * 银行返回结果动态属键值(json字符串)
     */
    private String reOwnSpec;

    /**
     * 状态(0->处理中;1->成功;2->失败)
     */
    @ExcelProperty(value = "状态", index = 10)
    private String status;

    /**
     * 是否发送到银行(0->未发送;1->已发送)
     */
    @ExcelProperty(value = "是否发送到银行", index = 11)
    private Integer isSend;

    /**
     * 银行返回代码
     */
    @ExcelProperty(value = "银行返回代码", index = 12)
    private String bankRetCode;

    /**
     * 失败原因
     */
    @ExcelProperty(value = "失败原因", index = 13)
    private String reason;

    /**
     * 单笔限额(元)
     */
    @ExcelProperty(value = "单笔限额(元)", index = 14)
    private BigDecimal singleMax;

    /**
     * 当天累计(元)
     */
    @ExcelProperty(value = "当天累计(元)", index = 15)
    private BigDecimal dayMax;

    /**
     * 当月累计(元)
     */
    @ExcelProperty(value = "当月累计(元)", index = 16)
    private BigDecimal monthMax;

    /**
     * 累计限额(元)
     */
    @ExcelProperty(value = "累计限额(元)", index = 17)
    private BigDecimal sumMax;

    /**
     * 合同结束日期
     */
    private String contractDate;

    /**
     * 协议生效日
     */
    @ExcelProperty(value = "协议生效日", index = 18)
    private String startDate;



    /**
     * 协议失效日
     */
    @ExcelProperty(value = "协议失效日", index = 19)
    private String endDate;

    private Date createTime;

    private Date updateTime;

}
