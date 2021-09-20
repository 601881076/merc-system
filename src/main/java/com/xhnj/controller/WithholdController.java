package com.xhnj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
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
@RequestMapping("/withhold")
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

    @ApiOperation(value = "查询扣款明细")
    @GetMapping("/getDetail/{batchNo}")
    public CommonResult<CommonPage<TPlatformserial>> list(@PathVariable("batchNo")String batchNo, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdService.listPage(batchNo,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

}
