package com.xhnj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.service.TWithholdAgreeService;
import com.xhnj.service.TWithholdCancleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/*
 @Description 代扣协议
 *@author kang.li
 *@date 2021/9/17 20:14   
 */
@Api(value = "代扣协议", tags = "代扣协议接口")
@RestController
@RequestMapping("/withhold/agree")
@Slf4j
public class WithholdAgreeController {
    @Autowired
    private TWithholdAgreeService withholdAgreeService;

    @Autowired
    private TWithholdCancleService withholdCancleService;

    @ApiOperation(value = "授权条件查询")
    @GetMapping("/query")
    public CommonResult<CommonPage<TWithholdAgree>> list(TWithholdAgree withholdAgree, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        log.info("授权条件查询，请求参数{}", withholdAgree.toString());


        // 查询信息
        IPage page = withholdAgreeService.conditionQuery(withholdAgree,pageSize, pageNum);


        return CommonResult.success(CommonPage.restPage(page));
    }

   /* @ApiOperation(value = "授权报告查询导出")
    @GetMapping("/excelBatchExport")
    public void excelBatchExport(HttpServletResponse response, @RequestParam("id") List<String> idList) {
        log.info("授权报告查询导出，请求参数{}", idList.toString());


        withholdAgreeService.exportExcel(response, idList);
    }*/
   @ApiOperation(value = "授权报告查询导出")
   @GetMapping("/excelBatchExport")
   public void excelBatchExport(HttpServletResponse response, TWithholdAgree withholdAgree) {

       withholdAgreeService.exportExcel(response, withholdAgree);

   }

}
