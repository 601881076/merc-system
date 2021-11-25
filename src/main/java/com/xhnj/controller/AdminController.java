package com.xhnj.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.annotation.MyLog;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.common.ResultCode;
import com.xhnj.model.TAdminRole;
import com.xhnj.model.TAdminRoles;
import com.xhnj.pojo.query.UmsAdminLoginParam;
import com.xhnj.model.TAdmin;
import com.xhnj.pojo.query.UmsAdminUpdatePassParam;
import com.xhnj.service.TAdminService;
import com.xhnj.service.TRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 *  后台用户管理
 * </p>
 *
 * @author lk
 * @since 2021-02-24
 */
@Api(value = "用户管理", tags = "用户接口")
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private TAdminService adminService;
    @Autowired
    private TRoleService roleService;

    @ApiOperation(value = "分页获取用户列表数据")
    @GetMapping("/list")
    public CommonResult<CommonPage<TAdminRoles>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                      @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = adminService.getUserPage(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }


    @ApiOperation(value = "登录以后返回token")
    @PostMapping("/login")
    @MyLog(operate = "登录", objectType = "登录操作", objectName = "登录", descript = "登录以后返回token")
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result, HttpServletRequest request) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        if(!fieldErrors.isEmpty()){
            return CommonResult.failed(fieldErrors.get(0).getDefaultMessage());
        }
        //校验验证码
        String sessionCode = String.valueOf(request.getServletContext().getAttribute("JCCODE")).toLowerCase();
        String receivedCode = umsAdminLoginParam.getCode().toLowerCase();
        boolean b = !sessionCode.equals("") && !receivedCode.equals("") && sessionCode.equals(receivedCode);
        if(!b){
            return CommonResult.failed(ResultCode.VALIDATE_FAILED,"验证码错误");
        }

        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }

        // 校验用户是否被锁
        if ("502".equals(token)) {
            return CommonResult.failed(ResultCode.USER_DISSABLE, "用户已被锁，无法登录");
        }
        TAdmin adminByUsername = adminService.getAdminByUsername(umsAdminLoginParam.getUsername());


        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        if(adminByUsername.getFirstLoginTime() == null){
            return CommonResult.success(tokenMap,"密码已重制,请重新修改密码");

        }
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/info")
    public CommonResult<Map<String, Object>> info(Principal principal) {
        if (principal == null)
            return CommonResult.unauthorized(null);
        TAdmin admin = adminService.getAdminByUsername(principal.getName());
        Long id = admin.getId();
        TAdminRole admrol = adminService.slecRole(id);
        Map<String, Object> data = new HashMap<>();
        data.put("username", admin.getUsername());
        data.put("userType", admin.getUserType());
        data.put("nikeName", admin.getNickName());
        data.put("roles", roleService.getUmsRole(id));
        data.put("icon", admin.getIcon());
        Optional.ofNullable(admrol).ifPresent(e -> data.put("roleId", admrol.getRoleId()));
        data.put("menus", roleService.getUmsMenuByAdminId(id));

        if (admin.getFirstLoginTime()== null) {
            adminService.updateAdminFirstTime(admin.getUsername());
            data.put("fistFlag", "0");
        }else{
            data.put("fistFlag", "1");
        }
        return CommonResult.success(data);


    }

    @ApiOperation(value = "退出")
    @PostMapping("/logout")
    @MyLog(operate = "退出", objectType = "退出操作", objectName = "退出", descript = "退出")
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/insert")
    @MyLog(operate = "添加", objectType = "系统权限管理", objectName = "用户管理", descript = "添加用户")
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
    @MyLog(operate = "删除", objectType = "系统权限管理", objectName = "用户管理", descript = "删除用户信息")
    public CommonResult delete(@PathVariable("id") Long id) {
        log.info("删除用户 = " + id);
        int count = adminService.deleteAdmin(id);
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
