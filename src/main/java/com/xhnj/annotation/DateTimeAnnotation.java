package com.xhnj.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 @Description 自定义日期格式校验
 *@author kang.li
 *@date 2021/8/9 16:48   
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeValidator.class)
public @interface DateTimeAnnotation {
    String message() default "Invalid format";

    String format() default "yyyy-MM-dd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
