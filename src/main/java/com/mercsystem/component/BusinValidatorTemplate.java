package com.mercsystem.component;

import com.mercsystem.common.ResultCode;
import com.mercsystem.common.exception.BusinValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName BusinValidatorTemplate.java
 * @Description: 业务校验器模板，业务校验器需继承模板类
 * @ProjectName com.mercsystem.component
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:01
*/
@Slf4j
@Component
public abstract class BusinValidatorTemplate {
    /**
     * 校验方法
     */
    public void validate() {
        try {
            validateInner();
        } catch (BusinValidateException e) {
            log.error("业务校验失败", e);
            throw e;
        } catch (Exception e) {
            log.error("业务校验异常", e);
            BusinValidateException validateException = new BusinValidateException(ResultCode.VALIDATE_FAILED);
            throw validateException;
        }
    }

    /**
     * 校验方法,由子类具体实现
     *
     * @throws BusinValidateException
     */
    public abstract void validateInner() throws BusinValidateException;
}
