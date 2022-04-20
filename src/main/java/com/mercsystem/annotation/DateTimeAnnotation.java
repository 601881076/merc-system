package com.mercsystem.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName DateTimeAnnotation.java
 * @Description: 自定义日期格式校验 注解
 * @ProjectName com.mercsystem.annotation
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 10:58
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
