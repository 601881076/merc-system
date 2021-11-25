package com.xhnj.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TAdminRoleMapper;
import com.xhnj.model.TAdminRole;
import com.xhnj.model.TAdminRoles;
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
    public IPage getUserPage(String keyword, Integer pageSize, Integer pageNum) {
        Page<TAdminRoles> page = new Page<>(pageNum, pageSize);
        TAdmin admin=new TAdmin();
        if(StrUtil.isNotBlank(keyword)) {
            admin.setUsername(keyword);
        }
        return adminMapper.listPage(page, admin);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String username) {
        //用户不存在则创建
        TAdmin admin = getAdminByUsername(username);
        if (admin == null) {
//            TAdmin adm = new TAdmin();
//            adm.setUsername(username);
//            adm.setPassword(passwordEncoder.encode(defaultpassword));
//            adminMapper.insert(adm);
            return null;
        }
        return new AdminUserDetails(admin);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resetPass(TAdmin admin) {
        String username = admin.getUsername();
        admin.setPassword("Passwd!23");
        admin.setFirstLoginTime(null);
        admin.setUsername(username);
        return adminMapper.updateByUsername(admin);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAdmin(TAdmin admin) {
        if(StrUtil.isNotBlank(admin.getPassword())){
            admin.setPassword(null);
        }
        return adminMapper.updateById(admin);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public int updatePass(UmsAdminUpdatePassParam adminpass) {
        log.info("修改密码："+adminpass.toString());
        TAdmin user = UserUtil.getCurrentAdminUser();
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        if(adminpass.getType()==0){
            String password = adminpass.getOpassword();
            if(userDetails == null)
                throw new UsernameNotFoundException("用户名或密码错误");
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
        }
        TAdmin admin=new TAdmin();
        admin.setUsername(user.getUsername());
        admin.setId(user.getId());
        admin.setPassword(passwordEncoder.encode(adminpass.getPassword()));
//        admin.setIsRepassword("0");//修改密码后状态改为0
        return adminMapper.updateById(admin);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(TAdminRole tadminrole) {
        log.info("tadminrole:"+tadminrole.toString());
        if(tadminrole.getUserId()==null){
            throw new BusinessException("用户id不能为空");
        }
        if(tadminrolemapper.getRole(tadminrole)>0){
            return  tadminrolemapper.updRole(tadminrole);
        }else{
            return  tadminrolemapper.insertRole(tadminrole);
        }

    }
    @Override
    public TAdminRole slecRole(Long id) {
        return  tadminrolemapper.selcRole(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAdminFirstTime(String username) {

        return adminMapper.updateAdminFirstTIme(username);
    }
}
