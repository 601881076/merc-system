package com.xhnj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @Description:    定义一个方法级别的@log注解
* @Author:         tan_yi
* @CreateDate:     2021/11/7 16:26
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/7 16:26
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {
    /** 操作动作： 添加/修改/删除/登录、退出*/
    String operate() default "";

    /** 对象类型： 授权批次/代扣批次/菜单 ...等*/
    String objectType() default "";

    /** 操作对象名称 菜单名称*/
    String objectName() default "";

    /** 对象描述。 功能描述*/
    String descript() default "";
 }
