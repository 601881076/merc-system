package com.mercsystem.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName FieldRepeatValidator.java
 * @Description: 校验数据库字段不能重复
 * @ProjectName com.mercsystem.annotation
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 10:58
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
