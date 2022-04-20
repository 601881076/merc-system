package com.mercsystem.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mercsystem.common.CommonPage;
import com.mercsystem.common.CommonResult;
import com.mercsystem.model.TLog;
import com.mercsystem.service.TLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TLogController.java
 * @Description: 日志管理
 * @ProjectName com.mercsystem.controller
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:05
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
