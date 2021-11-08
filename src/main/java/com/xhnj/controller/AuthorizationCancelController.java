package com.xhnj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TDismissBatchExcel;
import com.xhnj.service.AuthorizationCancelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
* @Description:    授权取消列表
* @Author:         tan_yi
* @CreateDate:     2021/11/6 15:41
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/6 15:41
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Slf4j
@Api(value = "授权取消列表", tags = "授权取消列表")
@RestController
@RequestMapping("/cancel")
public class AuthorizationCancelController {

    @Autowired
    private AuthorizationCancelService cancelService;

    @ApiOperation(value = "授权取消列表分页查询")
    @GetMapping("/cancelPage")
    public CommonResult<CommonPage<TDismissBatch>> SelectAuthorizationCancel(TDismissBatch dismissBatch,
                                                                             @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        log.info("授权取消列表分页查询1,{}", dismissBatch.toString());

        IPage page = cancelService.listPage(dismissBatch,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }


    @ApiOperation(value = "授权取消列表批量导出")
    @GetMapping("/excelBatchExport")
    public void excelBatchExport(HttpServletResponse response, TDismissBatchExcel dismissBatch) {
        log.info("授权取消列表批量导出, 参数 = " + dismissBatch.toString());
        cancelService.exportExcel(response, dismissBatch);
    }


}
