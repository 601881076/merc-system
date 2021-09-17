package com.xhnj.annotation;

import com.xhnj.util.FieldRepeatValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 @Description 注解接口实现类
 *@author kang.li
 *@date 2021/2/24 10:10   
 */
public class FieldRepeatValidatorClass implements ConstraintValidator<FieldRepeatValidator, Object> {
    @Autowired
    FieldRepeatValidatorUtils fieldRepeatValidatorUtils;

    private String [] fileds;
    private String message;

    @Override
    public void initialize(FieldRepeatValidator validator) {
        this.fileds = validator.fields();
        this.message = validator.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if(o == null)
            return true;
        return fieldRepeatValidatorUtils.fieldRepeat(fileds,message,o);
    }
}
