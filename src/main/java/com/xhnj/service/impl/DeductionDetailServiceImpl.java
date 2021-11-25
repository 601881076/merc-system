package com.xhnj.service.impl;


import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TDeductionDetailMapper;
import com.xhnj.model.TBatDtlExcel;
import com.xhnj.model.TBatchDtl;
import com.xhnj.pojo.query.DeductionDetailQuery;
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
 * @description: 代扣明细实现类
 * @author: xiel
 * @create: 2021-11-05 17:26
 **/
@Service
public class DeductionDetailServiceImpl implements DeductionDetailService {
    @Resource
    TDeductionDetailMapper TDeductionDetailMapper;
    @Override
    public IPage listPage(DeductionDetailQuery tbatchDtl, Integer pageSize, Integer pageNum) {
        IPage<TBatchDtl> page = new Page<>(pageNum, pageSize);
        return TDeductionDetailMapper.listPage(page,tbatchDtl);
    }
/**
 * @Auther: zhanglujun
 * @Date: 2021/11/18 下午2:14
 * @Description: TODO
 * @param: 扣款明细导出
 */
    @Override
    public void exportExcel(HttpServletResponse response, DeductionDetailQuery tbatchDtl) {
        List<TBatchDtl> tBatchDtls = TDeductionDetailMapper.listPage1(tbatchDtl);
        //先声音明细excel的pojo类；在循环中创建新的对象，每一次都会有一个新的地址值，不会出现数据重复
        TBatDtlExcel tBatDtlExcel;
        List<TBatDtlExcel> list = new ArrayList<>();
            for (int i = 0; i < tBatchDtls.size(); i++) {
                tBatDtlExcel=new TBatDtlExcel();
                tBatDtlExcel.setAgreementId(tBatchDtls.get(i).getAgreementId());
                tBatDtlExcel.setBankCode(tBatchDtls.get(i).getBankCode());
                tBatDtlExcel.setBatchNo(tBatchDtls.get(i).getBatchNo());
                tBatDtlExcel.setCardNo(tBatchDtls.get(i).getCardNo());
                tBatDtlExcel.setCustomerName(tBatchDtls.get(i).getCustomerName());
                tBatDtlExcel.setMoney(tBatchDtls.get(i).getMoney());
                tBatDtlExcel.setOrderNo(tBatchDtls.get(i).getOrderNo());
                tBatDtlExcel.setPayBankName(tBatchDtls.get(i).getPayBankName());

                if (null != tBatchDtls.get(i).getPayResult()) {
                    switch (tBatchDtls.get(i).getPayResult()) {
                        case 0 :
                            tBatDtlExcel.setPayResult("正在处理");
                            break;
                        case 1:
                            tBatDtlExcel.setPayResult("成功");
                            break;
                        case 2:
                            tBatDtlExcel.setPayResult("银行返回失败");
                            break;
                        case 3:
                            tBatDtlExcel.setPayResult("连不上银行服务导致失败");
                            break;
                        case 4:
                            tBatDtlExcel.setPayResult("收不到银行响应导致失败");
                            break;
                        case 5:
                            tBatDtlExcel.setPayResult("管理员手工设置失败");
                            break;
                        default:
                            break;
                    }
                }

                tBatDtlExcel.setReason(tBatchDtls.get(i).getReason());
                tBatDtlExcel.setRecoResult(tBatchDtls.get(i).getRecoResult());

                if (null != tBatchDtls.get(i).getRecoState()) {
                    switch (tBatchDtls.get(i).getRecoState()) {
                        case 0:
                            tBatDtlExcel.setRecoState("未对账");
                            break;
                        case 1:
                            tBatDtlExcel.setRecoState("已对账");
                            break;
                        default:
                            break;
                    }
                }

                if (null != tBatchDtls.get(i).getSourceType()) {
                    switch (tBatchDtls.get(i).getSourceType()) {
                        case 0:
                            tBatDtlExcel.setSourceType("DD");
                            break;
                        case 1:
                            tBatDtlExcel.setSourceType("SDSP");
                            break;
                        case 2:
                            tBatDtlExcel.setSourceType("MDD");
                            break;
                        default:
                            break;
                    }
                }

                tBatDtlExcel.setRecvAccountName(tBatchDtls.get(i).getRecvAccountName());
                tBatDtlExcel.setRetCode(tBatchDtls.get(i).getRetCode());
                tBatDtlExcel.setTradeTime(tBatchDtls.get(i).getTradeTime());
                tBatDtlExcel.setUsages(tBatchDtls.get(i).getUsages());
                list.add(tBatDtlExcel);
            }
            String fileName = "扣款交易明细";
            try {
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
                ServletOutputStream out = response.getOutputStream();
                ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
                Sheet sheet = new Sheet(1,0, TBatDtlExcel.class);
                //设置自适应宽度
                sheet.setAutoWidth(Boolean.TRUE);
                sheet.setSheetName("扣款交易明细");
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

