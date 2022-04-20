package com.mercsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mercsystem.pojo.bo.MenuNode;
import com.mercsystem.model.TMenu;
import com.mercsystem.pojo.query.MenuParam;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author tanyi
 * @since 2021-02-24
 */
public interface TMenuService extends IService<TMenu> {
    Page listPage(MenuParam menuParam, Integer pageSize, Integer pageNum);

    Page listLevel(MenuParam menuParam, Integer pageSize, Integer pageNum);

    List<MenuNode> treeList();

    int create(TMenu menu);

    int update(TMenu menu);

    /**
     *
     * @param id 菜单id
     * @return
     */
    int delete(Long id, String name);
}
