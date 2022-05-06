package com.mercsystem.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.common.CommonResult;
import com.mercsystem.model.TAdmin;
import com.mercsystem.model.TMerchantInfo;
import com.mercsystem.pojo.query.AddMerchant;
import com.mercsystem.pojo.query.ExlInputMerchant;
import com.mercsystem.pojo.query.QryMerchantParam;
import com.mercsystem.service.TMercCoordinateService;
import com.mercsystem.service.TMerchantInfoService;
import com.mercsystem.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商户表 前端控制器
 * </p>
 *
 * @author songcn
 * @since 2022-04-22
 */
@RestController
@RequestMapping("/merchants")
@Api(tags = "商户管理页面")
public class TMerchantInfoController {
    @Resource
    private TMerchantInfoService tMerchantInfoService;

    /**
     * 分页查询商户信息
     * @param qryMerchantParam
     * @return
     */
    @PostMapping("/qryListMerchant")
    @ApiOperation(value = "分页查询商户信息")
    private Page qryListTMerchantInfo(@RequestBody QryMerchantParam qryMerchantParam){
        Page page = new Page(qryMerchantParam.getPageindex(),qryMerchantParam.getSize());
        Map<String,Object> paramMap = new HashMap<>();
        if (qryMerchantParam.getMerc_id()!=null){
            paramMap.put("merc_id",qryMerchantParam.getMerc_id());
        }
        if (qryMerchantParam.getMercName()!=null){
            paramMap.put("merc_name",qryMerchantParam.getMercName());
        }
        if (qryMerchantParam.getContactName()!=null){
            paramMap.put("contact_name",qryMerchantParam.getContactName());
        }
        if (qryMerchantParam.getContactPhone()!=null){
            paramMap.put("contact_phone",qryMerchantParam.getContactPhone());
        }
        if (qryMerchantParam.getStatus()!=null){
            paramMap.put("status",qryMerchantParam.getStatus());
        }
        if (qryMerchantParam.getCheckStatus()!=null){
            paramMap.put("check_status",qryMerchantParam.getCheckStatus());
        }
        if (qryMerchantParam.getManageStatus()!=null){
            paramMap.put("manage_status",qryMerchantParam.getManageStatus());
        }
        if (qryMerchantParam.getCreatePerson()!=null){
            paramMap.put("create_person",qryMerchantParam.getCreatePerson());
        }

        Page setpage = tMerchantInfoService.qryTMerchantInfo(page,paramMap);
        return setpage;
    }

    /**
     * 新增商户
     * @param tMerchantInfo
     * @return
     */
    @ApiOperation(value = "商户新增接口")
    @PostMapping("/addMerchant")
    private CommonResult addMerchant(@RequestBody AddMerchant tMerchantInfo){
        Integer tmer = tMerchantInfoService.addTMerchant(tMerchantInfo);
        if (tmer>0){
            return CommonResult.success("添加商户成功");
        }else{
            return CommonResult.success("添加商户失败");
        }

    }

    /**
     * 商户详情查询
     * @param merc_id
     * @return
     */
    @ApiOperation("商户详情查询")
    @PostMapping("/qryMerchanInfo")
    private CommonResult<TMerchantInfo> qryMerchanInfo(Integer merc_id){
        TMerchantInfo tMerchantInfo =tMerchantInfoService.tmerchantInfo(merc_id);
        return CommonResult.success(tMerchantInfo);
    }

    /**
     * 商户删除
     * @param merc_id
     * @return
     */
    @ApiOperation(value = "商户删除")
    @PostMapping("/delMerchant")
    private CommonResult delMerchant(Integer merc_id){
        Integer rat = tMerchantInfoService.delMerchantByMercId(merc_id);
        if (rat>0){
            return CommonResult.success("删除商户成功");
        }else{
            return CommonResult.success("删除商户失败");
        }

    }

