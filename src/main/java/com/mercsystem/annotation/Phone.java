package com.mercsystem.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * @ClassName Phone.java
 * @Description: 类描述
 * @ProjectName com.mercsystem.annotation
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:00
*/
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {
    /**
     * 校验不通过的message
     */
    String message() default "Please enter the correct phone number";

    /**
     * 分组校验
     */
    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};

}
