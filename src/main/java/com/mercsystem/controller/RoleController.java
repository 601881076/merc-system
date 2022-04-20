package com.mercsystem.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.annotation.MyLog;
import com.mercsystem.common.CommonPage;
import com.mercsystem.common.CommonResult;
import com.mercsystem.model.TMenu;
import com.mercsystem.model.TRole;
import com.mercsystem.service.TRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName RoleController.java
 * @Description: 角色管理
 * @ProjectName com.mercsystem.controller
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:05
*/
@Api(value = "角色接口", tags = "角色接口")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private TRoleService roleService;

    @ApiOperation("获取所有角色")
    @GetMapping("/listAll")
    public CommonResult<List<TRole>> listAll() {
        return CommonResult.success(roleService.listAll());
    }

    @ApiOperation(value = "分页获取角色列表数据")
    @GetMapping("/list")
    public CommonResult<CommonPage<TRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page page = roleService.listPage(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation("获取指定角色的菜单")
    @GetMapping("/menu/{id}")
    public CommonResult<List<TMenu>> menu(@PathVariable("id") Long id) {
        List<TMenu> menus = roleService.getUmsMenu(id);
        return CommonResult.success(menus);
    }

    @ApiOperation("添加角色")
    @PostMapping("/add")
    @MyLog(operate = "添加", objectType = "系统权限管理", objectName = "角色管理", descript = "添加角色: #{#role.name}")
    public CommonResult create(@Validated @RequestBody TRole role, BindingResult result) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        if(!fieldErrors.isEmpty()){
            return CommonResult.failed(fieldErrors.get(0).getDefaultMessage());
        }
        int count = roleService.create(role);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation("编辑角色")
    @PostMapping("/update")
    @MyLog(operate = "修改", objectType = "系统权限管理", objectName = "角色管理", descript = "编辑角色")
    public CommonResult update(@RequestBody TRole role) {
        int count = roleService.update(role);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation("删除角色")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        String name = "";
        TRole role = roleService.getById(id);
        if(role != null)
            name = role.getName();
        int count = roleService.delete(id, name);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation("分配菜单")
    @PostMapping("/allocMenu")
    @MyLog(operate = "修改", objectType = "系统权限管理", objectName = "角色管理", descript = "分配菜单")
    public CommonResult allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

}
