package com.xhnj.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TBatchNo;
import com.xhnj.model.TBatchDtl;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.pojo.vo.BatchNoVO;
import com.xhnj.service.TBatchCheckService;
import com.xhnj.service.TWithholdService;
import com.xhnj.service.WithholdBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/*
 @Description 代扣
 *@author kang.li
 *@date 2021/9/18 16:14   
 */
@Api(value = "代扣", tags = "代扣接口")
@RestController
@RequestMapping("/wh")
@Slf4j
public class WithholdController {
    @Autowired
    private TWithholdService withholdService;
    @Autowired
    private WithholdBaseService withholdBaseService;
    @Autowired
    private TBatchCheckService batchCheckService;

    @ApiOperation(value = "上传代扣excel")
    @PostMapping("/excelImport")
    public CommonResult uploadExcel(@RequestParam("file") MultipartFile file,@Validated BatchNoVO batchNoVO){
        BusinValidatorContext context = BusinValidatorContext.getCurrentContext();
        context.set("totalTrans",batchNoVO.getTotalTrans());
        context.set("totalAmt",batchNoVO.getTotalAmt());
        context.set("password",batchNoVO.getPassword());
        int count = withholdService.uploadExcel(file);
        JSONObject data=new JSONObject();
        data.put("batchNo",context.get("batchNo"));
        if(count > 0)
            return CommonResult.success(data);
        return CommonResult.failed();
    }

    @ApiOperation(value = "下载扣款报告")
    @GetMapping("/export")
    public void excelExport(HttpServletResponse response, WithholdParam withholdParam){
        withholdBaseService.exportExcel(response,withholdParam);
    }


    @ApiOperation(value = "分页查询扣款批次")
    @GetMapping("/page")
    public CommonResult<CommonPage<TBatchNo>> page(TBatchNo batchNo, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        log.info("batchNo" + batchNo.toString());
        IPage page = withholdService.batchPage(batchNo,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation(value = "批量导出")
    @GetMapping("/batchExport")
    public void batchExport(HttpServletResponse response,@RequestParam List<String> batchNo){
        withholdBaseService.batchExport(response,batchNo);
    }

    @ApiOperation(value = "分页查询扣款明细")
    @GetMapping("/getDetail/{batchNo}")
    public CommonResult<CommonPage<TBatchDtl>> list(@PathVariable("batchNo")String batchNo, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdService.listPage(batchNo,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /*@ApiOperation(value = "批次审核")
    @GetMapping("/check/{batchId}")
    public CommonResult check(@PathVariable("batchId")Long batchId){

        return CommonResult.success(null);
    }*/

    @ApiOperation(value = "下载扣款模板")
    @GetMapping("/download/wh")
    public void downloadExport(HttpServletResponse response){

        withholdBaseService.exportExcel(response);
    }

    @ApiOperation(value = "扣款批次删除")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        int count =withholdService.delete(id);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation(value = "批量批次审核")
    @GetMapping("/batchCheck")
    public CommonResult batchCheck(@RequestParam List<String> batchId){
        int count=batchCheckService.insert(batchId);
        if (count>0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
