package com.xhnj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TDismissBatch;
import com.xhnj.pojo.query.DeductionBatchQuery;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.service.DeductionBatchService;
import com.xhnj.service.WithholdBaseService;
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
* @Description:    扣款批次审批页面
* @Author:         tan_yi
* @CreateDate:     2021/11/1 23:00
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/1 23:00
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/

@Slf4j
@Api(value = "扣款批次审批页面", tags = "扣款审批查询，修改接口")
@RestController
@RequestMapping("/deduction")
public class DeductionBatchApprovalController {

    @Autowired
    DeductionBatchService deductionBatchService;

    @ApiOperation(value = "分页查询扣款批次审批")
    @GetMapping("/page")
    public CommonResult<CommonPage<DeductionBatchQuery>> page(DeductionBatchQuery batchQuery,
                                                                @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        log.info("分页查询扣款批次审批查询参数" + batchQuery.toString());
        IPage page = deductionBatchService.page(batchQuery,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

}
