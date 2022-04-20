package com.mercsystem.annotation;

import com.mercsystem.util.FieldRepeatValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @ClassName FieldRepeatValidatorClass.java
 * @Description: 注解接口实现类
 * @ProjectName com.mercsystem.annotation
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 10:58
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
