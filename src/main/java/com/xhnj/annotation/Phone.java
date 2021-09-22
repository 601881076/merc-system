package com.xhnj.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/*
 @Description
 *@author kang.li
 *@date 2021/7/7 14:44   
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
