package com.xhnj.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 @Description 校验数据库字段不能重复
 *@author kang.li
 *@date 2021/2/24 10:05   
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Constraint(validatedBy = FieldRepeatValidatorClass.class)
public @interface FieldRepeatValidator {
    /**
     * 需要校验的字段
     * @return
     */
    String [] fields() default {};

    String message() default "你所输入的内容已存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[]  payload() default {};
}
