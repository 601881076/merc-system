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
import com.xhnj.model.*;
import com.xhnj.pojo.query.WithholdAgreeQuery;
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
import java.util.ArrayList;
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
        IPage<WithholdAgreeQuery> page = new Page<>(pageNum, pageSize);

        if (null != withholdAgree.getStatus()) {
            log.info("查询报告状态{}", withholdAgree.getStatus());

            switch (withholdAgree.getStatus()) {
                case 3 :
                    // 查询授权取消
                    log.info("查询授权取消{}", withholdAgree.toString());
                    return withholdAgreeMapper.selectAuthorizationCancel(page,withholdAgree);
                case 1 :
                    // 未完成授权
                    log.info("未完成授权{}", withholdAgree.toString());
                    return withholdAgreeMapper.notCompletedAuth(page, withholdAgree);


                    /* 本来想采用分批次查询，然后汇总到一起的方式处理，但是最后发现无法正确的显示每页的数据。 比如第一页的数据如何做到与第二页的数据不重复？



                    * 未完成授权查询分两种情况
                    * 1. 第一种情况，短信表存在数据，授权表无数据
                    * 2.  短信表、授权表都存在数据，但是授权表对应状态是失败的
                    * */

                    // 声明数据汇总集合，用户存储两次查询的数据
                    /*List<WithholdAgreeQuery> list = new ArrayList<>();

                    // 分页偏移量
                    int minNumber = pageSize * (pageNum - 1);

                    // 总数量
                    int count = 0;*/

                    /*
                    * 1. 第一种情况，短信表存在数据，授权表无数据。
                    * 分两次查询，第一次查出短信表有数据，授权表无数据的卡号+协议号
                    * 第二次根据 以上查出来的卡号 + 协议号查出具体数据
                    * */

                    // 第一次查询
                    /*List<WithholdAgreeQuery> smsList = withholdAgreeMapper.selectSmsExistsAndAuthorizationNotExists(withholdAgree);
                    if (0 < smsList.size()) {
                        // 第二次查询 需要进行分页查询

                        // count查询
                        count = withholdAgreeMapper.selectSmsExistsAndAuthorizationNotExistsCount(smsList, withholdAgree);

                        // 数据查询
                        list = withholdAgreeMapper.selectSmsExistsAndAuthorizationNotExistsList(smsList, withholdAgree, minNumber, pageSize);

                    }

                    *//*
                    * 第二种情况查询
                    * 短信表、授权表都存在数据，但是授权表对应状态是失败的
                    * *//*

                    // count查询
                    int twoResultCount = withholdAgreeMapper.selectSmsAuthorizationBothExistsCount(withholdAgree, minNumber, pageSize);

                    // 数据查询
                    list.addAll(withholdAgreeMapper.selectSmsAuthorizationBothExistsList(withholdAgree, minNumber, pageSize));

                    // 总数量
                    count += twoResultCount;
                    page.setTotal(count);

                    // 页数
                    page.setPages(0 == count % 20 ? count % 20 : count / 20 + 1);

                    // 当前页记录
                    page.setRecords(list);*/
            }
        }

        // 查询成功的授权报告
        return withholdAgreeMapper.selectSuccess(page,withholdAgree);
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
    public void exportExcel(HttpServletResponse response, TWithholdAgree withholdAgree) {
        log.info("授权取消列表批量导出");

        // 获取导出数据
        List<TWithholdAgreeExcel> list = null;

        if (null != withholdAgree.getStatus()) {
            log.info("查询报告状态{}", withholdAgree.getStatus());

            switch (withholdAgree.getStatus()) {
                case 3 :
                    // 查询授权取消
                    log.info("查询授权取消{}", withholdAgree.toString());
                    list =  withholdAgreeMapper.selectAuthorizationCancelExport(withholdAgree);
                    break;
                case 1 :
                    // 未完成授权
                    log.info("未完成授权{}", withholdAgree.toString());
                    list =  withholdAgreeMapper.notCompletedAuthExport(withholdAgree);
                    break;
                default:
                    list = withholdAgreeMapper.conditionQueryList(withholdAgree);
                    break;
            }
        }

        // 释义转换
        list.stream().forEach(item -> {
            // 状态 0 -> 授权成功； 1 -> 未完成授权失败；2 -> 短信已发送未完成授权；3 ->授权取消成功;4 -> 授权取消失败;
            if (null != item.getStatus()) {
                switch (item.getStatus()) {
                    case "0" :
                        item.setStatus(AuthorizationReportRseultEnum.SUCCESS.desc());
                        break;
                    case "1":
                        item.setStatus(AuthorizationReportRseultEnum.FAIL.desc());
                        break;
                    case "2" :
                        item.setStatus(AuthorizationReportRseultEnum.SMS_SUCCESS_AUTH_FAIL.desc());
                        break;
                    case "3" :
                        item.setStatus(AuthorizationReportRseultEnum.AUTH_CANCEL_SUCCESS.desc());
                        break;
                    case "4" :
                        item.setStatus(AuthorizationReportRseultEnum.AUTH_CANCEL_FAIL.desc());
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

            // 证件类型
            if (null != item.getCertType()) {
                switch (item.getCertType()) {
                    case "1" :
                        item.setCertType("身份证");
                        break;
                    default:
                        break;
                }
            }

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
