package com.xhnj.annotation;

import java.lang.annotation.*;

/*
 @Description 业务校验注解
 *@author kang.li
 *@date 2021/8/14 12:50   
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinValidator {
    /**
     * 业务类型，同一个校验器可以指定多个业务类型
     *
     * @return
     */
    String[] validateTypes();

    /**
     * 执行顺序,数值越小越先执行
     *
     * @return
     */
    int validateOrder();
}
