package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.component.ValidateProcessor;
import com.xhnj.constant.ValidateTypeConstant;
import com.xhnj.constant.ValueConstance;
import com.xhnj.mapper.TBatchNoMapper;
import com.xhnj.mapper.TPlatformserialMapper;
import com.xhnj.model.TBatchNo;
import com.xhnj.model.TPlatformserial;
import com.xhnj.model.WithholdFailExcel;
import com.xhnj.model.WithholdSuccessExcel;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.service.TPlatformserialService;
import com.xhnj.service.TWithholdService;
import com.xhnj.util.BusinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/9/18 17:14   
 */
@Service
public class TWithholdServiceImpl implements TWithholdService {
    @Autowired
    private ValidateProcessor validateProcessor;
    @Resource
    private TPlatformserialService platformserialService;
    @Resource
    private TPlatformserialMapper platformserialMapper;
    @Resource
    private TBatchNoMapper batchNoMapper;
    @Autowired
    private BusinUtil businUtil;

    @Override
    public IPage batchPage(Integer pageSize, Integer pageNum) {
        IPage<TBatchNo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<TBatchNo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        return batchNoMapper.selectPage(page,wrapper);
    }

    @Override
    public IPage listPage(String batchNo, Integer pageSize, Integer pageNum) {
        IPage<TPlatformserial> page = new Page<>(pageNum, pageSize);
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

    @Override
    public void exportExcelSuccess(HttpServletResponse response, WithholdParam withholdParam) {
        if(withholdParam.getFromType() == null){
            withholdParam.setFromType(ValueConstance.SOURCE_MDD);
        }
        List<WithholdSuccessExcel> list = platformserialService.getList(withholdParam);
        list.stream().forEach(e ->e.setCardNo(businUtil.maskBankCard(e.getCardNo())));
        String fileName = "扣款报告";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, WithholdSuccessExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("扣款报告");
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
        if(withholdParam.getFromType() == null){
            withholdParam.setFromType(ValueConstance.SOURCE_MDD);
        }
        List<WithholdFailExcel> list = platformserialService.getFailList(withholdParam);
        String fileName = "扣款失败报告";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, WithholdSuccessExcel.class);
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
