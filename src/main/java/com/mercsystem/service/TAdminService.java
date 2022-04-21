package com.mercsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mercsystem.model.TAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mercsystem.model.TAdminRole;
import com.mercsystem.pojo.query.UmsAdminUpdatePassParam;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author tanyi
 */
public interface TAdminService extends IService<TAdmin> {
    String login(String username, String password);

    TAdmin getAdminByUsername(String username);

    IPage getUserPage(String keyword, Integer pageSize, Integer pageNum);


    IPage getUserPage(Integer pageSize, Integer pageNum);

    UserDetails loadUserByUsername(String username);

    int updateAdmin(TAdmin admin);

    int resetPass(TAdmin admin);

    /**
     * 新增用户
     * @param admin
     * @return
     */
    int insertAdmin(TAdmin admin);

    int deleteAdmin(Long id, String username);

    int updatePass(UmsAdminUpdatePassParam admin);

    int updateRole(TAdminRole tadminrole);

    TAdminRole slecRole(Long id);
    int updateAdminFirstTime(Long id);
    int updateAdminFirstFlg(Long id);

    int updateAdminStatus(String username);

}
