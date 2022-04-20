package com.mercsystem.annotation;

import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

/**
 * @ClassName DateTimeValidator.java
 * @Description: 时间格式校验
 * @ProjectName com.mercsystem.annotation
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 10:58
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