package com.xhnj.pojo.bo;

import com.xhnj.model.TMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 后台菜单节点封装
 */
@Getter
@Setter
public class MenuNode extends TMenu {
    private List<MenuNode> children;
}
