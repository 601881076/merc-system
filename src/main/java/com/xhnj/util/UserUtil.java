package com.xhnj.util;

import com.xhnj.bo.AdminUserDetails;
import com.xhnj.model.TAdmin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/*
 @Description
 *@author kang.li
 *@date 2021/3/2 9:56   
 */
public class UserUtil {

    /**
     * 获取当前登录用户
     * @return
     */
    public static TAdmin getCurrentAdminUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof AdminUserDetails){
            AdminUserDetails adminUserDetails = (AdminUserDetails)authentication.getPrincipal();
            return adminUserDetails.getAdmin();
        }
        return null;
    }

}
