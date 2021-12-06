package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.component.ValidateProcessor;
import com.xhnj.constant.ValidateTypeConstant;
import com.xhnj.enums.AuthorizationReportRseultEnum;
import com.xhnj.enums.CertTypeEnum;
import com.xhnj.enums.IsSendEnum;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.mapper.TWithholdAgreeMapper;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.model.TWithholdAgreeExcel;
import com.xhnj.pojo.query.WithholdAgreeQuery;
import com.xhnj.service.TWithholdAgreeService;
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

        log.info("未完成授权{}", withholdAgree.toString());

        // 0 -> 授权成功; 1 -> 未完成授权; 3 -> 授权取消
        int count = withholdAgreeMapper.notCompletedAuthCount(withholdAgree);
        if (0 < count) {
            List<WithholdAgreeQuery> list = withholdAgreeMapper.notCompletedAuth(pageSize, pageNum, withholdAgree);

            page.setTotal(count);
            page.setRecords(list);
        }

        return page;

        /*if (null != withholdAgree.getStatus()) {
            int count = 0;
            // 分页数据
            List<WithholdAgreeQuery> list;

            switch (withholdAgree.getStatus()) {
                case 3 :
                    // 查询授权取消
                    log.info("查询授权取消{}", withholdAgree.toString());
                    return withholdAgreeMapper.selectAuthorizationCancel(page,withholdAgree);
                case 1 :
                    // 未完成授权 手动分页
                    log.info("未完成授权{}", withholdAgree.toString());

                    // 汇总查询
                    count = withholdAgreeMapper.notCompletedAuthCount(withholdAgree);
                    if (0 < count) {
                        list = withholdAgreeMapper.notCompletedAuth(pageSize, pageNum, withholdAgree);

                        page.setTotal(count);
                        page.setRecords(list);
                    }

                    return page;

                default:
                    log.info("查询成功数据");
                    // 手动分页汇总查询
                    count = withholdAgreeMapper.selectSuccessCount(withholdAgree);
                    if (0 < count) {
                        list = withholdAgreeMapper.selectSuccess(pageSize, pageNum, withholdAgree);
                        page.setRecords(list);
                        page.setTotal(count);
                    }

                    return page;
            }
        } else {
            *//*
            * 当条件存在精确查询时，例如(银行卡号、证件号、客户姓名、手机号、协议编号)
            * 此种模式下，将做多次数据查询，最后将数据汇总至一个list，且不做分页(total = 0)
            * *//*

            // 查询授权成功数据
            List<WithholdAgreeQuery> list = withholdAgreeMapper.selectSuccessList(withholdAgree);

            // 查询未完成授权数据
            list.addAll(withholdAgreeMapper.notCompletedAuthList(withholdAgree));

            // 查询授权取消数据
            list.addAll(withholdAgreeMapper.selectAuthorizationCancel(withholdAgree));

            page.setTotal(0);
            page.setRecords(list);

            return page;
        }*/

    }

    /**
     * 授权报告历史查询
     * 必须携带用户或授权信息之一
     * 状态需要根据以下枚举来定
     * 0 -> 授权成功； 1 -> 未完成授权失败；2 -> 短信已发送未完成授权；3 ->授权取消成功;4 -> 授权取消失败;
     * @param withholdAgree
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public IPage selectAgreeHistory(TWithholdAgree withholdAgree, Integer pageSize, Integer pageNum) {
        IPage<WithholdAgreeQuery> page = new Page<>(pageNum, pageSize);

        return withholdAgreeMapper.selectAgreeHistory(page, withholdAgree);
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
        log.info("授权报告查询 -- 未完成授权导出 {}", withholdAgree.toString());

        // 未完成授权数据查询
        List<TWithholdAgreeExcel> list = withholdAgreeMapper.notCompletedAuthExport(withholdAgree);


        // 翻译code
        for (int i = 0; i < list.size(); i++) {
            // 授权结果 0 -> 授权成功； 1 -> 未完成授权失败；2 -> 短信已发送未完成授权；3 ->授权取消成功;4 -> 授权取消失败; 5 -> 授权失败
            if (null != list.get(i).getStatus()) {
                switch (list.get(i).getStatus()) {
                    case "0":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.SMS_SUCCESS.desc());
                        break;
                    case "1":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.SMS_FAIL.desc());
                        break;
                    case "2":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.AUTHORIZATION_SUCCESS.desc());
                        break;
                    case "3":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.AUTHORIZATION_FAIL.desc());
                        break;
                    case "4":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.AUTH_CANCEL_SUCCESS.desc());
                        break;
                    case "5":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.AUTH_CANCEL_FAIL.desc());
                        break;
                    case "6":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.AUTH_CANCEL_WAIT.desc());
                        break;
                    default:
                        break;
                }
            }

            // 是否发送银行
            if (null != list.get(i).getIsSend()) {
                switch (list.get(i).getIsSend()) {
                    case "0" :
                        list.get(i).setIsSend(IsSendEnum.SEND_SUCCESS.desc());
                        break;
                    case "1" :
                        list.get(i).setIsSend(IsSendEnum.SEND_FAIL.desc());
                        break;
                    default:
                        break;
                }
            }

            // 证件信息
            if (null != list.get(i).getCertType()) {
                switch (list.get(i).getCertType()) {
                    case "1" :
                        list.get(i).setCertType(CertTypeEnum.ID_CARD.desc());
                        break;
                    default:
                        break;
                }
            }
        }


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

    /**
     * 授权报告历史导出
     * @param response
     * @param withholdAgree
     */
    @Override
    public void exportHistoryExcel(HttpServletResponse response, TWithholdAgree withholdAgree) {
        log.info("授权报告历史导出 --  {}", withholdAgree.toString());

        // 未完成授权数据查询
        List<TWithholdAgreeExcel> list = withholdAgreeMapper.selectAgreeHistoryExport(withholdAgree);


        // 翻译code
        for (int i = 0; i < list.size(); i++) {
            // 授权结果 0 -> 短信发送成功; 1 -> 短信发送失败; 2 -> 授权成功; 3 -> 授权失败; 4 -> 授权取消成功; 5 -> 授权取消失败; 6 -> 授权取消待处理
            if (null != list.get(i).getStatus()) {
                switch (list.get(i).getStatus()) {
                    case "0":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.SMS_SUCCESS.desc());
                        break;
                    case "1":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.SMS_FAIL.desc());
                        break;
                    case "2":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.AUTHORIZATION_SUCCESS.desc());
                        break;
                    case "3":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.AUTHORIZATION_FAIL.desc());
                        break;
                    case "4":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.AUTH_CANCEL_SUCCESS.desc());
                        break;
                    case "5":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.AUTH_CANCEL_FAIL.desc());
                        break;
                    case "6":
                        list.get(i).setStatus(AuthorizationReportRseultEnum.AUTH_CANCEL_WAIT.desc());
                        break;
                    default:
                        break;
                }
            }

            // 是否发送银行
            if (null != list.get(i).getIsSend()) {
                switch (list.get(i).getIsSend()) {
                    case "0" :
                        list.get(i).setIsSend(IsSendEnum.SEND_SUCCESS.desc());
                        break;
                    case "1" :
                        list.get(i).setIsSend(IsSendEnum.SEND_FAIL.desc());
                        break;
                    default:
                        break;
                }
            }

            // 证件信息
            if (null != list.get(i).getCertType()) {
                switch (list.get(i).getCertType()) {
                    case "1" :
                        list.get(i).setCertType(CertTypeEnum.ID_CARD.desc());
                        break;
                    default:
                        break;
                }
            }
        }


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
  /*  @Override
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

    }*/

}
