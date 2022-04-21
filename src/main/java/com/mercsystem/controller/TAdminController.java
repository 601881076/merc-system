package com.mercsystem.controller;


import com.mercsystem.common.CommonResult;
import com.mercsystem.model.TAdmin;
import com.mercsystem.service.TAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 后台管理用户表 前端控制器
 * </p>
 *
 * @author tanyi
 * @since 2022-04-21
 */
@RestController
@RequestMapping("/admin")
public class TAdminController {

    @Autowired
    private TAdminService adminService;

    /**
     * 用户注册
     * @param admin
     * @return
     */
    @PostMapping("/insert")
    public CommonResult insert(@RequestBody TAdmin admin) {
        adminService.insert(admin);
        return CommonResult.success("注册成功");
    }






















}
