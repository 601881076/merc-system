package com.xhnj.controller;

import com.xhnj.pojo.query.PoundageParam;
import com.xhnj.service.PoundageService;
import io.swagger.annotations.ApiOperation;
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
@RestController
@RequestMapping("/poundage")
public class PoundageController {
    @Autowired
    private PoundageService poundageService;

    @ApiOperation(value = "信息手续费导出")
    @GetMapping("/export")
    public void excelExport(HttpServletResponse response, PoundageParam poundageParam){
        poundageService.exportExcel(response,poundageParam);
    }

}
