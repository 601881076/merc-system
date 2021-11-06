package com.xhnj.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.model.TWithholdCancle;
import com.xhnj.pojo.vo.BatchNoVO;
import com.xhnj.service.TBatchCheckService;
import com.xhnj.service.TWithholdAgreeService;
import com.xhnj.service.TWithholdCancleService;
import com.xhnj.service.WithholdAgreeDismissBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
 @Description 代扣协议取消
 *@author kang.li
 *@date 2021/9/25 20:36
 */
@Api(value = "代扣协议取消", tags = "代扣协议取消接口")
@RestController
@RequestMapping("/wad")
@Slf4j
public class WithholdAgreeDismissController {

    @Value("${mq.exchange}")
    private String exchange;

    @Autowired
    private TWithholdAgreeService withholdAgreeService;
    @Autowired
    private TWithholdCancleService withholdCancleService;

    @Autowired
    private WithholdAgreeDismissBaseService WithholdAgreeDismissBaseService;
    @Autowired
    private TBatchCheckService batchCheckService;

    @ApiOperation(value = "分页查询授权取消批次")
    @GetMapping("/page")
    public CommonResult<CommonPage<TDismissBatch>> page(TDismissBatch dismissBatch, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdAgreeService.batchPage(dismissBatch,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation(value = "分页查询授权取消明细")
    @GetMapping("/getDetail/{batchNo}")
    public CommonResult<CommonPage<TWithholdCancle>> list(@PathVariable("batchNo")String batchNo, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdCancleService.listPage(batchNo,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation(value = "授权取消删除")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        int count =WithholdAgreeDismissBaseService.delete(id);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation(value = "取消授权excel上传")
    @PostMapping("/excelImport")
    public CommonResult uploadExcel(@RequestParam("file") MultipartFile file, BatchNoVO batchNoVO){
        BusinValidatorContext context = BusinValidatorContext.getCurrentContext();
        context.set("password",batchNoVO.getPassword());
        context.set("totalTrans",batchNoVO.getTotalTrans());
        int count = withholdAgreeService.uploadExcel(file);
        JSONObject data=new JSONObject();
        data.put("batchNo",context.get("batchNo"));
        if(count > 0)
            return CommonResult.success(data);
        return CommonResult.failed();
    }

    @ApiOperation(value = "下载授权取消模板")
    @GetMapping("/download/wad")
    public void downloadExport(HttpServletResponse response){

        WithholdAgreeDismissBaseService.exportExcel(response);
    }

    @ApiOperation(value = "授权取消提交")
    @GetMapping("/batchCheckSub")
    public CommonResult batchCheckSub(@RequestParam List<String> batchId){
        log.info("batchId;"+batchId.toString());
        int count=batchCheckService.insertCheck(batchId);
        if (count>0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


}
