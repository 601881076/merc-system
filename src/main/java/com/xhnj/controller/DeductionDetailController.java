package com.xhnj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TBatchDtl;
import com.xhnj.model.TBatchNo;
import com.xhnj.pojo.query.DeductionDetailQuery;
import com.xhnj.pojo.query.PoundageParam;
import com.xhnj.service.DeductionDetailService;
import com.xhnj.service.TWithholdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: ums-admin
 * @description: 扣款明细
 * @author: xiel
 * @create: 2021-11-05 16:34
 **/
@Api(value = "扣款明细", tags = "扣款明细接口")
@RestController
@RequestMapping("/dedu")
@Slf4j
public class DeductionDetailController {
    @Autowired
    private DeductionDetailService DeductionDetailService;

    @ApiOperation(value = "分页查询扣款明细")
    @GetMapping("/page")
    public CommonResult<CommonPage<TBatchDtl>> list(DeductionDetailQuery tbatchDtl, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = DeductionDetailService.listPage(tbatchDtl,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }
    @ApiOperation(value = "扣款明细导出excel")
    @GetMapping("/export")
    public void excelExport(HttpServletResponse response, DeductionDetailQuery tbatchDtl){
        DeductionDetailService.exportExcel(response,tbatchDtl);
    }
}
