package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.pojo.query.AuthCancelDetailQuery;


import javax.servlet.http.HttpServletResponse;

/**
 * @program: ums-admin
 * @description: 代扣明细服务类
 * @author: xiel
 * @create: 2021-11-05 17:14
 **/
public interface AuthCancelDetailService {
    /**
     *
     * @param detailQuery
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage listPage(AuthCancelDetailQuery detailQuery, Integer pageSize, Integer pageNum);
    void exportExcel(HttpServletResponse response, AuthCancelDetailQuery tbatchDtl);
}
