package com.mercsystem.common.exception;

import com.mercsystem.common.ResultCode;

/**
 * @ClassName BusinValidateException.java
 * @Description: 类描述
 * @ProjectName com.mercsystem.common.exception
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:02
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
