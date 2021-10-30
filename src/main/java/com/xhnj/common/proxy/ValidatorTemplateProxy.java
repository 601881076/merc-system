package com.xhnj.common.proxy;

import com.xhnj.common.exception.BusinValidateException;
import com.xhnj.component.BusinValidatorTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

/*
 @Description
 *@author kang.li
 *@date 2021/8/14 13:03   
 */
@Data
@AllArgsConstructor
public class ValidatorTemplateProxy extends BusinValidatorTemplate implements Comparable<ValidatorTemplateProxy> {
    private BusinValidatorTemplate validatorTemplate;
    private String validateType;
    private int validateOrder;

    @Override
    public int compareTo(ValidatorTemplateProxy o) {
        return Integer.compare(this.getValidateOrder(), o.getValidateOrder());
    }

    @Override
    public void validateInner() throws BusinValidateException {
        validatorTemplate.validateInner();
    }
}