    /**
     * 商户审核
     * @param merc_id
     * @param checkStatus
     * @return
     */
    @ApiOperation(value = "商户审核")
    @PostMapping("/checkMerchant")
    private CommonResult checkMerchant(Integer merc_id,Integer checkStatus){
        //查询商户是否符合审核条件
        TMerchantInfo tMerchantInfo = tMerchantInfoService.tmerchantInfo(merc_id);
        Integer rate =tMerchantInfo.getCheckStatus();
        if (rate==0){
            Integer ret = tMerchantInfoService.updateMerchantByMercId(merc_id,checkStatus);
            if (ret>0){
                return CommonResult.success("商户审核成功");
            }else{
                return CommonResult.success("商户审核失败");
            }

        }else{
            return CommonResult.success("商户审核状态不满足");
        }
    }

    /**
     * 商户修改
     * @param tMerchantInfo
     * @return
     */
    @ApiOperation(value = "商户修改")
    @PostMapping("/updateMerchant")
    private CommonResult updateMerchant(@RequestBody TMerchantInfo tMerchantInfo){
        TAdmin currentAdminUser = UserUtil.getCurrentAdminUser();
        tMerchantInfo.setUpdatePerson(currentAdminUser.getUsername());
        //调用修改接口
        Integer rat = tMerchantInfoService.updateMerchant(tMerchantInfo);
        if (rat>0){
            return CommonResult.success("修改商户信息成功");
        }else{
            return CommonResult.success("修改商户信息失败");
        }
    }

    /**
     * 商户导出
     * @param exlInputMerchant
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "商户导出")
    @GetMapping("/downloadAllClassmate")
    public CommonResult downloadAllClassmate(@RequestBody ExlInputMerchant exlInputMerchant) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();//创建HSSFWorkbook对象,  excel的文档对象
        HSSFSheet sheet = workbook.createSheet("信息表"); //excel的表单
        Map<String,Object> param = new HashMap<>();
        param.put("merc_id",exlInputMerchant.getMercId());
        param.put("merc_name",exlInputMerchant.getMercName());
        param.put("manage_start_time",exlInputMerchant.getManageStartTime());
        param.put("manage_end_time",exlInputMerchant.getManageEndTime());
        List<TMerchantInfo> classmateList = tMerchantInfoService.exlTMerchantInfo(param);

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
        for (TMerchantInfo tMerchantInfo : classmateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(tMerchantInfo.getMercId());
            row1.createCell(1).setCellValue(tMerchantInfo.getMercName());
            row1.createCell(2).setCellValue(tMerchantInfo.getAddress());
            row1.createCell(3).setCellValue(tMerchantInfo.getContactPhone());
            row1.createCell(4).setCellValue(tMerchantInfo.getManageStartTime().toString());
            row1.createCell(5).setCellValue(tMerchantInfo.getManageEndTime().toString());
            row1.createCell(6).setCellValue(tMerchantInfo.getManageStatus());
            row1.createCell(7).setCellValue(tMerchantInfo.getPlatformType());
            row1.createCell(8).setCellValue(tMerchantInfo.getScore());
            row1.createCell(9).setCellValue(tMerchantInfo.getCreateTime().toString());
            row1.createCell(10).setCellValue(tMerchantInfo.getCreatePerson());
            row1.createCell(11).setCellValue(tMerchantInfo.getRacmerchantId());
            rowNum++;
        }
        File file = new File("D:\\song\\exl");
        file.mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\song\\exl\\商户信息.xls");
        workbook.write(fileOutputStream);
        return CommonResult.success("D:\\song\\exl\\商户信息.xls");
    }


    /**
     * 冻结商户
     * @param merc_id
     * @return
     */
    @ApiOperation(value = "冻结商户")
    @PostMapping("/freezeMerchant")
    private  CommonResult freezeMerchant(Integer merc_id){
        int ret = tMerchantInfoService.freeZeMerchant(merc_id);
        if (ret<0){
            return CommonResult.success("商户已冻结成功");
        }else{
            return CommonResult.success("商户冻结失败");
        }
    }

}
