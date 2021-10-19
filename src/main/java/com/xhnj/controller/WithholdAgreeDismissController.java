package com.xhnj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.service.TWithholdAgreeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/*
 @Description 代扣协议取消
 *@author kang.li
 *@date 2021/9/25 20:36   
 */
@RestController
@RequestMapping("/wad")
public class WithholdAgreeDismissController {
    @Autowired
    private TWithholdAgreeService withholdAgreeService;

    @ApiOperation(value = "分页查询授权取消批次")
    @GetMapping("/page")
    public CommonResult<CommonPage<TDismissBatch>> page(@RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdAgreeService.batchPage(pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation(value = "分页查询授权取消明细")
    @GetMapping("/getDetail/{batchNo}")
    public CommonResult<CommonPage<TWithholdAgree>> list(@PathVariable("batchNo")String batchNo, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdAgreeService.listPage(batchNo,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }


    @ApiOperation(value = "取消授权excel上传")
    @PostMapping("/excelImport")
    public CommonResult uploadExcel(@RequestParam("file") MultipartFile file){
        int count = withholdAgreeService.uploadExcel(file);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }
}
