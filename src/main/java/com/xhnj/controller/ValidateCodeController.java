package com.xhnj.controller;

/*
 @Description 验证码
 *@author kang.li
 */
import com.xhnj.common.CommonResult;
import com.xhnj.util.ValidateCodeUtil;
import io.swagger.annotations.Api;
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

@Api(value = "验证码", tags = "验证码接口")
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
            validateCode.getRandomCodeImage(request, response);
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
            String sessionCode= String.valueOf(session.getAttribute("JCCODE")).toLowerCase();
            String receivedCode = code.toLowerCase();
            return !sessionCode.equals("") && !receivedCode.equals("") && sessionCode.equals(receivedCode);
        } catch (Exception e) {
            return false;
        }
    }

}
