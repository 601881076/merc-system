package com.xhnj.annotation;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 @Description
 *@author kang.li
 *@date 2021/7/7 14:47   
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Value("${phoneregex}")
    private String phoneregex;

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if(StrUtil.isBlank(phone))
            return false;
        return phone.matches(phoneregex);
    }
}
