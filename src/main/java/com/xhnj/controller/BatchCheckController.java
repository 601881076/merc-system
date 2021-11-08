package com.xhnj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.annotation.MyLog;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.common.ResultCode;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TBatchCheck;
import com.xhnj.model.TDismissBatch;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.service.ApprovalManagementService;
import com.xhnj.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 代扣批次审批
 * </p>
 *
 * @author lk
 * @since 2021-10-25
 */
@Slf4j
@Api(value = "代扣批次审批", tags = "代扣批次审批接口")
@RestController
@RequestMapping("/batchCheck")
public class BatchCheckController {

    @Value("${mq.exchange}")
    private String exchange;
    /**
     * mq消息队列处理
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 授权管理实现类
     */
    @Autowired
    ApprovalManagementService approvalManagementService;


    @ApiOperation(value = "分页查询授权取消批次")
    @GetMapping("/page")
    public CommonResult<CommonPage<TDismissBatch>> page(DisMissBatchQuery dismissBatch, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        log.info("授权取消批次查询参数" + dismissBatch.toString());
        IPage page = approvalManagementService.batchPage(dismissBatch, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }


    @ApiOperation(value = "代扣批次审批拒绝")
    @GetMapping("/refuse")
    @MyLog(operate = "修改", objectType = "代扣批次", objectName = "扣款批次审批", descript = "代扣批次审批拒绝")
    public CommonResult refuse(@RequestParam List<String> batchNo) {
        log.info("授权取消审批拒绝传入参数 = " + batchNo.toString());

        if ( 0 >= batchNo.size())
            return CommonResult.failed(ResultCode.PLEASE_SELECT, "请选择一条数据进行操作");

        // 校验该list中是否有已审核通过的批次
        List<TBatchCheck> batchCheckList =  approvalManagementService.selectCheckPassBatch(batchNo);
        if (0 < batchCheckList.size()) {
            // 将校验失败的批次号组成XXXXX,XXXXX的形式返给前端
            StringBuffer sb = new StringBuffer();
            batchCheckList.forEach(item -> sb.append(item.getBatchNo()).append(","));

            return CommonResult.failed(ResultCode.FAILED,"[" + sb + "]批次审核已通过，请重新选择");
        }

        // 获取当前登录用户
        TAdmin currentUser = UserUtil.getCurrentAdminUser();

        int count = approvalManagementService.update(2, batchNo, currentUser);
        if (count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation(value = "代扣批次审批批准")
    @GetMapping("/approve")
    @MyLog(operate = "修改", objectType = "代扣批次", objectName = "扣款批次审批", descript = "代扣批次审批批准")
    public CommonResult approve(@RequestParam List<String> batchNo) {
        log.info("授权取消审批批准传入参数 = " + batchNo.toString());

        if ( 0 >= batchNo.size())
            return CommonResult.failed(ResultCode.PLEASE_SELECT, "请选择一条数据进行操作");

        // 校验该list中是否有已审核通过的批次
        List<TBatchCheck> batchCheckList =  approvalManagementService.selectCheckPassBatch(batchNo);
        if (0 < batchCheckList.size()) {
            // 将校验失败的批次号组成XXXXX,XXXXX的形式返给前端
            StringBuffer sb = new StringBuffer();
            batchCheckList.forEach(item -> sb.append(item.getBatchNo()).append(","));

            return CommonResult.failed(ResultCode.FAILED,"[" + sb + "]批次审核已通过，请重新选择");
        }

        // 获取当前登录用户
        TAdmin currentUser = UserUtil.getCurrentAdminUser();

        int count = approvalManagementService.update(1, batchNo, currentUser);

        // 审核通过之后将批次号发往消息队列
//        batchNo.forEach(item -> mqSend(exchange, "/ums", item));

        if (count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }


    @ApiOperation(value = "下载批量导出报告")
    @GetMapping("/excelBatchExport")
    public void excelBatchExport(HttpServletResponse response, DisMissBatchQuery disMissBatchQuery) {
        log.info("下载取消授权批量报告, 参数 = " + disMissBatchQuery.toString());
        approvalManagementService.exportExcel(response, disMissBatchQuery);
    }


    /**
     * 推送消息队列
     *
     * @param exchange
     * @param routeKey
     * @param message
     */
    public void mqSend(String exchange, String routeKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routeKey, message, msg -> {
            msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return msg;
        });
    }


}
