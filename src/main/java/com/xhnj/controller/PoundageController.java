package com.xhnj.controller;

import cn.hutool.json.JSONObject;
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
import java.io.IOException;
import java.io.PrintWriter;

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
        if (null == poundageParam.getStartDate() || null == poundageParam.getEndDate()) {
            try {
                ResponseInfo(response);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        poundageService.exportExcel(response,poundageParam);
    }

    @ApiOperation(value = "信息手续费PDF导出")
    @GetMapping("/pdfExport")
    public void pdfExport(HttpServletResponse response, PoundageParam poundageParam) {
        if (null == poundageParam.getStartDate() || null == poundageParam.getEndDate()) {
            try {
                ResponseInfo(response);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        log.info("信息手续费PDF导出{}", poundageParam.toString());
        poundageService.pdfExport(response,poundageParam);
    }

    /**
     * 返回错误信息
     * @param response
     */
    public void ResponseInfo(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",500);
        jsonObject.put("message", "导出失败");
        jsonObject.put("data", "请选择导出日期");

        // 获取PrintWriter对象
        PrintWriter out = response.getWriter();

        out.print(jsonObject);
        // 释放PrintWriter对象
        out.flush();
        out.close();


    }

}
