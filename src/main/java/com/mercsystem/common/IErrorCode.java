package com.mercsystem.common;

/**
 * @ClassName IErrorCode.java
 * @Description: 封装错误码
 * @ProjectName com.mercsystem.common
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 10:59
*/
public interface IErrorCode {
    long getCode();

    String getMessage();
}
