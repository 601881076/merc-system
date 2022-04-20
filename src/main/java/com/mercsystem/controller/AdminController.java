package com.mercsystem.controller;


import com.mercsystem.annotation.MyLog;
import com.mercsystem.common.CommonResult;
import com.mercsystem.model.TAdminRole;
import com.mercsystem.pojo.query.MercAdminLoginParam;
import com.mercsystem.model.TAdmin;
import com.mercsystem.pojo.query.UmsAdminUpdatePassParam;
import com.mercsystem.service.TAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AdminController.java
 * @Description: 后台用户管理
 * @ProjectName com.mercsystem.controller
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:05
*/
@Api(value = "用户管理", tags = "用户接口")
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private TAdminService adminService;



    @ApiOperation(value = "登录以后返回token")
    @PostMapping("/login")
    @MyLog(operate = "登录", objectType = "登录操作", objectName = "登录", descript = "登录以后返回token")
    public CommonResult login(@Validated @RequestBody MercAdminLoginParam loginParam, BindingResult result, HttpServletRequest request) {
        log.info("登录接口 , 用户: {}", loginParam.getUsername());


        return CommonResult.success("登录成功");
    }

//    @ApiOperation(value = "获取登录用户信息")
//    @GetMapping("/info")
//    public CommonResult<Map<String, Object>> info(Principal principal) {
//        if (principal == null)
//            return CommonResult.unauthorized(null);
//        TAdmin admin = adminService.getAdminByUsername(principal.getName());
//        Long id = admin.getId();
//        TAdminRole admrol = adminService.slecRole(id);
//        Map<String, Object> data = new HashMap<>();
//        data.put("username", admin.getUsername());
//        data.put("userType", admin.getUserType());
//        data.put("nikeName", admin.getNickName());
//        data.put("roles", roleService.getUmsRole(id));
//        data.put("icon", admin.getIcon());
//        Optional.ofNullable(admrol).ifPresent(e -> data.put("roleId", admrol.getRoleId()));
//        data.put("menus", roleService.getUmsMenuByAdminId(id));
//        data.put("fistFlag", admin.getFistFlag());
//        TConfig tConfig = new TConfig();
//        tConfig.setFieldName("passwordTime");
//        TConfig tConfig1 = configService.selectByFieldName(tConfig);
//        String passwordTime = tConfig1.getValue();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String ly_time = sdf.format(new java.util.Date());
//        if(admin.getFirstLoginTime() != null){
//            String format = sdf.format(admin.getFirstLoginTime());
//            int i = Integer.parseInt(format);
//            int i1 = Integer.parseInt(ly_time);
//            if(i1 - i >= Integer.parseInt(passwordTime)){
//                adminService.updateAdminStatus(admin.getUsername());
//                adminService.updateAdminFirstFlg(admin.getId());
//            }
//        }
//        if (admin.getFistFlag().equals("0") ) {
//            adminService.updateAdminFirstTime(admin.getId());
//        }
//        return CommonResult.success(data);
//
//
//    }

    @ApiOperation(value = "退出")
    @PostMapping("/logout")
    @MyLog(operate = "退出", objectType = "退出操作", objectName = "退出", descript = "退出")
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/insert")
    @MyLog(operate = "添加", objectType = "系统权限管理", objectName = "用户管理", descript = "添加用户: #{#admin.username}")
    public CommonResult insert(TAdmin admin){
        int count=adminService.insertAdmin(admin);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation("编辑用户")
    @PostMapping("/update")
    @MyLog(operate = "修改", objectType = "系统权限管理", objectName = "用户管理", descript = "修改用户信息")
    public CommonResult update(TAdmin admin) {
        int count = adminService.updateAdmin(admin);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete/{id}")
    //@MyLog(operate = "删除", objectType = "系统权限管理", objectName = "用户管理", descript = "删除用户信息")
    public CommonResult delete(@PathVariable("id") Long id) {
        log.info("删除用户 = " + id);
        TAdmin admin = adminService.getById(id);
        String username = "";
        if(admin != null)
            username = admin.getUsername();
        int count = adminService.deleteAdmin(id, username);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation("更改密码")
    @PostMapping("/updatepass")
    @MyLog(operate = "修改", objectType = "系统权限管理", objectName = "用户管理", descript = "修改用户密码")
    public CommonResult updatePass(UmsAdminUpdatePassParam UmsAdminUpdatePassParam) {
        int count = adminService.updatePass(UmsAdminUpdatePassParam);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation("管理员重置密码")
    @PostMapping("/resetpass")
    @MyLog(operate = "修改", objectType = "系统权限管理", objectName = "用户管理", descript = "管理员重置密码")
    public CommonResult resetPass(TAdmin admin) {
        int count = adminService.resetPass(admin);
        if(count > 0)
            return CommonResult.success("重制密码成功，密码为 Passwd!23，请稍后自行修改密码");
        return CommonResult.failed();
    }
    @ApiOperation("分配角色")
    @PostMapping("/updaterole")
    @MyLog(operate = "修改", objectType = "系统权限管理", objectName = "角色管理", descript = "用户角色分配")
    public CommonResult updateRole(TAdminRole tadminrole) {
        int count = adminService.updateRole(tadminrole);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }
}
