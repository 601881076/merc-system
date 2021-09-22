package com.xhnj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TPlatformserial;
import com.xhnj.service.TWithholdAgreeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    private TWithholdAgreeService withholdAgreeService;

    @ApiOperation(value = "授权查询")
    @GetMapping("/getDetail/{batchNo}")
    public CommonResult<CommonPage<TPlatformserial>> list(@PathVariable("batchNo")String batchNo, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdAgreeService.listPage(batchNo,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }


    @ApiOperation(value = "取消授权上传")
    @PostMapping("/excelImport")
    public CommonResult uploadExcel(@RequestParam("file") MultipartFile file){
        int count = withholdAgreeService.uploadExcel(file);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }


}
