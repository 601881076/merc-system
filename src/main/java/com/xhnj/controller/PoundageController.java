package com.xhnj.controller;

import com.xhnj.pojo.query.PoundageParam;
import com.xhnj.service.PoundageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/*
 @Description 信息手续费
 *@author kang.li
 *@date 2021/9/25 20:40   
 */
@Api(value = "信息手续费", tags = "信息手续费接口")
@RestController
@RequestMapping("/poundage")
@Slf4j
public class PoundageController {
    @Autowired
    private PoundageService poundageService;

    @ApiOperation(value = "信息手续费Excel导出")
    @GetMapping("/export")
    public void excelExport(HttpServletResponse response, PoundageParam poundageParam){
        poundageService.exportExcel(response,poundageParam);
    }

    @ApiOperation(value = "信息手续费PDF导出")
    @GetMapping("/pdfExport")
    public void pdfExport(HttpServletResponse response, PoundageParam poundageParam) {
        log.info("信息手续费PDF导出{}", poundageParam.toString());
        poundageService.pdfExport(response,poundageParam);
    }

}
