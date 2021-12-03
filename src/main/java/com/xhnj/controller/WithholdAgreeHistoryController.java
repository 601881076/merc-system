package com.xhnj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.service.TWithholdAgreeService;
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
 * @Description: 授权报告历史查询主控
 * @Author: tan_yi
 * @CreateDate: 2021/12/2 17:04
 * @UpdateUser: tan_yi
 * @UpdateDate: 2021/12/2 17:04
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 * @company: newLand
 */
@Api(value = "授权报告历史查询", tags = "授权报告历史查询")
@RestController
@RequestMapping("/agree/history")
@Slf4j
public class WithholdAgreeHistoryController {

    @Autowired
    private TWithholdAgreeService withholdAgreeService;

    @ApiOperation(value = "授权报告历史数据查询")
    @GetMapping("/query")
    public CommonResult<CommonPage<TWithholdAgree>> list(TWithholdAgree withholdAgree, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        log.info("授权条件查询，请求参数{}", withholdAgree.toString());

        // 查询信息
        IPage page = withholdAgreeService.selectAgreeHistory(withholdAgree,pageSize, pageNum);

        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation(value = "授权报告查询导出")
    @GetMapping("/excelBatchExport")
    public void excelBatchExport(HttpServletResponse response, TWithholdAgree withholdAgree) {

        withholdAgreeService.exportHistoryExcel(response, withholdAgree);

    }
}
