package com.mercsystem.common.proxy;

import com.mercsystem.common.exception.BusinValidateException;
import com.mercsystem.component.BusinValidatorTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName ValidatorTemplateProxy.java
 * @Description: 类描述
 * @ProjectName com.mercsystem.common.proxy
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:02
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
