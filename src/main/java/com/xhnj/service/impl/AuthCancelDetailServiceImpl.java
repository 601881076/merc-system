package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TAuthCancelDetailMapper;
import com.xhnj.mapper.TDeductionDetailMapper;
import com.xhnj.model.*;
import com.xhnj.pojo.query.AuthCancelDetailQuery;
import com.xhnj.pojo.query.DeductionDetailQuery;
import com.xhnj.service.AuthCancelDetailService;
import com.xhnj.service.DeductionDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: ums-admin
 * @description: 授权取消明细实现类
 * @author: xiel
 * @create: 2021-11-05 17:26
 **/
@Service
public class AuthCancelDetailServiceImpl implements AuthCancelDetailService {
    @Resource
    TAuthCancelDetailMapper authCancelDetailMapper;
    @Override
    public IPage listPage(AuthCancelDetailQuery detailQuery, Integer pageSize, Integer pageNum) {
        IPage<TWithholdCancle> page = new Page<>(pageNum, pageSize);
        return authCancelDetailMapper.listPage(page,detailQuery);
    }

    @Override
    public void exportExcel(HttpServletResponse response, AuthCancelDetailQuery tbatchDtl) {
        List<TWithholdCancle>  tBatchDtls = authCancelDetailMapper.listPage(tbatchDtl);
        TAuthCancelExcel tAuthCancelExcel;
        List<TAuthCancelExcel> list = new ArrayList<>();
        for (int i = 0; i < tBatchDtls.size(); i++) {
            tAuthCancelExcel=new TAuthCancelExcel();
            tAuthCancelExcel.setAgreementId(tBatchDtls.get(i).getAgreementId());
            tAuthCancelExcel.setBankRetCode(tBatchDtls.get(i).getBankRetCode());
            tAuthCancelExcel.setCardNo(tBatchDtls.get(i).getCardNo());
            tAuthCancelExcel.setCertNo(tBatchDtls.get(i).getCertNo());
            tAuthCancelExcel.setCertType(tBatchDtls.get(i).getCertType());
            tAuthCancelExcel.setContractDate(tBatchDtls.get(i).getContractDate());
            tAuthCancelExcel.setCustomerName(tBatchDtls.get(i).getCustomerName());
            tAuthCancelExcel.setEndDate(tBatchDtls.get(i).getEndDate());
            //0->未发送;1->已发送
            switch (tBatchDtls.get(i).getIsSend()) {
                case 0:
                    tAuthCancelExcel.setIsSend("未发送");
                    break;
                case 1:
                    tAuthCancelExcel.setIsSend("已发送");
                    break;
                default:
                    break;
            }
            tAuthCancelExcel.setMobileNo(tBatchDtls.get(i).getMobileNo());
            tAuthCancelExcel.setReason(tBatchDtls.get(i).getReason());
            //(0->处理中;1->成功;2->失败)
            switch (tBatchDtls.get(i).getStatus()) {
                case 0:
                    tAuthCancelExcel.setIsSend("处理中");
                    break;
                case 1:
                    tAuthCancelExcel.setIsSend("成功");
                    break;
                case 2:
                    tAuthCancelExcel.setIsSend("失败");
                    break;
                default:
                    break;
            }
            list.add(tAuthCancelExcel);
        }
        String fileName = "授权取消明细";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, TAuthCancelExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("授权取消明细");
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
