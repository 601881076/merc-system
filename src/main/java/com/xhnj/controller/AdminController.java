package com.xhnj.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.common.ResultCode;
import com.xhnj.dto.UmsAdminLoginParam;
import com.xhnj.model.TAdmin;
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

import javax.servlet.http.HttpSession;
import java.security.Principal;
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
@Api(value = "用户管理", tags = "用户接口类")
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
    public CommonResult<CommonPage<TAdmin>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                 @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page page = adminService.getUserPage(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }


    @ApiOperation(value = "登录以后返回token")
    @PostMapping("/login")
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result, HttpSession session) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        if(!fieldErrors.isEmpty()){
            return CommonResult.failed(fieldErrors.get(0).getDefaultMessage());
        }
        //校验验证码
        String sessionCode = String.valueOf(session.getAttribute("JCCODE")).toLowerCase();
        String receivedCode = umsAdminLoginParam.getCode().toLowerCase();
        boolean b = !sessionCode.equals("") && !receivedCode.equals("") && sessionCode.equals(receivedCode);
        if(!b){
            return CommonResult.failed(ResultCode.VALIDATE_FAILED,"验证码错误");
        }
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("登录失败");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/info")
    public CommonResult<Map<String, Object>> info(Principal principal) {
        if (principal == null)
            return CommonResult.unauthorized(null);
        TAdmin admin = adminService.getAdminByUsername(principal.getName());
        Long id = admin.getId();
        Map<String, Object> data = new HashMap<>();
        data.put("username", admin.getUsername());
        data.put("userType", admin.getUserType());
        data.put("nikeName", admin.getNickName());
        data.put("roles", roleService.getUmsRole(id));
        data.put("icon", admin.getIcon());
        data.put("menus", roleService.getUmsMenuByAdminId(id));
        return CommonResult.success(data);
    }

    @ApiOperation(value = "退出")
    @PostMapping("/logout")
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/insert")
    public CommonResult insert(TAdmin admin){
        int count=adminService.insertAdmin(admin);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation("编辑用户")
    @PostMapping("/update")
    public CommonResult update(TAdmin admin) {
        int count = adminService.updateAdmin(admin);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        int count = adminService.deleteAdmin(id);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

}
