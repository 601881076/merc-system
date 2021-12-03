package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.component.ValidateProcessor;
import com.xhnj.constant.ValidateTypeConstant;
import com.xhnj.mapper.TBatchDtlMapper;
import com.xhnj.mapper.TBatchNoMapper;
import com.xhnj.model.*;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.pojo.vo.WithholdDetailVO;
import com.xhnj.service.TBatchDtlService;
import com.xhnj.service.TWithholdService;
import com.xhnj.util.BusinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
 *@date 2021/9/18 17:14   
 */
@Service
@Slf4j
public class TWithholdServiceImpl implements TWithholdService {
    @Autowired
    private ValidateProcessor validateProcessor;
    @Resource
    private TBatchDtlService platformserialService;
    @Resource
    private TBatchDtlMapper platformserialMapper;
    @Resource
    private TBatchNoMapper batchNoMapper;
    @Autowired
    private BusinUtil businUtil;

    @Override
    public IPage batchPage(TBatchNo batchNo, Integer pageSize, Integer pageNum) {
        IPage<TBatchNo> page = new Page<>(pageNum, pageSize);
//        QueryWrapper<TBatchNo> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("create_time");
        return batchNoMapper.listPage(page,batchNo);
    }

    @Override
    public IPage listPage(String batchNo, Integer pageSize, Integer pageNum) {
        IPage<TBatchDtl> page = new Page<>(pageNum, pageSize);
        return platformserialMapper.listPageByBatchNo(page,batchNo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int uploadExcel(MultipartFile file) {
        //业务校验
        BusinValidatorContext validatorContext = BusinValidatorContext.getCurrentContext();
        validatorContext.setRequestDto(file);
        validateProcessor.validate(ValidateTypeConstant.WITHHOLD_BATCH);
        return 1;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delete(Long id){

        return batchNoMapper.deleteById(id);
    }

    @Override
    public void exportExcelSuccess(HttpServletResponse response, DisMissBatchQuery dismissBatch) {

        List<TBatchCheckSuccessExcel> list = platformserialService.getListToBatchCheck(dismissBatch);

        String fileName = "授权取消审批报告";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, WithholdSuccessExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("授权取消审批报告");
            writer.write(list,sheet);
            writer.finish();
            out.flush();
            response.getOutputStream().close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportExcelSuccess(HttpServletResponse response, WithholdParam withholdParam) {
        List<WithholdSuccessExcel> list = platformserialService.getList(withholdParam);
        log.info("list:"+list.toString());
//        list.stream().forEach(e ->e.setRecvAccNo(businUtil.maskBankCard(e.getRecvAccNo())));
        String fileName = "扣款成功报告";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, WithholdSuccessExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("扣款成功报告");
            writer.write(list,sheet);
            writer.finish();
            out.flush();
            response.getOutputStream().close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportExcelSuccess(HttpServletResponse response) {
        List<WithholdDetailVO> list = new ArrayList<>();
//        list.stream().forEach(e ->e.setCardNo(businUtil.maskBankCard(e.getCardNo())));
        String fileName = "扣款批次模板";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, WithholdDetailVO.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("扣款批次");
            writer.write(list,sheet);
            writer.finish();
            out.flush();
            response.getOutputStream().close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void batchExportExcelSuccess(HttpServletResponse response,List<String> batchNos) {
        List<WithholdDetailVO> list = platformserialService.getByBatchNoList(batchNos);
//        list.stream().forEach(e ->e.setCardNo(businUtil.maskBankCard(e.getCardNo())));
        String fileName = "批量扣款报告";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, WithholdDetailVO.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("批量扣款报告");
            writer.write(list,sheet);
            writer.finish();
            out.flush();
            response.getOutputStream().close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportExcelFail(HttpServletResponse response, WithholdParam withholdParam) {
        List<WithholdFailExcel> list = platformserialService.getFailList(withholdParam);
//        list.stream().forEach(e ->e.setRecvAccount(businUtil.maskBankCard(e.getRecvAccount())));
        String fileName = "扣款失败报告";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, WithholdFailExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("扣款失败报告");
            writer.write(list,sheet);
            writer.finish();
            out.flush();
            response.getOutputStream().close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
