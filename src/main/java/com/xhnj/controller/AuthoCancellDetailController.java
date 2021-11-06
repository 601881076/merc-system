package com.xhnj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TWithholdCancle;
import com.xhnj.pojo.query.AuthCancelDetailQuery;
import com.xhnj.service.AuthCancelDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ums-admin
 * @description: 授权取消明细
 * @author: xiel
 * @create: 2021-11-06 15:19
 **/
@Api(value = "扣款明细", tags = "扣款明细接口")
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthoCancellDetailController {
    @Autowired
    private com.xhnj.service.AuthCancelDetailService AuthCancelDetailService;

    @ApiOperation(value = "分页查询授权取消明细")
    @GetMapping("/page")
    public CommonResult<CommonPage<TWithholdCancle>> list(AuthCancelDetailQuery detailQuery, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = AuthCancelDetailService.listPage(detailQuery,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }
}
