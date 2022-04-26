package com.mercsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mercsystem.common.CommonResult;
import com.mercsystem.common.exception.BusinessException;
import com.mercsystem.model.TAdmin;
import com.mercsystem.pojo.bo.AdminUserDetails;
import com.mercsystem.pojo.query.MercAdminLoginParam;
import com.mercsystem.service.TAdminService;
import com.mercsystem.util.JwtTokenUtil;
import com.mercsystem.util.SMSUtils;
import com.mercsystem.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
     * 登录接口， 登录成功返回token
     * @param loginParam
     * @return
     */
    @PostMapping("/login")
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
     * 用户查询
     * @param admin
     * @return
     */
    @GetMapping("/list")
    public CommonResult select(TAdmin admin) {
        TAdmin user = UserUtil.getCurrentAdminUser();
        return CommonResult.success(user);
    }

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


    /**
     * 用户修改
     * @param admin
     * @return
     */
    @PostMapping("/update")
    public CommonResult update(@RequestBody TAdmin admin) {
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
    @GetMapping("/freeze/{userId}")
    public CommonResult freeze(@PathVariable Integer userId) {

        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.set("status",0);
        wrapper.eq("id",userId);
        boolean result = adminService.update(wrapper);


        return CommonResult.success("冻结成功");
    }






















}
