package com.xhnj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhnj.bo.MenuNode;
import com.xhnj.model.TMenu;
import com.xhnj.pojo.query.MenuParam;
import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author lk
 * @since 2021-02-24
 */
public interface TMenuService extends IService<TMenu> {
    Page listPage(MenuParam menuParam, Integer pageSize, Integer pageNum);

    Page listLevel(MenuParam menuParam,Integer pageSize, Integer pageNum);

    List<MenuNode> treeList();

    int create(TMenu menu);

    int update(TMenu menu);

    /**
     *
     * @param id 菜单id
     * @return
     */
    int delete(Long id);
}
