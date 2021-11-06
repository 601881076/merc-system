package com.xhnj.controller;

import com.xhnj.common.CommonResult;
import com.xhnj.model.TConfig;
import com.xhnj.model.TMenu;
import com.xhnj.model.TRole;
import com.xhnj.service.TConfigService;
import com.xhnj.service.TRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  后台用户管理
 * </p>
 *
 * @author lk
 * @since 2021-02-24
 */
@Api(value = "配置管理", tags = "配置接口")
@RestController
@RequestMapping("/config")
@Slf4j
public class ConfigController {
    @Autowired
    private TConfigService configService;

    @ApiOperation("获取指定配置")
    @GetMapping("/qvalue")
    public CommonResult<TConfig> qvalue(TConfig configs) {
        TConfig config = configService.selctOne(configs);
        return CommonResult.success(config);
    }

    @ApiOperation("编辑配置")
    @PostMapping("/update")
    public CommonResult update(@RequestBody TConfig configs) {
        int count = configService.updOne(configs);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }
}
