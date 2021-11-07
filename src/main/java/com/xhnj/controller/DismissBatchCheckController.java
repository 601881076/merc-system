package com.xhnj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.annotation.MyLog;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TDismissBatch;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.service.TDismissBatchCheckService;
import com.xhnj.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 授权取消批次审批
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@Slf4j
@Api(value = "授权取消批次审批", tags = "授权取消批次审批接口")
@RestController
@RequestMapping("/disbatchcheck")
public class DismissBatchCheckController {

    @Autowired
    private TDismissBatchCheckService dismissBatchCheckService;


    @ApiOperation(value = "分页查询授权取消批次")
    @GetMapping("/page")
    public CommonResult<CommonPage<TDismissBatch>> page(DisMissBatchQuery dismissBatch, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        log.info("授权取消批次查询参数, {}", dismissBatch.toString());
        IPage page = dismissBatchCheckService.batchPage(dismissBatch, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }


    @ApiOperation(value = "授权取消审批拒绝")
    @GetMapping("/refuse")
    @MyLog(operate = "修改", objectType = "授权批次", objectName = "授权取消审批", descript = "授权取消审批拒绝")
    public CommonResult refuse(@RequestParam List<String> batchNo) {
        log.info("授权取消审批拒绝传入参数 = " + batchNo.toString());

        // 获取当前登录用户
        TAdmin currentUser = UserUtil.getCurrentAdminUser();

        int count = dismissBatchCheckService.update(2, batchNo, currentUser);
        if (count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation(value = "授权取消审批批准")
    @GetMapping("/approve")
    @MyLog(operate = "修改", objectType = "授权批次", objectName = "授权取消审批", descript = "授权取消审批批准")
    public CommonResult approve(@RequestParam List<String> batchNo) {
        log.info("授权取消审批批准传入参数 = " + batchNo.toString());

        // 获取当前登录用户
        TAdmin currentUser = UserUtil.getCurrentAdminUser();

        int count = dismissBatchCheckService.update(1, batchNo, currentUser);

        // 审核通过之后讲批次号发往消息队列
//        batchNo.forEach(item -> mqSend(exchange, "/ums", item));

        if (count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

}
