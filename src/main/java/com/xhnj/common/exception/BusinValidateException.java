package com.xhnj.common.exception;

import com.xhnj.common.ResultCode;

/*
 @Description
 *@author kang.li
 *@date 2021/8/14 12:57   
 */
public class BusinValidateException extends RuntimeException {
    // 异常码
    private long code;

    public BusinValidateException() {
    }

    public BusinValidateException(String message) {
        super(message);
    }

    public BusinValidateException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

}
