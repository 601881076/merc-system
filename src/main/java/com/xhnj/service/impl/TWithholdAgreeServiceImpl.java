package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.component.ValidateProcessor;
import com.xhnj.constant.ValidateTypeConstant;
import com.xhnj.enums.*;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.mapper.TWithholdAgreeMapper;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TDismissBatchExcel;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.model.TWithholdAgreeExcel;
import com.xhnj.service.TWithholdAgreeService;
import com.xhnj.service.TWithholdCancleService;
import com.xhnj.util.BusinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/9/19 18:00
 */
@Service
@Slf4j
public class TWithholdAgreeServiceImpl implements TWithholdAgreeService {
    @Autowired
    private ValidateProcessor validateProcessor;
    @Resource
    private TWithholdAgreeMapper withholdAgreeMapper;
    @Resource
    private TDismissBatchMapper dismissBatchMapper;
    @Autowired
    private BusinUtil businUtil;


    @Override
    public IPage conditionQuery(TWithholdAgree withholdAgree, Integer pageSize, Integer pageNum) {
        log.info("授权报告查询");
        IPage<TWithholdAgree> page = new Page<>(pageNum, pageSize);

        if (null != withholdAgree.getStatus()) {
            log.info("查询报告状态{}", withholdAgree.getStatus());

            switch (withholdAgree.getStatus()) {
                case 0 :
                    // 查询成功/失败
                    return withholdAgreeMapper.selectSuccess(page,withholdAgree);
            }
        }

        // 根据前端上送状态判断本次查询内容
        // 1 > 成功; 2 > 失败；3 > 短信发送未完成授权（需要关联短信表）；4 > 只显示最新授权状态（查询最新一条）；5 > 授权取消查标识为接触的数据
        /*if (null != withholdAgree.getStatus()) {
            int status = withholdAgree.getStatus();
            log.info("状态{}", status);
            switch (status) {
                case 3:
                    // 短信发送未完成授权（需要关联短信表）
                    return withholdAgreeMapper.selectSmsIsNotCompleted(page,withholdAgree);
                case 4:
                    // 查询最新的一条数据
                    page = new Page<>(1,1);
                    return withholdAgreeMapper.selectLatestDate(page,withholdAgree);
                case 5:
                    //
                    return withholdAgreeMapper.conditionQuery(page,withholdAgree);
                default:
                    return withholdAgreeMapper.conditionQuery(page,withholdAgree);
            }
        }*/

        return withholdAgreeMapper.conditionQuery(page,withholdAgree);
    }

    @Override
    public IPage batchPage(TDismissBatch dismissBatch,Integer pageSize, Integer pageNum) {
        IPage<TDismissBatch> page = new Page<>(pageNum, pageSize);
//        QueryWrapper<TDismissBatch> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("create_time");
        return dismissBatchMapper.listPage(page,dismissBatch);
    }

    @Override
    public IPage listPage(String batchNo, Integer pageSize, Integer pageNum) {
        IPage<TWithholdAgree> page = new Page<>(pageNum, pageSize);
        List<TWithholdAgree> records = page.getRecords();
        records.stream().forEach(e ->e.setCardNo(businUtil.maskBankCard(e.getCardNo())));
        records.stream().forEach(e ->e.setCertNo(businUtil.maskBankCard(e.getCertNo())));
        records.stream().forEach(e ->e.setMobileNo(businUtil.maskPhone(e.getMobileNo())));
        page.setRecords(records);
        return withholdAgreeMapper.listPageByBatchNo(page,batchNo);
    }

    @Override
    public int uploadExcel(MultipartFile file) {
        //业务校验
        BusinValidatorContext validatorContext = BusinValidatorContext.getCurrentContext();
        validatorContext.setRequestDto(file);
        validateProcessor.validate(ValidateTypeConstant.WITHHOLDAGREE_BATCH_CANCLE);

        return 1;
    }

    @Override
    public void exportExcel(HttpServletResponse response, List<String> idList) {
        log.info("授权取消列表批量导出");

        // 获取导出数据
        List<TWithholdAgreeExcel> list = withholdAgreeMapper.conditionQueryList(idList);

        // 释义转换
        list.stream().forEach(item -> {
            log.info("状态1{}, 发送状态1{}", item.getStatus(), item.getIsSend());
            // 状态
            if (null != item.getStatus()) {
                switch (item.getStatus()) {
                    case "0" :
                        item.setStatus(AuthorizationRseultEnum.PROCESSING.desc());
                        break;
                    case "1":
                        item.setStatus(AuthorizationRseultEnum.SUCCESS.desc());
                        break;
                    case "2" :
                        item.setStatus(AuthorizationRseultEnum.FAIL.desc());
                        break;
                    default:
                        break;
                }
            }

            // 是否发送到银行
            if (null != item.getIsSend()) {
                switch (item.getIsSend()) {
                    case "0" :
                        item.setIsSend(IsSendEnum.SEND_SUCCESS.desc());
                        break;
                    case "1":
                        item.setIsSend(IsSendEnum.SEND_FAIL.desc());
                        break;
                    default:
                        break;
                }
            }

            log.info("状态2{}, 发送状态2{}", item.getStatus(), item.getIsSend());
        });


        String fileName = "授权报告查询";

        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, TWithholdAgreeExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("授权报告查询");
            writer.write(list, sheet);
            writer.finish();
            out.flush();
            response.getOutputStream().close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
