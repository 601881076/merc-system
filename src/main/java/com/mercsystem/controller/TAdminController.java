package com.mercsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.annotation.MyLog;
import com.mercsystem.common.CommonPage;
import com.mercsystem.common.CommonResult;
import com.mercsystem.common.exception.BusinessException;
import com.mercsystem.model.TAdmin;
import com.mercsystem.pojo.bo.AdminUserDetails;
import com.mercsystem.pojo.query.AdminRequestParam;
import com.mercsystem.pojo.query.MercAdminLoginParam;
import com.mercsystem.service.TAdminService;
import com.mercsystem.util.JwtTokenUtil;
import com.mercsystem.util.SMSUtils;
import com.mercsystem.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

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
@Slf4j
@Api(tags = "用户管理页面")
public class TAdminController {

    @Autowired
    private TAdminService adminService;

    /** jwt工具类*/
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /** 密码加密实现类*/
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    /**
     *
     * @param param
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "用户分页查询接口")
    public CommonResult<CommonPage<TAdmin>> list(AdminRequestParam param) {
        // 分页组件
        Page page = new Page(param.getPageSize(), param.getPageNum());

        // 日期格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 拼接sql条件
        QueryWrapper wrapper = new QueryWrapper();
        if (param.getUserId() != null) {
            // 用户id
            wrapper.eq("id", param.getUserId());
        }

        if (param.getStatus() != null) {
            // 用户状态
            wrapper.eq("status", param.getStatus());
        }

        if (StringUtils.hasLength(param.getStartTime())) {
            // 开始时间
            LocalDateTime startTime = LocalDateTime.parse(param.getStartTime(), formatter);
            // 创建时间 >= 开始时间
            wrapper.ge("create_time", startTime);
        }

        if (StringUtils.hasLength(param.getEndTime())) {
            log.info("时间查询 {}", param.getEndTime());
            // 结束时间
            LocalDateTime endTime = LocalDateTime.parse(param.getEndTime(), formatter);
            // 创建时间 <= 结束时间
            wrapper.le("create_time", endTime);
        }

        // 分页查询 sql
        wrapper.orderByAsc("id");
        Page result = adminService.page(page, wrapper);

        return  CommonResult.success(CommonPage.restPage(result));
    }

    /**
     * 登录接口， 登录成功返回token
     * @param loginParam
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public CommonResult login(MercAdminLoginParam loginParam) {
        log.info("登录接口 , 请求数据: {}", loginParam);
        HashMap<String, String> map = new HashMap<>();

        // 获取Json报文。

        //  校验参数是否正常 手机号
        if (!SMSUtils.isMobile(loginParam.getUsername()))
            return CommonResult.failed("手机号格式有误");

        // 查询用户信息
        TAdmin admin = adminService.selectUserInfoByUsername(loginParam.getUsername());

        // 比较用户密码 如果前端传进来的密码由进行RSA加密的话，这里还需要进行解密
        if(!passwordEncoder.matches(loginParam.getPassword(), admin.getPassword())){
            throw new BadCredentialsException("账号或密码不正确");
        }

        // 认证通过生成token
        String token = jwtTokenUtil.generateToken(new AdminUserDetails(admin));

        // 通过数据库存储token
        admin.setToken(token);
        int updateResult = adminService.update(admin);
        if (1 != updateResult)
            throw new BusinessException("更新用户数据异常");

        // 返回token
        map.put("token",token);
        map.put("tokenHead", tokenHead);

        return CommonResult.success(map);
    }

    /**
     * 用户详情查询
     * @param admin
     * @return
     */
    @GetMapping("/detail")
    @ApiOperation(value = "用户查询接口")
    public CommonResult select(TAdmin admin) {
        TAdmin user = UserUtil.getCurrentAdminUser();
        return CommonResult.success(user);
    }

    /**
     * 用户注册
     * @param admin
     * @return
     */
    @ApiOperation(value = "用户注册接口")
    @PostMapping("/insert")
    public CommonResult insert( TAdmin admin) {
        adminService.insert(admin);
        return CommonResult.success("注册成功");
    }


    /**
     * 用户修改
     * @param admin
     * @return
     */
    @ApiOperation(value = "用户修改接口")
    @PostMapping("/update")
    public CommonResult update( TAdmin admin) {
        log.info("用户修改 username = {}, 对象 = {}", admin.getUsername(), admin);
        int update = adminService.update(admin);
        if (update != 1)
            throw new BusinessException("用户不存在");

        return CommonResult.success("修改成功");
    }


    /**
     * 删除用户
     * @param userId
     * @return
     */
    @ApiOperation(value = "用户删除接口")
    @GetMapping("/delete/{userId}")
    public CommonResult delete(@PathVariable Integer userId) {
        boolean result = adminService.removeById(userId);
        if (!result)
            return CommonResult.failed("删除异常");

        return CommonResult.success("删除成功");
    }

    /**
     * 冻结用户
     * @param userId
     * @return
     */
    @ApiOperation(value = "用户冻结接口")
    @GetMapping("/freeze/{userId}")
    public CommonResult freeze(@PathVariable Integer userId) {

        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.set("status",0);
        wrapper.eq("id",userId);
        boolean result = adminService.update(wrapper);

        return CommonResult.success("冻结成功");
    }


    @ApiOperation(value = "退出接口")
    @PostMapping("/logout")
    public CommonResult logout() {
        log.info("退出接口");
        return CommonResult.success("退出成功");
    }






















}
