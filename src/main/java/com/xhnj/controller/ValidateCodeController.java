package com.xhnj.controller;

/*
 @Description 验证码
 *@author kang.li
 *@date 2021/2/22 11:25   
 */

import com.xhnj.common.CommonResult;
import com.xhnj.util.ValidateCodeUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/validateCode")
@Slf4j
public class ValidateCodeController {
    @Autowired
    private ValidateCodeUtil validateCode;

    @ApiOperation("获取验证码图片")
    @GetMapping("/getCaptchaImg")
    public void getCaptchaImg(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expire", "0");
            response.setHeader("Pragma", "no-cache");
            // getRandomCodeImage方法会直接将生成的验证码图片写入response
            validateCode.getRandomCodeImage(request, response);
            // System.out.println("session里面存储的验证码为："+session.getAttribute("JCCODE"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("返回base64验证码")
    @GetMapping("/getCaptchaBase64")
    public CommonResult getCaptchaBase64(HttpServletRequest request, HttpServletResponse response) {
        //Map result = new HashMap();
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expire", "0");
        response.setHeader("Pragma", "no-cache");
        return CommonResult.success(validateCode.getRandomCodeBase64(request, response));
    }


    @ApiOperation("验证码校验")
    @GetMapping("/checkCaptcha")
    public boolean getCheckCaptcha(@RequestParam("code") String code, HttpSession session) {
        try {
            //toLowerCase() 不区分大小写进行验证码校验
            String sessionCode= String.valueOf(session.getAttribute("JCCODE")).toLowerCase();
            String receivedCode = code.toLowerCase();
            log.info("session里的验证码{}, 用户的验证码{}", sessionCode, receivedCode);
            return !sessionCode.equals("") && !receivedCode.equals("") && sessionCode.equals(receivedCode);
        } catch (Exception e) {
            return false;
        }
    }

}
