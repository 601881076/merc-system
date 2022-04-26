package com.mercsystem.controller;


import com.mercsystem.common.CommonResult;
import com.mercsystem.model.TActivityInfo;
import com.mercsystem.model.TAdmin;
import com.mercsystem.service.TActivityInfoService;
import com.mercsystem.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 活动信息表 前端控制器
 * </p>
 *
 * @author tanyi
 * @since 2022-04-26
 */
@RestController
@RequestMapping("/activity")
public class TActivityInfoController {

    /** 活动服务层实现类*/
    @Autowired
    private TActivityInfoService activityInfoService;

    /**
     * 活动创建
     * @param activityInfo
     * @return
     */
    @PostMapping("/create")
    public CommonResult createActivity( TActivityInfo activityInfo) {
        // 获取当前登录用户
        TAdmin currentAdminUser = UserUtil.getCurrentAdminUser();
        activityInfo.setCreatePerson(currentAdminUser.getUsername());

        if (!activityInfoService.save(activityInfo))
            return CommonResult.failed("活动创建异常");

        return CommonResult.success("创建成功");
    }
}
