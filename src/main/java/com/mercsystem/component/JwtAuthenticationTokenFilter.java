package com.mercsystem.component;

import com.mercsystem.model.TAdmin;
import com.mercsystem.pojo.bo.AdminUserDetails;
import com.mercsystem.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JwtAuthenticationTokenFilter.java
 * @Description: JWT登录授权过滤器
 * @ProjectName com.mercsystem.component
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:02
*/
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

//    @Autowired
//    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // token 置于 Authorization 里
        String requestToken = request.getHeader(tokenHeader);
        log.info("Bearer Token:{}", requestToken);

        if (requestToken != null && !"".equals(requestToken.trim())) {
            // 去掉前缀Bearer 此处通过HttpServletRequest获取的token = (数据库存储的 token + requestID) → MD5
            requestToken = requestToken.substring(tokenHead.length() + 1);

            // 对token进行验证并获取当前用户登录对象
            TAdmin admin = jwtTokenUtil.validateToken(requestToken, request, response);

            if (!ObjectUtils.isEmpty(admin)) {
                log.info("授权过滤器：授权完成！");
                AdminUserDetails userDetails = new AdminUserDetails(admin);
                // 将本次请求用户对象交给 UsernamePasswordAuthenticationToken 对象
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                // 在上下文中塞入本次请求用户对象
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

//        String authHeader = request.getHeader(this.tokenHeader);
//        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
//            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
//            String username = jwtTokenUtil.getUserNameFromToken(authToken);
//            LOGGER.info("checking username:{}", username);
//            String servletPath = request.getServletPath();
//            LOGGER.info("path: " + servletPath);
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    LOGGER.info("authenticated user:{}", username);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }
        chain.doFilter(request, response);
    }
}
