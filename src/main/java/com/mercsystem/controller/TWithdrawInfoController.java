package com.mercsystem.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.common.CommonResult;
import com.mercsystem.model.TMerchantInfo;
import com.mercsystem.model.TWithdrawInfo;
import com.mercsystem.pojo.query.ExlInputMerchant;
import com.mercsystem.pojo.query.QryMerchantParam;
import com.mercsystem.pojo.query.QryWithdarw;
import com.mercsystem.service.TWithdrawInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提现记录表 前端控制器
 * </p>
 *
 * @author songcn
 * @since 2022-04-28
 */
@RestController
@RequestMapping("/twithdrawinfo")
@Api(tags = "提现管理页面")
public class TWithdrawInfoController {

    @Resource
    private TWithdrawInfoService tWithdrawInfoService;

    @PostMapping("/qryListTWihtdrawInfo")
    @ApiOperation(value = "提现订单分页查询")
    private Page qryListTWihtdrawInfo(@RequestBody QryWithdarw qryWithdarw){
        Page page = new Page(qryWithdarw.getPageindex(),qryWithdarw.getSize());
        Map<String,Object> paramMap = new HashMap<>();
        if (qryWithdarw.getUserId()!=null){
            paramMap.put("user_id",qryWithdarw.getUserId());
        }
        if (qryWithdarw.getStatus()!=null){
            paramMap.put("status",qryWithdarw.getStatus());
        }
        if (qryWithdarw.getWithdrawAmonut()!=null){
            paramMap.put("withdraw_amonut",qryWithdarw.getWithdrawAmonut());
        }
        if (qryWithdarw.getStartTime()!=null){
            paramMap.put("start_time",qryWithdarw.getStartTime());
        }
        Page setpage = tWithdrawInfoService.qryTWithdrawInfo(page,paramMap);
        return setpage;
    }


    @PostMapping("/checkWithdrew")
    @ApiOperation(value = "提现订单审核")
    private CommonResult checkWithdrew(Integer id,Integer status) {
        int ret = tWithdrawInfoService.chelkWithdatwInfo(id,status);
        if (ret<0){
            return CommonResult.success("审核失败");
        }else{
            return CommonResult.success("审核成功");
        }
    }

    @GetMapping("/outInputWithdrew")
    @ApiOperation(value = "订单导出")
    public CommonResult outInputWithdrew() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();//创建HSSFWorkbook对象,  excel的文档对象
        HSSFSheet sheet = workbook.createSheet("信息表"); //excel的表单
        Map<String,Object> param = new HashMap<>();
        List<TWithdrawInfo> classmateList = tWithdrawInfoService.outInputWithdarwInfo();

        String fileName = "商户信息"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "商户号", "商户名", "商户地址",
                "联系人电话","经营开始时间","经营关闭时间","经营状态","平台类型","得分","商户创建时间","创建人","上层商户id"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (TWithdrawInfo tWithdrawInfo : classmateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
//            row1.createCell(0).setCellValue(tMerchantInfo.getMercId());
//            row1.createCell(1).setCellValue(tMerchantInfo.getMercName());
//            row1.createCell(2).setCellValue(tMerchantInfo.getAddress());
//            row1.createCell(3).setCellValue(tMerchantInfo.getContactPhone());
//            row1.createCell(4).setCellValue(tMerchantInfo.getManageStartTime().toString());
//            row1.createCell(5).setCellValue(tMerchantInfo.getManageEndTime().toString());
//            row1.createCell(6).setCellValue(tMerchantInfo.getManageStatus());
//            row1.createCell(7).setCellValue(tMerchantInfo.getPlatformType());
//            row1.createCell(8).setCellValue(tMerchantInfo.getScore());
//            row1.createCell(9).setCellValue(tMerchantInfo.getCreateTime().toString());
//            row1.createCell(10).setCellValue(tMerchantInfo.getCreatePerson());
//            row1.createCell(11).setCellValue(tMerchantInfo.getRacmerchantId());
            rowNum++;
        }
        File file = new File("D:\\song\\exl");
        file.mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\song\\exl\\商户信息.xls");
        workbook.write(fileOutputStream);
        return CommonResult.success("D:\\song\\exl\\商户信息.xls");
    }
}
