package com.xhnj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.pojo.query.WithholdAgreeParam;
import com.xhnj.service.TWithholdAgreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 @Description 代扣协议
 *@author kang.li
 *@date 2021/9/17 20:14   
 */
@Api(value = "代扣协议", tags = "代扣协议接口")
@RestController
@RequestMapping("/withhold/agree")
public class WithholdAgreeController {
    @Autowired
    private TWithholdAgreeService withholdAgreeService;

    @ApiOperation(value = "授权条件查询")
    @GetMapping("/query")
    public CommonResult<CommonPage<TWithholdAgree>> list(WithholdAgreeParam withholdAgreeParam, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdAgreeService.conditionQuery(withholdAgreeParam,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

}
