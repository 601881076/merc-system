package com.xhnj.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.model.TMenu;
import com.xhnj.pojo.bo.MenuNode;
import com.xhnj.pojo.query.MenuParam;
import com.xhnj.service.TMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author lk
 * @since 2021-02-24
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
        int count = menuService.delete(id);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

}
