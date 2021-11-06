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

    @ApiOperation(value = "授权条件查询")
    @GetMapping("/query")
    public CommonResult<CommonPage<TWithholdAgree>> list(TWithholdAgree withholdAgree, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        log.info("授权条件查询，请求参数{}", withholdAgree.toString());

        // 如果status = 5，则查询 操作标志 = 2 的数据。
        if (null != withholdAgree.getStatus()) {
            int status = withholdAgree.getStatus();
            if (5 == status) {
                log.info("操作标志");
                withholdAgree.setStatus(null);
                // 给操作标志字段赋值
                withholdAgree.setDealFlag(2);
            }
        }

        IPage page = withholdAgreeService.conditionQuery(withholdAgree,pageSize, pageNum);

        // 当status = 4 且 totalpage > 1 时，需修改返回的totalpage数量
        if (null != withholdAgree.getStatus()) {
            int status = withholdAgree.getStatus();

            long totalPage = status == 4  ? 1 : page.getTotal();
            page.setTotal(totalPage);
        }

        return CommonResult.success(CommonPage.restPage(page));
    }

}
