package com.xhnj.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.xhnj.common.CommonResult;
import com.xhnj.component.ExcelListener;
import com.xhnj.model.ErrorCodeExcel;
import com.xhnj.pojo.vo.AgreeDismissDetailVO;
import com.xhnj.util.RedisUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 @Description 导入错误码到redis
 *@author kang.li
 *@date 2021/9/23 14:43   
 */
@RestController
@RequestMapping("test")
public class TestController {

   /* @Autowired
    private ExcelListener excelListener;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "导入excel错误码")
    @PostMapping("/excelImport")
    public CommonResult uploadExcel(@RequestParam("file") MultipartFile file){
        try {
            ExcelReader excelReader = new ExcelReader(file.getInputStream(), null, excelListener);
            //读取信息
            excelReader.read(new Sheet(1, 1, ErrorCodeExcel.class));
            //获取数据
            List<Object> list = excelListener.getDatas();
            ErrorCodeExcel vo = null;
            for (int i = 0; i < list.size(); i++) {
                vo = (ErrorCodeExcel) list.get(i);
                Map<String, Object> map = BeanUtil.beanToMap(vo);
                redisUtils.hmset(vo.getBankName()+"-"+vo.getBankRetCode(), map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.success(null);
    }*/
}
