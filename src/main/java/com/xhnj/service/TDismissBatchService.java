package com.xhnj.service;

import com.xhnj.model.TDismissBatch;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 授权取消批次表 服务类
 * </p>
 *
 * @author lk
 * @since 2021-09-22
 */
public interface TDismissBatchService extends IService<TDismissBatch> {
    /**
     * 下载模板
     * @return
     */
    void exportExcel(HttpServletResponse response);


}
