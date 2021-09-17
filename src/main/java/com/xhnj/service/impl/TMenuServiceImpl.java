package com.xhnj.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.bo.MenuNode;
import com.xhnj.common.exception.BusinessException;
import com.xhnj.mapper.TMenuMapper;
import com.xhnj.model.TMenu;
import com.xhnj.pojo.query.MenuParam;
import com.xhnj.service.TMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-02-24
 */
@Service
public class TMenuServiceImpl extends ServiceImpl<TMenuMapper, TMenu> implements TMenuService {
    @Resource
    private TMenuMapper menuMapper;

    @Override
    public Page listPage(MenuParam menuParam, Integer pageSize, Integer pageNum) {
        Page<TMenu> page = new Page<>(pageNum, pageSize);
        QueryWrapper wrapper = new QueryWrapper();
        String keyword = menuParam.getKeyword();
        Long parentId = menuParam.getParentId();
        if(StrUtil.isNotBlank(keyword)) {
            wrapper.like("name", keyword);
        }
        if(parentId != null){
            wrapper.eq("parent_id",parentId);
        }
        return menuMapper.selectPage(page, wrapper);
    }

    @Override
    public List<MenuNode> treeList() {
            List<TMenu> menuList =  menuMapper.selectList(new QueryWrapper<TMenu>());
        List<MenuNode> result = menuList.stream()
                .filter(menu -> menu.getParentId() == 0)
                .map(menu ->covertMenuNode(menu, menuList))
                .collect(Collectors.toList());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int create(TMenu menu) {
        if(menu.getParentId() == null)
            menu.setParentId(0L);
        return menuMapper.insert(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(TMenu menu) {
        if(menu.getId() == null)
            throw new BusinessException("id不能为空");
        return menuMapper.updateById(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delete(Long id) {
        if(id == null)
            throw new BusinessException("id不能为空");
        return menuMapper.deleteById(id);
    }

    private MenuNode covertMenuNode(TMenu menu, List<TMenu> menuList) {
        MenuNode node = new MenuNode();
        BeanUtils.copyProperties(menu, node);
        List<MenuNode> childrens = menuList.stream()
                .filter(subMenu ->node.getId().equals(subMenu.getParentId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList))
                .collect(Collectors.toList());
     /*   if (CollUtil.isNotEmpty(childrens)) {
            node.setChildren(childrens);
        }*/
        node.setChildren(childrens);
        return node;
    }
}
