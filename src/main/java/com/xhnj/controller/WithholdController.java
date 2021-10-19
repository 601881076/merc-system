package com.xhnj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TBatchNo;
import com.xhnj.model.TPlatformserial;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.service.TWithholdService;
import com.xhnj.service.WithholdBaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/*
 @Description 代扣
 *@author kang.li
 *@date 2021/9/18 16:14   
 */
@ApiOperation("代扣")
@RestController
@RequestMapping("/wh")
public class WithholdController {
    @Autowired
    private TWithholdService withholdService;
    @Autowired
    private WithholdBaseService withholdBaseService;

    @ApiOperation(value = "上传代扣excel")
    @PostMapping("/excelImport")
    public CommonResult uploadExcel(@RequestParam("file") MultipartFile file){
        int count = withholdService.uploadExcel(file);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation(value = "下载扣款报告")
    @GetMapping("/export")
    public void excelExport(HttpServletResponse response, WithholdParam withholdParam){
        withholdBaseService.exportExcel(response,withholdParam);
    }

    @ApiOperation(value = "分页查询扣款批次")
    @GetMapping("/page")
    public CommonResult<CommonPage<TBatchNo>> page(@RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdService.batchPage(pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }


    @ApiOperation(value = "分页查询扣款明细")
    @GetMapping("/getDetail/{batchNo}")
    public CommonResult<CommonPage<TPlatformserial>> list(@PathVariable("batchNo")String batchNo, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdService.listPage(batchNo,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation(value = "批次审核")
    @GetMapping("/check/{batchId}")
    public CommonResult check(@PathVariable("batchId")Long batchId){


        return CommonResult.success(null);
    }

}
