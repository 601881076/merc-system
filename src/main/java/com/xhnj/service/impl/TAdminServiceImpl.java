package com.xhnj.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TAdminRoleMapper;
import com.xhnj.model.TAdminRole;
import com.xhnj.pojo.bo.AdminUserDetails;
import com.xhnj.common.exception.BusinessException;
import com.xhnj.model.TAdmin;
import com.xhnj.mapper.TAdminMapper;
import com.xhnj.pojo.query.UmsAdminUpdatePassParam;
import com.xhnj.service.TAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.util.JwtTokenUtil;
import com.xhnj.util.RSAUtils;
import com.xhnj.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-02-24
 */
@Service
@Slf4j
public class TAdminServiceImpl extends ServiceImpl<TAdminMapper, TAdmin> implements TAdminService {
    @Resource
    private TAdminMapper adminMapper;
    @Resource
    private TAdminRoleMapper tadminrolemapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${defaultpassword}")
    private String defaultpassword;
    @Value("${rsa.privateKey}")
    private String privateKey;


    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            password = RSAUtils.decryptDataOnJava(password, privateKey);  //解密密码
            UserDetails userDetails = loadUserByUsername(username);
            if(userDetails == null)
                throw new UsernameNotFoundException("用户名或密码错误");
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
            //insertLoginLog(username);
            TAdmin user = UserUtil.getCurrentAdminUser();

            if (0 == user.getStatus())
                return "502";

            user.setLoginTime(DateUtil.date());
            adminMapper.updateById(user);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public TAdmin getAdminByUsername(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public Page getUserPage(String keyword, Integer pageSize, Integer pageNum) {
        Page<TAdmin> page = new Page<>(pageNum, pageSize);
        QueryWrapper wrapper = new QueryWrapper();
        if(StrUtil.isNotBlank(keyword)) {
            wrapper.like("name", keyword);
        }
        return adminMapper.selectPage(page, wrapper);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //用户不存在则创建
        TAdmin admin = getAdminByUsername(username);
        if (admin == null) {
            TAdmin adm = new TAdmin();
            adm.setUsername(username);
            adm.setPassword(passwordEncoder.encode(defaultpassword));
            adminMapper.insert(adm);
            return new AdminUserDetails(adm);
        }
        return new AdminUserDetails(admin);
    }

    @Override
    public int resetPass(TAdmin admin) {
        if(StrUtil.isBlank(admin.getPassword())) {
            throw new BusinessException("用户密码不能为空");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminMapper.updateById(admin);
    }

    @Override
    public int updateAdmin(TAdmin admin) {
        if(StrUtil.isNotBlank(admin.getPassword())){
            admin.setPassword(null);
        }
        return adminMapper.updateById(admin);
    }

    @Override
    public int insertAdmin(TAdmin admin) {
        if(StrUtil.isNotBlank(admin.getPassword())){
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }
        return adminMapper.insert(admin);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteAdmin(Long id) {
        if(id == null)
            throw new BusinessException("用户id不能为空");
        return adminMapper.deleteById(id);
    }

    @Override
    public int updatePass(UmsAdminUpdatePassParam adminpass) {
        log.info("修改密码："+adminpass.toString());
        UserDetails userDetails = loadUserByUsername(adminpass.getUsername());
        if(adminpass.getType()==0){
            String password = adminpass.getOpassword();
            if(userDetails == null)
                throw new UsernameNotFoundException("用户名或密码错误");
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
        }
        TAdmin admin=new TAdmin();
        admin.setUsername(adminpass.getUsername());
        admin.setId(adminpass.getId());
        admin.setPassword(passwordEncoder.encode(adminpass.getPassword()));
        return adminMapper.updateById(admin);
    }

    @Override
    public int updateRole(TAdminRole tadminrole) {
        if(tadminrole.getUserId()==null){
            throw new BusinessException("用户id不能为空");
        }
        if(tadminrolemapper.getRole(tadminrole)>0){
            return  tadminrolemapper.updRole(tadminrole);
        }else{
            return  tadminrolemapper.insertRole(tadminrole);
        }

    }
}
