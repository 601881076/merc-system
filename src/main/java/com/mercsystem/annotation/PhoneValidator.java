package com.mercsystem.annotation;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @ClassName PhoneValidator.java
 * @Description: 类描述
 * @ProjectName com.mercsystem.annotation
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:00
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
