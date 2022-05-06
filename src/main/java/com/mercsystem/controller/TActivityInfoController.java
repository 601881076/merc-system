package com.mercsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.common.CommonPage;
import com.mercsystem.common.CommonResult;
import com.mercsystem.common.exception.BusinessException;
import com.mercsystem.model.TActivityInfo;
import com.mercsystem.model.TAdmin;
import com.mercsystem.pojo.query.ActivityRequestParam;
import com.mercsystem.service.TActivityInfoService;
import com.mercsystem.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
@Api(tags = "活动管理页面")
public class TActivityInfoController {

    /** 活动服务层实现类*/
    @Autowired
    private TActivityInfoService activityInfoService;


    /**
     * 分页查询活动数据
     * @param param
     * @return
     */
    @ApiOperation(value = "分页查询活动信息数据")
    @PostMapping("/list")
    public CommonResult<CommonPage<TActivityInfo>> list(ActivityRequestParam param) {
        Page page = new Page(param.getPageNum(),param.getPageSize());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        QueryWrapper wrapper = new QueryWrapper();
        if (!ObjectUtils.isEmpty(param.getActivityId())) {
            // 活动id条件
            wrapper.eq("activity_id",param.getActivityId());
        }

        if (StringUtils.hasLength(param.getStartTime())) {
            // 开始时间
            LocalDateTime startTime = LocalDateTime.parse(param.getStartTime(), formatter);
            // 创建时间 >= 开始时间
            wrapper.ge("create_time", startTime);
        }

        if (StringUtils.hasLength(param.getEndTime())) {
            // 结束时间
            LocalDateTime endTime = LocalDateTime.parse(param.getEndTime(), formatter);
            // 创建时间 <= 结束时间
            wrapper.le("create_time",endTime);
        }

        Page result = activityInfoService.page(page, wrapper);

        return CommonResult.success(CommonPage.restPage(result));
    }
    /**
     * 活动创建
     * @param activityInfo
     * @return
     */
    @PostMapping("/create")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "活动创建")
    public CommonResult createActivity(TActivityInfo activityInfo) {
        // 获取当前登录用户
        TAdmin currentAdminUser = UserUtil.getCurrentAdminUser();
        activityInfo.setCreatePerson(currentAdminUser.getUsername());

        if (!activityInfoService.save(activityInfo))
            throw new BusinessException("活动创建异常");

        return CommonResult.success("创建成功");
    }

    /**
     * 活动修改
     * @param activityInfo
     * @return
     */
    @PostMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "活动修改")
    public CommonResult update(TActivityInfo activityInfo) {
        // 获取当前登录用户
        TAdmin currentAdminUser = UserUtil.getCurrentAdminUser();
        activityInfo.setUpdatePerson(currentAdminUser.getUsername());
        activityInfo.setUpdateTime(LocalDateTime.now());

        if (!activityInfoService.updateById(activityInfo))
            throw new BusinessException("修改活动信息异常");

        return CommonResult.success("修改成功");
    }





}
