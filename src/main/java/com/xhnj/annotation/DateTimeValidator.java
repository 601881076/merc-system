package com.xhnj.annotation;

import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

/*
 @Description 时间格式校验
 *@author kang.li
 *@date 2021/8/9 16:49   
 */
public class DateTimeValidator implements ConstraintValidator<DateTimeAnnotation, String> {
    private DateTimeAnnotation dateTime;

    @Override
    public void initialize(DateTimeAnnotation dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(value)) {
            return true;
        }
        String format = dateTime.format();
        if (value.length() != format.length()) {
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            simpleDateFormat.parse(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}