package com.mercsystem.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercsystem.annotation.MyLog;
import com.mercsystem.common.CommonPage;
import com.mercsystem.common.CommonResult;
import com.mercsystem.model.TMenu;
import com.mercsystem.pojo.bo.MenuNode;
import com.mercsystem.pojo.query.MenuParam;
import com.mercsystem.service.TMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName MenuController.java
 * @Description: 菜单管理
 * @ProjectName com.mercsystem.controller
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:05
*/
@Api(value = "菜单接口", tags = "菜单接口")
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private TMenuService menuService;

    /*@ApiOperation("获取所有菜单")
    @GetMapping("/listAll")
    public CommonResult<List<TMenu>> listAll() {
        return CommonResult.success(menuService.list());
    }*/

    @ApiOperation("根据级数获取菜单")
    @GetMapping("/listLevel")
    public CommonResult<CommonPage<TMenu>> listLevel(MenuParam menuParam,
                                               @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page page = menuService.listLevel(menuParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation(value = "分页获取菜单")
    @GetMapping("/list/{parentId}")
    public CommonResult<CommonPage<TMenu>> list(MenuParam menuParam,
                                                @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page page = menuService.listPage(menuParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @GetMapping("/treeList")
    public CommonResult<List<MenuNode>> treeList() {
        List<MenuNode> menuNodes = menuService.treeList();
        return CommonResult.success(menuNodes);
    }

    @ApiOperation("添加菜单")
    @PostMapping("/add")
    @MyLog(operate = "添加", objectType = "菜单", objectName = "菜单管理", descript = "添加菜单: #{#menu.name}")
    public CommonResult create(@Validated @RequestBody TMenu menu, BindingResult result) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        if(!fieldErrors.isEmpty()){
            return CommonResult.failed(fieldErrors.get(0).getDefaultMessage());
        }
        int count = menuService.create(menu);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation("编辑菜单")
    @PostMapping("/update")
    @MyLog(operate = "修改", objectType = "菜单", objectName = "菜单管理", descript = "编辑菜单")
    public CommonResult update(@Validated @RequestBody TMenu menu, BindingResult result) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        if(!fieldErrors.isEmpty()){
            return CommonResult.failed(fieldErrors.get(0).getDefaultMessage());
        }
        int count = menuService.update(menu);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation("删除菜单")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        String name = "";
        TMenu menu = menuService.getById(id);
        if(menu != null)
            name = menu.getName();
        int count = menuService.delete(id, name);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

}
