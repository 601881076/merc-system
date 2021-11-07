package com.xhnj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TLog;
import com.xhnj.service.TLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* @Description:    日志管理
* @Author:         tan_yi
* @CreateDate:     2021/11/7 17:42
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/7 17:42
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Api(value = "日志管理", tags = "日志管理")
@RestController
@RequestMapping("/log")
@Slf4j
public class TLogController {

    @Autowired
    TLogService logService;

    @ApiOperation(value = "分页获取日志数据")
    @GetMapping("/list")
    public CommonResult<CommonPage<TLog>> list(TLog tLog,
                                               @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        IPage page = logService.listPage(tLog, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }


}
