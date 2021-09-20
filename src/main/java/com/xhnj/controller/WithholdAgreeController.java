package com.xhnj.controller;

import com.xhnj.common.CommonResult;
import com.xhnj.service.TWithholdService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/*
 @Description 代扣协议
 *@author kang.li
 *@date 2021/9/17 20:14   
 */
@RestController
@RequestMapping("/withhold/agree")
public class WithholdAgreeController {
    @Autowired
    private TWithholdService withholdService;


    @ApiOperation(value = "上传代扣协议excel")
    @PostMapping("/excelImport")
    public CommonResult uploadExcel(@RequestParam("file") MultipartFile file){
        int count = withholdService.uploadExcel(file);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

}
