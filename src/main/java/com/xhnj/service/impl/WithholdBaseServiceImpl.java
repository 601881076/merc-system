package com.xhnj.service.impl;

import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.service.TWithholdService;
import com.xhnj.service.WithholdBaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/9/20 18:51   
 */
@Service
public class WithholdBaseServiceImpl implements WithholdBaseService {
    @Resource
    private TWithholdService withholdService;

    @Override
    public void exportExcel(HttpServletResponse response, WithholdParam withholdParam) {
          if(withholdParam.getPayResult() == null){
              withholdService.exportExcelSuccess(response,withholdParam);
          } else if(withholdParam.getPayResult() == 3){
              withholdService.exportExcelFail(response,withholdParam);
          } else if(withholdParam.getPayResult() == 2){
              withholdService.exportExcelSuccess(response,withholdParam);
          }
    }
    @Override
    public void exportExcel(HttpServletResponse response){
        withholdService.exportExcelSuccess(response);
    }
    @Override
    public void batchExport(HttpServletResponse response, List<String> list){
        withholdService.batchExportExcelSuccess(response,list);
    }
}
