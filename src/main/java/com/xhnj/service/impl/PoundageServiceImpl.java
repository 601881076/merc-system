package com.xhnj.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xhnj.mapper.TBatchDtlMapper;
import com.xhnj.model.PoundageExcel;
import com.xhnj.model.TBatchDtl;
import com.xhnj.model.WithholdSuccessExcel;
import com.xhnj.pojo.query.PoundageParam;
import com.xhnj.service.PoundageService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/9/25 20:46   
 */
@Service
public class PoundageServiceImpl implements PoundageService {
    @Resource
    private TBatchDtlMapper platformserialMapper;

    @Override
    public void exportExcel(HttpServletResponse response, PoundageParam poundageParam) {
        QueryWrapper<TBatchDtl> wrapper = new QueryWrapper<>();
        wrapper.and(StrUtil.isNotBlank(poundageParam.getStartDate()),wp -> wp.ge("trade_time",poundageParam.getStartDate()));
        wrapper.and(StrUtil.isNotBlank(poundageParam.getEndDate()),wp -> wp.le("trade_time",poundageParam.getEndDate()));
        wrapper.eq("pay_result",2);
        List<TBatchDtl> tBatchDtls = platformserialMapper.selectList(wrapper);
        PoundageExcel poundageExcel=new PoundageExcel();
        poundageExcel.setDateRange(poundageParam.getStartDate()+"---"+poundageParam.getEndDate());
        poundageExcel.setSuccessTrans(tBatchDtls.size());
        if (tBatchDtls.size()!=0) {
            poundageExcel.setBankCode(tBatchDtls.get(0).getPayBankName());
        }
        Double charge=Double.valueOf(tBatchDtls.size()/10);
        poundageExcel.setCharge(charge);
        List<PoundageExcel> list = new ArrayList<>();
        list.add(poundageExcel);
        String fileName = "信息手续费报表";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, PoundageExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("信息手续费报表");
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
